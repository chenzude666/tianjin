package com.stq.test;


import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.stq.lingjoin.sentiment.Sentiment;
import com.stq.repository.stq.words.WordsSencondDao;
import com.stq.entity.stq.text.TextEsWeiboIndex;
import com.stq.entity.stq.text.TextZhihu;
import com.stq.entity.stq.words.WordsTaskSencond;
import com.stq.lingjoin.nlpir.NLPIR;
import com.stq.service.stq.words.WordsTaskSencondService;
import com.stq.service.stq.words.weibo.WeiboBaseService;
import com.stq.service.stq.words.weibo.WeiboStatsService;
import com.stq.service.stq.words.weixin.WeixinStatsService;
import com.stq.util.DoubleUtil;
import com.stq.util.ESRestClientHelper;
import com.stq.util.JTime;
import com.sun.el.parser.ParseException;



@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TeTest2 extends SpringTransactionalTestCase{
	
	private static final Logger log = LoggerFactory.getLogger(TeTest2.class);
	
	@Autowired
	private WeiboStatsService weiboStatsService;
	
	@Autowired
	private WeixinStatsService weixinStatsService;
	
	@Autowired
	private WordsTaskSencondService wordsTaskSencondService;
	
	//@Test
	public void test() throws IOException, ParseException {	
		log.debug("测试1");
		double f = 111231.5585;
		BigDecimal b = new BigDecimal(f);
		double f1 = b.setScale(2,RoundingMode.HALF_UP).doubleValue();
		double f2 = b.setScale(0,RoundingMode.HALF_UP).doubleValue();
		log.debug("测试1  " + f1 + "  "  + f2);
		
        DecimalFormat df = new DecimalFormat("######0"); //四色五入转换成整数  
        Double x=1231233.1415934234;  
        Double y=6663.64522342341;  
        log.debug("测试2  " +df.format(x)+"  "+df.format(y));  
		
	}
	
	//@Test
	public void test1() throws IOException, ParseException, NumberFormatException, java.text.ParseException {	
/*		log.debug("测试2 每天文章总量");
		Map<String, String> map1 = weiboStatsService.countPerdays("2017-06-01 00:00:00", "2017-08-01 00:00:00");
		for (Map.Entry<String, String> entry : map1.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}*/
		
		log.debug("测试2 每天系数");
		Map<String, String> map2 = weiboStatsService.coefficient("2017-08-01 00:00:00", "2017-08-15 00:00:00");
		for (Map.Entry<String, String> entry : map2.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
		
/*		log.debug("测试2 每天声量");
		Map<String, String> map3 = weiboStatsService.volume("2017-06-01 00:00:00", "2017-08-01 00:00:00", "哈利波特二十周年+网易新闻||哈利波特20周年+网易新闻");
		for (Map.Entry<String, String> entry : map3.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
		
		log.debug("测试2 每天实际声量");
		Map<String, String> map4 = weiboStatsService.actualVolume("2017-06-01 00:00:00", "2017-08-01 00:00:00", "哈利波特二十周年+网易新闻||哈利波特20周年+网易新闻");
		for (Map.Entry<String, String> entry : map4.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}*/
		
		log.debug("测试2 独立用户曝光量");
		Map<String, Map<String, String>> map5 = weiboStatsService.exposureAndIndependentPercentage("2017-08-01 00:00:00", "2017-08-15 00:00:00","腾讯新闻",map2);
		for (Entry<String, String> entry : map5.get("exposure").entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
		
		for (Entry<String, String> entry : map5.get("independentPercentage").entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
	}
	
	//@Test
	public void test13() throws IOException, ParseException, NumberFormatException, java.text.ParseException {	
		log.debug("测试2 互动量");
		Map<String, String> map2 = weiboStatsService.coefficient("2017-08-01 00:00:00", "2017-08-02 00:00:00");
		Map<String, Map<String, String>> map5 = weiboStatsService.forwardAndCommentsAndUp("2017-08-01 00:00:00", "2017-08-02 00:00:00","腾讯新闻",map2);
	}
	
	//@Test
	public void test14() throws IOException, ParseException, NumberFormatException, java.text.ParseException {	
		log.debug("测试2 原发提及占比 分子 分母");
		Map<String, String> map5 = weiboStatsService.primaryPercentageFenMu("2017-08-01 00:00:00", "2017-08-02 00:00:00","腾讯新闻");
		Map<String, String> map6 = weiboStatsService.primaryPercentageFenZi("2017-08-01 00:00:00", "2017-08-02 00:00:00","腾讯新闻");
		
		List<String> datesNotDate2 = JTime.daysNotdate2("2017-08-01", "2017-08-02");
		double a = 0; //分子
		double b = 0; //分母
		for (String string : datesNotDate2) {
			a = a + Double.valueOf(map6.get(string));
			b = b + Double.valueOf(map5.get(string));
		}
		System.out.println("ttttttt  "  + a  + "  "  + b);
		if(b != 0){
			System.out.println(String.valueOf(Double.valueOf(a/b)));
		}else{
			System.out.println("0");
		}
	}
	
	//@Test
	public void test15() throws IOException, ParseException, NumberFormatException, java.text.ParseException {	
		log.debug("测试2 情感");
		List<String> datesNotDate2 = JTime.daysNotdate2("2017-08-01", "2017-08-02");
		Map<String, Map<String, Long>> map5 = weiboStatsService.sentimentAndNipir("2017-08-01 00:00:00", "2017-08-02 00:00:00","网易新闻");
		Map<String, String> actualVolume = weiboStatsService.actualVolume("2017-08-01 00:00:00", "2017-08-02 00:00:00","网易新闻");
		double a = 0;//分子
		double b = 0;//分母
		for (String string : datesNotDate2) {
			a = a + Double.valueOf(map5.get("neutralNumsMap").get(string));
			b = b + (Double.valueOf(actualVolume.get(string)));
		}
		System.out.println("111  " + a  + "  " +b);
		if(b != 0){
			System.out.println(String.valueOf(Double.valueOf(a/b)));
		}else{
			System.out.println(String.valueOf(Double.valueOf("0")));
		}
	}
	

	//@Test
	public void test16() throws IOException, ParseException, NumberFormatException, java.text.ParseException {	
		Map<String, Map<String, Long>> map5 = weiboStatsService.sentimentAndNipir("2017-08-01 00:00:00", "2017-08-02 00:00:00","腾讯新闻");
	}
	

	DecimalFormat df = new DecimalFormat("######0"); //四色五入转换成整数 
	
	//@Test
	public void test2() throws IOException, ParseException, NumberFormatException, java.text.ParseException {	
		log.debug("测试2 科学计数法");
		BigDecimal bd = new BigDecimal("3.0000856182503598E18");
		System.out.println(bd.doubleValue());
		System.out.println(bd.toPlainString());
		System.out.println(WeiboBaseService.WEIBOER_REAL_COUNTS.get(0));
		System.out.println(DoubleUtil.add(WeiboBaseService.WEIBOER_REAL_COUNTS.get(0),WeiboBaseService.WEIBOER_REAL_COUNTS.get(0)));
		System.out.println(40305.857614958746/3.61E8/52.336654756524666);
		System.out.println(Double.valueOf(40305.857614958746/3.61E8/52.336654756524666));
		System.out.println(df.format(Double.valueOf(40305.857614958746/3.61E8/52.336654756524666)));
		
		System.out.println(DoubleUtil.div(DoubleUtil.mul(40305.857614958746, 3.61E8), 52.336654756524666));
	}
	
	//@Test
	public void test3() throws IOException, ParseException, NumberFormatException, java.text.ParseException {	
		String startDate = "2017-06-01 00:00:00";
		String endDate = "2017-08-01 00:00:00";
		
		String subdate = "1";
		String strBool = weiboStatsService.getElasticsearchDSLByContentFull("饭饭桶饭自己+网易新闻");
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		HttpEntity entity = new NStringEntity(""
				+ "{"
				+ 	"\"query\": {"
				+ 		"\"bool\": {"
				+ 			"\"filter\": [{"
				+			 	"\"range\": {"
				+ 					"\"published_at\": {"
				+ 						"\"gte\": \""+startDate+"\","
				+ 						"\"lte\": \""+endDate+"\","
				+                       "\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\","
				+ 						"\"time_zone\": \"Asia/Shanghai\""
				+ 					"}"
				+ 				"}"
				+ 			"}],"
				+          "\"should\": ["
				+ 		   ""+strBool+""
				+          "],"
				+ 		   "\"minimum_should_match\": \"1\""
				+ 		"}"
				+ 	"},"
				+ 	"\"size\": 0,"
				+   "\"aggs\": {"
				+     "\"data_dayByday\": {"
				+       "\"date_histogram\": {"
				+         "\"field\": \"published_at\","
				+         "\"interval\": \""+subdate+"d\","
				+         "\"time_zone\": \"Asia/Shanghai\","
				+         "\"format\": \"yyyy-MM-dd\","
				+         "\"min_doc_count\": 0,"
				+           "\"extended_bounds\" : { "
				+             "\"min\" : \""+JTime.excelDay(startDate)+"\","
				+             "\"max\" : \""+JTime.excelDay(endDate)+"\""
				+           "}"
				+         "},"
				+ 	  	"\"aggs\": {"
				+ 			"\"groupByIsTop\": {"
				+ 				"\"terms\": {"
				+ 					"\"field\": \"is_top\","
				+ 					"\"size\": 2,"
				+ 					"\"missing\": false,"	
				+ 					"\"execution_hint\": \"map\""
				+ 				"},"
				+ 				"\"aggs\": {"
				+ 					"\"comment_count_sum\": {"
				+ 						"\"sum\": {"
				+	 						"\"field\": \"comment_count\""
				+ 						"}"
				+ 					"}, "
				+ 					"\"up_count_sum\": {"
				+ 						"\"sum\": {"
				+ 							"\"field\": \"up_count\""
				+ 						"}"
				+ 					"},"
				+ 					"\"repost_count_sum\": {"
				+ 						"\"sum\": {"
				+ 							"\"field\": \"repost_count\""
				+ 						"}"
				+ 					"}"
				+ 				"}"
				+ 			"},"
				+ 			"\"oldInteractiveCountSum\":{"
				+ 				"\"sum\": {"
				+ 					"\"script\": \"doc.comment_count.value + doc.up_count.value + doc.repost_count.value\""
				+ 				"}"
				+ 			"}"
				+ 		"}"
				+     "}"
				+   "}"
				+ "}", ContentType.APPLICATION_JSON);
    	Response indexResponse = restClient.performRequest(
    		    "GET",
    		    "/weibo_articles_and_weiboers/weibo_articles_and_weiboer/_search",
    		    Collections.singletonMap("pretty", "true"),
    		    entity);
    	String json = EntityUtils.toString(indexResponse.getEntity());
    	System.out.println("1111  "  + json);
    	Map<String, Object> userMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
		String list = JSON.parseObject(JSON.parseObject(userMap.get("aggregations").toString()).getString("data_dayByday")).getString("buckets");
		List<Object> data_dayBydays = JSON.parseArray(list);
		for (Object data_dayByday : data_dayBydays) {//聚合的第一层 data_dayByday
			JSONObject data_dayByday_1 = JSON.parseObject(data_dayByday.toString());
			System.out.println(data_dayByday_1);
			System.out.println(JSON.parseObject(data_dayByday_1.getString("oldInteractiveCountSum")).getString("value"));
		}
	}
	

	//@Test
	public void test4() throws IOException, ParseException, NumberFormatException, java.text.ParseException {	
		Map<String, String> map = new HashMap<String, String>();
		map.put("2012-01-11", "1.0");
		map.put("2012-01-12", "2.0");
		map.put("2012-01-13", "2.1");
		map.put("2012-01-14", "1.8");
		map.put("2012-01-15", "2.00");
		System.out.println(getMaxValue(map).toString());
	}
    public static Object getMaxValue(Map<String, String> map) {
        if (map == null)
            return null;
        int length =map.size();
        Collection<String> c = map.values();
        Object[] obj = c.toArray();
        Arrays.sort(obj);
        return obj[length-1];
    }
    

	/**
	 * 分词
	 */
	//@Test
	public void test5() throws IOException, ParseException {
		String str="///xs 分享/v 网易/nz 新闻/n :/wp 《/wkz 华为/brands 手机/films 英国/stars 禁/fans 售/v ？/ww 业务/n 未/d 受/v 任何/rz 影响/vn   已/d 提起/v 上诉/v 》/wky   O/n 华为/brands 手机/n 英国/ns 禁/v 售/v ？/ww 业务/n 未/d 受/v 任何/rz 影响/vn   已/d 提起/v ./wj ./wj ./wj   @网易新闻客户端/nr   #/w 网易/nz 新闻/n #/w   ​​​​/w "; 
		if(str.indexOf("/brands")!=-1){  
			System.out.println("包含"); 
		}else { 
			System.out.println("不包含"); 
		}
		
		if(str.indexOf("/films")!=-1){  
			System.out.println("包含"); 
		}else { 
			System.out.println("不包含"); 
		} 
		
		if(str.indexOf("/stars")!=-1){  
			System.out.println("包含"); 
		}else { 
			System.out.println("不包含"); 
		} 
		
		if(str.indexOf("/fans")!=-1){  
			System.out.println("包含"); 
		}else { 
			System.out.println("不包含"); 
		} 
		String s = "跨界冰雪王	、抢票、表白、打榜、应援、粉丝、福利、见面会、投票、会服、人气榜、前线、高清、前线、预览、轮博、净化、评论、粉丝、抓拍、接机、转发、福利、转发、关注、转发、好友、抽奖、刷榜、后援、饭团、小组、个站、分会、话题、家族";
		System.out.println("111  " + NLPIR.NLPIR_ParagraphProcess(s, 1));
	}
	
	/**
	 * scroll
	 */
	//@Test
	public void test6() throws IOException, ParseException {
		log.debug("测试6 scroll");
		Map<String, String> map = weiboStatsService.GetScrollId("2017-06-01 00:00:00", "2017-08-01 00:00:00","网易新闻","\"content_full\",\"video_links\",\"published_at\"");
		String total = map.get("total");
		String scrollId = map.get("scrollId");
		String scrollSize = map.get("scrollSize");
		System.out.println("tttttt  "  + total);
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		HttpEntity entity = new NStringEntity("{\"scroll\":\"50s\",\"scroll_id\":\""+scrollId+"\"}");
		for(int i=1;i<= (Long.valueOf(total)/Long.valueOf(scrollSize)+1);i++){
			Response indexResponse = restClient.performRequest(
	    		    "POST",
	    		    "/_search/scroll",
	    		    Collections.singletonMap("pretty", "true"),
	    		    entity);
			String json = EntityUtils.toString(indexResponse.getEntity());
			Map<String, Object> userMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
	    	String list = JSON.parseObject(userMap.get("hits").toString()).getString("hits");
	    	List<Object> lists =  JSON.parseArray(list);
	    	for (Object object : lists) {
	    		String video_links =  JSON.parseObject(JSON.parseObject(object.toString()).getString("_source")).getString("video_links");
	    		String content_full =  JSON.parseObject(JSON.parseObject(object.toString()).getString("_source")).getString("content_full");
	    		System.out.println("111  " + NLPIR.NLPIR_ParagraphProcess(content_full, 1));
	    	}
		}
		
	}
	
	/**
	 * sliced scroll
	 */
	//@Test
	public void test7() throws IOException, ParseException {
		log.debug("测试6 sliced scroll");
		Map<String, Map<String, String>> msliceds = weiboStatsService.GetSlicedScrollId("2017-06-01 00:00:00", "2017-08-01 00:00:00","网易新闻","\"content_full\",\"video_links\",\"published_at\"");
		System.out.println(msliceds.size());
		long a = 0;
		for (Entry<String, Map<String, String>> msliced: msliceds.entrySet()) {
			Map<String, String> map = msliced.getValue();
			String total = map.get("total");
			String scrollId = map.get("scrollId");
			String scrollSize = map.get("scrollSize");
			System.out.println("tttttt  "  + total);
			RestClient restClient = ESRestClientHelper.getInstance().getClient();
			HttpEntity entity = new NStringEntity("{\"scroll\":\"50s\",\"scroll_id\":\""+scrollId+"\"}");
			for(int i=1;i<= (Long.valueOf(total)/Long.valueOf(scrollSize)+1);i++){
				System.out.println("scroll_id 第几批："+i);
				Response indexResponse = restClient.performRequest(
		    		    "POST",
		    		    "/_search/scroll",
		    		    Collections.singletonMap("pretty", "true"),
		    		    entity);
				String json = EntityUtils.toString(indexResponse.getEntity());
				Map<String, Object> userMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
		    	String list = JSON.parseObject(userMap.get("hits").toString()).getString("hits");
		    	List<Object> lists =  JSON.parseArray(list);
		    	System.out.println("1111    "+i+ "   "  + lists.size());
			}
			a = a + Long.valueOf(total);
		}
		System.out.println("ttttt   "  +a);
	}
	
	
	/**
	 * scroll
	 */
	//@Test
	public void test8() throws IOException, ParseException {
		log.debug("测试8 scroll");
		Map<String, String> map = weiboStatsService.GetScrollId("2017-06-01 00:00:00", "2017-08-01 00:00:00","网易新闻","\"content_full\",\"video_links\",\"published_at\"");
		String total = map.get("total");
		String scrollId = map.get("scrollId");
		String scrollSize = map.get("scrollSize");
		System.out.println("tttttt  "  + total);
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		HttpEntity entity = new NStringEntity("{\"scroll\":\"50s\",\"scroll_id\":\""+scrollId+"\"}");
		long videoLinksNums = 0;//链接分类占比	
		long brandsNums = 0; //品牌分类占比
		long filmsNums = 0; //	明星分类占比	
		long starsNums = 0;//视频分类占比	
		long fansNums = 0;//粉丝分类占比	
		long otherNums = 0;//其他分类占比
		for(int i=1;i<= (Long.valueOf(total)/Long.valueOf(scrollSize)+1);i++){
			System.out.println("555    " + i);
			Response indexResponse = restClient.performRequest(
	    		    "POST",
	    		    "/_search/scroll",
	    		    Collections.singletonMap("pretty", "true"),
	    		    entity);
			String json = EntityUtils.toString(indexResponse.getEntity());
			Map<String, Object> userMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
	    	String list = JSON.parseObject(userMap.get("hits").toString()).getString("hits");
	    	List<Object> lists =  JSON.parseArray(list);
	    	for (Object object : lists) {
	    		List<Object> video_links =  JSON.parseArray(JSON.parseObject(JSON.parseObject(object.toString()).getString("_source")).getString("video_links"));
	    		String content_full =  JSON.parseObject(JSON.parseObject(object.toString()).getString("_source")).getString("content_full");
	    		String nlp = NLPIR.NLPIR_ParagraphProcess(content_full, 1);
	    		boolean flag = true;
	    		if(JSON.parseObject(JSON.parseObject(object.toString()).getString("_source")).containsKey("video_links")){
	    			if(!video_links.isEmpty()){
	    				videoLinksNums++;
	    				flag = false;
	    			}
	    		} 
	    		
	    		if(nlp.indexOf("/brands")!=-1){  
	    			brandsNums++;
	    			flag = false;
	    		} 
	    		
	    		if(nlp.indexOf("/films")!=-1){  
	    			filmsNums++;
	    			flag = false;
	    		} 
	    		
	    		if(nlp.indexOf("/stars")!=-1){  
	    			starsNums++;
	    			flag = false;
	    		} 
	    		
	    		if(nlp.indexOf("/fans")!=-1){  
	    			fansNums++;
	    			flag = false;
	    		}
	    		
	    		if(flag){
	    			otherNums++;
	    		}
	    	}
		}
		System.out.println("1111  "   + videoLinksNums   + "  " +total +" " + (Double.valueOf(videoLinksNums)/Double.valueOf(total)));
		System.out.println("1111  "   + brandsNums    + "  " +total +" " + (Double.valueOf(brandsNums)/Double.valueOf(total)));
		System.out.println("1111  "   + filmsNums    + "  " +total +" " + (Double.valueOf(filmsNums)/Double.valueOf(total)));
		System.out.println("1111  "   + starsNums    + "  " +total +" " + (Double.valueOf(starsNums)/Double.valueOf(total)));
		System.out.println("1111  "   + fansNums     + "  " +total +" " + (Double.valueOf(fansNums)/Double.valueOf(total)));
		System.out.println("1111  "   + otherNums    + "  " +total +" " + (Double.valueOf(otherNums)/Double.valueOf(total)));
	}
	
	
	
	/**
	 * scroll
	 */
	//@Test
	public void test11() throws IOException, ParseException {
		log.debug("测试11 scroll");
		
		Map<String, Map<String, String>> rmap = Maps.newLinkedHashMap();
		
		Map<String, String> map = weiboStatsService.GetScrollId("2017-06-01 00:00:00", "2017-08-01 00:00:00","网易新闻","\"content_full\",\"video_links\",\"published_at\"");
		String total = map.get("total");
		String scrollId = map.get("scrollId");
		String scrollSize = map.get("scrollSize");
		System.out.println("tttttt  "  + total);
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		HttpEntity entity = new NStringEntity("{\"scroll\":\"50s\",\"scroll_id\":\""+scrollId+"\"}");
		long videoLinksNums = 0;//链接分类占比	
		long brandsNums = 0; //品牌分类占比
		long filmsNums = 0; //	明星分类占比	
		long starsNums = 0;//视频分类占比	
		long fansNums = 0;//粉丝分类占比	
		long otherNums = 0;//其他分类占比
		
		Map<String, Long> videoLinksMap = Maps.newLinkedHashMap();
		Map<String, Long> brandsNumsMap = Maps.newLinkedHashMap();
		Map<String, Long> filmsNumsMap = Maps.newLinkedHashMap();
		Map<String, Long> starsNumsMap = Maps.newLinkedHashMap();
		Map<String, Long> fansNumsMap = Maps.newLinkedHashMap();
		Map<String, Long> otherNumsMap = Maps.newLinkedHashMap();
		List<String> data = JTime.days(JTime.excelDay("2017-06-01 00:00:00"), JTime.excelDay("2017-08-01 00:00:00"));
		for (String string : data) {
			videoLinksMap.put(string, Long.valueOf(0));
			brandsNumsMap.put(string, Long.valueOf(0));
			filmsNumsMap.put(string, Long.valueOf(0));
			starsNumsMap.put(string, Long.valueOf(0));
			fansNumsMap.put(string, Long.valueOf(0));
			otherNumsMap.put(string, Long.valueOf(0));
		}

		long videoLinksNums1 = 0;//链接分类占比	
		long brandsNums1 = 0; //品牌分类占比
		long filmsNums1 = 0; //	明星分类占比	
		long starsNums1 = 0;//视频分类占比	
		long fansNums1 = 0;//粉丝分类占比	
		long otherNums1 = 0;//其他分类占比
		
		for(int i=1;i<= (Long.valueOf(total)/Long.valueOf(scrollSize)+1);i++){
			System.out.println("scroll_id 第几批："+i+"，每批大小："+scrollSize);
			Response indexResponse = restClient.performRequest(
	    		    "POST",
	    		    "/_search/scroll",
	    		    Collections.singletonMap("pretty", "true"),
	    		    entity);
			String json = EntityUtils.toString(indexResponse.getEntity());
			Map<String, Object> userMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
	    	String list = JSON.parseObject(userMap.get("hits").toString()).getString("hits");
	    	List<Object> lists =  JSON.parseArray(list);
	    	for (Object object : lists) {
	    		List<Object> video_links =  JSON.parseArray(JSON.parseObject(JSON.parseObject(object.toString()).getString("_source")).getString("video_links"));
	    		String content_full =  JSON.parseObject(JSON.parseObject(object.toString()).getString("_source")).getString("content_full");
	    		String published_at =  JSON.parseObject(JSON.parseObject(object.toString()).getString("_source")).getString("published_at");
	    		String nlp = NLPIR.NLPIR_ParagraphProcess(content_full, 1);
	    		boolean flag = true;
	    		if(JSON.parseObject(JSON.parseObject(object.toString()).getString("_source")).containsKey("video_links")){
	    			if(!video_links.isEmpty()){
	    				String d = JTime.utc2Data(published_at);
	    				videoLinksNums = videoLinksMap.get(d) + 1;
	    				videoLinksMap.put(d, videoLinksNums);
	    				flag = false;
	    				videoLinksNums1++;
	    			}
	    		} 
	    		if(JSON.parseObject(JSON.parseObject(object.toString()).getString("_source")).containsKey("published_at")){
	    			String d = JTime.utc2Data(published_at);
		    		if(nlp.indexOf("/brands")!=-1){  
		    			brandsNums = brandsNumsMap.get(d) + 1;
		    			brandsNumsMap.put(d, brandsNums);
		    			flag = false;
		    			brandsNums1++;
		    		} 
		    		if(nlp.indexOf("/films")!=-1){  
		    			filmsNums = filmsNumsMap.get(d) + 1;
		    			filmsNumsMap.put(d, filmsNums);
		    			flag = false;
		    			filmsNums1++;
		    		} 
		    		if(nlp.indexOf("/stars")!=-1){  
		    			starsNums = starsNumsMap.get(d) + 1;
		    			starsNumsMap.put(d, starsNums);
		    			flag = false;
		    			starsNums1++;
		    		} 
		    		if(nlp.indexOf("/fans")!=-1){  
		    			fansNums = fansNumsMap.get(d) + 1;
		    			fansNumsMap.put(d, fansNums);
		    			flag = false;
		    			fansNums1++;
		    		}
		    		if(flag){
		    			otherNums = otherNumsMap.get(d) + 1;
		    			otherNumsMap.put(d, otherNums);
		    			otherNums1++;
		    		}
	    		}
	    	}
		}
		
		long a1 = 0;long a2 = 0;long a3 = 0;long a4 = 0;long a5 = 0;long a6 = 0;
		for (Entry<String, Long> string : videoLinksMap.entrySet()) {
			a1 = a1 + string.getValue();
		}
		for (Entry<String, Long> string : brandsNumsMap.entrySet()) {
			a2 = a2 + string.getValue();
		}
		for (Entry<String, Long> string : filmsNumsMap.entrySet()) {
			a3 = a3 + string.getValue();
		}
		for (Entry<String, Long> string : starsNumsMap.entrySet()) {
			a4 = a4 + string.getValue();
		}
		for (Entry<String, Long> string : fansNumsMap.entrySet()) {
			a5 = a5 + string.getValue();
		}
		for (Entry<String, Long> string : otherNumsMap.entrySet()) {
			a6 = a6 + string.getValue();
		}
		
		
		System.out.println(videoLinksNums1+" 1111  a1  "   + a1   + "  " +total +" " + (Double.valueOf(a1)/Double.valueOf(total)));
		System.out.println(brandsNums1 +" 1111  a2  "   + a2    + "  " +total +" " + (Double.valueOf(a2)/Double.valueOf(total)));
		System.out.println(filmsNums1 + " 1111  a3  "   + a3    + "  " +total +" " + (Double.valueOf(a3)/Double.valueOf(total)));
		System.out.println(starsNums1+ " 1111  a4  "   + a4    + "  " +total +" " + (Double.valueOf(a4)/Double.valueOf(total)));
		System.out.println(fansNums1 + " 1111  a5  "   + a5     + "  " +total +" " + (Double.valueOf(a5)/Double.valueOf(total)));
		System.out.println(otherNums1+" 1111  a6  "   + a6    + "  " +total +" " + (Double.valueOf(a6)/Double.valueOf(total)));
	}
	
	
	
	/**
	 * scroll 添加情感分析
	 */
	//@Test
	public void test12() throws IOException, ParseException {
		log.debug("测试12 scroll");
		
		Map<String, Map<String, String>> rmap = Maps.newLinkedHashMap();
		
		Map<String, String> map = weiboStatsService.GetScrollId("2017-06-01 00:00:00", "2017-08-01 00:00:00","网易新闻","\"content_full\",\"video_links\",\"published_at\"");
		String total = map.get("total");
		String scrollId = map.get("scrollId");
		String scrollSize = map.get("scrollSize");
		System.out.println("tttttt  "  + total);
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		HttpEntity entity = new NStringEntity("{\"scroll\":\"50s\",\"scroll_id\":\""+scrollId+"\"}");
		long videoLinksNums = 0;//链接分类占比	
		long brandsNums = 0; //品牌分类占比
		long filmsNums = 0; //	明星分类占比	
		long starsNums = 0;//视频分类占比	
		long fansNums = 0;//粉丝分类占比	
		long otherNums = 0;//其他分类占比
		long positiveNums = 0;//正面情绪占比
		long negativeNums = 0;//负面情绪占比
		long neutralNums = 0;//中立情绪占比
		
		Map<String, Long> videoLinksMap = Maps.newLinkedHashMap();
		Map<String, Long> brandsNumsMap = Maps.newLinkedHashMap();
		Map<String, Long> filmsNumsMap = Maps.newLinkedHashMap();
		Map<String, Long> starsNumsMap = Maps.newLinkedHashMap();
		Map<String, Long> fansNumsMap = Maps.newLinkedHashMap();
		Map<String, Long> otherNumsMap = Maps.newLinkedHashMap();
		List<String> data = JTime.days(JTime.excelDay("2017-06-01 00:00:00"), JTime.excelDay("2017-08-01 00:00:00"));
		for (String string : data) {
			videoLinksMap.put(string, Long.valueOf(0));
			brandsNumsMap.put(string, Long.valueOf(0));
			filmsNumsMap.put(string, Long.valueOf(0));
			starsNumsMap.put(string, Long.valueOf(0));
			fansNumsMap.put(string, Long.valueOf(0));
			otherNumsMap.put(string, Long.valueOf(0));
		}

		long videoLinksNums1 = 0;//链接分类占比	
		long brandsNums1 = 0; //品牌分类占比
		long filmsNums1 = 0; //	明星分类占比	
		long starsNums1 = 0;//视频分类占比	
		long fansNums1 = 0;//粉丝分类占比	
		long otherNums1 = 0;//其他分类占比
		
		for(int i=1;i<= (Long.valueOf(total)/Long.valueOf(scrollSize)+1);i++){
			System.out.println("scroll_id 第几批："+i+"，每批大小："+scrollSize);
			Response indexResponse = restClient.performRequest(
	    		    "POST",
	    		    "/_search/scroll",
	    		    Collections.singletonMap("pretty", "true"),
	    		    entity);
			String json = EntityUtils.toString(indexResponse.getEntity());
			Map<String, Object> userMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
	    	String list = JSON.parseObject(userMap.get("hits").toString()).getString("hits");
	    	List<Object> lists =  JSON.parseArray(list);
	    	for (Object object : lists) {
	    		List<Object> video_links =  JSON.parseArray(JSON.parseObject(JSON.parseObject(object.toString()).getString("_source")).getString("video_links"));
	    		String content_full =  JSON.parseObject(JSON.parseObject(object.toString()).getString("_source")).getString("content_full");
	    		String published_at =  JSON.parseObject(JSON.parseObject(object.toString()).getString("_source")).getString("published_at");
	    		
	    		double sentiment = Sentiment.getSentimentPoint(content_full);
		    	if(sentiment<0){
		    		negativeNums++;
		    	}else if(sentiment>0){
		    		positiveNums++;
		    	}else{
		    		neutralNums++;
		    	}
	    		
	    		String nlp = NLPIR.NLPIR_ParagraphProcess(content_full, 1);
	    		boolean flag = true;
	    		if(JSON.parseObject(JSON.parseObject(object.toString()).getString("_source")).containsKey("video_links")){
	    			if(!video_links.isEmpty()){
	    				String d = JTime.utc2Data(published_at);
	    				videoLinksNums = videoLinksMap.get(d) + 1;
	    				videoLinksMap.put(d, videoLinksNums);
	    				flag = false;
	    				videoLinksNums1++;
	    			}
	    		} 
	    		if(JSON.parseObject(JSON.parseObject(object.toString()).getString("_source")).containsKey("published_at")){
	    			String d = JTime.utc2Data(published_at);
		    		if(nlp.indexOf("/brands")!=-1){  
		    			brandsNums = brandsNumsMap.get(d) + 1;
		    			brandsNumsMap.put(d, brandsNums);
		    			flag = false;
		    			brandsNums1++;
		    		} 
		    		if(nlp.indexOf("/films")!=-1){  
		    			filmsNums = filmsNumsMap.get(d) + 1;
		    			filmsNumsMap.put(d, filmsNums);
		    			flag = false;
		    			filmsNums1++;
		    		} 
		    		if(nlp.indexOf("/stars")!=-1){  
		    			starsNums = starsNumsMap.get(d) + 1;
		    			starsNumsMap.put(d, starsNums);
		    			flag = false;
		    			starsNums1++;
		    		} 
		    		if(nlp.indexOf("/fans")!=-1){  
		    			fansNums = fansNumsMap.get(d) + 1;
		    			fansNumsMap.put(d, fansNums);
		    			flag = false;
		    			fansNums1++;
		    		}
		    		if(flag){
		    			otherNums = otherNumsMap.get(d) + 1;
		    			otherNumsMap.put(d, otherNums);
		    			otherNums1++;
		    		}
	    		}
	    	}
		}
		
		long a1 = 0;long a2 = 0;long a3 = 0;long a4 = 0;long a5 = 0;long a6 = 0;
		for (Entry<String, Long> string : videoLinksMap.entrySet()) {
			a1 = a1 + string.getValue();
		}
		for (Entry<String, Long> string : brandsNumsMap.entrySet()) {
			a2 = a2 + string.getValue();
		}
		for (Entry<String, Long> string : filmsNumsMap.entrySet()) {
			a3 = a3 + string.getValue();
		}
		for (Entry<String, Long> string : starsNumsMap.entrySet()) {
			a4 = a4 + string.getValue();
		}
		for (Entry<String, Long> string : fansNumsMap.entrySet()) {
			a5 = a5 + string.getValue();
		}
		for (Entry<String, Long> string : otherNumsMap.entrySet()) {
			a6 = a6 + string.getValue();
		}
		
		
		System.out.println(videoLinksNums1+" 1111  a1  "   + a1   + "  " +total +" " + (Double.valueOf(a1)/Double.valueOf(total)));
		System.out.println(brandsNums1 +" 1111  a2  "   + a2    + "  " +total +" " + (Double.valueOf(a2)/Double.valueOf(total)));
		System.out.println(filmsNums1 + " 1111  a3  "   + a3    + "  " +total +" " + (Double.valueOf(a3)/Double.valueOf(total)));
		System.out.println(starsNums1+ " 1111  a4  "   + a4    + "  " +total +" " + (Double.valueOf(a4)/Double.valueOf(total)));
		System.out.println(fansNums1 + " 1111  a5  "   + a5     + "  " +total +" " + (Double.valueOf(a5)/Double.valueOf(total)));
		System.out.println(otherNums1+" 1111  a6  "   + a6    + "  " +total +" " + (Double.valueOf(a6)/Double.valueOf(total)));
		System.out.println("1111 正面情绪 "   + positiveNums + "  " +total +" " + (Double.valueOf(positiveNums)/Double.valueOf(total)));
		System.out.println("1111 负面情绪 "   + negativeNums + "  " +total +" " + (Double.valueOf(negativeNums)/Double.valueOf(total)));
		System.out.println("1111 中立情绪 "   + neutralNums  + "  " +total +" " + (Double.valueOf(neutralNums)/Double.valueOf(total)));
		System.out.println("1111  "+(100*(Double.valueOf(positiveNums)/Double.valueOf(total))-(Double.valueOf(negativeNums)/Double.valueOf(total))));
	}
	
	@Autowired
	private WordsSencondDao WordsSencondDao;
	
	@Test
	public void test17() throws IOException, ParseException, NumberFormatException, java.text.ParseException {
		log.debug("测试2 每天实际声量+提及账号数");
/*		
		Map<String, String> coefficient = weiboStatsService.coefficient("2017-08-01 00:00:00", "2017-10-01 00:00:00");
		
		Map<String, Map<String, String>> map = weiboStatsService.account("2017-08-01 00:00:00", "2017-10-01 00:00:00", "哈士奇",coefficient);
		Map<String, Map<String, String>> map4 = weiboStatsService.actualVolumeAndCommentAndUpAndRepost("2017-08-01 00:00:00", "2017-10-01 00:00:00", "哈士奇",coefficient);

		for (Map.Entry<String, String> entry : map.get("accountNumMap").entrySet()) {  
		    System.out.println("accountNumMap Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
		
		for (Map.Entry<String, String> entry : map4.get("commentMap").entrySet()) {  
		    System.out.println("commentMap Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
		
		for (Map.Entry<String, String> entry : map4.get("upMap").entrySet()) {  
		    System.out.println("upMap Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
		
		for (Map.Entry<String, String> entry : map4.get("repostMap").entrySet()) {  
		    System.out.println("repostMap Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
		
		List<WordsTaskSencond> wordsTaskSenconds1 = wordsTaskSencondService.findByCategoryAndIvstDuration("ivst", "1");
		System.out.println(wordsTaskSenconds1.size() + " " +wordsTaskSenconds1.get(0).getTaskType() );
		List<WordsTaskSencond> wordsTaskSenconds2 = wordsTaskSencondService.findByCategoryAndIvstDuration("ivst", "0");
		System.out.println(wordsTaskSenconds2.size() + " " +wordsTaskSenconds2.get(0).getTaskType() );
		*/
		
		//Map<String, String> coefficient1 = weiboStatsService.coefficient("2014-06-01 00:00:00", "2017-10-01 00:00:00");
		//Map<String, Map<String, String>> map5 = weiboStatsService.account("2014-06-01 00:00:00", "2017-10-01 00:00:00", "鹿晗",coefficient1);
		
/*		
		Map<String, String> coefficient2 = weiboStatsService.coefficient("2016-04-01 00:00:00", "2016-05-01 00:00:00");
		Map<String, Map<String, String>> dayMap = weiboStatsService.exposureAndIndependentPercentageAggs("2016-04-01 00:00:00", "2016-05-01 00:00:00", "鹿晗",coefficient2);
		Map<String, String> exposureMap = Maps.newLinkedHashMap();
		Map<String, String> independentPercentageMap = Maps.newLinkedHashMap();
		exposureMap.putAll(dayMap.get("exposure"));
		independentPercentageMap.putAll(dayMap.get("independentPercentage"));*/
		
		//无限，是否已添加
		List<WordsTaskSencond> wordsTaskSenconds1 = wordsTaskSencondService.findByCategoryAndCategoryDurationAndAndStartDate("哈士奇", "2017-09-02","ivst");
		System.out.println(wordsTaskSenconds1.size());
		//有限，是否已添加
		List<WordsTaskSencond> wordsTaskSenconds2 = wordsTaskSencondService.findByCategoryAndCategoryDurationAndAndStartDate("哈士奇", "2017-08-30","2017-09-10","ivst");
		System.out.println(wordsTaskSenconds2.size());
	}
	
	//@Test
	public void test18() throws IOException, ParseException, NumberFormatException, java.text.ParseException {
		String test = "http://"+InetAddress.getLocalHost().getHostAddress()+"/download/";
		System.out.println(test);
		
		Map<String, String> map = Maps.newLinkedHashMap();
		List<String> dates = new ArrayList<>();
		dates = JTime.days(JTime.excelDay("2017-10-01"), JTime.excelDay(LocalDate.now().toString()),10);
		for (String date : dates) {
			for (Map.Entry<String, String> entry : weiboStatsService.countPerdays(date, JTime.nextNDayCoefficient(date, LocalDate.now().toString(), 10)).entrySet()) {  
			    //es当天文章总量
			    double a = Double.parseDouble(entry.getValue());
			    //微博实际当天文章总量（根据系数调整）
			    double b = WeiboBaseService.baseRatio.get(JTime.getWeekOfDate(entry.getKey())-1);
			    String delta = new String();
				if(a < 1.5*b){
					delta =  String.valueOf(b/a*weiboStatsService.getWeiboerRatio(JTime.esDay(entry.getKey())));
				}else{
					delta = String.valueOf(weiboStatsService.getWeiboerRatio(JTime.esDay(entry.getKey())));
				}
				map.put(entry.getKey(), delta);
			}
		}
		map.put(LocalDate.now().plusDays(1).toString(), "0");
        RestClient restClient = ESRestClientHelper.getInstance().getClient();
        StringBuilder bulkRequestBody = new StringBuilder();
        for (Entry<String, String> m : map.entrySet()) {
    		StringBuilder s = new StringBuilder();
    		s.append("weibo");
    		s.append(m.getKey());
    		String asB64 = Base64.getEncoder().encodeToString(s.toString().getBytes("utf-8"));
            bulkRequestBody.append("{ \"update\" : {\"_id\":\"" +asB64 + "\",\"_retry_on_conflict\" : 3} }");
            bulkRequestBody.append("\n");
            bulkRequestBody.append("{ \"doc\" : {\"type\" : \"weibo\",\"key\" : \""+m.getKey()+"\",\"value\" : \""+m.getValue()+"\"}, \"doc_as_upsert\" : true }");
            bulkRequestBody.append("\n");
        }
        HttpEntity entity = new NStringEntity(bulkRequestBody.toString(), ContentType.APPLICATION_JSON);
        restClient.performRequest("POST", "/cibao_coefficient/cibao_coefficient/_bulk", Collections.<String, String>emptyMap(), entity);
        restClient.performRequest("POST", "/cibao_coefficient/_refresh");

		Map<String, String> coefficient = weiboStatsService.coefficient("2017-09-25 00:00:00", "2017-10-20 00:00:00");
		for (Map.Entry<String, String> entry : coefficient.entrySet()) {  
		    System.out.println("coefficient Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
		
		Map<String, String> actualVolume = weiboStatsService.actualVolume("2017-09-25 00:00:00", "2017-10-20 00:00:00", "大话西游之爱你一万年");

		for (Map.Entry<String, String> entry : actualVolume.entrySet()) {  
		    System.out.println("actualVolume Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
		
		Map<String, String> volume = weiboStatsService.volume("2017-09-25 00:00:00", "2017-10-20 00:00:00", "大话西游之爱你一万年");
		for (Map.Entry<String, String> entry : volume.entrySet()) {  
		    System.out.println("volume Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
	}
	
	
	@Test
	public void test19() throws IOException, NumberFormatException, java.text.ParseException   {	
/*		Map<String, String> coefficient = weiboStatsService.coefficient("2017-10-01 00:00:00", "2017-10-22 00:00:00");
		for (Map.Entry<String, String> entry : coefficient.entrySet()) {  
		    System.out.println("coefficient Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
		Map<String, Map<String, String>> map = weiboStatsService.account("2017-10-01 00:00:00", "2017-10-22 00:00:00","明日之子",coefficient);
		for (Map.Entry<String, String> entry : map.get("accountNumMap").entrySet()) {  
		    System.out.println("accountNumMap Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}*/
		
/*		String stDate = LocalDate.now().plusDays(-7).toString();
		String enDate = LocalDate.now().toString();
		Map<String, String> map1 = weixinStatsService.coefficient(JTime.esDay(stDate),  JTime.esNextDay(enDate) );*/
        Field[] fields =  TextEsWeiboIndex.class.getDeclaredFields();
        for (Field field: fields) {
        	System.out.println(field.getName());
        }
	}
}
