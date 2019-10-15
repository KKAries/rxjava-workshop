package com.kunzhou.rxjava;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by kzhou on 6/12/2018.
 */
public enum Weather {
    SUNNY,
    WINDY,
    RAINY,
    CLOUDY,
    SNOWY;

    private static final Random ran = new Random();
    private static final List<Weather> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    public static Weather getWeather(City city, LocalDate date) {
        return VALUES.get(ran.nextInt(VALUES.size()));
    }
}
