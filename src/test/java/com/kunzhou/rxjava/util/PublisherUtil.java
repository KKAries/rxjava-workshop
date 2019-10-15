package com.kunzhou.rxjava.util;


import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kzhou on 5/30/2018.
 */
public class PublisherUtil {

    private static final Logger log = LoggerFactory.getLogger(PublisherUtil.class);

    public static Flowable<Integer> natrualNumberFlowable() {
        return Flowable.create(emitter -> {
            int i=0;
            while(!emitter.isCancelled()){
                emitter.onNext(i);
                log.info("emitted " + i);
                i++;
            }
            emitter.onComplete();
        }, BackpressureStrategy.DROP);
    }

    public static String generateString() {
        String s = "Hello Houston, this is POGO";
        log.info("generating String: "+ s);
        return s;
    }
}
