package com.raddle.crud.web.toolbox;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * description: 
 * @author xurong
 * time : 2016年6月14日 上午9:16:25
 */
public class SqlUtils {
    public static boolean isEmpty(Object obj) {
        return !isNotEmpty(obj);
    }

    public static boolean isNotEmpty(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            return StringUtils.isNotEmpty((String) obj);
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).size() > 0;
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).size() > 0;
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) > 0;
        }
        return true;
    }

    /**
     * 拼出in的sql
     * @return
     */
    public static String joinIn(List<Map<String, Object>> list, String columnName) {
        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException("joinIn list is empty");
        }
        if (columnName == null) {
            throw new IllegalArgumentException("joinIn columnName is empty");
        }
        List<Object> ins = new ArrayList<Object>();
        for (Map<String, Object> map : list) {
            Object o = map.get(columnName);
            if (o != null) {
                ins.add(o);
            }
        }
        if (ins.size() == 0) {
            throw new IllegalArgumentException("joinIn column:" + columnName + " all values is null");
        }
        if (ins.get(0) instanceof String) {
            return "'" + StringUtils.join(ins, "','") + "'";
        } else if (ins.get(0) instanceof Number) {
            return StringUtils.join(ins, ",");
        } else {
            throw new IllegalArgumentException("joinIn unsupport type :" + ins.get(0).getClass().getSimpleName());
        }
    }
}
