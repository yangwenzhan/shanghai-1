package com.tianqiauto.textile.weaving.util.JPASql;

import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.model.sys.Heyuehao;
import com.tianqiauto.textile.weaving.model.sys.Order;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 动态更新sql
 *
 * @Author bjw
 * @Date 2019/3/18 14:29
 */
@Slf4j
public class DynamicUpdateSQL<T> {

    private String table_name;

    private Param id;

    private List<Param> params;

    private DynamicUpdateSQL() {
        params = new ArrayList<>();
    }

    public DynamicUpdateSQL(T entity) {
        this();//初始化
        Class clzz = entity.getClass();
        setTable_name(clzz); //初始化表名
        Field[] fields = clzz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Id.class)) {  //如果是id
                setId(field, entity);                   //初始化id
            } else if (field.isAnnotationPresent(JoinColumn.class) && (field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToOne.class))) {
                addJoinColumn(field, entity);           //设置对象关联字段
            } else {
                addPara(field, entity);
            }
        }
    }

    /**
     * 设置表名
     *
     * @Author bjw
     * @Date 2019/3/20 16:37
     **/
    private void setTable_name(Class clzz) {
        Entity entityAnnotation = (Entity) clzz.getAnnotation(Entity.class);
        this.table_name = entityAnnotation.name();
        if (StringUtils.isEmpty(this.table_name)) {
            this.table_name = camelToline(clzz.getSimpleName());
        }
    }

    /**
     * 添加普通参数
     * @Author bjw
     * @Date 2019/3/20 17:08
     **/
    private void addPara(Field field, T entity) {
        String name = camelToline(field.getName());
        Param param = new Param();
        param.setName(name);
        try {
            param.setValue(field.get(entity));
            params.add(param);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加JoinColumn参数
     * @Author bjw
     * @Date 2019/3/20 17:09
     **/
    private void addJoinColumn(Field field, T entity) {
        Param param = new Param();
        String joinColumnName = field.getAnnotation(JoinColumn.class).name();
        param.setName(joinColumnName);
        //设置子对象的关联id字段。
        Object obj = null;
        try {
            obj = field.get(entity);
            if (null != obj) {
                Field[] fields2 = obj.getClass().getDeclaredFields();
                for (Field field2 : fields2) {
                    field2.setAccessible(true);
                    if (field2.isAnnotationPresent(Id.class)) {//如果是id
                        param.setValue(field2.get(obj));
                    }
                }
            }
            params.add(param);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置id属性
     *
     * @Author bjw
     * @Date 2019/3/20 16:38
     **/
    private void setId(Field field, T entity) {
        Param param = new Param();
        param.setName(camelToline(field.getName()));
        try {
            param.setValue(field.get(entity));
            this.id = param;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态拼接SQL
     * @Author bjw
     * @Date 2019/3/20 17:12
     **/
    public Container getUpdateSql() {
        Container container = new Container();
        if (null == id.getValue()) {
            throw new RuntimeException("更新表["+table_name+"]中的关联id为NULL！");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(this.table_name).append(" SET ");
        StringBuilder sbTemp = new StringBuilder();
        for (Param param : params) {
            if (null != param.getValue()) {
                sbTemp.append(param.getName()).append("=?, ");
                container.setParamList(param.getValue());
            }
        }
        String temp = sbTemp.substring(0, sbTemp.length() - 2);
        sb.append(temp);
        sb.append(" WHERE ");
        sb.append(id.getName()).append("=? ");
        container.setParamList(id.getValue());
        container.setSql(sb.toString());
        return container;
    }

    /**
     * 驼峰转换成下划线
     *
     * @Author bjw
     * @Date 2019/3/19 11:27
     **/
    private String camelToline(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }

    @Data
    public class Param {
        private String name;
        private Object value;
    }

    public static void main(String[] args) {
        Order order = new Order();
        order.setId(132L);

        order.setBeizhu(null);

        User user = new User();
        user.setId(123L);
        order.setJingli(user);


        Container RUSql2 = new DynamicUpdateSQL<>(order).getUpdateSql();
//        return jdbcTemplate.update(RUSql2.getSql(),RUSql2.getParam());
        System.out.println(RUSql2.getSql());
        System.out.println(RUSql2.getParam());

    }

}
