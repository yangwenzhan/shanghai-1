package com.tianqiauto.textile.weaving.util;

import com.tianqiauto.textile.weaving.model.sys.YuanSha;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @Author bjw
 * @Date 2019/4/1 12:55
 */
public class ModelUtil{


    private Object obj;

    public ModelUtil(Object obj){
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
                Class c = currentObject.getClass();
                Field field = currentObject.getClass().getDeclaredField(arr[i]);
                field.setAccessible(true);
                if(i==arrEnd) return StringUtils.isEmpty(field.get(currentObject));
                else{
                    Object temp = field.get(currentObject);
                    if(StringUtils.isEmpty(temp)){
                        return true;
                    }else{
                        currentObject = temp;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
