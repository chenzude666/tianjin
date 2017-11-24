package com.stq.util.mongoHelper;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
//import org.bson.Document;
//import com.mongodb.client.model.ReturnDocument;
//import com.mongodb.client.model.FindOneAndUpdateOptions;

/**
 * mongo集合的基本连接建立，用于创建可以进行find等基础操作的MongoCollection对象
 * Created by dazongshi on 17-9-7.
 */
public class CollectionConnector {
    private String mongoUrl = "mongodb://tarantula:Joke123098@101.201.37.28:3725/tarantula";
    private String dataBase = "tarantula";
    private String collectionName = "test_javas";
    private MongoClientURI mongoClientURI;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection connection;

    /**
     * constructor
     *
     * @param mongoUrl
     * @param dataBase
     * @param collectionName
     */
    public CollectionConnector(String mongoUrl, String dataBase, String collectionName) {
        this.mongoUrl = mongoUrl;
        this.dataBase = dataBase;
        this.collectionName = collectionName;
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
//        builder.socketKeepAlive(true);
        builder.threadsAllowedToBlockForConnectionMultiplier(1000);
        builder.serverSelectionTimeout(1800_000);
        mongoClientURI = new MongoClientURI(mongoUrl, builder);
        mongoClient = new MongoClient(mongoClientURI);
        mongoDatabase = mongoClient.getDatabase(dataBase);
        connection = mongoDatabase.getCollection(collectionName);
    }

    /**
     * constructor
     */
    public CollectionConnector() {
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
//        builder.socketKeepAlive(true);
        builder.threadsAllowedToBlockForConnectionMultiplier(1000);
        builder.serverSelectionTimeout(1800_000);
        mongoClientURI = new MongoClientURI(mongoUrl, builder);
        mongoClient = new MongoClient(mongoClientURI);
        mongoDatabase = mongoClient.getDatabase(dataBase);
        connection = mongoDatabase.getCollection(collectionName);
    }

    /**
     * 返回可以使用的数据库集合
     *
     * @return
     */
    public MongoCollection getConnection() {
        return connection;
    }

    /**
     * 关闭数据库链接
     */
    public void connectionClose() {
        mongoClient.close();
    }
}
