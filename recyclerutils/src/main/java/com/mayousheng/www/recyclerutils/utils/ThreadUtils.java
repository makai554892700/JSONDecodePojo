package com.mayousheng.www.recyclerutils.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {
    private static final ExecutorService singleService = Executors.newSingleThreadExecutor();
    private static final ExecutorService fixedService = Executors.newFixedThreadPool(10);

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
        }
    }

    public static void runSingle(Runnable runnable) {
        singleService.execute(runnable);
    }

    public static void runFixed(Runnable runnable) {
        fixedService.execute(runnable);
    }

}
