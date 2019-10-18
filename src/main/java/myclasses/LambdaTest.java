package myclasses;

@FunctionalInterface
interface FunctionalOne{
  int apply (int x, int y);
}


@FunctionalInterface
interface FunctionalTwo{
  String apply (int x, int y);
}


public class LambdaTest {
  private static int a = 1;
  private static int b = 2;


  public static void print(FunctionalOne f) {
    System.out.println(f.apply(a, b));
  }

  public static void print(FunctionalTwo f) {
    System.out.println(f.apply(a, b));
  }


  public static void main(String[] args) {
    print((FunctionalOne) (x, y) -> x + y);
  }
}
