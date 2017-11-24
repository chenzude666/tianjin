package com.stq;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.stq.service.stq.words.weixin.WeixinStatsService;
import com.stq.util.JTime;
import com.sun.el.parser.ParseException;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class EsWeixinTest extends SpringTransactionalTestCase{
	
	private static final Logger log = LoggerFactory.getLogger(EsWeixinTest.class);
	
	@Autowired
	private WeixinStatsService weixinStatsService;
	
	
	//@Test
	public void test1() throws IOException, ParseException, NumberFormatException, java.text.ParseException {	
		log.debug("测试2 每天总文章量");
		Map<String, String> map2 = weixinStatsService.countPerdays("2017-06-01 00:00:00", "2017-08-01 00:00:00");
		for (Map.Entry<String, String> entry : map2.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
	}
	
	//@Test
	public void test2() throws IOException, ParseException, NumberFormatException, java.text.ParseException {	
		log.debug("测试2 实际声量");
		Map<String, String> map2 = weixinStatsService.actualVolume("2017-08-01 00:00:00", "2017-08-16 00:00:00","搜狐新闻");
		for (Map.Entry<String, String> entry : map2.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
	}
	
	@Test
	public void test3() throws IOException, ParseException, NumberFormatException, java.text.ParseException {	
		log.debug("测试2 声量 系数");
		Map<String, String> map2 = weixinStatsService.coefficient("2017-08-01 00:00:00", "2017-08-30 00:00:00");
		for (Map.Entry<String, String> entry : map2.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
		
		log.debug("测试2 每天总文章量");
		Map<String, String> map3 = weixinStatsService.countPerdays("2017-08-01 00:00:00", "2017-08-30 00:00:00");
		for (Map.Entry<String, String> entry : map3.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
	}
}
