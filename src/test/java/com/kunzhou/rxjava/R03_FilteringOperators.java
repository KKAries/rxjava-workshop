package com.kunzhou.rxjava;


import io.reactivex.rxjava3.core.Flowable;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Created by kzhou on 6/1/2018.
 */

@Ignore
public class R03_FilteringOperators {

    private static final Logger log = LoggerFactory.getLogger(R03_FilteringOperators.class);

    @Test
    public void testFilter(){
        Flowable<String> source = Flowable.just("River", "James", "Oven", "Micheal", "Zhihua", "Kun");

        source.filter(name -> name.length() < 5)
                .test().assertValues("Oven", "Kun");

    }


    @Test
    public void testDistinct() {
        Flowable<Game> gameFlow = Flowable.fromIterable(Game.MyGameCollection);
        gameFlow.map(game -> game.getPublisher()).distinct().subscribe(System.out::println);
    }

    @Test
    public void testDistinctByKey() {
        Flowable<Game> gameFlow = Flowable.fromIterable(Game.MyGameCollection);
        gameFlow.distinct(game -> game.getTitle().replaceAll("[0-9]", "")).subscribe(System.out::println);
    }

    @Test
    public void testSkipByNumber() {
        Flowable.range(1, 20).skip(10).subscribe(System.out::println);
    }

    @Test
    public void testSkipByTime() {
        Flowable.interval(500, TimeUnit.MILLISECONDS)
                .doOnNext(i -> log.info("emitted " + String.valueOf(i)))
                .skip(2, TimeUnit.SECONDS)
                .blockingSubscribe(i -> log.info("received " + String.valueOf(i)));

    }
}
