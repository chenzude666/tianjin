package com.stq.service.stq.stats;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.stq.service.stq.stats.EsQueryHttpEntity;
import com.stq.service.stq.words.weibo.WeiboStatsService;
import com.stq.util.ESRestClientHelper;

public class EsQueryHttpEntityTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetWeiboKolAggQueryHttpEntity() throws Exception {
		String originalStr = "{\"query\": {\"bool\": {\"filter\": [{\"range\": {\"published_at\": {\"gte\": \"2017-08-01\",\"lte\": \"2017-08-31\",\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\",\"time_zone\": \"Asia/Shanghai\"}}}],\"must\": [{\"terms\": {\"weiboer_id\":[\"1782599645\",\"1779837945\",\"3767561485\"]}}]}},\"aggs\": {\"groupByWeiboerId\": {\"terms\": {\"field\": \"weiboer_id\",\"size\": \"3\",\"execution_hint\": \"map\"},\"aggs\": {\"document_count\": {\"sum\": {\"script\": \"1\"}},\"avg_doc_count_per_week\": {\"bucket_script\": {\"buckets_path\": {\"documentCount\": \"document_count\"},\"script\": \"params.documentCount/4\"}},\"follower_count\": {\"max\": {\"field\": \"follower_count\",\"missing\": 0}},\"repost_count_sum\": {\"sum\": {\"field\": \"repost_count\",\"missing\": 0}},\"repost_count_avg\": {\"avg\": {\"field\": \"repost_count\",\"missing\": 0}},\"repost_count_max\": {\"max\": {\"field\": \"repost_count\",\"missing\": 0}},\"repost_level_avg\": {\"avg\": {\"field\": \"repost_level\",\"missing\": 0}},\"repost_level_max\": {\"max\": {\"field\": \"repost_level\",\"missing\": 0}},\"comment_count_sum\": {\"sum\": {\"field\": \"comment_count\",\"missing\": 0}},\"comment_count_avg\": {\"avg\": {\"field\": \"comment_count\",\"missing\": 0}},\"comment_count_max\": {\"max\": {\"field\": \"comment_count\",\"missing\": 0}},\"up_count_sum\": {\"sum\": {\"field\": \"up_count\",\"missing\": 0}},\"up_count_avg\": {\"avg\": {\"field\": \"up_count\",\"missing\": 0}},\"up_count_max\": {\"max\": {\"field\": \"up_count\",\"missing\": 0}},\"expose\": {\"bucket_script\": {\"buckets_path\": {\"n\": \"avg_doc_count_per_week\",\"F\": \"follower_count\",\"documentCount\": \"document_count\"},\"script\": \"Math.pow(0.4 * Math.log(params.n+1) + 0.6 * Math.log(params.F+1), 2)\"}},\"repost\": {\"bucket_script\": {\"buckets_path\": {\"R\": \"repost_count_sum\",\"Rn\": \"repost_count_avg\",\"Rmax\": \"repost_count_max\",\"Ln\": \"repost_level_avg\",\"Lmax\": \"repost_level_max\"},\"script\": \"Math.pow(0.25 * Math.log(params.R+1) + 0.35 * Math.log(params.Rn+1) + 0.15 * Math.log(params.Rmax+1) + 0.15 * Math.log(100*params.Ln+1) + 0.10 * Math.log(100*params.Lmax+1), 2)\"}},\"comment\": {\"bucket_script\": {\"buckets_path\": {\"C\": \"comment_count_sum\",\"Cn\": \"comment_count_avg\",\"Cmax\": \"comment_count_max\"},\"script\": \"Math.pow(0.3 * Math.log(params.C+1) + 0.55 * Math.log(params.Cn+1) + 0.15 * Math.log(params.Cmax+1), 2)\"}},\"up\": {\"bucket_script\": {\"buckets_path\": {\"Z\": \"up_count_sum\",\"Zn\": \"up_count_avg\",\"Zmax\": \"up_count_max\"},\"script\": \"Math.pow(0.30 * Math.log(10*params.Z+1) + 0.55 * Math.log(10*params.Zn+1) + 0.15 * Math.log(10*params.Zmax+1), 2)\"}}}}}}";
		HttpEntity entity = EsQueryHttpEntity.getWeiboKolAggQueryHttpEntity("2017-08-01", "2017-08-31", new String[] {"1782599645", "1779837945", "3767561485"});
		InputStream content = entity.getContent();
		String contentStr = IOUtils.toString(content, StandardCharsets.UTF_8.name());
		assertEquals(originalStr, contentStr);
	}
	
	@Test
	public void testGetWeixinKolAggQueryHttpEntity() throws Exception {
		String originalStr = "{\"query\": {\"constant_score\": {\"filter\": {\"bool\": {\"must\": [{\"range\": {\"last_modified_at\": {\"gte\": \"2017-08-01\",\"lte\": \"2017-08-31\",\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\",\"time_zone\": \"Asia/Shanghai\"}}},{\"terms\": {\"biz\":[\"1782599645\",\"1779837945\",\"3767561485\"]}}]}}}},\"aggs\": {\"groupByBiz\": {\"terms\": {\"field\": \"biz\",\"size\": \"3\",\"execution_hint\": \"map\"},\"aggs\": {\"document_count\": {\"sum\": {\"script\": \"1\"}},\"stat_read_count_value_count\": {\"sum\": {\"script\": \"if(doc['stat_read_count'].value>0){1}\"}},\"avg_doc_count_per_day\": {\"bucket_script\": {\"buckets_path\": {\"documentCount\": \"document_count\"},\"script\": \"params.documentCount/31\"}},\"stat_read_count_sum\": {\"sum\": {\"field\": \"stat_read_count\",\"missing\": 0}},\"fixed_stat_read_count_sum\": {\"bucket_script\": {\"buckets_path\": {\"documentCount\": \"document_count\",\"statReadCountValueCount\": \"stat_read_count_value_count\",\"statReadCountSum\": \"stat_read_count_sum\"},\"script\": \"params.statReadCountValueCount != 0 ? params.statReadCountSum * (params.documentCount / params.statReadCountValueCount) : 0\"}},\"stat_read_count_avg\": {\"avg\": {\"script\": \"if(doc['stat_read_count'].value>0){doc['stat_read_count'].value}\"}},\"fixed_stat_read_count_avg\": {\"bucket_script\": {\"buckets_path\": {\"statReadCountValueCount\": \"stat_read_count_value_count\",\"statReadCountSum\": \"stat_read_count_sum\"},\"script\": \"params.statReadCountValueCount != 0 ? params.statReadCountSum / params.statReadCountValueCount : 0\"}},\"max_stat_read_count\": {\"max\": {\"field\": \"stat_read_count\",\"missing\": 0}},\"stat_like_count_sum\": {\"sum\": {\"field\": \"stat_like_count\",\"missing\": 0}},\"stat_like_count_avg\": {\"avg\": {\"field\": \"stat_like_count\",\"missing\": 0}},\"max_stat_like_count\": {\"max\": {\"field\": \"stat_like_count\",\"missing\": 0}},\"postScore\": {\"bucket_script\": {\"buckets_path\": {\"documentCount\": \"document_count\"},\"script\": \"Math.pow(Math.log(params.documentCount+1),2)\"}},\"readScore\": {\"bucket_script\": {\"buckets_path\": {\"fixedStatReadCountSum\": \"fixed_stat_read_count_sum\",\"fixedStatReadCountAvg\": \"fixed_stat_read_count_avg\",\"maxStatReadCount\": \"max_stat_read_count\"},\"script\": \"Math.pow(0.35*Math.log(params.fixedStatReadCountSum+1) + 0.5*Math.log(params.fixedStatReadCountAvg+1) + 0.15*Math.log(params.maxStatReadCount+1),2)\"}},\"upScore\": {\"bucket_script\": {\"buckets_path\": {\"statLikeCountSum\": \"stat_like_count_sum\",\"statLikeCountAvg\": \"stat_like_count_avg\",\"maxStatLikeCount\": \"max_stat_like_count\"},\"script\": \"Math.pow(0.35*Math.log(params.statLikeCountSum+1) + 0.5*Math.log(params.statLikeCountAvg+1) + 0.15*Math.log(params.maxStatLikeCount+1),2)\"}}}}}}";
		HttpEntity entity = EsQueryHttpEntity.getWeixinKolAggQueryHttpEntity("2017-08-01", "2017-08-31", new String[] {"1782599645", "1779837945", "3767561485"});
		InputStream content = entity.getContent();
		String contentStr = IOUtils.toString(content, StandardCharsets.UTF_8.name());
		assertEquals(originalStr, contentStr);
	}
	
	@Test
	public void testGetUserInvolveAggQueryHttpEntity() throws Exception {
		String originalStr = "{\"query\": {\"bool\": {\"filter\": [{\"range\": {\"published_at\": {\"gte\": \"2017-08-01\",\"lte\": \"2017-08-31\",\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\",\"time_zone\": \"Asia/Shanghai\"}}}],\"must\": [{\"match_phrase\": {\"content_full\": \"//@安徽卫视\"}}]}}}";
		HttpEntity entity = EsQueryHttpEntity.getUserInvolveAggQueryHttpEntity("2017-08-01", "2017-08-31", "安徽卫视", "10000");
		InputStream content = entity.getContent();
		String contentStr = IOUtils.toString(content, StandardCharsets.UTF_8.name());
		assertEquals(originalStr, contentStr);
	}
	
	@Test
	public void testGetUserInvolveAggQueryHttpEntity2() throws Exception {
		HttpEntity entity = EsQueryHttpEntity.getUserInvolveAggQueryHttpEntity("2017-08-01", "2017-08-31", "安徽卫视", 318);
		InputStream content = entity.getContent();
		String contentStr = IOUtils.toString(content, StandardCharsets.UTF_8.name());
		System.out.println(contentStr);
	}
	
	@Test
	public void testGetRepostLevelAggQueryHttpEntity() throws Exception {
		String originalStr = "{\"query\": {\"bool\": {\"filter\": [{\"range\": {\"published_at\": {\"gte\": \"2017-08-01\",\"lte\": \"2017-08-31\",\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\",\"time_zone\": \"Asia/Shanghai\"}}}],\"must\": [{\"match_phrase\": {\"content_full\": \"//@安徽卫视\"}}]}},\"aggs\": {\"groupByNothing\": {\"terms\": {\"script\": \"'a big trick'\"},\"aggs\": {\"document_count\": {\"sum\": {\"script\": \"1\"}},\"repost_level_1_count\": {\"sum\": {\"script\": \"if(doc['repost_level'].value==1){1}\"}},\"repost_level_2_count\": {\"sum\": {\"script\": \"if(doc['repost_level'].value==2){1}\"}},\"repost_level_3_count\": {\"sum\": {\"script\": \"if(doc['repost_level'].value>=3){1}\"}},\"repost_level_ratio\": {\"bucket_script\": {\"buckets_path\": {\"documentCount\": \"document_count\",\"repostLevel1Count\": \"repost_level_1_count\",\"repostLevel2Count\": \"repost_level_2_count\",\"repostLevel3Count\": \"repost_level_3_count\"},\"script\":\"params.repostLevel1Count / params.documentCount * 50 + params.repostLevel2Count / params.documentCount * 80 + params.repostLevel3Count / params.documentCount * 100\"}}}}}}";
		HttpEntity entity = EsQueryHttpEntity.getRepostLevelAggQueryHttpEntity("2017-08-01", "2017-08-31", "安徽卫视");
		InputStream content = entity.getContent();
		String contentStr = IOUtils.toString(content, StandardCharsets.UTF_8.name());
		assertEquals(originalStr, contentStr);
	}
	
	@Test
	public void testGetUserInvolveCountQueryHttpEntity() throws Exception {
		HttpEntity entity = EsQueryHttpEntity.getUserInvolveArticleCountHttpEntity("2017-08-01", "2017-08-31", "安徽卫视");
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		Map<String, String> params = new HashMap<String, String>();
		Response countResponse = null;
		String json = null;
		try {
			countResponse = restClient.performRequest("POST",
					String.format("/%s/%s/_count", "weibo_articles_and_weiboers",
							"weibo_articles_and_weiboer"),
					params, entity);
			json = EntityUtils.toString(countResponse.getEntity());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> resultMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
		});
		int count = (int)resultMap.get("count");
		System.out.println(count);
	}
	
	@Test
	public void testGetUserInvolveAggQueryHttpEntity21() throws Exception {
		HttpEntity entity = EsQueryHttpEntity.getUserInvolveAggQueryHttpEntity("2017-08-01", "2017-08-31", "安徽卫视", 318);
		RestClient restClient = ESRestClientHelper.getInstance().getClient();
		Map<String, String> params = new HashMap<String, String>();
		String json = null;
		params.put("size", "0");
		Response aggResponse = null;
		try {
			aggResponse = restClient.performRequest("POST",
					String.format("/%s/%s/_search", "weibo_articles_and_weiboers",
							"weibo_articles_and_weiboer"),
					params, entity);
			json = EntityUtils.toString(aggResponse.getEntity());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> resultMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
		String list = JSON.parseObject(JSON.parseObject(resultMap.get("aggregations").toString()).getString("groupByWeiboerId"))
				.getString("buckets");
		List<Object> _list = JSON.parseArray(list);
		int weiboerCount = _list.size();
		System.out.println(weiboerCount);
	}
	
	@Test
	public void testGetQueryByWeiboerIdHttpEntity() throws Exception {
		String originalStr = "{\"query\": {\"bool\": {\"filter\": [{\"range\": {\"published_at\": {\"gte\": \"2017-08-01\",\"lte\": \"2017-08-31\",\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\",\"time_zone\": \"Asia/Shanghai\"}}}],\"must\": [{\"term\": {\"weiboer_id\":1782599645}}]}},\"size\":\"10000\"}";
		HttpEntity entity = EsQueryHttpEntity.getQueryByWeiboerIdHttpEntity("2017-08-01", "2017-08-31", "1782599645", "10000");
		InputStream content = entity.getContent();
		String contentStr = IOUtils.toString(content, StandardCharsets.UTF_8.name());
		assertEquals(originalStr, contentStr);
	}
	
	@Test
	public void testGetTopicVolumeHttpEntity() throws Exception {
		String originalStr = "{\"query\": {\"bool\": {\"filter\": [{\"range\": {\"published_at\": {\"gte\": \"2017-08-01\",\"lte\": \"2017-08-31\",\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\",\"time_zone\": \"Asia/Shanghai\"}}}],\"must\": [{\"match_phrase\": {\"content_full\": \"#十九大#\"}}]}},\"aggs\": {\"daily_volumn\": {\"date_histogram\": {\"field\": \"published_at\",\"interval\": \"day\",\"format\": \"yyyy-M-d\",\"time_zone\": \"+08:00\",\"min_doc_count\": 0,\"extended_bounds\": {\"min\": 1501516800000,\"max\": 1504108800000}}}}}";
		HttpEntity entity = EsQueryHttpEntity.getTopicVolumeHttpEntity("2017-08-01", "2017-08-31", "#十九大#");
		InputStream content = entity.getContent();
		String contentStr = IOUtils.toString(content, StandardCharsets.UTF_8.name());
		System.out.println(contentStr);

		WeiboStatsService weiboStatsService = new WeiboStatsService();
		Map<String, String> coefficient = weiboStatsService.coefficient("2017-08-01 00:00:00", "2017-08-31 00:00:00");
		
		System.out.println(coefficient);
		System.out.println(Double.parseDouble(coefficient.get("2017-08-01")));
		
		assertEquals(originalStr, contentStr);
		
		
	}

}
