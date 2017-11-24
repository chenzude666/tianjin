package com.stq;

import com.google.common.annotations.VisibleForTesting;
import com.stq.entity.stq.stats.*;
import com.stq.entity.stq.text.TextEsWeiboIndex;
import com.stq.service.stq.stats.*;
import com.stq.service.stq.text.WeiboTextService;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import com.stq.util.ExcelUtil;
import com.stq.util.mongoHelper.mongoEntity.WeiboerLog.WeiboerLog;
import com.stq.util.mongoHelper.mongoEntity.WeiboerLog.WeiboerLogCollection;
import org.junit.Test;

/**
 * Created by dazongshi on 17-11-1.
 */
public class WeiboCrawlerTest {
//    @Test
    public void test1() throws IllegalAccessException, URISyntaxException, InterruptedException, NoSuchFieldException {
        List<OneArticleRCU> rList = new OneArticleRCUService().getOneArticleRCU(new String[]{"http://weibo.com/2057769762/Dyzwrkyw3", "http://weibo.com/5644433237/Fls4d4Ye8?from=page_1001065644433237_profile&wvr=6&mod=weibotime&type=comment#_rnd1509442186241"});
        Field[] fields = OneArticleRCU.class.getDeclaredFields();
        for (OneArticleRCU oneArticleRCU : rList) {
            for (Field field : fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s", field.getName(), field.get(oneArticleRCU)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试微博单条文章导出"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[]{"url", "内容", "内容带转发", "时间", "作者ID", "作者", "转发量", "评论量", "点赞量", "转发层级", "来源", "链接", "情感分析", "性别", "vip", "加V", "简介", "信息", "关注数", "粉丝数", "微博数", "级别", "地区", "教育", "生日", "公司", "性取向", "关系", "行业", "tags"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String, List<?>> linkedHashMap = new LinkedHashMap<String, List<?>>();
        linkedHashMap.put("结果", rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData, "./oneArticle.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void test2() throws IllegalAccessException, URISyntaxException, InterruptedException, NoSuchFieldException {
        List<WeiboerLog> rList = WeiboerLogCollection.getInstance().find("1638629382","2017-09-01","2017-09-08");
        Field[] fields = WeiboerLog.class.getDeclaredFields();
        for (WeiboerLog weiboerLog : rList) {
            for (Field field : fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s", field.getName(), field.get(weiboerLog)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试微博日志导出"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[]{"用户ID","关注数", "粉丝数", "微博数", "时间"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String, List<?>> linkedHashMap = new LinkedHashMap<String, List<?>>();
        linkedHashMap.put("结果", rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData, "./weiboLog2.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void test3() throws IllegalAccessException, URISyntaxException, InterruptedException, NoSuchFieldException, IOException {
        WeiboerLogStats weiboerLogStats = new WeiboerLogService().getWeiboerLog("1638629382","2017-09-01","2017-09-08");
        List<WeiboerLogStats> rList = new ArrayList<>();
        rList.add(weiboerLogStats);

//        List<WeiboerLogStats> rList = new WeiboerLogService().getWeiboerLog("1638629382","2017-09-01","2017-09-08");
        Field[] fields = WeiboerLogStats.class.getDeclaredFields();
        for (WeiboerLogStats weiboerLogStat : rList) {
            for (Field field : fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s", field.getName(), field.get(weiboerLogStat)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试微博日志导出2"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[]{"账号","开始时间", "结束时间", "当前粉丝量", "期间粉丝增量","总帖子量","总转发量","总评论量","总点赞量","url"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String, List<?>> linkedHashMap = new LinkedHashMap<String, List<?>>();
        linkedHashMap.put("结果", rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData, "./weiboLogFinal.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    @Test
    public void test4() throws IllegalAccessException, URISyntaxException, InterruptedException, NoSuchFieldException, IOException {
        List<DailyWeiboer> rList = new DailyWeiboerService().getDailiWeiboers("http://weibo.com/u/1638629382","2017-09-01","2017-09-08");

//        List<WeiboerLogStats> rList = new WeiboerLogService().getWeiboerLog("1638629382","2017-09-01","2017-09-08");
        Field[] fields = DailyWeiboer.class.getDeclaredFields();
        for (DailyWeiboer dailyWeiboer : rList) {
            for (Field field : fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s", field.getName(), field.get(dailyWeiboer)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试微博日志导出3"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[]{"ID","账号","url","日期","微博量","粉丝量","关注量"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String, List<?>> linkedHashMap = new LinkedHashMap<String, List<?>>();
        linkedHashMap.put("结果", rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData, "./dailyWeiboer.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void test5() throws IllegalAccessException, URISyntaxException, InterruptedException {
        List<WeiboByUrl> rList = new WeiboByUrlService().getWeiboByUrlWithSentiment("http","2017-01-01","2017-07-02",10_000,"repost_count", "desc" );
        Field[] fields =  WeiboByUrl.class.getDeclaredFields();
        for (WeiboByUrl weiboByUrl :  rList) {
            for (Field field: fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s",field.getName(),field.get(weiboByUrl)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试微博导出"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[] {"内容","内容带转发","时间","作者ID","作者","转发量","评论量","点赞量","转发层级","来源","链接","情感分析","性别","vip","加V","简介","信息","关注数","粉丝数","微博数","级别","地区","教育","生日","公司","性取向","关系","行业","tags"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String,List<?>> linkedHashMap = new LinkedHashMap<String,List<?>>();
        linkedHashMap.put("结果",rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData,"./testResultWeiboUrl.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test6() throws IllegalAccessException, URISyntaxException, InterruptedException {
       Boolean flag =  new WeiboerAddService().addWeiboer("dfdsfsdfdsf","0");
       System.out.println(flag);
       WeiboerAdd weiboerAdd = new WeiboerAdd();
       weiboerAdd.setUrl_or_id("dfdsfasfsd");
       weiboerAdd.setTo_log("0");
       WeiboerAdd weiboerAdd1 = new WeiboerAdd();
       weiboerAdd1.setUrl_or_id("2364670925");
       weiboerAdd1.setTo_log("0");

       List<WeiboerAdd> weiboerAdds = new ArrayList<>();
       weiboerAdds.add(weiboerAdd);
       weiboerAdds.add(weiboerAdd1);

        List<WeiboerAdd> weiboerAddsNew = new ArrayList<>();

        List<WeiboerAdd> rList = new WeiboerAddService().addWeiboerByList(weiboerAddsNew);
        Field[] fields =  WeiboerAdd.class.getDeclaredFields();
        for (WeiboerAdd weiboerAdd2 :  rList) {
            for (Field field: fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s",field.getName(),field.get(weiboerAdd2)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试微博账号添加"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[] {"url或id","to_log","添加是否成功"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String,List<?>> linkedHashMap = new LinkedHashMap<String,List<?>>();
        linkedHashMap.put("结果",rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData,"./addWeiboer.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
