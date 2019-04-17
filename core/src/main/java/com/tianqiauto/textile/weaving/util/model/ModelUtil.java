package com.tianqiauto.textile.weaving.util.model;

import org.assertj.core.util.Lists;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author bjw
 * @Date 2019/4/1 12:55
 */
public class ModelUtil<T>{


    private Object obj;

    private Root<T> root;

    List<Predicate> andPredicates;

    private ModelUtil(){
        this.andPredicates = Lists.newArrayList();
        this.paramMap = new HashMap<>();
    }

    public ModelUtil(Object obj,Root<T> root){
        this();
        this.obj = obj;
        this.root = root;
    }

    public ModelUtil(Object obj){
        this();
        this.obj = obj;
    }

    /**
     *  判断对象重的属性是否为空。
     * @Author bjw
     * @Date 2019/4/1 12:59
     **/
    public boolean paramIsEmpty(String paramReference){
        if(StringUtils.isEmpty(paramReference)){
            throw new RuntimeException("匹配参数不能为空");
        }
        String[] arr = paramReference.split("\\.");
        Object currentObject = obj;
        int arrEnd = arr.length-1;
        for(int i=0; i<=arrEnd; i++){
            try {
                Field field = currentObject.getClass().getDeclaredField(arr[i]);
                field.setAccessible(true);
                //if(i==arrEnd) return StringUtils.isEmpty(field.get(currentObject));
                //else{
                    Object temp = field.get(currentObject);
                    if(StringUtils.isEmpty(temp)){
                        return true;
                    }else{
                        currentObject = temp;
                    }
                //}
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    /**
     * 获取实体对应字段的值
     * @Author bjw
     * @Date 2019/4/16 19:16
     **/
    public Path<Object> getRoot(String paramReference){
        if(StringUtils.isEmpty(paramReference)){
            throw new RuntimeException("匹配参数不能为空");
        }
        String[] arr = paramReference.split("\\.");
        Path<Object> name = root.get(arr[0]);
        for(int i=1; i<=arr.length-1; i++){
            name = name.get(arr[i]);
        }
        return name;
    }

    /**
     * 根据字符串获取对象
     * @Author bjw
     * @Date 2019/4/16 20:16
     **/
    public Object getValue(String paramReference){
        if(StringUtils.isEmpty(paramReference)){
            throw new RuntimeException("匹配参数不能为空");
        }
        String[] arr = paramReference.split("\\.");
        Object currentObject = obj;
        int arrEnd = arr.length-1;
        for(int i=0; i<=arrEnd; i++){
            try {
                Field field = currentObject.getClass().getDeclaredField(arr[i]);
                field.setAccessible(true);
                Object temp = field.get(currentObject);
                currentObject = temp;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return currentObject;
    }


    /**
     * 添加封装Predicate
     * @Author bjw
     * @Date 2019/4/17 15:09
     **/
    public void addPred(Predicate pred){
        andPredicates.add(pred);
    }

    /**
     * 获取数组
     * @Author bjw
     * @Date 2019/4/17 15:13
     **/
    public Predicate[] getPred(){
        Predicate[] array = new Predicate[andPredicates.size()];
        return andPredicates.toArray(array);
    }

    //-----------------------------------------------第三种封装方式
    private Map<String,Param> paramMap;
    public Param initParam(String paramReference){
        if(StringUtils.isEmpty(paramReference)){
            throw new RuntimeException("匹配参数不能为空");
        }
        //如果map中有，不需要再次解析，提升效率
        Param param = paramMap.get(paramReference);
        if (null != param){
            return param;
        }
        String[] arr = paramReference.split("\\.");
        Path<Object> path = root.get(arr[0]);//第一次的数据库字段地址匹配
        Object currentObject = obj;         //对象
        Boolean isEmpty = false;             //对象默认为不是
        int arrEnd = arr.length-1;
        for(int i=1; i<=arrEnd; i++){
            path = path.get(arr[i]);
        }
        for(int i=0; i<=arrEnd; i++){
            try {
                Field field = currentObject.getClass().getDeclaredField(arr[i]);
                field.setAccessible(true);
                Object temp = field.get(currentObject);
                if(StringUtils.isEmpty(temp)){
                    currentObject = temp;
                    isEmpty = true;
                    break;
                }else{
                    currentObject = temp;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        Param p = new Param(isEmpty,currentObject,path);
        paramMap.put(paramReference,p);
        return p;
    }

}
