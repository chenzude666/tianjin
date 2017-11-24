package com.stq.util.mongoHelper.mongoEntity.Weiboer;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.stq.util.mongoHelper.CommonObject;
import com.stq.util.mongoHelper.MongoCollectPool;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Map;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;

/**
 * Created by dazongshi on 17-11-1.
 */
public class WeiboerCollection {
    protected MongoCollection collection;
    protected static WeiboerCollection instance = null;
    private WeiboerCollection() {
        collection = MongoCollectPool.getCollection("mongodb://weibo:Idt123098@101.201.37.28:3720/weibo", "weibo", "weiboers");
    }
    /**
     * 返回微博用户集合的唯一实例
     * @return
     */
    public static synchronized WeiboerCollection getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new WeiboerCollection();
            return instance;
        }
    }
    public Weiboer findOne(String weiboer_id) throws NoSuchFieldException, IllegalAccessException {
        MongoCursor cursor = collection.find(and(eq("_id", weiboer_id))).iterator();
        Weiboer weiboer = null;
        while (cursor.hasNext()) {
            weiboer = new Weiboer();
            weiboer.convertFromMap((Map<String,Object>)cursor.next());
        }
        cursor.close();
        return weiboer;
    }

}
