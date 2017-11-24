package com.stq.test;

import java.io.IOException;
import java.text.ParseException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.stq.service.stq.words.weixin.WeixinStatsService;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class WeixinStatsServiceTest extends SpringTransactionalTestCase{
	
	@Autowired
	private WeixinStatsService weixinStatsService;
	
	@Test
	public void test() throws NumberFormatException, IOException, ParseException{
		weixinStatsService.sentimentAndNipir("2017-06-01 00:00:00", "2017-07-01 00:00:00", "守望");
	}
}
