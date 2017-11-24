package com.stq.util.mongoHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;


/**
 * 用来维护mongo表结构
 * Created by dazongshi on 17-9-8.
 */

//schema :keyword ,st, et, source, data_type,value
public class CommonSchema extends CommonObject {
    protected String keyword = null;
    protected Date st = new Date(0);
    protected Date et = new Date(0);
    protected String source = null;
    protected String data_type = null;
    protected double value = 0;

    public CommonSchema() {
    }

    public CommonSchema(String keyword, Date st, Date et, String source, String data_type, double value) {
        this.keyword = keyword;
        this.st = st;
        this.et = et;
        this.source = source;
        this.data_type = data_type;
        this.value = value;
    }

    public CommonSchema(String keyword, Date st, Date et, String source) {
        this.keyword = keyword;
        this.st = st;
        this.et = et;
        this.source = source;
        this.data_type = data_type;
        this.value = value;
    }
}

