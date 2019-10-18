package myimplements;

import myclasses.myimplements.MyArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyArrayListTest {

  private static Collection<String> dataSource;

  @Before
  public void setup() {
    dataSource = Arrays.asList("Teddy", "Curry", "George", "Jordon", "Catherine", "Lily", "Kevin");
  }

  @Test
  public void testGet() {
    MyArrayList<String> myList = new MyArrayList<>(dataSource);
    Assert.assertEquals("George", myList.get(2));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetOutOfBound() {
    MyArrayList<String> myList = new MyArrayList<>(dataSource);
    System.out.println(myList.get(11));
  }



  @Test
  public void testIterator() {
    MyArrayList<String> myList = new MyArrayList<>(dataSource);
    Iterator<String> iterator = myList.iterator();
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }
  }

  @Test
  public void testListIterator() {
    MyArrayList<String> myList = new MyArrayList<>(dataSource);
    ListIterator<String> listIterator = myList.listIterator();

    listIterator.add("a");
  }

  @Test
  public void testAdd() {
    MyArrayList<String> myList = new MyArrayList<>(dataSource);
    myList.add(4, "insertGuy");
    Assert.assertEquals("insertGuy", myList.get(4));
    Assert.assertEquals("Catherine", myList.get(5));
  }

  @Test
  public void testSet() {
    MyArrayList<String> myList = new MyArrayList<>(dataSource);
    String old = myList.set(3, "newGuy");

    Assert.assertEquals("newGuy", myList.get(3));
    Assert.assertEquals("Jordon", old);

  }

  @Test
  public void testRemove(){
    MyArrayList<String> myList = new MyArrayList<>(dataSource);

    myList.remove(3);
    Assert.assertEquals("Catherine", myList.get(3));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testSetOutOfBound() {
    MyArrayList<String> myList = new MyArrayList<>(dataSource);
    myList.set(11, "newGuy");

  }

}
