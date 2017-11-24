package com.stq;

import com.stq.entity.stq.words.*;
import com.stq.service.stq.words.baiduxinwen.BaiduXinwenService;
import com.stq.util.ExcelUtil;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by dazongshi on 17-11-16.
 */
public class BaiduXinwenTest {
    @Test
    public void test1() throws Exception {
        BaiduXinwenAddSchema baiduXinwenAddSchema  = new BaiduXinwenAddSchema();
        baiduXinwenAddSchema.setKeyword("胡歌");
        baiduXinwenAddSchema.setFromId("测试胡歌");
        baiduXinwenAddSchema.setStartDate("2017-11-02");
        baiduXinwenAddSchema.setEndDate("2017-11-03");
        List<BaiduXinwenArticle> rList = new BaiduXinwenService().getBaiduXinwenArticleList(baiduXinwenAddSchema, "测试");
        Field[] fields = BaiduXinwenArticle.class.getDeclaredFields();
        for (BaiduXinwenArticle baiduXinwenArticle : rList) {
            for (Field field : fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s", field.getName(), field.get(baiduXinwenArticle)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试百度新闻1"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[]{"identifer", "id", "author", "title", "summary", "date" , "url"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String, List<?>> linkedHashMap = new LinkedHashMap<String, List<?>>();
        linkedHashMap.put("结果", rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData, "./testBaiduXinwenResult3.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




//    @Test
    public void test2() throws Exception {
        BaiduXinwenAddSchema baiduXinwenAddSchema  = new BaiduXinwenAddSchema();
        baiduXinwenAddSchema.setKeyword("胡歌");
        baiduXinwenAddSchema.setFromId("测试胡歌");
        baiduXinwenAddSchema.setStartDate("2017-11-02");
        baiduXinwenAddSchema.setEndDate("2017-11-03");
        List<BaiduXinwenVolume> rList = new BaiduXinwenService().getBaiduXinwenVolumeList(baiduXinwenAddSchema, "测试");
        Field[] fields = BaiduXinwenVolume.class.getDeclaredFields();
        for (BaiduXinwenVolume baiduXinwenVolume : rList) {
            for (Field field : fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s", field.getName(), field.get(baiduXinwenVolume)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试百度新闻2"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[]{"identifer", "fromId", "keyword", "date", "count"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String, List<?>> linkedHashMap = new LinkedHashMap<String, List<?>>();
        linkedHashMap.put("结果", rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData, "./testBaiduXinwenResult10.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
