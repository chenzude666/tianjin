package com.stq.util.mongoHelper.mongoEntity.CTimeHelper;

import com.stq.util.mongoHelper.CommonObject;

import java.util.Date;
import java.util.Map;

/**
 * Created by dazongshi on 17-10-20.
 */
public class CTimeHelper extends CommonObject {
    private String source_type;
    private Date created_at;
    private Date start_time;

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }
}
