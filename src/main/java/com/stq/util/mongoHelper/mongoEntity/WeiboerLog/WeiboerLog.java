package com.stq.util.mongoHelper.mongoEntity.WeiboerLog;

import com.stq.util.mongoHelper.CommonObject;
import org.joda.time.LocalDate;

import java.util.Date;


/**
 * Created by dazongshi on 17-11-1.
 */
public class WeiboerLog extends CommonObject {
    private String weiboer_id;
    private Long fans_count;
    private Long follower_count;
    private Long weibo_count;
    private Date crawled_at;

    public String getWeiboer_id() {
        return weiboer_id;
    }

    public void setWeiboer_id(String weiboer_id) {
        this.weiboer_id = weiboer_id;
    }

    public Long getFans_count() {
        return fans_count;
    }

    public void setFans_count(Long fans_count) {
        this.fans_count = fans_count;
    }

    public Long getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(Long follower_count) {
        this.follower_count = follower_count;
    }

    public Long getWeibo_count() {
        return weibo_count;
    }

    public void setWeibo_count(Long weibo_count) {
        this.weibo_count = weibo_count;
    }

    public Date getCrawled_at() {
        return crawled_at;
    }

    public void setCrawled_at(Date crawled_at) {
        this.crawled_at = crawled_at;
    }
}
