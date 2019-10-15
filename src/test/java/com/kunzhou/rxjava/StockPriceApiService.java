package com.kunzhou.rxjava;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by kzhou on 6/7/2018.
 */
public class StockPriceApiService {
    private static final int min = 100;
    private static final int max = 200;
    private LocalDateTime lastCallTime = LocalDateTime.now();
    private final LongAdder counter = new LongAdder();

    public double getPrice() {
        Random ran = new Random();
        return min + (max - min)*ran.nextDouble();
    }

    public double getPriceWithLimitation() throws ServiceUnavailableException{
        LocalDateTime newCallTime = LocalDateTime.now();
        if (getCallInterval(newCallTime) < 500) {
            lastCallTime = newCallTime;
            if (counter.sum() >= 3) {
                counter.reset();
                throw new ServiceUnavailableException("You are calling too fast man!");
            } else {
                counter.increment();
            }
        }

        lastCallTime = newCallTime;
        Random ran = new Random();
        return min + (max - min)*ran.nextDouble();
    }

    private long getCallInterval(LocalDateTime newCallTime) {
        return ChronoUnit.MILLIS.between(lastCallTime, newCallTime);
    }
}
