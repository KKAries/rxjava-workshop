package com.kunzhou.rxjava.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by kzhou on 5/30/2018.
 */
public class ThreadUtil {

    private static final Logger log = LoggerFactory.getLogger(ThreadUtil.class);

    public static final ExecutorService poolA = Executors.newFixedThreadPool(10, threadFactory("Sched-A-%d"));
    public static final ExecutorService poolB = Executors.newFixedThreadPool(10, threadFactory("Sched-B-%d"));
    public static final ExecutorService poolC = Executors.newFixedThreadPool(10, threadFactory("Sched-C-%d"));
    public static final Scheduler schedulerA = Schedulers.from(poolA);
    public static final Scheduler schedulerB = Schedulers.from(poolB);
    public static final Scheduler schedulerC = Schedulers.from(poolC);


    public static void sleep(int timeout, TimeUnit unit) {
        try{
            unit.sleep(timeout);
        } catch (InterruptedException ex){
            log.error("Sleep error: ", ex);
        }
    }

    public static void sleep10Secs() {
        sleep(10, TimeUnit.SECONDS);
    }

    private static ThreadFactory threadFactory(String pattern) {
        return new ThreadFactoryBuilder()
                .setNameFormat(pattern)
                .build();
    }


}
