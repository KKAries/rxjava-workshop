package exercises;

import classes.Automobile;
import classes.IterableX;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IterableXExercise {

  public static void main(String[] args) {
    IterableX<Automobile> fleet = IterableX.of(Arrays.asList(
      new Automobile(Color.RED, 98, "Alan"),
      new Automobile(Color.BLUE, 66, "Jane", "Rachel"),
      new Automobile(Color.WHITE, 72, "John", "Janet"),
      new Automobile(Color.GREEN, 23, "Sue", "Kim", "Eric", "Jo")
    ));

    fleet
      .filter(a -> a.getPassengers().size() > 1)
      .flatMap(a -> a.getPassengers())
      .forEach(System.out::println);
  }
}
