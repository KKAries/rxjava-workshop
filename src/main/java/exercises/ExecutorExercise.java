package exercises;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorExercise {

  public static void main(String[] args) {
    ExecutorService es = Executors.newFixedThreadPool(2);
    List<Future<String>> handleList = new ArrayList<>();
    for (int i=0;i<6;i++){
      handleList.add(es.submit(new MyJob()));
    }
    System.out.println("All job submitted...");
    es.shutdown();
    for (Future<String> handle:handleList){
      try {
        String result = handle.get();
        System.out.println("job produced: " + result);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        System.out.println("job blew up with error: " + e.getCause().getMessage());
      }
    }

    System.out.println("All job finished...");
  }


}

class MyJob implements Callable<String>{
  private static int nextJobId = 0;
  private String jobName = "job " + ++nextJobId;

  @Override
  public String call() throws Exception {
    System.out.println(jobName + " starting");
    if (Math.random() > 0.7) throw new SQLException("broken data");
    Thread.sleep((int) (Math.random()*2000 + 1000));
    System.out.println(jobName + " completed");
    return "finished: " + jobName;
  }
}