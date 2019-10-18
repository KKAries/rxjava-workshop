package myclasses.myimplements;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MyArrayList<E> extends AbstractList<E> implements List<E> {

  private Object[] elements;
  private final int INITIAL_CAPACITY = 20;
  private int size = 0;


  public MyArrayList() {
    elements = new Object[INITIAL_CAPACITY];
  }

  public MyArrayList(Collection<? extends E> c) {
    elements = new Object[INITIAL_CAPACITY];
    if (c != null) {
      Object[] array = c.toArray();
      elements = array;
      size = array.length;
    } else {
      elements = new Object[INITIAL_CAPACITY];
    }
  }

  public E get(int index) {
    if (isIndexSafe(index)) {
      return (E) elements[index];
    }
    throw new ArrayIndexOutOfBoundsException();
  }

  public int size() {
    return size;
  }


  private boolean isIndexSafe(int index) {
    return index < size && index >=0;
  }

  public E set(int index, E e) {
    if (isIndexSafe(index)) {
      E element = (E)elements[index];
      elements[index] = e;
      return element;
    }

    throw new IndexOutOfBoundsException();
  }

  public E remove(int index) {
    if (isIndexSafe(index)) {
      E element = (E)elements[index];

      for (int i = index; i < size-1; i++) {
        elements[i] = elements[i+1];
      }
      elements[size-1] = null;
      size --;
      return element;
    }
    throw new IndexOutOfBoundsException();
  }

  public void add(int index, E e) {
    if (index > size) {
      throw new IndexOutOfBoundsException();
    }
    ensureCapacity();
    for (int i = size -1; i >= index; i--){
      elements[i + 1] = elements[i];
    }
    elements[index] = e;
    size++;
  }

  private void ensureCapacity() {
    if (size >= elements.length) {
      int newLength = elements.length * 2;
      elements = Arrays.copyOf(elements, newLength);
    }
  }

}
