package com.stq;

import com.stq.util.mongoHelper.CommonSchema;
import com.stq.util.mongoHelper.EasyCollection;
import com.stq.util.mongoHelper.MongoCollectPool;
import com.stq.util.mongoHelper.mongoEntity.CTimeHelper.CTimeHelperCollection;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.joda.time.LocalDate;

import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.FindOneAndUpdateOptions;

import com.stq.service.stq.words.WordsBaseService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
//import java.util.Optional;


/**
 * Created by dazongshi on 17-9-7.
 */
public class MongoTest {
    private static final Logger log = LoggerFactory.getLogger(MongoTest.class);

    //    @Test
    public void test1() {

//        CollectionConnector collectionConnector = new CollectionConnector("mongodb://tarantula:Joke123098@101.201.37.28:3725/tarantula","tarantula","test_javas");
        MongoCollection collection = MongoCollectPool.getCollection("mongodb://tarantula:Joke123098@101.201.37.28:3725/tarantula", "tarantula", "test_javas");
        MongoCollection collection2 = MongoCollectPool.getCollection("mongodb://tarantula:Joke123098@101.201.37.28:3725/tarantula", "tarantula", "test_javas");
        Document test1 = new Document("name1", "cc10");
        System.out.println(test1);

        Document modifer = new Document("$set", test1);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();

        System.out.println(ReturnDocument.AFTER);
        options.returnDocument(ReturnDocument.BEFORE);
        options.upsert(true);

        Document abc = (Document) collection.findOneAndUpdate(test1, modifer, options);
        System.out.println("结果是");
        System.out.println(abc);
//        MongoCollectPool.closeConnection("mongodb://tarantula:Joke123098@101.201.37.28:3725/tarantula","tarantula","test_javas");
        for (Object cur : collection2.find()) {
            System.out.println(cur);
        }
//        MongoCollectPool.closeAll();
    }

    //    @Test
    public void test2() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

        CommonSchema schema = new CommonSchema();
        Map<String, Object> map = new HashMap<>();
        Document document = new Document();
        document.append("data_type", "曝光量");
        map.put("source", "weibo2");
//        CommonSchema schema2 = null;
        try {
            schema.convertFromMap(map);
            schema.convertFromDocument(document);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

//        EasyCollection easyCollection = EasyCollection.getInstance("mongodb://tarantula:Joke123098@101.201.37.28:3725/tarantula","tarantula","test_javas");
        EasyCollection easyCollection = EasyCollection.getInstance();

        for (Map<String, Object> cur : easyCollection.find()) {
            System.out.println(cur);
        }
        easyCollection.findOneAndUpdate(map, true);
        System.out.println("schema");
        for (Map<String, Object> cur : easyCollection.find(schema)) {
            System.out.println(cur);
            CommonSchema resultSchema = new CommonSchema();
            try {
                resultSchema.convertFromMap(cur);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Date date3 = null;
        try {
            date3 = simpleDateFormat.parse("2017-01-27 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String, Object> map2 = new HashMap<>();
        map2.put("st", date3);
        map2.put("et", date3);
        map2.put("keyword", "腾讯新闻");
        map2.put("data_type", "null");
//        easyCollection.findOneAndUpdate(map2, true);
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        System.out.println(bundle.getString("imagepath"));
        System.out.println("文档数量为" + easyCollection.countAllNum(map2));
        ArrayList<Map<String, Object>> arr2 = new ArrayList<>();
        ArrayList<CommonSchema> arr = new ArrayList<>();
        List<Callable<Map<String, Object>>> tasks = new ArrayList<>();
        for (int i = 0; i < 5000; ++i) {
            System.out.println(i);
            Map<String, Object> map3 = new HashMap<>();
            map3.put("st", date3);
            map3.put("et", date3);
            map3.put("keyword", "腾讯新闻" + i);
            map3.put("data_type", "null");
            map3.put("value", i);
            CommonSchema newSchema = new CommonSchema();
            try {
                newSchema.convertFromMap(map3);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            arr.add(newSchema);

            arr2.add(map3);
        }
//        easyCollection.updateSchema(arr, true);
//        List<Future<Map<String, Object>>> results = easyCollection.updates(arr2, true);
//        try {
//            System.out.println(results.get(0).get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        System.out.println(results);
//        System.out.println(arr2);
        easyCollection.bulkManySchemaWithLimit(arr);
        easyCollection.bulkManyMapWithLimit(arr2);
        System.out.println("success");

    }

    //    @Test
    public void test3() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Date st = null;
        try {
            st = simpleDateFormat.parse("2016-05-02 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date et = null;
        try {
            et = simpleDateFormat.parse("2017-05-02 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CommonSchema commonSchema = new CommonSchema("你的名字+电影", st, et, "weibo");
        EasyCollection easyCollection = EasyCollection.getInstance();
        ArrayList<Map<String, Object>> result = easyCollection.findWeiboResult(commonSchema, false);
        System.out.println(result);
        System.out.println(result.size());
    }

//    @Test
    public void test4() {
        WordsBaseService wordsBaseService = new WordsBaseService();
        try {
            boolean result = wordsBaseService.isResultEnough("2017-09-16", "2017-09-18", "你的名字", "weibo");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test5() {
        CTimeHelperCollection cTimeHelperCollection = CTimeHelperCollection.getInstance();
        Date date = cTimeHelperCollection.getStartTime("weibo");
        System.out.println(date);
        System.out.println(LocalDate.now().plusDays(-30));
        System.out.println(LocalDate.fromDateFields(date));
        System.out.println(LocalDate.now().plusDays(-30).isAfter(LocalDate.fromDateFields(date)));
        
    }


}
