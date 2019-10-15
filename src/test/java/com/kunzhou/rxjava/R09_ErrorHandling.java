package com.kunzhou.rxjava;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by kzhou on 6/8/2018.
 */
public class R09_ErrorHandling {

    private static final Logger log = LoggerFactory.getLogger(R09_ErrorHandling.class);
    private static final StockPriceApiService service = new StockPriceApiService();

    private Flowable<Double> getPriceFlowWithInterval(long millis) {
        return Flowable.interval(millis, TimeUnit.MILLISECONDS)
                .map(i -> service.getPriceWithLimitation());
    }

    private Flowable<Double> getBetterPriceFlow(long millis) {
        return Flowable.interval(millis, TimeUnit.MILLISECONDS)
                .map(i -> service.getPrice());
    }


    @Test
    public void testError() {
        getPriceFlowWithInterval(300)
                .blockingSubscribe(price -> log.info("get price {}", price), error -> log.error("error:", error));
    }

    @Test
    public void testRetry() {
        getPriceFlowWithInterval(300)
                .retry()
                .blockingSubscribe(price -> log.info("get price {}", price), error -> log.error("error:", error));
    }

    @Test
    public void testDefaultValue(){
        getPriceFlowWithInterval(300)
                .onErrorReturn(ex -> {
                            log.error("error:", ex);
                            return 100d;
                        }
                )
                .blockingSubscribe(price -> log.info("get price {}", price));
    }

    @Test
    public void testAlterFlow(){
        getPriceFlowWithInterval(300)
                .doOnError(error -> log.error("emm, error:", error))
                .onErrorResumeNext(error -> getBetterPriceFlow(300))
                .blockingSubscribe(price -> log.info("get price {}", price));
    }


}
