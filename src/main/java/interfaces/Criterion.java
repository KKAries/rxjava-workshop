package interfaces;

public interface Criterion<T> {
  boolean test (T type);

  default Criterion<T> inverse () {
    return (T t) -> !this.test(t);
  }
}
