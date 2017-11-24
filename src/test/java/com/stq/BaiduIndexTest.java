package com.stq;

import com.alibaba.fastjson.JSONObject;
import com.stq.entity.stq.stats.BaiduIndexHighPriority;
import com.stq.entity.stq.stats.BaiduIndexImportSchema;
import com.stq.entity.stq.words.WordsBaiduIndex;
import com.stq.service.stq.stats.BaiduIndexHighPriorityService;
import com.stq.service.stq.words.baiduindex.BaiduIndexService;
import com.stq.util.ExcelUtil;
import org.junit.Test;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.*;

/**
 * Created by dazongshi on 17-11-9.
 */
public class BaiduIndexTest {
    //    @Test
    public void test1() throws Exception {
        Map<String, Object> result = new BaiduIndexService().getBaiduIndex("寻龙诀", "2017-01-01", "2017-09-09");
        if ((Boolean) result.get("getDataSuccess") == true) {
            List<WordsBaiduIndex> rList = (List<WordsBaiduIndex>) result.get("baiduIndexList");
            Field[] fields = WordsBaiduIndex.class.getDeclaredFields();
            for (WordsBaiduIndex wordsBaiduIndex : rList) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    System.out.println(String.format("%s is %s", field.getName(), field.get(wordsBaiduIndex)));
                }
            }
            ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
            excelExportData.setTitles(new String[]{"测试百度指数导出2"});
            List<String[]> columnNames = new ArrayList<String[]>();
            columnNames.add(new String[]{"关键词", "时间", "百度指数"});
            excelExportData.setColumnNames(columnNames);
            String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
            LinkedHashMap<String, List<?>> linkedHashMap = new LinkedHashMap<String, List<?>>();
            linkedHashMap.put("结果", rList);
            List fieldNames = new ArrayList<String[]>();
            fieldNames.add(fieldArr);
            excelExportData.setFieldNames(fieldNames);
            excelExportData.setDataMap(linkedHashMap);
            try {
                ExcelUtil.export2File(excelExportData, "./testBaiduResult2.xls");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("task failed");
        }
    }


//    @Test
    public void test2() throws Exception {
        String json = new BaiduIndexService().setBaiduIndex("1334324测试寻龙诀", "寻龙诀dfdfdfdf", "2017-07-01", null);
        System.out.println(json);
    }
//    @Test
    public void test3() throws Exception {
        Map<String,String> json = new BaiduIndexService().addBaiduIndexKeyword("1334324测试寻龙诀", "寻龙诀dfdfdfdf", "2017-07-01", null);
        System.out.println(json);
    }

    @Test
    public void test4() throws Exception {
        BaiduIndexImportSchema baiduIndexImportSchema = new BaiduIndexImportSchema();
        baiduIndexImportSchema.setKeyword("霹雳");
        baiduIndexImportSchema.setPriority("high");
        baiduIndexImportSchema.setStat(0);
        baiduIndexImportSchema.setSt("2017-01-01");
        baiduIndexImportSchema.setEt("2017-01-02");
        baiduIndexImportSchema.setCategory("测试");

        BaiduIndexImportSchema baiduIndexImportSchema2 = new BaiduIndexImportSchema();

        baiduIndexImportSchema2.setKeyword("刀剑神域");
        baiduIndexImportSchema2.setPriority("high");
        baiduIndexImportSchema2.setStat(0);
        baiduIndexImportSchema2.setSt("2017-10-01");
        baiduIndexImportSchema2.setEt("2017-11-02");
        baiduIndexImportSchema2.setCategory("测试");

        JSONObject jsonObject = new BaiduIndexHighPriorityService().addHighPriorityBaiduIndex(baiduIndexImportSchema);
        System.out.println(jsonObject);
        JSONObject jsonObject2 = new BaiduIndexHighPriorityService().addHighPriorityBaiduIndex(baiduIndexImportSchema2);
        System.out.println(jsonObject2);
//        JSONObject jsonObject1 = new BaiduIndexHighPriorityService().checktHighPriorityBaiduIndexTaskFinish("霹雳");
//        System.out.println(jsonObject1);
        List<BaiduIndexImportSchema> list =  new ArrayList<>();
        list.add(baiduIndexImportSchema);
        list.add(baiduIndexImportSchema2);
        List<WordsBaiduIndex> rList =  new BaiduIndexHighPriorityService().getHighPriorityBaiduIndexFullFlow(list);
        Field[] fields = WordsBaiduIndex.class.getDeclaredFields();
        for (WordsBaiduIndex wordsBaiduIndex : rList) {
            for (Field field : fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s", field.getName(), field.get(wordsBaiduIndex)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试百度指数导出2"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[]{"关键词", "时间", "百度指数"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String, List<?>> linkedHashMap = new LinkedHashMap<String, List<?>>();
        linkedHashMap.put("结果", rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData, "./testBaiduResult12.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    @Test
    public void test5() throws Exception {
        BaiduIndexImportSchema baiduIndexImportSchema = new BaiduIndexImportSchema();
        baiduIndexImportSchema.setKeyword("霹雳");
        baiduIndexImportSchema.setPriority("high");
        baiduIndexImportSchema.setStat(0);
        baiduIndexImportSchema.setSt("2017-01-01");
        baiduIndexImportSchema.setEt("2017-01-02");
        baiduIndexImportSchema.setCategory("测试");


        String jsonObject1 = new BaiduIndexHighPriorityService().getHighPriorityBaiduIndex(baiduIndexImportSchema);
        System.out.println(jsonObject1);
    }


}
