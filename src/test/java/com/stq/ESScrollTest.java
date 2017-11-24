package com.stq;

import com.alibaba.fastjson.JSON;
import com.stq.entity.stq.text.*;
import com.stq.service.stq.crawler.WeiboCrawler;
import com.stq.service.stq.text.*;
import com.stq.util.ExcelUtil;
import com.stq.util.SocketClient;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.*;

//import com.
//import io.socket.engineio.client.Socket;

//import io.netty.channel.socket.nio.NioSocketChannel
//import com.carrotsearch.hppc.
//import com.corundumstudio.socketio.AckRequest;
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.listener.ConnectListener;
//import com.corundumstudio.socketio.listener.DataListener;


/**
 * Created by dazongshi on 17-10-23.
 */
public class ESScrollTest {
    private static final Logger log = LoggerFactory.getLogger(MongoTest.class);
//    @Test
    public void test1() {
        HttpEntity entity = new NStringEntity(""
                + "{"
                +       "\"query\": {"
                +           "\"bool\": {"
                + 				"\"filter\": [{\"term\":  { \"source\": \""+ "weibo" +"\" }}], "
                +               "\"should\": ["
                + 					"{"
                + 						"\"script\": {"
                + 							"\"script\": \"doc.updateDate.value - doc.startDate.value <= 86400000*7 \""
                + 						"}"
                + 					"}"
                +               "],"
                +               "\"minimum_should_match\": \"1\""
                +           "}"
                +       "},"
                +        "\"sort\" : ["
                +           "{\"weibo_actualVolume\" : {\"order\" : \"asc\"}}"
                +        "],"
                +       "\"size\": 10000,"
                +       "\"_source\": {\"includes\": [\"name\",\"keywords\",\"source\",\"startDateString\",\"endDateString\"]}"
                + "}", ContentType.APPLICATION_JSON);
        System.out.println(entity);
        String scrollSize = "10000";
        List<Object> result = null;
        try {
            result = new TextBaseService().GetScrollList("cibao_index","cibao_index",entity,scrollSize,1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Object object : result) {
           Map<String,Object> map =  (Map<String,Object>)object;
           System.out.println(map.get("_id"));
        }
    }
//    @Test
    public void test2() {
        String strBool = new TextBaseService().getElasticsearchDSLByFields("鹿晗+-陈赫||陈赫+-邓超",new String[]{"content","title"});
        String scrollSize = "10000";
        String startDate = "2017-01-01";
        String endDate = "2017-01-01";
        HttpEntity entity = new NStringEntity(""
                + "{"
                + 	"\"query\": {"
                + 		"\"bool\": {"
                + 			"\"filter\": [{"
                +			 	"\"range\": {"
                + 					"\"published_at\": {"
                + 						"\"gte\": \""+startDate+"\","
                + 						"\"lte\": \""+endDate+"\","
                +                       "\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\","
                + 						"\"time_zone\": \"Asia/Shanghai\""
                + 					"}"
                + 				"}"
                + 			"}],"
                +          "\"should\": ["
                + 		   ""+strBool+""
                +          "],"
                + 		   "\"minimum_should_match\": \"1\""
                + 		"}"
                + 	"},"
                + 	"\"size\": "+scrollSize+""
                + "}", ContentType.APPLICATION_JSON);
        try {
            System.out.println(EntityUtils.toString(entity));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Object> result = null;
        try {
            result = new TextBaseService().GetScrollList("weibo_articles_and_weiboers","weibo_articles_and_weiboer",entity,scrollSize,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        for (Object object : result) {
//            Map<String,Object> map =  (Map<String,Object>)object;
//            System.out.println(map.get("_source"));
//        }
    }
//    @Test
    public void test3() throws IllegalAccessException {
        List<TextEsWeiboIndex> rList = new WeiboTextService().getWeiboArticlesSortedWithSentiment("2017-01-01","2017-01-01","鹿晗+-陈赫||陈赫+-邓超",new String[]{"content","title"},3,"repost_count", "desc" );
        Field[] fields =  TextEsWeiboIndex.class.getDeclaredFields();
        for (TextEsWeiboIndex textEsWeiboIndex :  rList) {
            for (Field field: fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s",field.getName(),field.get(textEsWeiboIndex)));
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
            ExcelUtil.export2File(excelExportData,"./testResult.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    @Test
    public void test4() throws IllegalAccessException {
        List<TextXinwen> rList = new XinwenTextService().getXinwenArticlesSortedWithSentiment("2017-01-01","2017-01-01","鹿晗+-陈赫||陈赫+-邓超",new String[]{"content","title"},3 , "comment_num", "asc");
        Field[] fields =  TextXinwen.class.getDeclaredFields();
        for (TextXinwen textEsWeiboIndex :  rList) {
            for (Field field: fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s",field.getName(),field.get(textEsWeiboIndex)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试新闻导出"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[] {"频道","评论数","评论链接","内容","标题","发表时间","回复数","来源","转载自","投票数","情感分析","原文链接"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String,List<?>> linkedHashMap = new LinkedHashMap<String,List<?>>();
        linkedHashMap.put("结果",rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData,"./testXinwenResult.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void test5() throws IllegalAccessException {
        List<TextToutiao> rList = new ToutiaoTextService().getToutiaoArticlesSortedWithSentiment("2017-01-01","2017-07-01","鹿晗+-陈赫||陈赫+-邓超",new String[]{"content","title"},3, "follower_count", "asc");
        Field[] fields =  TextToutiao.class.getDeclaredFields();
        for (TextToutiao textToutiao :  rList) {
            for (Field field: fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s",field.getName(),field.get(textToutiao)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试头条导出"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[] {"头条号ID","头像图片链接","头条号名称","头条号简介","关注量","粉丝量","资源ID","文章标题","文章内容","文章图片","关键分词","阅读量","评论量","点赞量","踩量","发布时间","类型","视频时长","情感分析"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String,List<?>> linkedHashMap = new LinkedHashMap<String,List<?>>();
        linkedHashMap.put("结果",rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData,"./testToutiaoResult.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    @Test
    public void test6() throws IllegalAccessException {
        List<TextZhihu> rList = new ZhihuTextService().getZhihuArticlesSortedWithSentiment("2017-01-01","2017-07-01","鹿晗+-陈赫||陈赫+-邓超",new String[]{"desc","title"},3, "follows_num" , "asc");
        Field[] fields =  TextZhihu.class.getDeclaredFields();
        for (TextZhihu textZhihu :  rList) {
            for (Field field: fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s",field.getName(),field.get(textZhihu)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试知乎导出"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[] {"问题名称","问题所属话题","话题描述","关注人数","查看人数","回答人数","题主","题主ID","提问时间","情感分析"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String,List<?>> linkedHashMap = new LinkedHashMap<String,List<?>>();
        linkedHashMap.put("结果",rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData,"./testZhihuResult.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    @Test
    public void test7() throws IllegalAccessException {
        List<TextTianya> rList = new TianyaTextService().getTianyaArticlesSortedWithSentiment("2017-01-01","2017-07-01","鹿晗+-陈赫||陈赫+-邓超",new String[]{"content","title"},3, "clicks_num", "asc");
        Field[] fields =  TextTianya.class.getDeclaredFields();
        for (TextTianya textTianya :  rList) {
            for (Field field: fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s",field.getName(),field.get(textTianya)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试天涯导出"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[] {"帖子标题","帖子内容","发帖时间","帖子点击数","帖子作者","帖子回复数","帖子链接","情感分析"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String,List<?>> linkedHashMap = new LinkedHashMap<String,List<?>>();
        linkedHashMap.put("结果",rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData,"./testTianyaResult.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void test8() throws IllegalAccessException {
        List<TextZixun> rList = new ZixunTextService().getZixunArticlesSortedWithSentiment("2017-01-01","2017-07-01","鹿晗+-陈赫||陈赫+-邓超",new String[]{"content","title"},3, "supports_num", "asc");
        Field[] fields =  TextZixun.class.getDeclaredFields();
        for (TextZixun textZixun :  rList) {
            for (Field field: fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s",field.getName(),field.get(textZixun)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试资讯博客导出"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[] {"文章标题","文章内容","发布日期","简介","图片","链接","文章分类","关键词","点赞量","收藏量","评论量","浏览量","作者 id 或链接","情感分析"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String,List<?>> linkedHashMap = new LinkedHashMap<String,List<?>>();
        linkedHashMap.put("结果",rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData,"./testZixunResult.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void test9() throws IllegalAccessException {
        List<TextTieba> rList = new TiebaTextService().getTiebaArticlesSortedWithSentiment("2017-01-01","2017-07-01","鹿晗+-陈赫||陈赫+-邓超",new String[]{"content","title"},3,"reply_num", "asc");
        Field[] fields =  TextTieba.class.getDeclaredFields();
        for (TextTieba textTieba :  rList) {
            for (Field field: fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s",field.getName(),field.get(textTieba)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试贴吧导出"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[] {"贴吧", "发帖人id", "发帖人", "内容", "标题", "发帖时间", "回复数", "情感分析"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String,List<?>> linkedHashMap = new LinkedHashMap<String,List<?>>();
        linkedHashMap.put("结果",rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData,"./testTiebaResult.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("task Finsh!!!");
    }

    @Test
    public void test10() throws IllegalAccessException {
        List<TextWeixin> rList = null;
        try {
            rList = new WeixinTextService().getWeixinArticlesSortedWithSentiment("2017-01-01","2017-07-01","鹿",new String[]{"content","title"},10,"crawled_at","asc" );
        } catch (IOException e) {
            e.printStackTrace();
        }
        Field[] fields =  TextWeixin.class.getDeclaredFields();
        for (TextWeixin textWeixin :  rList) {
            for (Field field: fields) {
                field.setAccessible(true);
                System.out.println(String.format("%s is %s",field.getName(),field.get(textWeixin)));
            }
        }
        ExcelUtil.ExcelExportData excelExportData = new ExcelUtil.ExcelExportData();
        excelExportData.setTitles(new String[]{"测试微信导出"});
        List<String[]> columnNames = new ArrayList<String[]>();
        columnNames.add(new String[] {"标题", "内容", "时间", "作者", "作者名", "头条", "阅读量", "点赞量", "链接", "情感分析"});
        excelExportData.setColumnNames(columnNames);
        String[] fieldArr = Arrays.stream(fields).map(field -> field.getName()).toArray(String[]::new);
        LinkedHashMap<String,List<?>> linkedHashMap = new LinkedHashMap<String,List<?>>();
        linkedHashMap.put("结果",rList);
        List fieldNames = new ArrayList<String[]>();
        fieldNames.add(fieldArr);
        excelExportData.setFieldNames(fieldNames);
        excelExportData.setDataMap(linkedHashMap);
        try {
            ExcelUtil.export2File(excelExportData,"./testWeixinResult222222.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("task Finsh!!!");
    }
//    @Test
    public  void test11() throws URISyntaxException, InterruptedException {
        SocketClient socketClient = new SocketClient("http://localhost:6050");
        Map<String,String> weiboerMap = new HashMap<>();
        weiboerMap.put("urlOrId","http://weibo.com/dfd2810373291");
        weiboerMap.put("toLog","0");



        socketClient.on(SocketClient.EVENT_CONNECT,(Object... args)-> {
            System.out.println("socket connect");
        });
        socketClient.on("add Success",(Object... args)-> {
            System.out.println((JSON.parseObject(args[0].toString())));
        });
        socketClient.on("add Fail", (Object... args) -> {
            System.out.println(JSON.parseObject(args[0].toString()));
        });
        socketClient.emit("weiboer url or id",weiboerMap);
        socketClient.connect();
        Thread.currentThread().sleep(100000);
        System.out.println("hold on");
    }

//    @Test
    public  void test12() throws URISyntaxException, InterruptedException {
        WeiboCrawler weiboCrawler = new WeiboCrawler();
        JSON message =  weiboCrawler.addWeiboerWithRetry("2810373291","0",0);
        System.out.println(message);
    }
//    @Test
    public  void test13() throws URISyntaxException, InterruptedException {
        WeiboCrawler weiboCrawler = new WeiboCrawler();
        JSON message =  weiboCrawler.getWeiboArticle("http://weibo.com/2057769762/Dyzwrkyw3");
        System.out.println(message);
    }




}
