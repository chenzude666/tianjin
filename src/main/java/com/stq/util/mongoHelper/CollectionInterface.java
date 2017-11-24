package com.stq.util.mongoHelper;

import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Map;

import static com.mongodb.client.model.Filters.*;

/**
 * 用来提供自己需要的mongo查询与数据插入接口
 * Created by dazongshi on 17-9-11.
 */
public interface CollectionInterface <T extends CommonObject>{
    ArrayList<Map<String, Object>> find();

    ArrayList<Map<String, Object>> find(T schema);

    ArrayList<Map<String, Object>> find(Map<String, Object> map);

    Map<String, Object> findOneAndUpdate(T schema, boolean newData);

    Map<String, Object> findOneAndUpdate(Map<String, Object> sourceMap, boolean newData);
}
