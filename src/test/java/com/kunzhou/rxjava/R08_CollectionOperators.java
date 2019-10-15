package com.kunzhou.rxjava;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kzhou on 6/13/2018.
 */
public class R08_CollectionOperators {

    @Test
    public void testCollect() {
        Flowable<Game> flow = Flowable.fromIterable(Game.MyGameCollection);

        flow.map(Game::getPrice).collect(ArrayList::new, ArrayList::add).subscribe(list -> list.forEach(System.out::println));
    }

    @Test
    public void testToList() {
        Flowable<Game> flow = Flowable.fromIterable(Game.MyGameCollection);

        List<BigDecimal> list = flow.map(Game::getPrice).toList().blockingGet();
    }
}
