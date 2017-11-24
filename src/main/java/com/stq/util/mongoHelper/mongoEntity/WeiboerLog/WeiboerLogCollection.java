package com.stq.util.mongoHelper.mongoEntity.WeiboerLog;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.stq.util.mongoHelper.MongoCollectPool;
import com.stq.util.mongoHelper.mongoEntity.Weiboer.WeiboerCollection;
import org.bouncycastle.jcajce.provider.symmetric.DES;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;

import java.util.*;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;

/**
 * Created by dazongshi on 17-11-1.
 */
public class WeiboerLogCollection {
    protected MongoCollection collection;
    protected static WeiboerLogCollection instance = null;

    private WeiboerLogCollection() {
        collection = MongoCollectPool.getCollection("mongodb://weibo:Idt123098@101.201.37.28:3720/weibo", "weibo", "weiboer_logs");
    }

    /**
     * 返回微博日志的唯一实例
     *
     * @return
     */
    public static synchronized WeiboerLogCollection getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new WeiboerLogCollection();
            return instance;
        }
    }

    public List<WeiboerLog> find(String weiboer_id, String startTime, String endTime) throws NoSuchFieldException, IllegalAccessException {
        List<WeiboerLog> weiboerLogs = new ArrayList<>();
        MongoCursor cursor = collection.find(and(eq("weiboer_id", weiboer_id), gte("crawled_at", LocalDate.parse(startTime).toDate()), lt("crawled_at", LocalDate.parse(endTime).withFieldAdded(DurationFieldType.days(), 1).toDate()))).sort(ascending("crawled_at")).iterator();
        while (cursor.hasNext()) {
            WeiboerLog weiboerLog = new WeiboerLog();
            Object object = cursor.next();
            weiboerLog.convertFromMap((Map<String, Object>) object);
            weiboerLogs.add(weiboerLog);
        }
        return weiboerLogs;
    }
}
