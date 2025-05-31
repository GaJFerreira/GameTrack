package com.example.gametrack.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();

    public static ExecutorService getDatabaseExecutor() {
        return databaseExecutor;
    }
}

