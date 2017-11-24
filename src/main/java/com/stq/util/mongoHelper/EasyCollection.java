package com.stq.util.mongoHelper;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.*;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Collectors;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import org.apache.xalan.xsltc.DOM;
import org.bson.Document;
import com.mongodb.client.MongoCursor;

import javax.print.Doc;
import javax.tools.DocumentationTool;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Updates.inc;
import static com.mongodb.client.model.Updates.set;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Collections.checkedCollection;
import static java.util.Collections.singletonList;

import com.mongodb.client.model.Sorts.*;
import org.elasticsearch.common.recycler.Recycler;
import org.springframework.scheduling.annotation.Async;

/**
 * 对应数据源的数据存入以及取出接口
 * Created by dazongshi on 17-9-8.
 */
public class EasyCollection implements CollectionInterface<CommonSchema> {
    protected MongoCollection collection;
    protected static EasyCollection instance = null;
    protected static String[] weiboDataType = new String[]{
            "coefficient",
            "volume",
            "actualVolume",
            "exposure",
            "primaryPercentage",
            "independentPercentage",
            "interactive",
            "oldInteractive",
            "commentsPercentage",
            "praisePercentage",
            "secondaryPercentage",
            "threePercentage",
            "forwardDepth"
    };

    /**
     * constructor
     *
     * @param mongoUrl
     * @param dataBase
     * @param collectionName
     */
    public EasyCollection(String mongoUrl, String dataBase, String collectionName) {
        collection = MongoCollectPool.getCollection(mongoUrl, dataBase, collectionName);
        IndexOptions indexOptions = new IndexOptions().background(true);
        collection.createIndex(new Document().append("st", 1).append("et", 1).append("keyword", 1).append("source", 1).append("data_type", 1), indexOptions);
    }

    /**
     * constructor
     */
    public EasyCollection() {
        collection = MongoCollectPool.getCollection("mongodb://tarantula:Joke123098@101.201.37.28:3725/tarantula", "tarantula", "test_javas");
        IndexOptions indexOptions = new IndexOptions().background(true);
        collection.createIndex(new Document().append("st", 1).append("et", 1).append("keyword", 1).append("source", 1).append("data_type", 1), indexOptions);
    }

    /**
     * 返回唯一实例
     *
     * @return
     */
    public static synchronized EasyCollection getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new EasyCollection();
            return instance;
        }
    }

    /**
     * 返回唯一实例
     *
     * @param mongoUrl
     * @param dataBase
     * @param collectionName
     * @return
     */
    public static synchronized EasyCollection getInstance(String mongoUrl, String dataBase, String collectionName) {
        if (instance != null) {
            return instance;
        } else {
            instance = new EasyCollection(mongoUrl, dataBase, collectionName);
            return instance;
        }
    }


    /**
     * 将找到的结果返回为一个map的数组
     *
     * @return
     */
    public ArrayList<Map<String, Object>> find() {
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
        MongoCursor cursor = collection.find().sort(ascending("st")).iterator();
        while (cursor.hasNext()) {
            Object objcet = cursor.next();
            arrayList.add((Map<String, Object>) objcet);
        }
        cursor.close();
        return arrayList;
    }

    /**
     * 返回集合中的文档个数
     *
     * @return
     */
    public long count() {
        long countNum = collection.count();
        return countNum;
    }

    /**
     * 将找到的结果返回为一个map的数组
     *
     * @param schema
     * @return
     */
    public ArrayList<Map<String, Object>> find(CommonSchema schema) {
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
        MongoCursor cursor = collection.find(and(gte("st", schema.st), lte("st", schema.et), eq("keyword", schema.keyword), eq("source", schema.source), eq("data_type", schema.data_type))).sort(ascending("st")).iterator();
        while (cursor.hasNext()) {
            Object objcet = cursor.next();
            arrayList.add((Map<String, Object>) objcet);
        }
        cursor.close();
        return arrayList;
    }

    /**
     * 查找微博结果
     *
     * @param schema
     * @param isSeparate
     * @return
     */
    public ArrayList<Map<String, Object>> findWeiboResult(CommonSchema schema, boolean isSeparate) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Map<String, Object>> dateData = new LinkedHashMap<>();
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
        MongoCursor cursor = collection.find(and(gte("st", schema.st), lte("st", schema.et), eq("keyword", schema.keyword), eq("source", schema.source))).sort(ascending("st")).iterator();
        while (cursor.hasNext()) {
            Object objcet = cursor.next();
            arrayList.add((Map<String, Object>) objcet);
        }
        cursor.close();
        arrayList.forEach(x -> {
            Date st = (Date) x.get("st");
            Date et = (Date) x.get("et");
            String stString = simpleDateFormat.format(st);
            String etString = simpleDateFormat.format(et);
            Map<String, Object> assembleData = dateData.get(stString);
            if (assembleData == null) {
                assembleData = new LinkedHashMap<>();
                assembleData.put("startDate", stString);
                assembleData.put("endDate", etString);
                assembleData.put("keyword", x.get("keyword"));
                for (String s : weiboDataType) {
                    assembleData.put(s, (double) 0);
                }
                dateData.put(stString, assembleData);
            }
            dateData.get(stString).put((String) x.get("data_type"), x.get("value"));
        });
        ArrayList<Map<String, Object>> result = dateData.entrySet().parallelStream().map(x -> x.getValue()).collect(Collectors.toCollection(ArrayList<Map<String, Object>>::new));
        if (isSeparate) {
            return result;
        } else {
            if (result.size() == 0) {
                return result;
            } else {
                Map<String, Object> init = result.parallelStream().reduce((x, y) -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("startDate", x.get("startDate"));
                    map.put("endDate", y.get("endDate"));
                    map.put("keyword", y.get("keyword"));
                    for (String s : weiboDataType) {
                        map.put(s, (double) x.get(s) + (double) y.get(s));
                    }
                    return map;
                }).get();
                init.put("coefficient", (double) init.get("actualVolume") == (double) 0 ? 0 : (double) init.get("volume") / (double) init.get("actualVolume"));
                ArrayList<Map<String, Object>> resultList = new ArrayList<>();
                resultList.add(init);
                return resultList;
            }
        }
    }


    /**
     * 返回符合条件的文档个数
     *
     * @param schema
     * @return
     */
    public long count(CommonSchema schema) {
        return collection.count(and(gte("st", schema.st), lte("st", schema.et), eq("keyword", schema.keyword), eq("source", schema.source), eq("data_type", schema.data_type)));
    }

    /**
     * 返回不分别数据类型的文档个数
     *
     * @param schema
     * @return
     */
    public long countAllNum(CommonSchema schema) {
        return collection.count(and(gte("st", schema.st), lte("st", schema.et), eq("keyword", schema.keyword), eq("source", schema.source), ne("data_type", null)));
    }

    /**
     * 返回不分别数据类型的文档个数
     *
     * @param map
     * @return
     */
    public long countAllNum(Map<String, Object> map) {
        CommonSchema schema = new CommonSchema();
        try {
            schema.convertFromMap(map);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this.countAllNum(schema);
    }

    /**
     * 将找到的结果返回为一个map的数组
     *
     * @param map
     * @return
     */
    public ArrayList<Map<String, Object>> find(Map<String, Object> map) {
        CommonSchema schema = new CommonSchema();
        try {
            schema.convertFromMap(map);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this.find(schema);
    }

    /**
     * 返回符合条件的文档个数
     *
     * @param map
     * @return
     */
    public long count(Map<String, Object> map) {
        CommonSchema schema = new CommonSchema();
        try {
            schema.convertFromMap(map);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this.count(schema);
    }

    /**
     * 插入一个文档，并且返回插入之后的文档
     *
     * @param schema
     * @param newData
     * @return
     */
    public Map<String, Object> findOneAndUpdate(CommonSchema schema, boolean newData) {
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(newData ? ReturnDocument.AFTER : ReturnDocument.BEFORE);
        options.upsert(true);
        Document modifer = new Document("$set", schema.convertToDocument());
        Map<String, Object> map = (Map<String, Object>) collection.findOneAndUpdate(and(eq("st", schema.st), eq("source", schema.source), eq("data_type", schema.data_type), eq("keyword", schema.keyword)), modifer, options);
        return map;
    }

    /**
     * 插入一个文档，并且返回插入之后的文档
     *
     * @param sourceMap
     * @param newData
     * @return
     */
    public Map<String, Object> findOneAndUpdate(Map<String, Object> sourceMap, boolean newData) {
        CommonSchema schema = new CommonSchema();
        try {
            schema.convertFromMap(sourceMap);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this.findOneAndUpdate(schema, newData);
    }

    /**
     * 批量插入
     *
     * @param sourceList
     * @param newData
     * @return
     */
    private List<Future<Map<String, Object>>> updates(ArrayList<Map<String, Object>> sourceList, boolean newData) {
        List<Callable<Map<String, Object>>> tasks = new ArrayList<>();
        sourceList.forEach((s) -> {
            Callable task = () -> this.findOneAndUpdate(s, newData);
            tasks.add(task);
        });
        ExecutorService exec = Executors.newFixedThreadPool(100);
        List<Future<Map<String, Object>>> results = null;
        try {
            results = exec.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * 批量插入
     *
     * @param sourceList
     * @param newData
     * @return
     */
    private List<Future<Map<String, Object>>> updateSchema(ArrayList<CommonSchema> sourceList, boolean newData) {
        List<Callable<Map<String, Object>>> tasks = new ArrayList<>();
        sourceList.forEach((s) -> {
            Callable task = () -> this.findOneAndUpdate(s, newData);
            tasks.add(task);
        });
        ExecutorService exec = Executors.newFixedThreadPool(100);
        List<Future<Map<String, Object>>> results = null;
        try {
            results = exec.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * 批量插入
     *
     * @param sourceList
     * @return
     */
    private int bulkManySchema(List<CommonSchema> sourceList) {
        List<UpdateOneModel<Object>> insertList = sourceList.parallelStream().map(x -> {
            Document finder = new Document().append("st", x.st).append("et", x.et).append("keyword", x.keyword)
                    .append("source", x.source).append("data_type", x.data_type);
            UpdateOptions options = new UpdateOptions();
            options.upsert(true);
            Document setter = new Document("$set", x.convertToDocument());
            return new UpdateOneModel<>(finder, setter, options);
        }).collect(Collectors.toCollection(ArrayList<UpdateOneModel<Object>>::new));
        this.collection.bulkWrite(insertList);
        return 1;
    }

    /**
     * 批量插入
     *
     * @param sourceList
     * @return
     */
    private int bulkManyMap(List<Map<String, Object>> sourceList) {
        List<UpdateOneModel<Object>> insertList = sourceList.parallelStream().map(x_source -> {
            CommonSchema x = new CommonSchema();
            try {
                x.convertFromMap(x_source);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Document finder = new Document().append("st", x.st).append("et", x.et).append("keyword", x.keyword)
                    .append("source", x.source).append("data_type", x.data_type);

            UpdateOptions options = new UpdateOptions();
            options.upsert(true);
            Document setter = new Document("$set", x.convertToDocument());
            return new UpdateOneModel<>(finder, setter, options);
        }).collect(Collectors.toCollection(ArrayList<UpdateOneModel<Object>>::new));
        this.collection.bulkWrite(insertList);
        return 1;
    }

//    /**
//     * mongo限制条数的批量插入
//     * @param sourceList
//     * @return
//     */
//    public int bulkManyWithLimit(List<CommonSchema> sourceList) {
//        int limit = 3000;
//        List<CommonSchema> subList=  sourceList.subList(0,limit < sourceList.size()?limit:sourceList.size());
//        List<CommonSchema> remainList = sourceList.subList(limit < sourceList.size()?limit:sourceList.size(),sourceList.size());
//        while (!subList.isEmpty()) {
//            this.bulkMany(subList);
//            subList = remainList.subList(0,limit < remainList.size() ? limit : remainList.size());
//            remainList = remainList.subList(limit < remainList.size() ? limit : remainList.size(),remainList.size());
//        }
//        return 1;
//    }

    /**
     * 批量插入schema的对外接口
     *
     * @param sourceList
     * @return
     */
    public int bulkManySchemaWithLimit(List<CommonSchema> sourceList) {
        int limit = 1000;
        List<CommonSchema> subList = sourceList.subList(0, limit < sourceList.size() ? limit : sourceList.size());
        List<CommonSchema> remainList = sourceList.subList(limit < sourceList.size() ? limit : sourceList.size(), sourceList.size());
        while (!subList.isEmpty()) {
            this.bulkManySchema(subList);
            return this.bulkManySchemaWithLimit(remainList);
        }
        return 1;
    }


    /**
     * 批量插入map的对外接口
     *
     * @param sourceList
     * @return
     */
    public int bulkManyMapWithLimit(List<Map<String, Object>> sourceList) {
        int limit = 3000;
        List<Map<String, Object>> subList = sourceList.subList(0, limit < sourceList.size() ? limit : sourceList.size());
        List<Map<String, Object>> remainList = sourceList.subList(limit < sourceList.size() ? limit : sourceList.size(), sourceList.size());
        while (!subList.isEmpty()) {
            this.bulkManyMap(subList);
            return this.bulkManyMapWithLimit(remainList);
        }
        return 1;
    }


}
