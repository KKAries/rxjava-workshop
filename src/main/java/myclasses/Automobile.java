package myclasses;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class Automobile {
  private final Color color;
  private final int gasLevel;
  private final List<String> passengers;

  public Automobile (Color color, int gasLevel, String... passengers) {
    this.color = color;
    this.gasLevel = gasLevel;
    this.passengers = Arrays.asList(passengers);
  }

  public Color getColor() {
    return color;
  }

  public int getGasLevel() {
    return gasLevel;
  }

  public List<String> getPassengers() {
    return passengers;
  }

  public String toString() {
    return "Automobile: " + color + " " + gasLevel + " " + passengers;
  }
}
