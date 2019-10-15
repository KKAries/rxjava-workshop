package com.kunzhou.rxjava;

import com.kunzhou.rxjava.util.ThreadUtil;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by kzhou on 6/10/2018.
 */
public class R06_Schedulers {

    private static final Logger log = LoggerFactory.getLogger(R06_Schedulers.class);

    @Test
    public void testSubscribeOnBlocking() {
        Flowable.range(1, 10)
                .doOnNext(i -> log.info("emitting {}", String.valueOf(i)))
                .subscribeOn(ThreadUtil.schedulerA)
                .blockingSubscribe(i -> log.info("Received "+ i));
    }

    @Test
    public void testSubscribeOn() {
        Flowable.range(1, 10)
                .doOnNext(i -> log.info("emitting {}", String.valueOf(i)))
                .subscribeOn(ThreadUtil.schedulerA)
                .subscribe(i -> log.info("Received "+ i));

        ThreadUtil.sleep10Secs();
    }

    @Test
    public void testObserveOn() {
        Flowable.range(1, 10)
                .doOnNext(i -> log.info("emitting {}", String.valueOf(i)))
                .observeOn(ThreadUtil.schedulerA)
                .subscribe(i -> log.info("Received "+ i));

        ThreadUtil.sleep10Secs();
    }

    @Test
    public void testCombine() {
        Flowable.range(1, 10)
                .doOnNext(i -> log.info("emitting {}", String.valueOf(i)))
                .observeOn(ThreadUtil.schedulerA)
                .doOnNext(i -> log.info("after first observeOn {}", String.valueOf(i)))
                .observeOn(ThreadUtil.schedulerC)
                .doOnNext(i -> log.info("after second observeOn {}", String.valueOf(i)))
                .subscribeOn(ThreadUtil.schedulerB)
                .subscribe(i -> log.info("Received "+ i));

        ThreadUtil.sleep10Secs();
    }

    @Test
    public void testComputationScheduler(){
        Flowable.range(1,10000)
                .flatMap( i -> rxProcess(i).subscribeOn(Schedulers.computation()), 30)
                .blockingSubscribe();
    }

    @Test
    public void testIOScheduler(){
        Flowable.range(1,10000)
                .flatMap( i -> rxProcess(i).subscribeOn(Schedulers.io()), 30)
                .blockingSubscribe();
    }

    private Integer process(int i){
        log.info("processing {}", i);
        ThreadUtil.sleep(10, TimeUnit.SECONDS);
        return i;
    }

    private Flowable<Integer> rxProcess(int num) {
        return Flowable.fromCallable(() -> process(num));
    }
}
