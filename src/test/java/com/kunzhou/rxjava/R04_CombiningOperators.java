package com.kunzhou.rxjava;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

/**
 * Created by kzhou on 6/7/2018.
 */
public class R04_CombiningOperators {

    private static final Logger log = LoggerFactory.getLogger(R04_CombiningOperators.class);

    @Test
    public void testZipWithFreq() {
        Flowable<String> apple = Flowable.interval(10, TimeUnit.MILLISECONDS).map(i -> "apple".concat(String.valueOf(i)));
        Flowable<String> orange = Flowable.interval(10, TimeUnit.MILLISECONDS).map(i -> "orange".concat(String.valueOf(i)));

        Flowable.zip(apple, orange, (a,o) -> (a.concat("+").concat(o))).blockingSubscribe(x -> log.info(x.toString()));
    }

    @Test
    public void testZipWithDiffFreq() {
        Flowable<Long> apple = Flowable.interval(17, TimeUnit.MILLISECONDS).onBackpressureDrop();
        Flowable<Long> orange = Flowable.interval(10, TimeUnit.MILLISECONDS).onBackpressureDrop();

        Flowable.zip(apple.timestamp(), orange.timestamp(), (a,o) -> a.time() - o.time()).blockingSubscribe(x -> log.info(x.toString()));
    }

    @Test
    public void testCombineLatestWithDiffFreq() {
        Flowable<String> apple = Flowable.interval(17, TimeUnit.MILLISECONDS).map(i -> "apple".concat(String.valueOf(i)));
        Flowable<String> orange = Flowable.interval(10, TimeUnit.MILLISECONDS).map(i -> "orange".concat(String.valueOf(i)));

        Flowable.combineLatest(apple, orange, (a,o) -> (a.concat("+").concat(o))).blockingSubscribe(x -> log.info(x.toString()));
    }

    @Test
    public void testMergeMultiThread() {
        Flowable<String> flow1 = Flowable.interval(200, TimeUnit.MILLISECONDS).map(i -> String.valueOf(i).concat(" from flow1"));
        Flowable<String> flow2 = Flowable.interval(500, TimeUnit.MILLISECONDS).map(i -> String.valueOf(i).concat(" from flow2"));
        Flowable<String> flow3 = Flowable.interval(100, TimeUnit.MILLISECONDS).map(i -> String.valueOf(i).concat(" from flow3"));

        Flowable.merge(flow1, flow2, flow3).blockingSubscribe(s -> log.info(s));
    }

    @Test
    public void testMergeSingleThread() {
        Flowable<String> flow1 = Flowable.range(1, 10).map(i -> String.valueOf(i).concat(" from flow1"));
        Flowable<String> flow2 = Flowable.range(1, 5).map(i -> String.valueOf(i).concat(" from flow2"));
        Flowable<String> flow3 = Flowable.range(1, 9).map(i -> String.valueOf(i).concat(" from flow3"));

        Flowable.merge(flow1, flow2, flow3).blockingSubscribe(s -> log.info(s));  // just like concat
    }

    @Test
    public void testConcatMultiThread() {
        Flowable<String> flow1 = Flowable.interval(200, TimeUnit.MILLISECONDS).map(i -> String.valueOf(i).concat(" from flow1")).take(2, TimeUnit.SECONDS);
        Flowable<String> flow2 = Flowable.interval(500, TimeUnit.MILLISECONDS).map(i -> String.valueOf(i).concat(" from flow2")).take(3, TimeUnit.SECONDS);
        Flowable<String> flow3 = Flowable.interval(100, TimeUnit.MILLISECONDS).map(i -> String.valueOf(i).concat(" from flow3")).take(1, TimeUnit.SECONDS);

        Flowable.concat(flow1, flow2, flow3).blockingSubscribe(s -> log.info(s));
    }

    @Test
    public void testZipVacation() {
        Flowable<LocalDate> nextTenDays =
                Flowable
                        .range(1, 10)
                        .map(i -> LocalDate.now().plusDays(i));

        Flowable<Vacation> possibleVacations =
                Flowable.just(City.HANGZHOU, City.SHENZHEN, City.XIZANG)
                        .flatMap(city -> nextTenDays.map(date -> new Vacation(city, date)))
                        .flatMap(vacation ->
                                Flowable.zip(
                                        vacation.weather().filter(weather -> weather.equals(Weather.SUNNY)),
                                        vacation.cheapFlightFrom(City.SHANGHAI),
                                        vacation.cheapHotel(),
                                        (f,h,w) -> vacation
                                ));

        possibleVacations.subscribe(System.out::println);
    }

    //how will you implement this in pure java
}
