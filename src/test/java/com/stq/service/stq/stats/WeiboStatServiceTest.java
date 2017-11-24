package com.stq.service.stq.stats;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.stq.entity.stq.stats.WeiboAccountStat;
import com.stq.entity.stq.stats.WeiboTopicStat;

public class WeiboStatServiceTest {

	private String[] urls;

	@Before
	public void setUp() throws Exception {
		urls = new String[]{
//			"http://weibo.com/ahtv01",
//			"http://weibo.com/btvbeijing",
			"http://weibo.com/u/3767561485",
			"http://weibo.com/337579777"
		};

//		2016.11.02   2017.03.02   http://weibo.com/ahtv01
//		2016.11.02   2017.11.02  http://weibo.com/btvbeijing
//		2016.11.02   2017.11.02  http://weibo.com/u/3767561485
//		2016.11.02   2017.11.02  http://weibo.com/337579777
	}

	@After
	public void tearDown() throws Exception {
		urls = null;
	}

	@Test
	public void testGetWeiboKol() throws Exception {
		WeiboStatService service = new WeiboStatService("2017-08-01", "2017-08-31", urls);
		List<WeiboAccountStat> result = service.getWeiboKol();
		result.stream().forEach(System.out::println);
//		assertEquals(3, result.size());
	}
	
	@Test
	public void testGetWeiboKol2() throws Exception {
		WeiboStatService service = new WeiboStatService();
		List<WeiboAccountStat> result = service.getWeiboKol("2017-08-01", "2017-08-31", urls);
		result.stream().forEach(System.out::println);
//		assertEquals(4, result.size());
	}

	@Test
	public void testGetWeiboKolFromEmptyIds() throws Exception {
		WeiboStatService service = new WeiboStatService();
		List<WeiboAccountStat> result = service.getWeiboKol("2017-08-01", "2017-08-31", new String[]{});
		result.stream().forEach(System.out::println);
		assertEquals(0, result.size());
	}

//	@Test
//	public void testNoDocumentWeiboerKol() throws Exception {
//		WeiboAccountStatService service = new WeiboAccountStatService("2017-08-01", "2017-08-31", urls);
//		Map<String, WeiboAccountStat> stats = service.getWeiboAccountStats();
//		List<Object> lists = service.calculateKol(stats);
//		
//		String[] urlsPlus1 = new String[] { "http://weibo.com/gdws", "http://weibo.com/ahtv01", "http://weibo.com/btvbeijing", "http://weibo.com/u/3767561485" };
//		Map<String, WeiboAccountStat> statsPlus1 = WeiboAccountStatService.getWeiboAccountStats(urlsPlus1);
//		List<WeiboAccountStat> result = service.processEsResponse(lists, statsPlus1);
//		result.stream().forEach(System.out::println);
//		assertEquals(4, result.size());
//	}
//	
//	@Test
//	public void testNoDocumentWeiboerKol2() throws Exception {
//		WeiboAccountStatService service = new WeiboAccountStatService();
//		Map<String, WeiboAccountStat> stats = WeiboAccountStatService.getWeiboAccountStats(urls);
//		List<Object> lists = service.calculateKol(stats, "2017-08-01", "2017-08-31");
//		
//		String[] urlsPlus1 = new String[] { "http://weibo.com/gdws", "http://weibo.com/ahtv01", "http://weibo.com/btvbeijing", "http://weibo.com/u/3767561485" };
//		Map<String, WeiboAccountStat> statsPlus1 = WeiboAccountStatService.getWeiboAccountStats(urlsPlus1);
//		List<WeiboAccountStat> result = service.processEsResponse(lists, statsPlus1);
//		result.stream().forEach(System.out::println);
//		assertEquals(4, result.size());
//	}

	@Test
	public void testGetUserInvolve() throws Exception {
		WeiboStatService service = new WeiboStatService("2017-08-01", "2017-08-31", urls);
		List<WeiboAccountStat> result = service.getUserInvolve();
		result.stream().forEach(System.out::println);
//		assertEquals(3, result.size());
	}
	
	@Test
	public void testGetUserInvolve2() throws Exception {
		WeiboStatService service = new WeiboStatService();
		List<WeiboAccountStat> result = service.getUserInvolve("2017-08-01", "2017-08-31", urls);
		result.stream().forEach(System.out::println);
//		assertEquals(4, result.size());
	}

	@Test
	public void testGetUserInvolveFromEmptyIds() throws Exception {
		WeiboStatService service = new WeiboStatService();
		List<WeiboAccountStat> result = service.getUserInvolve("2017-08-01", "2017-08-31", new String[]{});
		result.stream().forEach(System.out::println);
//		assertEquals(0, result.size());
	}
	
	@Test
	public void testGetUserInvolve3() throws Exception {
		WeiboStatService service = new WeiboStatService();
		List<WeiboAccountStat> result = service.getUserInvolveByScroll("2017-08-01", "2017-08-31", urls);
		result.stream().forEach(System.out::println);
//		assertEquals(3, result.size());
	}
	
	@Test
	public void testGetSpreadDeepness() throws Exception {
		WeiboStatService service = new WeiboStatService("2017-08-01", "2017-08-31", urls);
		List<WeiboAccountStat> result = service.getSpreadDeepness();
		result.stream().forEach(System.out::println);
//		assertEquals(3, result.size());
	}
	
	@Test
	public void testGetSpreadDeepness2() throws Exception {
		WeiboStatService service = new WeiboStatService();
		List<WeiboAccountStat> result = service.getSpreadDeepness("2017-08-01", "2017-08-31", urls);
		result.stream().forEach(System.out::println);
//		assertEquals(3, result.size());
	}
	
	@Test
	public void testGetTopicFreq() throws Exception {
		WeiboStatService service = new WeiboStatService();
		List<WeiboTopicStat> result = service.getTopicFreq("2017-08-01", "2017-08-31", "http://weibo.com/ahtv01");
		result.stream().forEach(System.out::println);
//		assertEquals(3, result.size());
	}
	
	@Test
	public void testGetTopicVolume() throws Exception {
		WeiboStatService service = new WeiboStatService();
		List<WeiboTopicStat> result = service.getTopicVolume("2017-08-01", "2017-08-31", new String[] {"#十九大#", "#一带一路#"});
		result.stream().forEach(System.out::println);
	}

	@Test
	public void testGetTopicFreqVolume() throws Exception {
		WeiboStatService service = new WeiboStatService();
		List<WeiboTopicStat> result = service.getTopicFreqVolume("2016-11-02", "2017-11-02", urls);
		result.stream().forEach(System.out::println);
	}

	@Test
	public void testGetTopicFreqVolumeMap() throws Exception {
		WeiboStatService service = new WeiboStatService();
		List<WeiboTopicStat> list = new ArrayList<>();

		WeiboTopicStat stat = new WeiboTopicStat();
		stat.setUrl(urls[0]);
		stat.setStartDate("2017-08-01");
		stat.setEndDate("2017-08-05");
		list.add(stat);

		WeiboTopicStat stat1 = new WeiboTopicStat();
		stat1.setUrl(urls[1]);
		stat1.setStartDate("2017-08-10");
		stat1.setEndDate("2017-08-28");
		list.add(stat1);

		List<WeiboTopicStat> result = service.getTopicFreqVolume(list);
		result.stream().forEach(System.out::println);
	}

	@Test
	public void testEscape(){
		String a = "#地铁一号线首列样车完成第一轮\"试跑\" 下轮\"试跑\"将满载沙袋#";
		System.out.println(a.replaceAll("\"", "\\\\\""));
	}

}
