package com.stq.entity.stq.excel;

/**
 *	微信统计Excel导出字段 
 *
 */
public class WeixinStatsExcel {

	private String name;
	
	private String startDate;
	
	private String endDate;
	
	private String volume;
	
	private String actualVolume;
	
	private String coefficient;
	
	private String readCount;
	
	private String upCount;
	
	private String positiveSentiment;
	
	private String neutralSentiment;
	
	private String negativeSentiment;
	
	private String reputationValue;
	
	private String countPercentage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}
	
	public String getActualVolume() {
		return actualVolume;
	}

	public void setActualVolume(String actualVolume) {
		this.actualVolume = actualVolume;
	}

	public String getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(String coefficient) {
		this.coefficient = coefficient;
	}

	public String getReadCount() {
		return readCount;
	}

	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}

	public String getUpCount() {
		return upCount;
	}

	public void setUpCount(String upCount) {
		this.upCount = upCount;
	}

	public String getPositiveSentiment() {
		return positiveSentiment;
	}

	public void setPositiveSentiment(String positiveSentiment) {
		this.positiveSentiment = positiveSentiment;
	}

	public String getNeutralSentiment() {
		return neutralSentiment;
	}

	public void setNeutralSentiment(String neutralSentiment) {
		this.neutralSentiment = neutralSentiment;
	}

	public String getNegativeSentiment() {
		return negativeSentiment;
	}

	public void setNegativeSentiment(String negativeSentiment) {
		this.negativeSentiment = negativeSentiment;
	}

	public String getReputationValue() {
		return reputationValue;
	}

	public void setReputationValue(String reputationValue) {
		this.reputationValue = reputationValue;
	}

	public String getCountPercentage() {
		return countPercentage;
	}

	public void setCountPercentage(String countPercentage) {
		this.countPercentage = countPercentage;
	}
}
