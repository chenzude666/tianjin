package com.stq.test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.stq.entity.stq.PaWeixin;
import com.stq.util.ESClientHelper;
import com.stq.util.ESRestClientHelper;
import com.stq.util.ExcelUtil;
import com.stq.util.ExcelUtil.ExcelExportData;
import com.stq.util.FileUtil;
import com.stq.util.JTime;
import com.sun.el.parser.ParseException;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class EsTest3 extends SpringTransactionalTestCase{
	
	private static final Logger log = LoggerFactory.getLogger(EsTest3.class);
	
	
	//@Test
	public void test() throws IOException, ParseException {	
		log.debug("测试1");
		Client client = ESClientHelper.getInstance().getClient();
		System.out.println(client);
	    SearchResponse response = client.prepareSearch("test").setQuery(QueryBuilders.boolQuery().must( QueryBuilders.termsQuery("user", "test"))).execute().actionGet();	
	    System.out.println(response);
	    test1();
	}
	
	//@Test
	public void test1() throws IOException, ParseException {	
		log.debug("测试2");
		Client client = ESClientHelper.getInstance().getClient();
		System.out.println(client);
	    SearchResponse response = client.prepareSearch("test").setQuery(QueryBuilders.boolQuery().must( QueryBuilders.termsQuery("name", "tttt"))).execute().actionGet();	
	    System.out.println(response);
	}
	
	//@Test
	public void test3() throws IOException, ParseException {	
		log.debug("测试3");
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		System.out.println("1111  "  + restClient);
		Response response = restClient.performRequest("GET", "/",
		        Collections.singletonMap("pretty", "true"));
		System.out.println(EntityUtils.toString(response.getEntity()));
		
		HttpEntity entity = new NStringEntity("{\"query\":{\"bool\":{\"filter\":[{\"query_string\":{\"fields\":[\"content_full\"],\"query\":\"\\\"明星大侦探\\\"\"}},{\"range\":{\"published_at\":{\"gte\":\"2017-01-10\",\"lte\":\"2017-04-21\"}}}]}}}", ContentType.APPLICATION_JSON);
		Response indexResponse = restClient.performRequest(
		        "GET",
		        "/weibo_articles_and_weiboers/weibo_articles_and_weiboer/_search",
		        //Collections.<String, String>emptyMap(),
		        Collections.singletonMap("pretty", "true"),
		        entity);
		System.out.println(indexResponse);
		System.out.println("----------");
		String json = EntityUtils.toString(indexResponse.getEntity());
		System.out.println(json);
		System.out.println("11111ttttt  "  + JSONArray.toJSON(json));
		System.out.println("2222222222  "  + JSONObject.parseObject(json).getString("timed_out"));
		Map<String, Object> userMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
		System.out.println("rrrrrrrrrr  " + userMap);
		System.out.println("-------------");
		System.out.println(indexResponse.getEntity());
	}
	
	//@Test
	public void test4() throws IOException, ParseException {	
		log.debug("测试4");
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		System.out.println("2222  "  + restClient);
		Response response = restClient.performRequest("GET", "/",
		        Collections.singletonMap("pretty", "true"));
		System.out.println(EntityUtils.toString(response.getEntity()));
	}
	
	//@Test
	public void test5() throws IOException, ParseException {	
		log.debug("测试5");
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		HttpEntity entity = new NStringEntity(""
				+ "{"
				+ 	"\"query\": {"
				+ 		"\"bool\": {"
				+ 			"\"filter\": [{"
				+ 				"\"query_string\": {"
				+ 					"\"fields\": [\"content_full\"],"
				+ 					"\"query\" : \"\\\"@吃饭睡觉蠢木木\\\"\""
				+ 				"}"
				+ 			"},"
				+ 			"{"
				+			 	"\"range\": {"
				+ 					"\"published_at\": {"
				+ 						"\"gte\": \"2016-01-01\","
				+ 						"\"lte\": \"2017-05-01\""
				+ 					"}"
				+ 				"}"
				+ 			"}]"
				+ 		"}"
				+ 	"},"
				+ 	"\"size\": 0,"
				+ 	"\"aggs\": {"
				+ 		"\"sdf\": {"
				+ 			"\"terms\": {"
				+ 				"\"field\": \"tags\","
				+ 				"\"execution_hint\": \"map\""
				+ 			"}"
				+ 		"},"
				+ 		"\"test\": {"
				+ 			"\"terms\": {"
				+ 				"\"field\": \"city_agg\","
				+ 				"\"execution_hint\": \"map\""
				+ 			"}"
				+		"}"
				+ 	"}"
				+ "}", ContentType.APPLICATION_JSON);
		Response indexResponse = restClient.performRequest(
		        "GET",
		        "/weibo_articles_and_weiboers/weibo_articles_and_weiboer/_search",
		        //Collections.<String, String>emptyMap(),
		        Collections.singletonMap("pretty", "true"),
		        entity);
		String json = EntityUtils.toString(indexResponse.getEntity());
		System.out.println(json);
		Map<String, Object> userMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
		System.out.println("rrrrrrrrrr  " + JSON.parseObject(JSON.parseObject(userMap.get("aggregations").toString()).getString("sdf")).getString("buckets"));
		String list = JSON.parseObject(JSON.parseObject(userMap.get("aggregations").toString()).getString("sdf")).getString("buckets");
		JSON.parseArray(list);
	}
	
	private static List<String> keywords = new ArrayList<>();
	
	static {
		keywords.add("腾讯新闻");
		keywords.add("凤凰新闻");
		keywords.add("新浪新闻");
		keywords.add("网易新闻");
		keywords.add("搜狐新闻");
		keywords.add("今日头条");
		keywords.add("天天快报");
		keywords.add("一点资讯");
	}
	
	SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd" ); 
	Calendar calendar = new GregorianCalendar(); 
	
	//@Test
	public void test6() throws IOException, ParseException, java.text.ParseException {	
		log.debug("测试6");
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		
        String timeStart = "2017-06-01";
        String timeEnd = "2017-07-31";
        List<String> allDate = JTime.days(timeStart, timeEnd);	
		
		for (String keyword : keywords) {
			List<String> t3 = new ArrayList<>();
	        for (String date : allDate) {
	        	log.debug("测试  " + keyword + " " +date +" " + oneDayTo(date));
	    		HttpEntity entity = new NStringEntity(""
	    				+ "{"
	    				+ 	"\"query\": {"
	    				+ 		"\"bool\": {"
	    				+ 			"\"filter\": [{"
	    				+ 				"\"query_string\": {"
	    				+ 					"\"fields\": [\"content_full\"],"
	    				+ 					"\"query\" : \"\\\""+keyword+"\\\"\""
	    				+ 				"}"
	    				+ 			"},"
	    				+ 			"{"
	    				+			 	"\"range\": {"
	    				+ 					"\"published_at\": {"
	    				+ 						"\"gte\": \""+date+"\","
	    				+ 						"\"lte\": \""+oneDayTo(date)+"\""
	    				+ 					"}"
	    				+ 				"}"
	    				+ 			"}]"
	    				+ 		"}"
	    				+ 	"},"
	    				+ 	"\"size\": 200,"
	    				+   "\"sort\": {"
	    				+         "\"_script\": {"
	    				+         "\"script\": \"Math.random()\","
	    				+         "\"type\": \"number\""
	    				+     "}"
	    				+   "}"
	    				+ "}", ContentType.APPLICATION_JSON);
	    		Response indexResponse = restClient.performRequest(
	    		        "GET",
	    		        "/weibo_articles_and_weiboers/weibo_articles_and_weiboer/_search",
	    		        //Collections.<String, String>emptyMap(),
	    		        Collections.singletonMap("pretty", "true"),
	    		        entity);
	    		String json = EntityUtils.toString(indexResponse.getEntity());
	    		//System.out.println(json);
	    		Map<String, Object> userMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
	    		String list = JSON.parseObject(userMap.get("hits").toString()).getString("hits");
	    		List<Object> lists =  JSON.parseArray(list);
	    		for (Object object : lists) {
	    			String t2 = JSON.parseObject(object.toString()).getString("_source");
	    			t3.add(t2);
	    		}
			}
	        writeFile("/Users/admin/Desktop/output/微信_"+keyword+".json", t3.toString());  
		}
	}
	
	
	//@Test
	public void test7() throws IOException, ParseException, java.text.ParseException {	
		log.debug("测试7");
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		
        String timeStart = "2017-06-01";
        String timeEnd = "2017-07-31";
        List<String> allDate = JTime.days(timeStart, timeEnd);	
		
		for (String keyword : keywords) {
			List<String> t3 = new ArrayList<>();
	        for (String date : allDate) {
	        	log.debug("测试  " + keyword + " " +date +" " + oneDayTo(date));
	    		HttpEntity entity = new NStringEntity(""
	    				+ "{"
	    				+ 	"\"query\": {"
	    				+ 		"\"bool\": {"
	    				+ 			"\"filter\": [{"
	    				+ 				"\"query_string\": {"
	    				+ 					"\"fields\": [\"content\"],"
	    				+ 					"\"query\" : \"\\\""+keyword+"\\\"\""
	    				+ 				"}"
	    				+ 			"},"
	    				+ 			"{"
	    				+			 	"\"range\": {"
	    				+ 					"\"last_modified_at\": {"
	    				+ 						"\"gte\": \""+date+"\","
	    				+ 						"\"lte\": \""+oneDayTo(date)+"\""
	    				+ 					"}"
	    				+ 				"}"
	    				+ 			"}]"
	    				+ 		"}"
	    				+ 	"},"
	    				+ 	"\"size\": 200,"
	    				+   "\"sort\": {"
	    				+         "\"_script\": {"
	    				+         "\"script\": \"Math.random()\","
	    				+         "\"type\": \"number\""
	    				+     "}"
	    				+   "}"
	    				+ "}", ContentType.APPLICATION_JSON);
	    		Response indexResponse = restClient.performRequest(
	    		        "GET",
	    		        "/weixin_articles_and_weixiners/weixin_articles_and_weixiner/_search",
	    		        //Collections.<String, String>emptyMap(),
	    		        Collections.singletonMap("pretty", "true"),
	    		        entity);
	    		String json = EntityUtils.toString(indexResponse.getEntity());
	    		//System.out.println(json);
	    		Map<String, Object> userMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
	    		String list = JSON.parseObject(userMap.get("hits").toString()).getString("hits");
	    		List<Object> lists =  JSON.parseArray(list);
	    		for (Object object : lists) {
	    			String t2 = JSON.parseObject(object.toString()).getString("_source");
	    			t3.add(t2);
	    		}
			}
	        writeFile("/Users/admin/Desktop/output/微信_"+keyword+".json", t3.toString());  
		}
	}
	
	
	//@Test
	public void test8() throws IOException, ParseException, java.text.ParseException {	
		log.debug("测试8");
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		
        String timeStart = "2017-06-01";
        String timeEnd = "2017-07-31";
		
		for (String keyword : keywords) {
			List<PaWeixin> t3 = new ArrayList<>();
	        log.debug("测试  " + keyword + " " +timeStart +" " + timeEnd);
	    	HttpEntity entity = new NStringEntity(""
	    			+ "{"
	    			+ 	"\"query\": {"
	    			+ 		"\"bool\": {"
	    			+ 			"\"filter\": [{"
	    			+ 				"\"query_string\": {"
	    			+ 					"\"fields\": [\"content\"],"
	    			+ 					"\"query\" : \"\\\""+keyword+"\\\"\""
	    			+ 				"}"
	    			+ 			"},"
	    			+ 			"{"
	    			+			 	"\"range\": {"
	    			+ 					"\"last_modified_at\": {"
	    			+ 						"\"gte\": \""+timeStart+"\","
	    			+ 						"\"lte\": \""+timeEnd+"\""
	    			+ 					"}"
	    			+ 				"}"
	    			+ 			"}]"
	    			+ 		"}"
	    			+ 	"},"
	    			+ 	"\"size\": 200,"
	    			+   "\"sort\": {"
	    			+         "\"_script\": {"
	    			+         "\"script\": \"Math.random()\","
	    			+         "\"type\": \"number\""
	    			+     "}"
	    			+   "}"
	    			+ "}", ContentType.APPLICATION_JSON);
	    	Response indexResponse = restClient.performRequest(
	    		    "GET",
	    		    "/weixin_articles_and_weixiners/weixin_articles_and_weixiner/_search",
	    		    //Collections.<String, String>emptyMap(),
	    		    Collections.singletonMap("pretty", "true"),
	    		    entity);
	    	String json = EntityUtils.toString(indexResponse.getEntity());
	    	//System.out.println(json);
	    	Map<String, Object> userMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
	    	String list = JSON.parseObject(userMap.get("hits").toString()).getString("hits");
	    	List<Object> lists =  JSON.parseArray(list);
	    	for (Object object : lists) {
	    		String t2 = JSON.parseObject(object.toString()).getString("_source");
	    		t3.add(JSON.parseObject(t2, PaWeixin.class));
	    	}
	    	System.out.println("111"   + t3);
	        writeFile("/Users/admin/Desktop/output/微信_"+keyword+".json", t3.toString());  
	        
	        List<String[]> columNames = new ArrayList<String[]>();
	        columNames.add(new String[] { "标题" });

	        List<String[]> fieldNames = new ArrayList<String[]>();
	        fieldNames.add(new String[] { "title" });
	        
	        LinkedHashMap<String, List<?>> map = new LinkedHashMap<String, List<?>>();
	        
	        map.put("运单月报(1月)", t3);
	        
	        ExcelExportData setInfo = new ExcelExportData();
	        setInfo.setDataMap(map);
	        setInfo.setFieldNames(fieldNames);
	        setInfo.setTitles(new String[] { "微信原文导出"});
	        setInfo.setColumnNames(columNames);
	        try {
				ExcelUtil.export2File(setInfo, "/Users/admin/Desktop/output/Test_微信_"+keyword+".xls");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@Test
	public void test9() throws IOException, ParseException, java.text.ParseException {	
		log.debug("继春 今日头条");
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		
    	HttpEntity entity1 = new NStringEntity("{\"query\":{\"bool\":{\"filter\":[{\"range\":{\"last_modified_at\":{\"gte\":\"2017-06-01 00:00:00\",\"lte\":\"2017-07-31 00:00:00\",\"format\":\"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\",\"time_zone\":\"Asia/Shanghai\"}}}],\"should\":[{\"bool\":{\"must\":[{\"multi_match\":{\"query\":\"今日头条\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"]}}],\"must_not\":[{\"multi_match\":{\"query\":\"今日头条】\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"今日头条【\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"今日头条‖\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"今日头条~\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"今日头条|\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"今日头条：\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"今日头条:\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"今日头条/\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"今日头条！\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"今日头条——\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"今日头条-\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"V乐陵今日头条\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"〖今日头条〗\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"【今日头条\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"【2017.07.31】今日头条\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"『今日头条』\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"「今日头条」\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"《今日头条》\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"“今日头条“\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"[今日头条]\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"（今日头条）\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"#今日头条#\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"# 今日头条 #\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"\\\"今日头条 |\\\"\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"今日头条 |\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}},{\"multi_match\":{\"query\":\"\\\"今日头条┃\\\"\",\"type\":\"phrase_prefix\",\"fields\":[\"content\",\"title\"],\"operator\":\"and\"}}]}}],\"minimum_should_match\":1}},\"size\":1000,\"_source\":{\"excludes\":[]}}", ContentType.APPLICATION_JSON);
    	Response indexResponse1 = restClient.performRequest(
    		    "POST",
    		    "/weixin_articles_and_weixiners/weixin_articles_and_weixiner/_search?scroll=300s",
    		    //Collections.<String, String>emptyMap(),
    		    Collections.singletonMap("pretty", "true"),
    		    entity1);
		String json1 = EntityUtils.toString(indexResponse1.getEntity());
		String scrollId = JSON.parseObject(json1).get("_scroll_id").toString();
		//System.out.println(json1);
		//System.out.println(scrollId);
		
		List<PaWeixin> t3 = new ArrayList<>();
		HttpEntity entity2 = new NStringEntity("{\"scroll\":\"300s\",\"scroll_id\":\""+scrollId+"\"}");
		for(int i=0;i<34;i++){
			System.out.println("scroll_id 第几批："+i);
			Response indexResponse2 = restClient.performRequest(
	    		    "POST",
	    		    "/_search/scroll",
	    		    //Collections.<String, String>emptyMap(),
	    		    Collections.singletonMap("pretty", "true"),
	    		    entity2);
			String json2 = EntityUtils.toString(indexResponse2.getEntity());
			//System.out.println(json2);
			Map<String, Object> userMap = JSON.parseObject(json2, new TypeReference<Map<String, Object>>() {});
	    	String list = JSON.parseObject(userMap.get("hits").toString()).getString("hits");
	    	List<Object> lists =  JSON.parseArray(list);
	    	for (Object object : lists) {
	    		String t2 = JSON.parseObject(object.toString()).getString("_source");
	    		PaWeixin paWeixin = JSON.parseObject(t2, PaWeixin.class);
	    		String link = "http://mp.weixin.qq.com/s?__biz="+paWeixin.getBiz()+"&mid="+paWeixin.getMid()+"&idx="+paWeixin.getIdx()+"&sn="+paWeixin.getSn()+"";
	    		if(paWeixin.getIdx().equals("1")){
	    			paWeixin.setIdx("是");
	    		}else{
	    			paWeixin.setIdx("否");
	    		}
	    		paWeixin.setLink(link);
	    		t3.add(paWeixin);
	    	}
		}
		System.out.println("开始写excel");
    	List<String[]> columNames = new ArrayList<String[]>();
	    columNames.add(new String[] { "标题","时间","作者ID","作者用户名","作者","头条","阅读量","点赞量","链接","情感色彩","抓取时间" });

	    List<String[]> fieldNames = new ArrayList<String[]>();
	    fieldNames.add(new String[] { "title","last_modified_at","authorId","authorName","authorPa","idx","stat_read_count","stat_like_count","link","emotion","crawled_at" });
	        
	    LinkedHashMap<String, List<?>> map = new LinkedHashMap<String, List<?>>();
	        
	    map.put("微信今日头条原文", t3);
	        
	    ExcelExportData setInfo = new ExcelExportData();
	    setInfo.setDataMap(map);
	    setInfo.setFieldNames(fieldNames);
	    setInfo.setTitles(new String[] { "微信今日头条原文"});
	    setInfo.setColumnNames(columNames);
	    try {
	    	ExcelUtil.export2File(setInfo, "/Users/admin/Desktop/output/微信今日头条原文.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
    public static void writeFile(String filePath, String sets)  
            throws IOException {  
        FileWriter fw = new FileWriter(filePath);  
        PrintWriter out = new PrintWriter(fw);  
        out.write(sets);  
        out.println();  
        fw.close();  
        out.close();  
    }  
    
	@SuppressWarnings("static-access")
	public String oneDayTo(String d) throws java.text.ParseException{
	    calendar.setTime(sdf.parse(d)); 
	    calendar.add(calendar.DATE,+1);
	    Date date=calendar.getTime();
	    String da = sdf.format(date); 
		return da;
	}
    
}
