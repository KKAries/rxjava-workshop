package com.kunzhou.rxjava;

import com.kunzhou.rxjava.util.ThreadUtil;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by kzhou on 5/31/2018.
 */

@Ignore
public class R02_ConnectableFlowable {
    private static final Logger log = LoggerFactory.getLogger(R02_ConnectableFlowable.class);

    private Flowable<String> getSource() {
        return Flowable.create(emitter -> {
            log.info("Establishing connection...");

            //some task

            emitter.setCancellable(() -> log.info("Disconnecting..."));

        }, BackpressureStrategy.DROP);
    }

    private Flowable<Integer> getSource2() {
        return Flowable.create(emitter -> {
            log.info("Start emitting...");

            int i =0;
            while (true) {
                emitter.onNext(i++);
                ThreadUtil.sleep(500, TimeUnit.MILLISECONDS);
            }


        }, BackpressureStrategy.DROP);
    }

    @Test
    public void testPublish(){
        ConnectableFlowable<Integer> flow = getSource2().subscribeOn(Schedulers.io()).publish();
        flow.connect();
        ThreadUtil.sleep(1, TimeUnit.SECONDS);
        log.info("Start subscribe sub1...");
        Disposable ref1 = flow.subscribe(i -> log.info("sub1 received {}", i));
        ThreadUtil.sleep(3, TimeUnit.SECONDS);
        ref1.dispose();
        log.info("Start subscribe sub2...");
        Disposable ref2 = flow.subscribe(i -> log.info("sub2 received {}", i));
        ThreadUtil.sleep(3, TimeUnit.SECONDS);
        ref2.dispose();

    }


    @Test
    public void testMultipleSubscribe(){
        Flowable flow = getSource().publish().refCount();
        log.info("Start subscribe...");
        Disposable ref1 = flow.subscribe();
        log.info("Subscribed 1");
        Disposable ref2 = flow.subscribe();
        log.info("Subscribed 2");
        ref1.dispose();
        log.info("Unsubscribed 1");
        ref2.dispose();
        log.info("Unsubscribed 2");
    }


}
