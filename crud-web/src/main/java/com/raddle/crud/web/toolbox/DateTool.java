package com.raddle.crud.web.toolbox;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 类DateTools.java的实现描述：日期操作
 * @author raddle60 2013-3-4 下午9:59:41
 */
public class DateTool {

    private static final Date staticDate = new Date();

    public String getStaticDateStr() {
        return DateFormatUtils.format(staticDate, "yyyyMMddHHmmss");
    }

    public String getCurDateStr() {
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
    }

    public String getCurDateStrByFormat(String format) {
        return DateFormatUtils.format(new Date(), format);
    }
}
