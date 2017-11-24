package com.stq.util.mongoHelper;

import com.mongodb.client.MongoCollection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于创建mongo的集合池
 * Created by dazongshi on 17-9-8.
 */
public class MongoCollectPool {
    //存储集合池的map
    private static Map<String, CollectionConnector> map = new ConcurrentHashMap<>();

    /**
     * 获得数据集合的连接，没有则创建一个
     *
     * @param mongoUrl
     * @param dataBase
     * @param collectionName
     * @return
     */
    public synchronized static MongoCollection getCollection(String mongoUrl, String dataBase, String collectionName) {
        String collectionIdentifer = mongoUrl + "-" + dataBase + "-" + collectionName;
        if (map.get(collectionIdentifer) == null) {
            System.out.println("init new mongo collection connect");
            CollectionConnector newConnector = new CollectionConnector(mongoUrl, dataBase, collectionName);
            map.put(collectionIdentifer, newConnector);
            return newConnector.getConnection();
        } else {
            System.out.println("use collection connect that has existed");
            return map.get(collectionIdentifer).getConnection();
        }
    }

    /**
     * 关闭所有的数据集合连接
     */
    public synchronized static void closeAll() {
        for (String k : map.keySet()) {
            map.get(k).connectionClose();
        }
        map = new ConcurrentHashMap<>();
        System.out.println("all connect has been closed !");
    }

    /**
     * 关闭单个数据集合连接
     *
     * @param mongoUrl
     * @param dataBase
     * @param collectionName
     * @return
     */
    public synchronized static String closeConnection(String mongoUrl, String dataBase, String collectionName) {
        String collectionIdentifer = mongoUrl + "-" + dataBase + "-" + collectionName;
        if (map.get(collectionIdentifer) == null) {
            System.out.println("mongo connect doesn't exist!!!");
        } else {
            map.get(collectionIdentifer).connectionClose();
            map.remove(collectionIdentifer);
            System.out.println("mongo connect" + collectionIdentifer + "has been closed!");
        }
        return collectionIdentifer;
    }
}
