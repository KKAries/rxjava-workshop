package com.kunzhou.rxjava;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * Created by kzhou on 6/10/2018.
 */
public class R05_TransformingOperators {

    private static final Logger log = LoggerFactory.getLogger(R05_TransformingOperators.class);

    @Test
    public void testMap() {
        Flowable.fromIterable(Game.MyGameCollection)
                .map(game -> game.getPrice())
                .subscribe(System.out::println);
    }


    @Test
    public void testFlatMap() {
        Flowable<Game> flow = Flowable.fromIterable(Game.MyGameCollection);
        flow.filter(game -> game.getPrice().compareTo(new BigDecimal(10)) > 0)
                .flatMap(game -> rxProcess(game))   //.subscribeOn(Schedulers.computation()))
                .blockingSubscribe(disk -> log.info(disk));
    }

    @Test
    public void testConcatMap() {
        Flowable<Game> flow = Flowable.fromIterable(Game.MyGameCollection);
        flow.filter(game -> game.getPrice().compareTo(new BigDecimal(10)) > 0)
                .concatMap(game -> rxProcess(game).subscribeOn(Schedulers.computation()))
                .blockingSubscribe(disk -> log.info(disk));
    }

    private Flowable<String> rxProcess (Game game) {
        return Flowable.fromIterable(game.getDisks())
                //.doOnNext(disk -> log.info("processing game {}", game.getTitle()))
                .map(disk -> game.getTitle().concat("-").concat(disk));
    }


    @Test
    public void testDelay() {
        Flowable.just("A", "B", "C", "D")
                .delay(3, TimeUnit.SECONDS)
                .blockingSubscribe(s -> log.info(s));
    }

    @Test
    public void testScan() {
        Flowable.range(1,100)
                .scan((a,b) -> a + b)
                .subscribe(System.out::println);
    }
}
