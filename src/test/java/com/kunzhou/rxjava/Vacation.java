package com.kunzhou.rxjava;


import io.reactivex.rxjava3.core.Flowable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

/**
 * Created by kzhou on 6/12/2018.
 */
public class Vacation {
    private final City where;
    private final LocalDate when;
    private final Random RANDOM = new Random();

    private Weather weather;
    private String cheapFlight;
    private String cheapHotel;


    public Weather getWeather() {
        return weather;
    }

    public String getCheapFlight() {
        return cheapFlight == null? "":cheapFlight;
    }

    public String getCheapHotel() {
        return cheapHotel;
    }


    Vacation (City where, LocalDate when) {
        this.where = where;
        this.when = when;
        weather = Weather.getWeather(where, when);
    }

    public Flowable<Weather> weather() {
        return Flowable.just(weather);
    }

    public Flowable<String> cheapFlightFrom(City from) {
        if (RANDOM.nextBoolean()) {
            cheapFlight = "AC0".concat(String.valueOf(RANDOM.nextInt(50)));
            return Flowable.just(cheapFlight);
        } else {
            return Flowable.empty();
        }
    }

    public Flowable<String> cheapHotel() {
        if (RANDOM.nextBoolean()) {
            cheapHotel = "Hotel ".concat(String.valueOf(RANDOM.nextInt(50)));
            return Flowable.just(cheapHotel);
        } else {
            return Flowable.empty();
        }
    }

    @Override
    public String toString() {
        return String.format("['%s', '%s', '%s', '%s', '%s']",
                where, when, getWeather(), getCheapFlight(), getCheapHotel());
    }
}
