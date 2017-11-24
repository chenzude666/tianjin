package com.stq.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.stq.entity.stq.words.WordsEsWeiboIndex;
import com.stq.entity.stq.words.WordsTask;
import com.stq.service.stq.words.WordsTaskSencondService;
import com.stq.service.stq.words.WordsTaskService;
import com.stq.util.ExcelUtil;
import com.stq.util.ExcelUtil.ExcelExportData;
import org.joda.time.LocalDateTime;


@ContextConfiguration(locations = {"/applicationContext.xml"})
public class ExcelTest1 extends SpringTransactionalTestCase{
	
	@Autowired
	private WordsTaskService wordsTaskService;
	
	@Autowired
	private WordsTaskSencondService wordsTaskSencondService;
	
	@Value("#{envProps.excelDownPath}")
	private String excelDownPath;
	
	SimpleDateFormat s =   new SimpleDateFormat("yyyyMMddHHmmss" ); 
	SimpleDateFormat sdfExcelName =   new SimpleDateFormat("yyyyMMdd" ); 
	
	@Test
	public void test1() throws Exception {
		WordsTask wordsTask = wordsTaskService.findById(1L);
		List<WordsEsWeiboIndex> weibos = new ArrayList<>();
		List<WordsEsWeiboIndex> w = wordsTaskSencondService.findWeiboIndexFromEs("2016-01-01", "2017-10-22", "球爱-我们约球吧", "weibo", "1" ,"测试");
		for (int i = 0; i < 1500; i++) {
			weibos.addAll(w);
		}
		System.out.println(weibos.size());
		//list集合写入数据到excel
        List<String[]> columNames = new ArrayList<String[]>();
        List<String[]> fieldNames = new ArrayList<String[]>();
        LinkedHashMap<String, List<?>> map = new LinkedHashMap<String, List<?>>();
        List<String> titleNames = new LinkedList<>();
        
        String taskTypes = wordsTask.getTaskType();
		if(taskTypes.contains("weibo")){
	        columNames.add(new String[] { "项目名称","监测项关键字","起始时间","结束时间"
	        		,"声量","实际声量","曝光量","互动量","转发深度","帐号提及量","评论量","点赞量","转发量"});
	        fieldNames.add(new String[] { "name","keywords", "startDateString" ,"endDateString",
	        		"weibo_volume","weibo_actualVolume","weibo_exposure","weibo_interactive","weibo_forwardDepth","weibo_account","weibo_commentCount","weibo_upCount","weibo_repostCount"});
	        map.put("微博", weibos);
	        titleNames.add("微博_"+wordsTask.getTaskName()); 
		}
		
        ExcelExportData setInfo = new ExcelExportData();
        setInfo.setDataMap(map);
        setInfo.setFieldNames(fieldNames);
        setInfo.setTitles( (String[])titleNames.toArray(new String[titleNames.size()]) );
        setInfo.setColumnNames(columNames);

        String timeName = s.format(new Date());
        // 将需要导出的数据输出到文件
        System.out.println(LocalDateTime.now());
        String excelName =  excelDownPath+"/"+sdfExcelName.format(new Date())+"/"+ wordsTask.getTaskName()+"_"+timeName+".xlsx";
		ExcelUtil.export2File(setInfo, excelName);
		System.out.println(LocalDateTime.now());
	}
}
