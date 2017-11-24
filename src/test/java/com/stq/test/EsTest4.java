package com.stq.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.stq.entity.stq.Task;
import com.stq.entity.stq.words.WordsEsWeiboIndex;
import com.stq.entity.stq.words.WordsTaskSencond;
import com.stq.service.stq.TaskService;
import com.stq.service.stq.words.WordsTaskSencondService;
import com.stq.service.stq.words.WordsUpdateService;
import com.stq.service.stq.words.tianya.TianYaStatsService;
import com.stq.service.stq.words.tieba.TiebaStatsService;
import com.stq.service.stq.words.toutiao.ToutiaoStatsService;
import com.stq.service.stq.words.weibo.WeiboStatsService;
import com.stq.service.stq.words.weixin.WeixinStatsService;
import com.stq.service.stq.words.zhihuquestions.ZhihuQuestionsStatsService;
import com.stq.service.stq.words.zixun.ZiXunStatsService;
import com.stq.util.ESRestClientHelper;
import com.stq.util.JTime;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class EsTest4 extends SpringTransactionalTestCase{
	
	private static final Logger log = LoggerFactory.getLogger(EsTest4.class);
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private WordsTaskSencondService wordsTaskSencondService;
	
	@Autowired
	private WeixinStatsService weixinStatsService;
	
	//@Test
	public void test()  {	
		log.debug("测试1");
		Map<String, Integer> map = new HashMap<String, Integer>();
		if(map.get("2018") == null){
			System.out.println("111   "   +map.size());
		}
		System.out.println(JTime.days("2012-02-02", "2012-02-10"));
		
		List<String> dates = JTime.days("2012-02-02", "2012-02-10");
		for (int i = 0; i < dates.size(); i++) {
			if(i!=dates.size()-1){
				System.out.println(dates.get(i) + " " +dates.get(i+1) );
			}
			if(i!=dates.size()-1){
				System.out.println(JTime.esDay(dates.get(i)) + " " +JTime.esDay(dates.get(i+1)) );
			}
		}
		
		Map<String, Map<String, String>> allMap = Maps.newLinkedHashMap();
		Map<String, Map<String, String>> dayMap = Maps.newLinkedHashMap();
		Map<String, String> a1 = Maps.newLinkedHashMap();
		Map<String, String> a2 = Maps.newLinkedHashMap();
		Map<String, String> b1 = Maps.newLinkedHashMap();
		Map<String, String> b2 = Maps.newLinkedHashMap();
		a1.put("a1", "a1");
		a2.put("a2", "a2");
		
		b1.put("b1", "b1");
		b2.put("b2", "b2");
		allMap.put("exposure", a1);
		allMap.put("independentPercentage", a2);
		dayMap.put("exposure", b1);
		dayMap.put("independentPercentage", b2);
		
		allMap.putAll(dayMap);
		
		System.out.println(allMap);
 	}
	
	//@Test
	public void test1() throws IOException   {	
		List<Task> tasks = taskService.findByTaskStatus("2", "1");
		System.out.println("111  "  +tasks );
		List<Task> task = taskService.findByTaskStatusLimit("2", "1");
		System.out.println("222  "  +task );
		// 编码
		for (int i = 0; i < 10; i++) {
			String asB64 = Base64.getEncoder().encodeToString("some string".getBytes("utf-8"));
			System.out.println(asB64 + "  " + i); // 输出为: c29tZSBzdHJpbmc=
			// 解码
			byte[] asBytes = Base64.getDecoder().decode("c29tZSBzdHJpbmc=");
			System.out.println(new String(asBytes, "utf-8")); // 输出为: some string
		}
		List<Map<String, String>> esList = new ArrayList<>();
		Map<String, String> m = Maps.newLinkedHashMap();
		String asB64 = Base64.getEncoder().encodeToString("2017-09-132017-09-14测试".getBytes("utf-8"));
		System.out.println(asB64);
		m.put("esId", "\""+asB64+"\"");
		m.put("esDoc", "\"startDate\" : \"2017-09-13\",\"endDate\" : \"2017-09-14\",\"keywords\" : \"测试\",\"source\" : \"weibo\",\"volume\" : \"2\"");
		esList.add(m);
		wordsTaskSencondService.PostCreateBulk(esList);
	}
	
	
	//@Test
	public void test2() throws IOException   {	
/*		// 使用lambda表达式
		List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
		costBeforeTax.stream().map((cost) -> cost + .12*cost).forEach(System.out::println);
		
		List<WordsTaskSencond> wordsTaskSenconds2 = wordsTaskSencondService.findByKeywordsAndStartDateAndEndDateAndSourceAndtaskStatusNotOk("你的名字+电影", "2016-11-02", "2017-05-02", "weibo");
		System.out.println(wordsTaskSenconds2.size());
		List<WordsTaskSencond> wordsTaskSenconds1 = wordsTaskSencondService.findByKeywordsAndStartDateAndEndDateAndSourceAndtaskStatusOk("你的名字+电影", "2016-11-02", "2017-05-03", "weibo");
		System.out.println(wordsTaskSenconds1.size());*/
		
		wordsTaskSencondService.findWeiboIndexFromEsAggs("2016-11-02", "2017-05-02","你的名字+电影","weibo","测试");
		wordsTaskSencondService.findWeiboIndexFromEsNotAggs("2016-11-02", "2017-05-02","你的名字+电影","weibo","测试");

		try {
			wordsTaskSencondService.findWeiboIndexFromEs("2016-11-02", "2017-05-02","你的名字+电影","weibo","0","测试");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		wordsTaskSencondService.findByWordsTaskSecondId(1L);
		System.out.println(wordsTaskSencondService.findByWordsTaskSecondId(1L)  +   "  " +wordsTaskSencondService.findByWordsTaskSecondId(1L).getKeyWord());
	}
	
	//@Test
	public void test3() throws IOException   {	
		List<WordsTaskSencond> wordsTaskSenconds1 = wordsTaskSencondService.findFailSecondTaskByPrimaryId(2L);
		System.out.println(wordsTaskSenconds1.size());
		List<WordsTaskSencond> wordsTaskSenconds2 = wordsTaskSencondService.findSuccessSecondTaskByPrimaryId(2L);
		System.out.println(wordsTaskSenconds2.size());
		Integer count = wordsTaskSencondService.findCountSecondTaskByPrimaryId(2L);
		System.out.println(count);
	}
	
	//@Test
	public void test4() throws IOException   {	
		Map<String, Map<String, String>> readAndUpAndStatistics = weixinStatsService.readAndUpAndStatistics("2016-03-30", "2017-08-14", "我想和你唱1||一起来HIGH唱||一起来嗨唱||我想和你唱1||一起来HIGH唱||一起来嗨唱");
		
		for (Entry<String, String> object : readAndUpAndStatistics.get("readMap").entrySet()) {
			System.out.println("readMap   "  + object.getKey() + "  " + object.getValue());
		}
		
		for (Entry<String, String> object : readAndUpAndStatistics.get("upMap").entrySet()) {
			System.out.println("upMap   "  + object.getKey() + "  " + object.getValue());
		}
		
		for (Entry<String, String> object : readAndUpAndStatistics.get("statisticsMap").entrySet()) {
			System.out.println("statisticsMap   "  + object.getKey() + "  " + object.getValue());
		}
		
	}
	
	@Autowired
	private WordsUpdateService wordsUpdateService;
	
	//@Test
	public void test5() throws IOException, NumberFormatException, ParseException   {	
		Map<String, String> coefficient = weixinStatsService.coefficient("2017-09-01", "2017-09-29");
		for (Entry<String, String> object : coefficient.entrySet()) {
			System.out.println("coefficient   "  + object.getKey() + "  " + object.getValue());
		}
		
		Map<String, String> actualVolume = weixinStatsService.actualVolume("2017-09-01", "2017-09-29","腾格尔");
		for (Entry<String, String> object : actualVolume.entrySet()) {
			System.out.println("actualVolume   "  + object.getKey() + "  " + object.getValue());
		}
		//wordsUpdateService.getEsWord();
	}
	
	@Autowired
	private TiebaStatsService tiebaStatsService;
	
//	@Test
	public void test6() throws IOException, NumberFormatException, ParseException   {	
		Map<String, Map<String, String>> map = tiebaStatsService.recoveryCount("2017-06-01 00:00:00", "2017-07-01 00:00:00", "守望");
		for (Entry<String, String> object : map.get("recoveryCountMap").entrySet()) {
			System.out.println("recoveryCountMap   "  + object.getKey() + "  " + object.getValue());
		}
		for (Entry<String, String> object : map.get("articleNumMap").entrySet()) {
			System.out.println("articleNumMap   "  + object.getKey() + "  " + object.getValue());
		}
		for (Entry<String, String> object : map.get("accountNumMap").entrySet()) {
			System.out.println("accountNumMap   "  + object.getKey() + "  " + object.getValue());
		}
	}
	
	@Autowired
	private ZhihuQuestionsStatsService zhihuQuestionsStatsService;
	
//	@Test
	public void test7() throws IOException, NumberFormatException, ParseException   {	
		Map<String, Map<String, String>> map = zhihuQuestionsStatsService.questionsAndFollowAndViewAndAnswer("2017-06-01 00:00:00", "2017-07-01 00:00:00", "守望");
		for (Entry<String, String> object : map.get("questionsCountMap").entrySet()) {
			System.out.println("questionsCountMap   "  + object.getKey() + "  " + object.getValue());
		}
		
		for (Entry<String, String> object : map.get("followsCountMap").entrySet()) {
			System.out.println("followsCountMap   "  + object.getKey() + "  " + object.getValue());
		}
		
		for (Entry<String, String> object : map.get("viewsCountMap").entrySet()) {
			System.out.println("viewsCountMap   "  + object.getKey() + "  " + object.getValue());
		}
		
		for (Entry<String, String> object : map.get("answersCountMap").entrySet()) {
			System.out.println("answersCountMap   "  + object.getKey() + "  " + object.getValue());
		}
		for (Entry<String, String> object : map.get("accountNumMap").entrySet()) {
			System.out.println("accountNumMap   "  + object.getKey() + "  " + object.getValue());
		}
		for (Entry<String, String> object : map.get("articleNumMap").entrySet()) {
			System.out.println("articleNumMap   "  + object.getKey() + "  " + object.getValue());
		}
	}
	
	@Autowired
	private TianYaStatsService tianYaStatsService;
	
//	@Test
	public void test8() throws IOException, NumberFormatException, ParseException   {	
		Map<String, Map<String, String>> map = tianYaStatsService.clicksAndReplays("2017-06-01 00:00:00", "2017-07-01 00:00:00", "守望");
		for (Entry<String, String> object : map.get("clicksCountMap").entrySet()) {
			System.out.println("clicksCountMap   "  + object.getKey() + "  " + object.getValue());
		}
		
		for (Entry<String, String> object : map.get("replaysCountMap").entrySet()) {
			System.out.println("replaysCountMap   "  + object.getKey() + "  " + object.getValue());
		}
		for (Entry<String, String> object : map.get("accountNumMap").entrySet()) {
			System.out.println("accountNumMap   "  + object.getKey() + "  " + object.getValue());
		}

		for (Entry<String, String> object : map.get("articleNumMap").entrySet()) {
			System.out.println("articleNumMap   "  + object.getKey() + "  " + object.getValue());
		}
	}
	
	@Autowired
	private ToutiaoStatsService toutiaoStatsService;
	
//	@Test
	public void test9() throws IOException, NumberFormatException, ParseException   {	
		Map<String, Map<String, String>> map = toutiaoStatsService.followerAndFansAndReadAndCommentAndUpAndDown("2017-06-01 00:00:00", "2017-07-01 00:00:00", "小宝宝");
		for (Entry<String, String> object : map.get("followerCountMap").entrySet()) {
			System.out.println("followerCountMap   "  + object.getKey() + "  " + object.getValue());
		}
		
		for (Entry<String, String> object : map.get("fansCountMap").entrySet()) {
			System.out.println("fansCountMap   "  + object.getKey() + "  " + object.getValue());
		}
		
		for (Entry<String, String> object : map.get("readCountMap").entrySet()) {
			System.out.println("readCountMap   "  + object.getKey() + "  " + object.getValue());
		}
		
		for (Entry<String, String> object : map.get("commentCountMap").entrySet()) {
			System.out.println("commentCountMap   "  + object.getKey() + "  " + object.getValue());
		}
		
		for (Entry<String, String> object : map.get("upCountMap").entrySet()) {
			System.out.println("upCountMap   "  + object.getKey() + "  " + object.getValue());
		}
		
		for (Entry<String, String> object : map.get("downCountMap").entrySet()) {
			System.out.println("downCountMap   "  + object.getKey() + "  " + object.getValue());
		}

		for (Entry<String, String> object : map.get("accountNumMap").entrySet()) {
			System.out.println("accountNumMap   "  + object.getKey() + "  " + object.getValue());
		}

		for (Entry<String, String> object : map.get("articleNumMap").entrySet()) {
			System.out.println("articleNumMap   "  + object.getKey() + "  " + object.getValue());
		}
	}

	@Autowired
	private ZiXunStatsService ziXunStatsService;
	//@Test
	public void test11() throws IOException,NumberFormatException,ParseException {
		Map<String,Map<String,String>> map = ziXunStatsService.upAndCollectionAndCommentAndZixun("2017-06-01 00:00:00", "2017-07-01 00:00:00", "手机");
		for (Entry<String,String> object: map.get("upCountMap").entrySet()) {
			System.out.println("upCountMap   "  + object.getKey() + "  " + object.getValue());
		}
		for (Entry<String,String> object: map.get("collectionCountMap").entrySet()) {
			System.out.println("collectionCountMap   "  + object.getKey() + "  " + object.getValue());
		}
		for (Entry<String,String> object: map.get("commentCountMap").entrySet()) {
			System.out.println("commentCountMap   "  + object.getKey() + "  " + object.getValue());
		}
		for (Entry<String,String> object: map.get("seeCountMap").entrySet()) {
			System.out.println("seeCountMap   "  + object.getKey() + "  " + object.getValue());
		}
		for (Entry<String,String> object: map.get("accountNumMap").entrySet()) {
			System.out.println("accountNumMap   "  + object.getKey() + "  " + object.getValue());
		}
		for(Entry<String, String> object: map.get("articleNumMap").entrySet()) {
			System.out.println("articleNumMap   "	+ object.getKey() + "  " + object.getValue() );
		}
	}


	//@Test
	public void test10() throws IOException, NumberFormatException, ParseException   {	
		String test = "adsadsadadadadads111";
		if(test.length()>100){
			System.out.println(test.substring(0, 50));
		}else{
			System.out.println(test.substring(0, test.length()));
		}
		
	}
	
	//@Test
	public void test12() throws IOException, NumberFormatException, ParseException   {	
		List<WordsEsWeiboIndex> weibos = wordsTaskSencondService.findWeiboIndexFromEs("2017-09-5", "2017-09-11", "中国有嘻哈", "weibo", "1" ,"测试");
		System.out.println(JSON.toJSON(weibos));
	}
	
	
	//@Test
	public void scrollTest() throws IOException, NumberFormatException, ParseException   {	
		//List<Object> map = GetScrollList("toutiao");
		//System.out.println("1111  "  + map.size());
		
		List<WordsTaskSencond> wordsTaskSenconds =  wordsTaskSencondService.findByStatus(LocalDate.now().toString());
		System.out.println(wordsTaskSenconds.size());
		
		//wordsUpdateService.getEsWord();
	}
    
	
	public List<Object> GetScrollList(String source) throws IOException{
		Map<String, String> map = GetScrollId(source);
		String scrollId = map.get("scrollId");
		String total = map.get("total");
		String scrollSize = map.get("scrollSize");
		String scrollList = map.get("scrollList");
		List<Object> sList =  JSON.parseArray(scrollList);
		
		System.out.println("【cinbao_index 更新导出】scroll总条数："  + total+" scroll_id 第1批，此批大小："+sList.size() );
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		HttpEntity entity = new NStringEntity("{\"scroll\":\"1200s\",\"scroll_id\":\""+scrollId+"\"}");
		
		for(int i=1;i<= (Long.valueOf(total)/Long.valueOf(scrollSize));i++){
			Response indexResponse = restClient.performRequest(
	    		    "POST",
	    		    "/_search/scroll",
	    		    Collections.<String, String>emptyMap(),
	    		    entity);
			String json = EntityUtils.toString(indexResponse.getEntity());
			Map<String, Object> userMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
	    	String list = JSON.parseObject(userMap.get("hits").toString()).getString("hits");
	    	List<Object> lists =  JSON.parseArray(list);
	    	sList.addAll(lists);
	    	System.out.println("【cinbao_index 更新导出】scroll总条数："  + total+" scroll_id 第"+(i+1)+"批，此批大小："+lists.size() );
		}
		//clear scroll
		Response clearScrollResponse = restClient.performRequest(
    		    "DELETE",
    		    "/_search/scroll/"+scrollId,
    		    Collections.<String, String>emptyMap());
		System.out.println("清除 scrollId "+EntityUtils.toString(clearScrollResponse.getEntity())); 
		return sList;
	}
	
	//获取微博关键字搜索的scrollId
	public Map<String, String> GetScrollId(String source) throws IOException{
		String scrollSize = "10000";
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
        HttpEntity entity = new NStringEntity(""
                + "{"
                +       "\"query\": {"
                +           "\"bool\": {"
                + 				"\"filter\": [{\"term\":  { \"source\": \""+source+"\" }}], "
                +               "\"should\": ["
                + 					"{"
                + 						"\"script\": {"
                + 							"\"script\": \"doc.updateDate.value - doc.startDate.value <= 86400000*7 \""
                + 						"}"
                + 					"}"
                +               "],"
                +               "\"minimum_should_match\": \"1\""
                +           "}"
                +       "},"
                +       "\"size\": "+scrollSize+","
                +       "\"_source\": {\"includes\": [\"name\",\"keywords\",\"source\",\"startDateString\",\"endDateString\"]}"
                + "}", ContentType.APPLICATION_JSON);
        Response indexResponse = restClient.performRequest(
                "GET",
                "/cibao_index/cibao_index/_search?scroll=1800s",
                Collections.singletonMap("pretty", "true"),
                entity);
    	String json = EntityUtils.toString(indexResponse.getEntity());
		String scrollId = JSON.parseObject(json).get("_scroll_id").toString();
		Map<String, Object> userMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
		String total = JSON.parseObject(userMap.get("hits").toString()).getString("total");
		String scrollList = JSON.parseObject(userMap.get("hits").toString()).getString("hits");
		Map<String, String> map = Maps.newLinkedHashMap();
		map.put("scrollId", scrollId);
		map.put("total", total);
		map.put("scrollSize", scrollSize);
		map.put("scrollList", scrollList);
		return map;
	}
	
	@Autowired
	private WeiboStatsService weiboStatsService;
	
	@Test
	public void test13() throws IOException, NumberFormatException, ParseException   {	
/*		List<WordsEsWeiboIndex> weibos = wordsTaskSencondService.findWeiboIndexFromEs("2017-09-5", "2017-09-11", "中国有嘻哈", "weibo", "1" ,"测试");
		System.out.println(JSON.toJSON(weibos));*/
		
		//Map<String, String> coefficient = weiboStatsService.coefficient("2016-01-01 00:00:00", "2017-10-26 00:00:00");
		//weiboStatsService.exposureAndIndependentPercentage("2016-01-01 00:00:00", "2016-01-06 00:00:00", "球爱-我们约球吧", coefficient);
		
		//weixinStatsService.volume("2016-01-01 00:00:00", "2016-01-06 00:00:00", "");
		
		String keywords = "";
		List<String> kn = new ArrayList<>();kn.add("");
		List<String> kwords =  keywords.equals("")?kn:ImmutableList.copyOf(StringUtils.split(keywords, "||"));
		weixinStatsService.actualVolume("2016-01-01 00:00:00", "2016-01-06 00:00:00", "");
		System.out.println(kwords.size());
		
	}
	
}
