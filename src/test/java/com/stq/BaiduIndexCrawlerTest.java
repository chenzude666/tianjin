package com.stq;

import com.alibaba.fastjson.JSONObject;
import com.stq.entity.stq.stats.BaiduIndexHighPriority;
import com.stq.entity.stq.stats.BaiduIndexImportSchema;
import com.stq.entity.stq.text.TextEsWeiboIndex;
import com.stq.service.stq.crawler.BaiduIndexCrawler;
import com.stq.service.stq.stats.BaiduIndexHighPriorityService;
import com.stq.util.ExcelUtil;
import org.junit.Test;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by dazongshi on 17-11-7.
 */
public class BaiduIndexCrawlerTest {
//    @Test
    public void test1() throws URISyntaxException, InterruptedException, IllegalAccessException {
        BaiduIndexImportSchema baiduIndexImportSchema = new BaiduIndexImportSchema();
        baiduIndexImportSchema.setKeyword("霹雳");
        baiduIndexImportSchema.setPriority("high");
        baiduIndexImportSchema.setStat(0);
        baiduIndexImportSchema.setSt("2017-01-01");
        baiduIndexImportSchema.setEt("2017-01-02");
        baiduIndexImportSchema.setCategory("测试");
        List<BaiduIndexImportSchema> baiduIndexImportSchemaList = new ArrayList<>();
        baiduIndexImportSchemaList.add(baiduIndexImportSchema);
        JSONObject jsonObject=  new BaiduIndexCrawler().getBaiduIndexWithBaiduIndexImportSchema(baiduIndexImportSchemaList);
        System.out.println(jsonObject);
    }

    @Test
    public void test2() throws IllegalAccessException, URISyntaxException, InterruptedException {
        BaiduIndexImportSchema baiduIndexImportSchema = new BaiduIndexImportSchema();
        baiduIndexImportSchema.setKeyword("霹雳");
        baiduIndexImportSchema.setPriority("high");
        baiduIndexImportSchema.setStat(0);
        baiduIndexImportSchema.setSt("2017-01-01");
        baiduIndexImportSchema.setEt("2017-01-02");
        baiduIndexImportSchema.setCategory("测试");

        BaiduIndexImportSchema baiduIndexImportSchema2 = new BaiduIndexImportSchema();
        baiduIndexImportSchema2.setKeyword("金光");
        baiduIndexImportSchema2.setPriority("high");
        baiduIndexImportSchema2.setStat(0);
        baiduIndexImportSchema2.setSt("2017-01-01");
        baiduIndexImportSchema2.setEt("2017-01-02");
        baiduIndexImportSchema2.setCategory("测试");

        BaiduIndexImportSchema baiduIndexImportSchema3 = new BaiduIndexImportSchema();
        baiduIndexImportSchema3.setKeyword("布袋戏");
        baiduIndexImportSchema3.setPriority("high");
        baiduIndexImportSchema3.setStat(0);
        baiduIndexImportSchema3.setSt("2017-01-01");
        baiduIndexImportSchema3.setEt("2017-07-02");
        baiduIndexImportSchema3.setCategory("测试");

        List<BaiduIndexImportSchema> baiduIndexImportSchemaList = new ArrayList<>();
        baiduIndexImportSchemaList.add(baiduIndexImportSchema);
        baiduIndexImportSchemaList.add(baiduIndexImportSchema2);
        baiduIndexImportSchemaList.add(baiduIndexImportSchema3);

        List<BaiduIndexHighPriority> rList = new BaiduIndexHighPriorityService().getBaiduIndex(baiduIndexImportSchemaList);
        Field[] fields =  BaiduIndexHighPriority.class.getDeclaredFields();
        for (BaiduIndexHighPriority baiduIndexHighPriority :  rList) {
            for (Field field: fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s",field.getName(),field.get(baiduIndexHighPriority)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试百度指数导出"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[] {"关键词","时间","百度指数"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String,List<?>> linkedHashMap = new LinkedHashMap<String,List<?>>();
        linkedHashMap.put("结果",rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData,"./testBaiduResult.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
