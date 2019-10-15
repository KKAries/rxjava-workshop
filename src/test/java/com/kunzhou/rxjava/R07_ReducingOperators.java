package com.kunzhou.rxjava;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by kzhou on 6/11/2018.
 */
public class R07_ReducingOperators {
    private static final Logger log = LoggerFactory.getLogger(R07_ReducingOperators.class);

    @Test
    public void testReduce() {
        Flowable<Game> flow = Flowable.fromIterable(Game.MyGameCollection);

        flow.map(Game::getPrice).reduce((acc,p) -> acc.add(p)).subscribe(System.out::println);
    }

    @Test
    public void testReduceWithSeed() {
        Flowable<Game> flow = Flowable.fromIterable(Game.MyGameCollection);

        flow.reduce(new ArrayList<>(), (list,game) -> {
            list.add(game);
            return list;
        }).subscribe(list -> list.forEach(System.out::println));
        // simplified version -- collect
    }

    @Test
    public void testAny() {
        Flowable<Game> gameFlow = Flowable.fromIterable(Game.MyGameCollection);
        Flowable<Long> tickerFlow = Flowable.interval(1, TimeUnit.SECONDS);

        boolean haveAny = gameFlow.zipWith(tickerFlow, (g, t) -> g).doOnNext(game -> log.info(game.toString())).any(game -> game.getPrice().compareTo(new BigDecimal(20)) >0).blockingGet();
        log.info(String.valueOf(haveAny));
    }


}
