package com.stq.entity.stq;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import com.stq.entity.BaseEntry;
/**
 * 微博统计
 *
 */
@Entity
@Table(name = "stq_weibostats")
public class WeiboStats extends BaseEntry{
	
	/**
	 * 有情感分析
	 */
	public static final String TASK_SENTIMENT_YES = "1";
	
	/**
	 * 没有情感分析
	 */
	public static final String TASK_SENTIMENT_NO = "0";
	
	/**
	 * 有效
	 */
	public static final String STATUS_VALIDE = "1";
	/**
	 * 无效
	 */
	public static final String STATUS_INVALIDE = "0";
	
	/**
	 * 分天
	 */
	public static final String STATUS_SUBDATA = "1";
	/**
	 * 不分田
	 */
	public static final String STATUS_NOSUBDATA = "0";
	
	/**
	 * 任务执行完成
	 */
	public static final String TASK_OVER = "3";
	
	/**
	 * 任务执行完成
	 */
	public static final String TASK_END = "2";
	
	/**
	 * 任务执行进行中
	 */
	public static final String TASK_ING = "1";
	
	/**
	 * 任务没有开始执行
	 */
	public static final String TASK_START = "0";
	
	
	/**
	 * 监测项目
	 */
	public String monitorItem;
	
	/**
	 * 起始时间（含）
	 */
	public String startDate;
	
	/**
	 * 结束时间（含）
	 */
	public String endDate;
	
	/**
	 * 时间分段
	 */
	public String subDate;
	
	/**
	 * 情感分析
	 */
	public String sentiment;
	
	/**
	 * 导出类型
	 */
	public String taskType;
	
	/**
	 * 查询关键字
	 */
	public String keyWord;
	
	/**
	 * 任务状态，查看此条任务是否开始执行
	 */
	public String taskStatus;
	
	/**
	 * 任务ID，关联task类
	 */
	public Long taskId;

	public String getMonitorItem() {
		return monitorItem;
	}

	public void setMonitorItem(String monitorItem) {
		this.monitorItem = monitorItem;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSubDate() {
		return subDate;
	}

	public void setSubDate(String subDate) {
		this.subDate = subDate;
	}

	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	@Transient
	@JsonIgnore
	public List<String> getKeyWordList() {
		// 角色列表在数据库中实际以逗号分隔字符串存储，因此返回不能修改的List.
		return ImmutableList.copyOf(StringUtils.split(keyWord, "||"));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}


}
