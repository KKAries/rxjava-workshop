package myclasses;

public class OutterClass {
  private int number;
  private int element = 1;
  OutterClass (int x) {
    number = x;
  }

  static class InnerClass {
    private int inner;

    public void getInner(OutterClass outter) {
      this.inner = outter.number;
    }

  }

  public static void main(String[] args) {
    OutterClass outter = new OutterClass(222);
    InnerClass inner = new InnerClass();
    inner.getInner(outter);


    System.out.println(inner.inner);
  }

}

