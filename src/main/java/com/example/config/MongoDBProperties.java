package com.example.config;

public class MongoDBProperties {
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 27017;
    private static final String DEFAULT_DATABASE = "test";

    public static String getConnectionString() {
        return String.format("mongodb://%s:%s", DEFAULT_HOST, DEFAULT_PORT);
    }

    public static String getDatabaseName() {
        return DEFAULT_DATABASE;
    }
}
