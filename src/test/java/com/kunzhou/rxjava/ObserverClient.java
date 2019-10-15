package com.kunzhou.rxjava;

import com.kunzhou.rxjava.util.ThreadUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created by kzhou on 6/7/2018.
 */
public class ObserverClient {

    private final List<Consumer<String>> observers = new ArrayList<>();

    public void addObserver(Consumer<String> observer) {
        observers.add(observer);
    }

    public void start() {
        StockPriceApiService service = new StockPriceApiService();
        while (true) {
            final String price = String.valueOf(service.getPrice());
            observers.forEach(observer -> observer.accept(price));
            ThreadUtil.sleep(1, TimeUnit.SECONDS);
        }
    }

    public static void main(String[] args) {

        ObserverClient client = new ObserverClient();
        client.addObserver(System.out::println);
        client.start();

    }

}
