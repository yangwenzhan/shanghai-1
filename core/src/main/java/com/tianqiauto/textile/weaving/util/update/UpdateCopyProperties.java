package com.tianqiauto.textile.weaving.util.update;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName UpdateCopyProperties
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-03-28 17:07
 * @Version 1.0
 **/
public class UpdateCopyProperties {


    public static void copyProperties(Object src, Object trg, Iterable<String> props) {

        BeanWrapper srcWrap = PropertyAccessorFactory.forBeanPropertyAccess(src);
        BeanWrapper trgWrap = PropertyAccessorFactory.forBeanPropertyAccess(trg);

        props.forEach(p -> trgWrap.setPropertyValue(p, srcWrap.getPropertyValue(p)));

    }

}
