package com.stq.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 操作日志
 */
@Entity
@Table(name = "stq_log")
public class Log extends BaseEntry{
	
	/**
	 * 查询Mongo指标
	 */
	public static final String STQ_TYPE_DATA_MONGO = "stq_mongo";
	
	/**
	 * 访问微博数据统计任务页面
	 */
	public static final String STQ_TYPE_DATA_WEIBO_STATS = "stq_weibo_stats";
	
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 创建人
	 */
	private String crUser;
	
	
	private String type;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCrUser() {
		return crUser;
	}
	public void setCrUser(String crUser) {
		this.crUser = crUser;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
