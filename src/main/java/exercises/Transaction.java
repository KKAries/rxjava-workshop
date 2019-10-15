package exercises;

public class Transaction implements Runnable{
  private int count = 0;

  @Override
  public void run() {
    for (int i=0;i<10_000;i++) {
      count++;
    }
    System.out.println(Thread.currentThread().getName() + " completed");
  }

  public static void main(String[] args) throws InterruptedException {
    Transaction trans = new Transaction();

    new Thread(trans).start();
    new Thread(trans).start();

    Thread.sleep(1000);

    System.out.println("final count is " + trans.count);
  }

}
