package com.stq;

import com.alibaba.fastjson.JSONObject;
import com.stq.entity.stq.stats.WeiXinKOLArticle;
import com.stq.entity.stq.stats.WeiboerAdd;
import com.stq.entity.stq.stats.WeixinKOLStat;
import com.stq.service.stq.crawler.WeiXinCrawler;
import com.stq.service.stq.stats.WeiboerAddService;
import com.stq.service.stq.stats.WeixinKOLService;
import com.stq.util.ExcelUtil;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by dazongshi on 17-11-2.
 */
public class WeiXinCrawlerTest {
//    @Test
    public void test1() throws IllegalAccessException, URISyntaxException, InterruptedException, NoSuchFieldException, IOException {
//        WeiboerLogStats weiboerLogStats = new WeiboerLogService().getWeiboerLog("1638629382","2017-09-01","2017-09-08");
//        WeiXinCrawler.getInstance().getTest();
        JSONObject jsonObject = WeiXinCrawler.getInstance().getBiz("https://mp.weixin.qq.com/s?__biz=MjM5MjAxNDM4MA==&mid=2666174750&idx=1&sn=4efe5bbca8c7fa5bd375ddfc23b2deef&chksm=bdb2f05d8ac5794b9fc270a13974eb800296ecbd6c323400b0a2731e4ddd66ef48d75df88bf4&scene=0#rd", "2017-10-01", "2017-10-31");
        System.out.println(jsonObject);
        JSONObject jsonObject2 = WeiXinCrawler.getInstance().getBiz("MzA4NDI3NjcyNA==", "2017-10-01", "2017-10-31");
        System.out.println(jsonObject2);
    }

//    @Test
    public void test2() throws IllegalAccessException, URISyntaxException, InterruptedException, NoSuchFieldException, IOException {
        String biz =  new WeixinKOLService().getBiz("人民日报", "2017-10-01", "2017-10-31");
        System.out.println(biz);
        new WeixinKOLService().getWeixinKol("人民日报", "2017-10-01", "2017-10-31");

    }

//    @Test
    public void test3() throws InterruptedException, IOException, URISyntaxException, IllegalAccessException {
        WeixinKOLStat weixinKOLStat = new WeixinKOLService().getWeixinKol("人民日报", "2017-10-01", "2017-10-31");
        List<WeixinKOLStat> rList = new ArrayList<>();
        rList.add(weixinKOLStat);
        Field[] fields =  WeixinKOLStat.class.getDeclaredFields();
        for (WeixinKOLStat weixinKOLStat1 :  rList) {
            for (Field field: fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s",field.getName(),field.get(weixinKOLStat1)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试微信KOL导出"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[] {"账号名称","微信号","开始时间","结束时间","总文章量","总阅读量","总点赞量","头条总数","头条总阅读量","头条总点赞量"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String,List<?>> linkedHashMap = new LinkedHashMap<String,List<?>>();
        linkedHashMap.put("结果",rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData,"./weixinKOL.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test4() throws InterruptedException, IOException, URISyntaxException, IllegalAccessException {
        List<WeiXinKOLArticle> rList = new WeixinKOLService().getWeiXinKOLArticle("人民日报", "2017-10-01", "2017-10-31", 500, "last_modified_at", "asc");
        Field[] fields =  WeiXinKOLArticle.class.getDeclaredFields();
        for (WeiXinKOLArticle weiXinKOLArticle :  rList) {
            for (Field field: fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s",field.getName(),field.get(weiXinKOLArticle)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试微信KOL文章导出"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[] {"标题","内容","时间","biz","头条","阅读量","点赞量","链接","作者用户名","作者账号"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String,List<?>> linkedHashMap = new LinkedHashMap<String,List<?>>();
        linkedHashMap.put("结果",rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData,"./weixinKOLArticle.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
