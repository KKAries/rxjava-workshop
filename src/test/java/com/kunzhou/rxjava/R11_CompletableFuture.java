package com.kunzhou.rxjava;

import com.kunzhou.rxjava.util.ThreadUtil;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by kzhou on 6/6/2018.
 */

//todo create an example that download file from net and then update and save to local use completable future and rxjava
public class R11_CompletableFuture {

    private int queryDatabase() {
        ThreadUtil.sleep(1, TimeUnit.SECONDS);
        Random rand = new Random();
        return rand.nextInt(50);
    }

    @Test
    public void testFuture() {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> queryDatabase(), ThreadUtil.poolA);
        completableFuture.thenAccept(System.out::println);

        completableFuture.join();
    }
}
