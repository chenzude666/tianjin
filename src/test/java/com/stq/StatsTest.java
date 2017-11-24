package com.stq;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.ImmutableList;
import com.stq.entity.stq.Task;
import com.stq.entity.stq.WeiboStats;
import com.stq.entity.stq.WeixinStats;
import com.stq.service.stq.TaskService;
import com.stq.service.stq.words.weibo.WeiboBaseService;
import com.stq.service.stq.words.weibo.WeiboStatsService;
import com.stq.service.stq.words.weixin.WeixinStatsService;
import com.stq.util.ESRestClientHelper;
import com.stq.util.JTime;
import com.sun.el.parser.ParseException;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class StatsTest extends SpringTransactionalTestCase{
	
	private static final Logger log = LoggerFactory.getLogger(StatsTest.class);
	
	@Autowired
	private WeiboStatsService weiboStatsService; 
	
	@Autowired
	private WeixinStatsService weixinStatsService;
	
	public static final String weixinTaskType = "2";
	
	
	@Autowired
	private TaskService taskService;
	
	//@Test
	@Rollback(false)
	public void test() throws IOException, ParseException, java.text.ParseException {	
		log.debug("测试1");
		WeiboStats weibo = new WeiboStats();
		
		weibo.setMonitorItem("明星大侦探");
		weibo.setStartDate("2017-01-10");
		weibo.setEndDate("2017-04-21");
		weibo.setSubDate("0");
		weibo.setSentiment("1");
		weibo.setTaskType("1");
		weibo.setKeyWord("明星大侦探,胜利的游戏");
		weibo.setTaskStatus("0");
		weibo.setTaskId(1L);
		
		
		WeixinStats weixin = new WeixinStats();
		//weixin.setCategory("综艺");
		weixin.setMonitorItem("明星大侦探");
		weixin.setStartDate("2017-01-10");
		weixin.setEndDate("2017-04-21");
		weixin.setSubDate("0");
		weixin.setSentiment("1");
		weixin.setTaskType("2");
		weixin.setKeyWord("明星大侦探,胜利的游戏");
		weixin.setTaskStatus("0");
		weixin.setTaskId(2L);
		
		weiboStatsService.add(weibo);
		weixinStatsService.add(weixin);
	}
	
	//@Test
	public void test1() throws IOException, ParseException, java.text.ParseException {	
		log.debug("测试2");
		WeiboStats weiboStats =  weiboStatsService.findById(8L);
		String query = weiboStats.getKeyWord()
				.replaceAll("\\|", " OR ")
				.replaceAll("\\+", "\\\\\\\\\\\\\\\" AND \\\\\\\\\\\\\\\"")//英文字符
				.replaceAll("\\-", "\\\\\\\\\\\\\\\" AND NOT \\\\\\\\\\\\\\\"")//英文字符
				.replaceAll("\\(", "(\\\\\\\\\\\\\\\"")//英文字符
				.replaceAll("\\)", "\\\\\\\\\\\\\\\")")//英文字符
				.replaceAll("\\（", "(\\\\\\\\\\\\\\\"")//中文字符
				.replaceAll("\\）", "\\\\\\\\\\\\\\\")")//中文字符
				;
		//测试 (三少爷的剑-电影)|(三少爷的剑+林更新)|(三少爷的剑+何润东)|(三少爷的剑+江一燕)|(三少爷的剑+蒋梦婕)
		//测试 (\\\"三少爷的剑\\\" AND NOT \\\"电影\\\") OR (\\\"三少爷的剑\\\" AND \\\"林更新\\\") OR (\\\"三少爷的剑\\\" AND \\\"何润东\\\") OR (\\\"三少爷的剑\\\" AND \\\"江一燕\\\") OR (\\\"三少爷的剑\\\" AND \\\"蒋梦婕\\\")
		log.debug("测试 " + weiboStats.getKeyWord());
		log.debug("测试 " + query);
	}
	
	//@Test
	public void test2() throws IOException, ParseException, java.text.ParseException {	
		String keywords = "三少爷的剑+电影+-测试1+-测试2+测试3+-测试4+中文加+-中文减||三少爷的剑+林更新||三少爷的剑+何润东||三少爷的剑+江一燕||三少爷的剑+蒋梦婕";
		
		StringBuilder strBool = new StringBuilder();
		List<String> kwords = ImmutableList.copyOf(StringUtils.split(keywords, "||"));
		for (int j = 0; j < kwords.size(); j++) {
			strBool.append("{\"bool\":{");
			List<String> ksReduces = ImmutableList.copyOf(StringUtils.split(kwords.get(j), "+"));
			StringBuilder strMustNot = new StringBuilder();
			StringBuilder strMust = new StringBuilder();
			strMustNot.append("\"must_not\":[");
			strMust.append("\"must\":[");
			List<String> listMustNot = new ArrayList<>();	
			List<String> listMust = new ArrayList<>();
			for (String string : ksReduces) {
				if(string.startsWith("-")||string.startsWith("-")){
					listMustNot.add(string);
					
				}else{
					listMust.add(string);
				}
			}
			for (int i = 0; i < listMustNot.size(); i++) {
				strMustNot.append("{\"match_phrase\":{\"content_full\":\""+listMustNot.get(i).substring(1, listMustNot.get(i).length())+"\"}}");
				if(i+1!=listMustNot.size()){
					strMustNot.append(",");
				}
			}
			for (int i = 0; i < listMust.size(); i++) {
				strMust.append("{\"match_phrase\":{\"content_full\":\""+listMust.get(i)+"\"}}");
				if(i+1!=listMust.size()){
					strMust.append(",");
				}
			}
			strMustNot.append("]");
			strMust.append("]");
			
			strBool.append(strMust).append(",").append(strMustNot);
			strBool.append("}}");
			if(j+1!=kwords.size()){
				strBool.append(",");
			}
		}
		System.out.println("1111  "  + strBool);
		
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		HttpEntity entity = new NStringEntity(""
				+ "{"
				+ 	"\"query\": {"
				+ 		"\"bool\": {"
				+ 			"\"filter\": [{"
				+			 	"\"range\": {"
				+ 					"\"published_at\": {"
				+ 						"\"gte\": \"2016-12-09 00:00:00\","
				+ 						"\"lte\": \"2016-12-10 00:00:00\","
				+                       "\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\","
				+ 						"\"time_zone\": \"Asia/Shanghai\""
				+ 					"}"
				+ 				"}"
				+ 			"}],"
				+          "\"should\": ["
				+ 		   ""+strBool.toString()+""
				+          "],"
				+ 		   "\"minimum_should_match\": \"1\""
				+ 		"}"
				+ 	"},"
				+ 	"\"size\": 0"
				+ "}", ContentType.APPLICATION_JSON);
    	Response indexResponse = restClient.performRequest(
    		    "GET",
    		    "/weibo_articles_and_weiboers/weibo_articles_and_weiboer/_search",
    		    //Collections.<String, String>emptyMap(),
    		    Collections.singletonMap("pretty", "true"),
    		    entity);
    	String json = EntityUtils.toString(indexResponse.getEntity());
    	System.out.println(json);
    	
    	JSON.parseObject(json).get("hits");
    	System.out.println("222222  "   +  JSON.parseObject(JSON.parseObject(json).get("hits").toString()).get("total"));
    	
		Map<String, Object> userMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
		System.out.println("rrrrrrrrrr  " + JSON.parseObject(JSON.parseObject(userMap.get("aggregations").toString()).getString("sdf")).getString("buckets"));
		String list = JSON.parseObject(JSON.parseObject(userMap.get("aggregations").toString()).getString("sdf")).getString("buckets");
		JSON.parseArray(list);
    	
	}
	
	//@Test
	public void test3() throws IOException, ParseException, java.text.ParseException {	
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		HttpEntity entity = new NStringEntity(""
				+ "{"
				+ 	"\"query\": {"
				+ 		"\"bool\": {"
				+ 			"\"filter\": [{"
				+			 	"\"range\": {"
				+ 					"\"published_at\": {"
				+ 						"\"gte\": \"2016-12-09 00:00:00\","
				+ 						"\"lte\": \"2016-12-10 00:00:00\","
				+                       "\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\","
				+ 						"\"time_zone\": \"Asia/Shanghai\""
				+ 					"}"
				+ 				"}"
				+ 			"}]"
				+ 		"}"
				+ 	"},"
				+ 	"\"size\": 0"
				+ "}", ContentType.APPLICATION_JSON);
    	Response indexResponse = restClient.performRequest(
    		    "GET",
    		    "/weibo_articles_and_weiboers/weibo_articles_and_weiboer/_search",
    		    //Collections.<String, String>emptyMap(),
    		    Collections.singletonMap("pretty", "true"),
    		    entity);
    	String json = EntityUtils.toString(indexResponse.getEntity());
    	System.out.println(JSON.parseObject(JSON.parseObject(json).get("hits").toString()).get("total").toString());
    	
    	
    	
    	
    	
		//es当天文章总量
		double a = Double.parseDouble(JSON.parseObject(JSON.parseObject(json).get("hits").toString()).get("total").toString());
		//微博实际当天文章总量（根据系数调整）
		String startDate = "2016-12-09 00:00:00";
/*		System.out.println("星期："+JTime.getWeekOfDate(JTime.esFormat.parse(startDate)));
		
		System.out.println("000  "  + WeiboBaseService.baseRatio.get(Integer.valueOf(JTime.getWeekOfDate(new Date()))-1));
		double b = 1.5 * WeiboBaseService.baseRatio.get(Integer.valueOf(JTime.getWeekOfDate(JTime.esFormat.parse(startDate)))-1);
		
		System.out.println("11111  "  + a + "  "  +b );
		if(a < b){
			
		}else{
			
		}*/
    	
	}
	
	@Test
	public void test4() throws IOException, ParseException, java.text.ParseException {	
		String keywords = "哈利波特二十周年+网易新闻||哈利波特20周年+网易新闻";
		
		StringBuilder strBool = new StringBuilder();
		List<String> kwords = ImmutableList.copyOf(StringUtils.split(keywords, "||"));
		for (int j = 0; j < kwords.size(); j++) {
			strBool.append("{\"bool\":{");
			List<String> ksReduces = ImmutableList.copyOf(StringUtils.split(kwords.get(j), "+"));
			StringBuilder strMustNot = new StringBuilder();
			StringBuilder strMust = new StringBuilder();
			strMustNot.append("\"must_not\":[");
			strMust.append("\"must\":[");
			List<String> listMustNot = new ArrayList<>();	
			List<String> listMust = new ArrayList<>();
			for (String string : ksReduces) {
				if(string.startsWith("-")||string.startsWith("-")){
					listMustNot.add(string);
					
				}else{
					listMust.add(string);
				}
			}
			for (int i = 0; i < listMustNot.size(); i++) {
				strMustNot.append("{\"match_phrase\":{\"content_full\":\""+listMustNot.get(i).substring(1, listMustNot.get(i).length())+"\"}}");
				if(i+1!=listMustNot.size()){
					strMustNot.append(",");
				}
			}
			for (int i = 0; i < listMust.size(); i++) {
				strMust.append("{\"match_phrase\":{\"content_full\":\""+listMust.get(i)+"\"}}");
				if(i+1!=listMust.size()){
					strMust.append(",");
				}
			}
			strMustNot.append("]");
			strMust.append("]");
			
			strBool.append(strMust).append(",").append(strMustNot);
			strBool.append("}}");
			if(j+1!=kwords.size()){
				strBool.append(",");
			}
		}
		System.out.println("1111  "  + strBool);
	}
	
}
