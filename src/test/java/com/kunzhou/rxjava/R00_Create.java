package com.kunzhou.rxjava;

import com.kunzhou.rxjava.util.PublisherUtil;
import com.kunzhou.rxjava.util.ThreadUtil;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kzhou on 5/30/2018.
 */

@Ignore
public class R00_Create {

    private static final Logger log = LoggerFactory.getLogger(R00_Create.class);

    @Test
    public void testCreate() {
        Flowable<Integer> flowable = Flowable.create(emitter -> {
            int i= 0;
            try {
                while (!emitter.isCancelled() && i < 15){
                    emitter.onNext(i++);
                }
                emitter.onComplete();
            } catch (Exception e){
                emitter.onError(e);
            }
        }, BackpressureStrategy.BUFFER);
        flowable.subscribe(x -> log.info("received " + x));
    }


    @Test
    public void testJust(){
        Flowable<String> flow = Flowable.just("A", "B", "C").doOnNext(x -> log.info("emitting " + x));
        log.info("start");
        flow.subscribe(x -> log.info("received "+ x));
    }

    @Test
    public void testJustTrap() {
        Flowable<String> flow = Flowable.just(PublisherUtil.generateString()).doOnNext(x -> log.info("emitting " +x)).subscribeOn(Schedulers.computation());

        //ThreadUtil.sleep(10, TimeUnit.SECONDS);

        flow.blockingSubscribe(x -> log.info("receive "+x));
    }

    @Test
    public void testRange() {
        Flowable.range(2, 5)
                .subscribe(System.out::println);
                //.test().assertValues(2, 3, 4, 5, 6);
    }

    @Test
    public void testDefer() {
        AtomicInteger num = new AtomicInteger();
        num.set(5);
        Flowable<Integer> flow = Flowable.defer(() -> Flowable.range(1, num.get()));
        num.set(6);
        flow.subscribe(System.out::println);
    }

    @Test
    public void testCache() {
        Flowable<Integer> flowable = PublisherUtil.natrualNumberFlowable().subscribeOn(Schedulers.computation()).cache();
        log.info("start");
        flowable.take(3).blockingSubscribe(m -> log.info("subscriber 1 receive " + m));
        flowable.take(4).blockingSubscribe(m -> log.info("subscriber 2 receive " + m));
        ThreadUtil.sleep(10, TimeUnit.SECONDS);
    }

    @Test
    public void testFromIterable() {
        Flowable.fromIterable(Game.MyGameCollection)
                .subscribe(System.out::println);
    }




    @Test
    public void testFromCallable(){
        Flowable<String> flow = Flowable.fromCallable(() -> PublisherUtil.generateString());


        flow.subscribe(x -> log.info("receive " + x));
    }


    @Test
    public void testInterval() {
        Flowable.interval(1, TimeUnit.SECONDS).doOnNext(i -> log.info("emitting "+i)).blockingSubscribe(i -> log.info("received " +i));
    }

    @Test
    public void testTimer() {
        log.info("Start...");
        Flowable.timer(2, TimeUnit.SECONDS).doOnNext(i -> log.info("emitting "+i)).blockingSubscribe();
    }

    @Test
    public void testMaybe() {
        Maybe.create(emitter -> emitter.onSuccess("a")).subscribe(s -> log.info("get {}", s));

    }

    @Test
    public void testStatelessGenerate() {
        Random ran = new Random();
        Flowable<Integer> flow = Flowable.generate(emitter -> {
            int num = ran.nextInt();
            log.info("emitting {}", num);
            emitter.onNext(num);
        });
        flow.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation(), false, 1)
            .map(i -> process(i))
            .subscribe();

        ThreadUtil.sleep10Secs();
    }

    @Test
    public void testStatefuleGenerate() {
        Flowable<Integer> flow = Flowable.generate(() -> 0, (state, emitter) -> {
            if (state > 10) {
                emitter.onComplete();
                return 0;
            }
            log.info("emitting {}", state);
            emitter.onNext(state++);
            return state;
        });
        flow.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation(), false, 1)
                .map(i -> process(i))
                .blockingSubscribe();

    }

    private int process(int num) {
        log.info("processing {}", num);
        ThreadUtil.sleep(2, TimeUnit.SECONDS);
        return num;
    }
}
