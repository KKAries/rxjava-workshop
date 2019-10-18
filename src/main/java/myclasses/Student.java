package myclasses;

import com.google.common.base.MoreObjects;

public class Student {

  private final float gpa;
  private final String firstName;
  private final String lastName;

  public float getGpa() {
    return gpa;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Student (float gpa, String firstName, String lastName) {
    this.gpa = gpa;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String toString() {
    return MoreObjects.toStringHelper(this)
              .add("gpa", gpa)
              .add("first name", firstName)
              .add("lastName", lastName)
              .toString();
  }

}
