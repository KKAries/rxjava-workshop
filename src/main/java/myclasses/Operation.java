package myclasses;

import java.util.function.IntBinaryOperator;

public enum Operation {
  ADD ((x, y) -> x+y),
  SUBTRACT ((x, y) -> x - y),
  MULTIPLY ((x, y) -> x * y),
  DIVIDE ((x, y) -> x/y);

  private final IntBinaryOperator operator;

  Operation(IntBinaryOperator operator) {
    this.operator = operator;
  }

  public int apply(int x, int y) {
    return operator.applyAsInt(x, y);
  }

  public static void main(String[] args) {
    System.out.println(ADD.apply(1, 2));
    System.out.println(ADD.toString());
  }
}
