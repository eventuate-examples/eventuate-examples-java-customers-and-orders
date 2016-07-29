package net.chrisrichardson.eventstore.examples.customersandorders.commontest;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TestUtil {

  public static <T> T await(CompletableFuture<T> o) {
    try {
      return o.get(100, TimeUnit.SECONDS);
    } catch (InterruptedException | TimeoutException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }


  static class Tuple2<A, B> {
    private A first;
    private B second;

    Tuple2(A first, B second) {
      this.first = first;
      this.second = second;
    }
  }

  interface Outcome<T> {

  }

  static  class Success<T> implements Outcome<T> {

    T value;

    public Success(T value) {
      this.value = value;
    }
  }

  static class Failure<T> implements Outcome<T> {
    Throwable t;

    public Failure(Throwable t) {
      this.t = t;
    }
  }

  public static <T> T eventually(Supplier<T> producer, Predicate<T> predicate) {
    return eventuallyAsync(() -> Observable.just(producer.get()), predicate);
  }

  public static <T> T eventuallyAsync(Supplier<Observable<T>> producer, Predicate<T> predicate) {
    try {
      return Observable.interval(1000, TimeUnit.MILLISECONDS)
              .take(30)
              .flatMap(idx -> producer.get())
              .filter(predicate::test)
              .toBlocking().first();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
