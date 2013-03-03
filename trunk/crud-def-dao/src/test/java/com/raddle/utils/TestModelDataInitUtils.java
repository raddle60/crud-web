package com.raddle.utils;

import java.beans.PropertyDescriptor;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * 设置null的默认的值，保证每个字段都执行了sql语句
 *
 * @author xurong
 *
 */
public class TestModelDataInitUtils {

    public static void initData(Object bean) {
        for (PropertyDescriptor propertyDescriptor : PropertyUtils.getPropertyDescriptors(bean.getClass())) {
            if (propertyDescriptor.getReadMethod() != null && propertyDescriptor.getWriteMethod() != null) {
                try {
                    Object value = PropertyUtils.getProperty(bean, propertyDescriptor.getName());
                    Class<?> propType = propertyDescriptor.getPropertyType();
                    if (value == null) {
                        if (propType.isAssignableFrom(Date.class)) {
                            PropertyUtils.setProperty(bean, propertyDescriptor.getName(), new Date());
                        } else if (propType.isAssignableFrom(Boolean.class)) {
                            PropertyUtils.setProperty(bean, propertyDescriptor.getName(), Boolean.FALSE);
                        } else {
                            BeanUtils.setProperty(bean, propertyDescriptor.getName(), "1");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
