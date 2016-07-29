package net.chrisrichardson.eventstore.examples.customersandorders.commontest;

public interface Verifier<T> {
  public void verify(T x);
}
