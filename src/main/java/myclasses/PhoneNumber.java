package myclasses;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Comparator;
import java.util.Objects;

public class PhoneNumber implements Comparable<PhoneNumber>{

  private static final Comparator<PhoneNumber> PHONE_NUMBER_COMPARATOR = Comparator
      .comparingInt((PhoneNumber p) -> p.areaCode)
      .thenComparingInt(p -> p.number);

  public int getAreaCode() {
    return areaCode;
  }

  public int getNumber() {
    return number;
  }

  private final int areaCode;
  private final int number;
  private final int hash;

  private PhoneNumber(int areaCode, int number) {
    this.areaCode = areaCode;
    this.number = number;
    this.hash = Objects.hash(areaCode, number);
  }

  public static PhoneNumber of(int areaCode, int number) {
    checkArgument(areaCode > 100);
    return new PhoneNumber(areaCode, number);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj instanceof  PhoneNumber) {
      PhoneNumber that = (PhoneNumber) obj;
      return this.areaCode == that.areaCode && this.number == that.number;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return hash;
  }


  @Override
  public int compareTo(PhoneNumber o) {
    return PHONE_NUMBER_COMPARATOR
                      .compare(this, o);
  }
}
