package com.stq.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.stq.util.ESClientHelper;
import com.stq.util.ESRestClientHelper;
import com.stq.util.JTime;
import com.sun.el.parser.ParseException;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class EsTest extends SpringTransactionalTestCase{
	
	private static final Logger log = LoggerFactory.getLogger(EsTest.class);
	
	
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
	
	@Test
	public void test6() throws IOException, ParseException {	
		log.debug("测试6");
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		String query = "(\\\"三少爷的剑\\\" AND NOT \\\"电影\\\") OR (\\\"三少爷的剑\\\" AND \\\"林更新\\\") OR (\\\"三少爷的剑\\\" AND \\\"何润东\\\") OR (\\\"三少爷的剑\\\" AND \\\"江一燕\\\") OR (\\\"三少爷的剑\\\" AND \\\"蒋梦婕\\\")";
		String query2 = "(\\\"佩小姐的奇幻城堡\\\")";
		String query3 = "(\\\"你的名字\\\" AND \\\"电影\\\")";
		HttpEntity entity = new NStringEntity(""
				+ "{"
				+ 	"\"query\": {"
				+ 		"\"bool\": {"
				+ 			"\"filter\": [{"
				+ 				"\"query_string\": {"
				+ 					"\"fields\": [\"content_full\"],"
				+ 					"\"query\" : \""+query3+"\""
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
				+ 	"\"size\": 1"
				+ "}", ContentType.APPLICATION_JSON);
		Response indexResponse = restClient.performRequest(
		        "GET",
		        "/weibo_articles_and_weiboers/weibo_articles_and_weiboer/_search",
		        //Collections.<String, String>emptyMap(),
		        Collections.singletonMap("pretty", "true"),
		        entity);
		String json = EntityUtils.toString(indexResponse.getEntity());
		System.out.println(json);
	}
}
