package com.stq.util.mongoHelper.mongoEntity.Weiboer;

import com.stq.util.mongoHelper.CommonObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dazongshi on 17-11-1.
 */
public class Weiboer extends CommonObject {
    private String _id;
    private String username;
    private String name;
    private String intro;
    private Long gender;
    private Long vip;
    private Long verify;
    private Long fans_count;
    private Long follower_count;
    private Long weibo_count;
    private Long level;
    private Date last_weibo_at;
    private String info;
    private String location_str;
    private String edu_str;
    private String birthday_str;
    private String company;
    private String sexual;
    private String relationship;
    private String industry_str;
    private ArrayList<Object> tags;

    public String getIndustry_str() {
        return industry_str;
    }

    public void setIndustry_str(String industry_str) {
        this.industry_str = industry_str;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Long getGender() {
        return gender;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    public Long getVip() {
        return vip;
    }

    public void setVip(Long vip) {
        this.vip = vip;
    }

    public Long getVerify() {
        return verify;
    }

    public void setVerify(Long verify) {
        this.verify = verify;
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

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Date getLast_weibo_at() {
        return last_weibo_at;
    }

    public void setLast_weibo_at(Date last_weibo_at) {
        this.last_weibo_at = last_weibo_at;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLocation_str() {
        return location_str;
    }

    public void setLocation_str(String location_str) {
        this.location_str = location_str;
    }

    public String getEdu_str() {
        return edu_str;
    }

    public void setEdu_str(String edu_str) {
        this.edu_str = edu_str;
    }

    public String getBirthday_str() {
        return birthday_str;
    }

    public void setBirthday_str(String birthday_str) {
        this.birthday_str = birthday_str;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSexual() {
        return sexual;
    }

    public void setSexual(String sexual) {
        this.sexual = sexual;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public ArrayList<Object> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Object> tags) {
        this.tags = tags;
    }
}
