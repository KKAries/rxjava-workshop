package myclasses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class IterableX<E> implements Iterable<E> {

  private Iterable<E> wrapped;
  private IterableX() {}

  public static <E> IterableX<E> of(Iterable<E> iterable) {
    IterableX<E> self = new IterableX<>();
    self.wrapped = iterable;
    return self;
  }

  public Iterator<E> iterator(){
    return wrapped.iterator();
  }

  public IterableX<E> filter(Predicate<E> predicate) {
    List<E> updated = new ArrayList<>();
    wrapped.forEach( x -> {
      if (predicate.test(x))
        updated.add(x);
    });
    return of(updated);
  }

  public <R> IterableX<R> map(Function<E, R> operator) {
    List<R> updated = new ArrayList<>();
    wrapped.forEach( x -> updated.add(operator.apply(x)));
    return of(updated);
  }

  public <R> IterableX<R> flatMap(Function<E, Iterable<R>> operator) {
    List<R> updated = new ArrayList<>();
    wrapped.forEach( x -> {
      Iterable<R> iterableR = operator.apply(x);
      iterableR.forEach(r -> updated.add(r));
    });
    return of(updated);
  }
}
