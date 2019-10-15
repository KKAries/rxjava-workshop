package com.kunzhou.rxjava;

import com.kunzhou.rxjava.util.ThreadUtil;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Ignore;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by kzhou on 5/31/2018.
 */

@Ignore
public class R10_Backpressure {

    private static final Logger log = LoggerFactory.getLogger(R10_Backpressure.class);


    private Observable<Dish> generateObservable() {
        return Observable.range(1, 1000000000)
                .map(i -> new Dish());
    }

    private Flowable<Dish> generateFlowable() {
        return Flowable.range(1, 1000000000)
                .map(i -> new Dish());
    }

    private ConnectableFlowable<Integer> generateConnectableFlowable() {
        return Flowable.range(1, 1000000000)
                .map(i -> {
                    Random ran = new Random();
                    return ran.nextInt(50);
                })
                .subscribeOn(Schedulers.io())
                .publish();
    }

    @Test
    public void testObservableBackPressure() {
        Observable<Dish> dishes = generateObservable();
        dishes.observeOn(Schedulers.io())
                .subscribe(dish -> {
                    log.info("Washing dish: " + dish);
                    ThreadUtil.sleep(50, TimeUnit.MILLISECONDS);
                }, Throwable::printStackTrace);
    }

    @Test
    public void testFlowableBackPressure() {
        Flowable<Dish> dishes = generateFlowable();
        dishes.observeOn(Schedulers.newThread())
                .subscribe(dish -> {
                    log.info("Washing dish: " + dish);
                    ThreadUtil.sleep(50, TimeUnit.MILLISECONDS);
                }, Throwable::printStackTrace);

        ThreadUtil.sleep10Secs();

    }

    @Test
    public void testThrottleHotFlowable() {
        ConnectableFlowable<Integer> hot = generateConnectableFlowable();
        hot.connect();
        hot
                .sample(300, TimeUnit.MILLISECONDS)
                //.buffer(30).map(list -> list.stream().mapToInt(i -> i).average())
                //.throttleFirst(300, TimeUnit.MILLISECONDS)
                .blockingSubscribe(num -> log.info("receive integer {}", num), Throwable::printStackTrace);
    }

    @Test
    public void testMissingBackPressure() {
        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .doOnNext(num -> log.info(String.valueOf(num)))
                .observeOn(Schedulers.io())
                .subscribe(next -> ThreadUtil.sleep(50, TimeUnit.MICROSECONDS));

        ThreadUtil.sleep10Secs();
    }

    @Test
    public void testRequestOneBackPressure() {
        Flowable.range(1, 1000).doOnNext(x -> log.info("emitted {}", x)).subscribeOn(Schedulers.computation())
                .filter( x -> {
                    ThreadUtil.sleep(1, TimeUnit.SECONDS);
                    return true;
                }).blockingSubscribe( x -> log.info("received {}", x));
    }


    private static Publisher<Dish> dishPublisher() {
        return subscriber -> {
            subscriber.onSubscribe(new Subscription() {
                @Override
                public void request(long l) {
                    for (long i=0; i<l ; i++) {
                        try{
                            subscriber.onNext(new Dish());
                        }
                        catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                }

                @Override
                public void cancel() {
                    subscriber.onComplete();
                }
            });
        };
    }

    private static Subscriber<Dish> dishSubscriber() {
        return new Subscriber<Dish>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1);

            }

            @Override
            public void onNext(Dish dish) {
                //some computation work
                ThreadUtil.sleep(500, TimeUnit.MILLISECONDS);
                log.info("washing dish {}", dish);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                log.info("Done");
            }
        };
    }

    @Test
    public void testBackPressure() {
        Flowable.fromPublisher(dishPublisher()).subscribeOn(Schedulers.io()).blockingSubscribe(dishSubscriber());
    }

}
