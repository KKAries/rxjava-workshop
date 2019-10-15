package exercises;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureEx {

  public static void main(String[] args) {
    CompletableFuture<Void> cf = CompletableFuture
          .supplyAsync(()->"file.txt")
          .thenComposeAsync(CompletableFutureEx::readFile)
          .thenAcceptAsync(x -> System.out.println("Result is " + x));
    System.out.println("pipeline assembly completed");
    cf.join();
    System.out.println("pipeline execution completed");
  }

  private static CompletableFuture<String> readFile(String fileName) {
    CompletableFuture<String> cf = new CompletableFuture<>();
    System.out.println(Thread.currentThread().getName());
    new Thread(() -> {
      System.out.println("reading file " + fileName);
      try {
        Thread.sleep(1000 + (int)(Math.random() * 2000));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      String fileContent = "this is the file content";
      cf.complete(fileContent);
    }).start();

    return cf;
  }


}
