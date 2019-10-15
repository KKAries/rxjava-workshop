package exercises.stream.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceTest {

  private static final Logger log = LoggerFactory.getLogger(PerformanceTest.class);


  public static void main(String[] args) {
    int count = 100000000;

    useParallel(count);
    useSequential(count);
  }

  private static void useSequential(int count) {
   // CopyOnWriteArrayList list = new CopyOnWriteArrayList();
    long startTime = System.nanoTime();

    IntStream.range(0, count).forEach(i -> i++);
    //.map(i -> i++).forEach(list::add);

    long stopTime = System.nanoTime();

    System.out.println(" items using sequential in " + (stopTime - startTime));
  }


  private static void useParallel(int count) {
    //CopyOnWriteArrayList list = new CopyOnWriteArrayList();

    long startTime = System.nanoTime();

    IntStream.range(0, count).parallel().forEach(i -> i++);
    //.map(i -> i++).forEach(list::add);

    long stopTime = System.nanoTime();


    System.out.println(" items using parallel in " + (stopTime - startTime));
  }

}
