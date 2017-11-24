package com.stq.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.stq.entity.stq.text.TextTaskSeek;
import com.stq.entity.stq.words.WordsTaskSencond;
import com.stq.service.stq.text.TextTaskService;
import com.stq.service.stq.words.WordsTaskSencondService;
import com.stq.util.PropertiesUtil;
import com.sun.el.parser.ParseException;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TeTest4 extends SpringTransactionalTestCase{
	
	
	@Autowired
	private TextTaskService textTaskService;
	
	@Autowired
	private WordsTaskSencondService wordsTaskSencondService;
	
	@Test
	public void test() throws IOException, ParseException {
		List<TextTaskSeek> textTaskSeeks = textTaskService.findTextSeekBySecondId(32L);
		textTaskSeeks.stream().forEach(
				textTaskSeek -> 
					System.out.println(textTaskSeek.getId() + " " + textTaskSeek.getSecondId() + " " + textTaskSeek.getEsBy() + " " + textTaskSeek.getExportSort() + " " + textTaskSeek.getExportType())
		);
		String str = "1,2,3";  
        String[] a = Arrays.stream(str.split(",")).map(s -> String.valueOf(s)).toArray(String[]::new);  
        System.out.println(a[2]);
        String[] b = {"content","title"};
        System.out.println(b[1]);
        System.out.println(str.split(",")[1]);
	}
	
	@Test
	public void test1() throws IOException, ParseException {
		System.out.println(PropertiesUtil.remoteAddr());
		List<WordsTaskSencond> wordsTaskSenconds = new ArrayList<>();
		wordsTaskSenconds = wordsTaskSencondService.findByKeyword("叶问3");
		System.out.println(wordsTaskSenconds.size());
	}
}
