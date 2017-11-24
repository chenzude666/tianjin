package com.stq.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.RestClient;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.stq.entity.stq.words.WordsWeiboUserAnalysis;
import com.stq.service.stq.words.WordsBaseService;
import com.stq.service.stq.words.weibo.WeiboStatsService;
import com.stq.util.ESRestClientHelper;
import com.stq.util.JTime;
import com.stq.util.ShortUtil;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class WeiboStatsServiceTest extends SpringTransactionalTestCase{
	
	@Autowired
	private WeiboStatsService weiboStatsService;
	
	@Autowired
	private WordsBaseService wordsBaseService;
	
	@Test
	public void test1() throws NumberFormatException, IOException, ParseException{
		String endDate = JTime.esDay(LocalDate.now().toString());
		List<WordsWeiboUserAnalysis> userAnalysis = weiboStatsService.userAnalysis(endDate, "冰河追凶");
		System.out.println("11  "  + JSON.toJSONString(userAnalysis));
		//PostUpsertBulk(getWeiboUserAnalysisEsList(userAnalysis));
		//System.out.println(wordsBaseService.isResultEnoughByWeiboUserAnalysis("守望", "weibo"));
	}
    
    public List<Map<String, Object>> getWeiboUserAnalysisEsList(List<WordsWeiboUserAnalysis> wordsWeiboUserAnalysis) throws IOException{
    	List<Map<String, Object>> esList = new ArrayList<>();
    	for (WordsWeiboUserAnalysis weiboUserAnalysis : wordsWeiboUserAnalysis) {
    		Map<String, Object> m = Maps.newLinkedHashMap();
        	String updateDate = LocalDate.now().toString(); //任务更新时间
    		StringBuilder s = new StringBuilder();
    		s.append(ShortUtil.getShortUrl(weiboUserAnalysis.getKeywords()));
    		s.append(weiboUserAnalysis.getSource());
    		String asB64 = Base64.getEncoder().encodeToString(s.toString().getBytes("utf-8"));
    		weiboUserAnalysis.setStartDate(JTime.date2TimeStamp(weiboUserAnalysis.getStartDateString()));
    		weiboUserAnalysis.setEndDate(JTime.date2TimeStamp(weiboUserAnalysis.getEndDateString()));
    		weiboUserAnalysis.setUpdateDate(JTime.date2TimeStamp(updateDate));
    		weiboUserAnalysis.setUpdateDateString(updateDate);
    		m.put("esId", "\""+asB64+"\"");
    		m.put("esDoc", weiboUserAnalysis);
    		esList.add(m);
		}
		return esList;
    }
    
    /**
     *
     * @param esList
     * @throws IOException
     */
    public void PostUpsertBulk(List<Map<String, Object>> esList) throws IOException {
        RestClient restClient = ESRestClientHelper.getInstance().getClient();
        StringBuilder bulkRequestBody = new StringBuilder();
        for (Map<String, Object> map : esList) {
            bulkRequestBody.append("{ \"update\" : {\"_id\":" + map.get("esId") + ",\"_retry_on_conflict\" : 3} }");
            bulkRequestBody.append("\n");
            bulkRequestBody.append("{ \"doc\" : "+ JSON.toJSONString(map.get("esDoc")) +", \"doc_as_upsert\" : true }");
            bulkRequestBody.append("\n");
        }
        HttpEntity entity = new NStringEntity(bulkRequestBody.toString(), ContentType.APPLICATION_JSON);
        restClient.performRequest("POST", "/cibao_user_analysis/cibao_user_analysis/_bulk", Collections.<String, String>emptyMap(), entity);
        restClient.performRequest("POST", "/cibao_user_analysis/_refresh");
    }
}
