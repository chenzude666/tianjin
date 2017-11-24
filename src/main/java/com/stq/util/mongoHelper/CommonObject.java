package com.stq.util.mongoHelper;

import org.bson.Document;

import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;

/**
 * 最基础的mongo数据对象类型
 * Created by dazongshi on 17-9-8.
 */
public class CommonObject {
    /**
     * 将此类型转化为Document
     *
     * @return
     */
    public Document convertToDocument() {
        Document document = new Document();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.get(this) != null) {
                    document.append(field.getName(), field.get(this));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return document;
    }

    /**
     * 将此类型转化为Map
     *
     * @return
     */
    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.get(this) != null) {
                    map.put(field.getName(), field.get(this));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 从Map类型转化为基本数据集合类型
     *
     * @param map
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */

    public void convertFromMap(Map<String, Object> map) throws NoSuchFieldException, IllegalAccessException {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (map.get(field.getName()) != null) {
                    field.setAccessible(true);
                    field.set(this, (map.get(field.getName()) instanceof Integer) ? Long.valueOf(map.get(field.getName()).toString()) : map.get(field.getName()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从Document类型转化为基本数据集合类型
     *
     * @param document
     */
    public void convertFromDocument(Document document) {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (document.get(field.getName()) != null) {
                    field.set(this, document.get(field.getName()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
