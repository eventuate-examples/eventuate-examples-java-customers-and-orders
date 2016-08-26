package net.chrisrichardson.eventstore.examples.customersandorders.commontest;

import rx.Observable;

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
    Throwable laste = null;
    for (int i = 0; i < 30 ; i++) {
      try {
        T x = producer.get();
        if (predicate.test(x))
          return x;
      } catch (Throwable t) {
        laste = t;
      }
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    if (laste != null)
      throw new RuntimeException("Last exception was", laste);
    else
      throw new RuntimeException("predicate never satisfied");
  }

}
