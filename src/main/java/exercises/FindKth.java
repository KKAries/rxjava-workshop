package exercises;

import java.util.Iterator;
import java.util.List;

public class FindKth {
  public static <T> T findKth(List<T> list, int k){
    if ( k == 0){
      return null;
    }
    Iterator<T> probeIterator = list.iterator();
    Iterator<T> resultIterator = list.iterator();
    int i = 0;
    while(probeIterator.hasNext()){
      i ++;
      probeIterator.next();
      if ( i > k) {
        resultIterator.next();
      }
    }
    if (i >= k) {
      return resultIterator.next();
    }

    return null;
  }

}
