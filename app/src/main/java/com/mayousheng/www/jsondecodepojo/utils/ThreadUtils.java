package com.mayousheng.www.jsondecodepojo.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtils {
    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(2000, 20000
            , 2000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(2000));
}
