package net.chrisrichardson.eventstore.examples.customersandorders.commontest;

import java.util.concurrent.CompletableFuture;

public interface Producer<T> {
  public CompletableFuture<T> produce();
}
