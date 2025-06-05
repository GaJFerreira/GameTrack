package com.example.gametrack.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();

    private static final Executor mainThreadExecutor = command ->
            new Handler(Looper.getMainLooper()).post(command);

    public static ExecutorService getDatabaseExecutor() {
        return databaseExecutor;
    }

    public static Executor getMainThreadExecutor() {
        return mainThreadExecutor;
    }
}


