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
 *	excel上传的任务
 */
@Entity
@Table(name = "stq_stats_task")
public class Task extends BaseEntry{
	
	/**
	 * 有效
	 */
	public static final String STATUS_VALIDE = "1";    
	/**
	 * 无效
	 */
	public static final String STATUS_INVALIDE = "0";
	
	/**
	 * 任务没有开始执行
	 */
	public static final String TASK_START = "0";
	
	/**
	 * 任务执行进行中
	 */
	public static final String TASK_ING = "1";
	
	/**
	 * 任务执行完成
	 */
	public static final String TASK_END = "2";
	
	/**
	 * 任务执行失败
	 */
	public static final String TASK_OVER = "3";
	
	/**
	 * 任务分类 微博统计
	 */
	public static final String TASK_TYPE_WEIBO_STATS = "1";
	
	/**
	 * 任务分类 微信统计
	 */
	public static final String TASK_TYPE_WEIXIN_STATS = "2";
	/**
	 * 任务分类  微博导出
	 */
	public static final String TASK_TYPE_WEIBO_EXPORT = "3";
	/**
	 * 任务分类 微信导出
	 */
	public static final String TASK_TYPE_WEIXIN_EXPORT = "4";
	
	/**
	 * 任务名称
	 */
	public String taskName;
	
	/**
	 * 任务分类
	 */
	public String taskType;
	
	/**
	 * 任务状态，查看此条任务是否开始执行
	 */
	public String taskStatus;
	
	/**
	 * Excel 下载地址 
	 */
	public String ExcelPath;
	
	/**
	 * 保存需要导出的数据指标（1,2,3...），如：声量、曝光量等
	 */
	public String dataIndex;
	
	public String dataIndexName;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getExcelPath() {
		return ExcelPath;
	}

	public void setExcelPath(String excelPath) {
		ExcelPath = excelPath;
	}

	public String getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}
	
	public String getDataIndexName() {
		return dataIndexName;
	}

	public void setDataIndexName(String dataIndexName) {
		this.dataIndexName = dataIndexName;
	}

	@Transient
	@JsonIgnore
	public List<String> getDataIndexList() {
		// 角色列表在数据库中实际以逗号分隔字符串存储，因此返回不能修改的List.
		return ImmutableList.copyOf(StringUtils.split(dataIndex, ","));
	}
	
	@Transient
	@JsonIgnore
	public List<String> getDataIndexNameList() {
		// 角色列表在数据库中实际以逗号分隔字符串存储，因此返回不能修改的List.
		return ImmutableList.copyOf(StringUtils.split(dataIndexName, ","));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
