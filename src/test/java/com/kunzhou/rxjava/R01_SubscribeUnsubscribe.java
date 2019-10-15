package com.kunzhou.rxjava;

import com.kunzhou.rxjava.util.PublisherUtil;
import com.kunzhou.rxjava.util.ThreadUtil;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Ignore;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by kzhou on 5/30/2018.
 */

@Ignore
public class R01_SubscribeUnsubscribe {

    private static final Logger log = LoggerFactory.getLogger(R01_SubscribeUnsubscribe.class);

    @Test
    public void testUnsubscribe() {
        Flowable<Integer> flow = PublisherUtil.natrualNumberFlowable();

        Disposable subscription = flow.subscribeOn(Schedulers.computation()).observeOn(Schedulers.io()).subscribe(x -> log.info("received "+x));

        ThreadUtil.sleep(5, TimeUnit.SECONDS);

        log.info("now cancel subscription");
        subscription.dispose();

        ThreadUtil.sleep(5, TimeUnit.SECONDS);

    }

    @Test
    public void testUnsubscribe2() {
        Flowable<Integer> flow = PublisherUtil.natrualNumberFlowable();
        flow.subscribe(new Subscriber<Integer>() {
            private Subscription subscription;
            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                if (integer > 10) {
                    subscription.cancel();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                log.info("Complete");
            }
        });

    }

    @Test
    public void testNoComplete() {
        Flowable<String> flowable = Flowable.create(emitter -> {
            emitter.onNext("a");
            emitter.onNext("b");
            emitter.onNext("c");
        }, BackpressureStrategy.BUFFER);

        flowable.blockingSubscribe(System.out::println);
    }

}
