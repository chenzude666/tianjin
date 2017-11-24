package com.stq.util.mongoHelper.mongoEntity.CTimeHelper;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.stq.util.mongoHelper.CommonObject;
import com.stq.util.mongoHelper.MongoCollectPool;

import static com.mongodb.client.model.Filters.*;

import java.util.Date;
import java.util.Map;

/**
 * Created by dazongshi on 17-10-20.
 */
public class CTimeHelperCollection {
    protected MongoCollection collection;
    protected static CTimeHelperCollection instance = null;
    private CTimeHelperCollection() {
        collection = MongoCollectPool.getCollection("mongodb://pikachu:pikachu123@101.201.37.28:5717/pikachu-dev", "pikachu-dev", "c_time_helpers");
    }
    /**
     * 返回时间助手集合的唯一实例
     * @return
     */
    public static synchronized CTimeHelperCollection getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new CTimeHelperCollection();
            return instance;
        }
    }

    /**
     * 获得任务开始时间
     * @param source_type
     * @return
     */
    public Date getStartTime (String source_type) {
        MongoCursor cursor  = collection.find(eq("source_type",source_type)).iterator();
        Object object = cursor.next();
        CTimeHelper cTimeHelper = new CTimeHelper();
        try {
            cTimeHelper.convertFromMap((Map<String,Object>) object);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return cTimeHelper.getStart_time();
    }
}
