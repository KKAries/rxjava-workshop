package com.kunzhou.rxjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kzhou on 6/9/2018.
 */
public class Dish {
    private static final Logger log = LoggerFactory.getLogger(Dish.class);
    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;

    public Dish () {
        id = count.incrementAndGet();
        log.info("Created dish: " + id);
    }

    public String toString() {
        return String.valueOf(id);
    }
}
