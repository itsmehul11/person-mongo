package com.example.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;

public class MongoDBConfig {
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    private MongoDBConfig() {
        // Private constructor to prevent instantiation
    }

    public static MongoClient getMongoClient() {
        if (mongoClient == null) {
            ConnectionString connectionString = new ConnectionString(MongoDBProperties.getConnectionString());
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();
            mongoClient = MongoClients.create(settings);
        }
        return mongoClient;
    }

    public static MongoDatabase getDatabase() {
        if (database == null) {
            database = getMongoClient().getDatabase(MongoDBProperties.getDatabaseName());
        }
        return database;
    }

    public static void closeClient() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
        }
    }


}
