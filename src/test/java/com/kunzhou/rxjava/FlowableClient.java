package com.kunzhou.rxjava;

import io.reactivex.rxjava3.core.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by kzhou on 6/7/2018.
 */
public class FlowableClient {

    private static final Logger log = LoggerFactory.getLogger(FlowableClient.class);

    public static void main(String[] args) {
        StockPriceApiService service = new StockPriceApiService();
        Flowable<Double> priceFlow = Flowable.interval(1, TimeUnit.SECONDS)
                .map(i -> service.getPrice()).doOnNext(price -> log.info("emitting Price " + price));

        //Flowable<String> anotherFlow = Flowable.range(0,1000).map(i -> String.valueOf(i)).doOnNext(i -> log.info("get i " + i));

        //priceFlow.filter(price -> price > 160).map(price -> String.valueOf(price)).zipWith(anotherFlow, (e1, e2) -> (e1 + " mix with " + e2))
        //        .blockingSubscribe(element -> log.info(element));

        priceFlow.blockingSubscribe(price -> log.info("get {}", price));
    }
}
