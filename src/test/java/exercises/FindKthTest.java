package exercises;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class FindKthTest {

  @Test
  public void testNormal(){
    Assert.assertEquals(2, (int)FindKth.findKth(Arrays.asList(1,2,3,4,5), 4));
    Assert.assertEquals(1, (int)FindKth.findKth(Arrays.asList(1,2,3,4), 4));
    Assert.assertEquals(1, (int)FindKth.findKth(Arrays.asList(1), 1));
  }

  @Test
  public void testEmpty(){
    Assert.assertNull(FindKth.findKth(new ArrayList<Integer>(), 1));
  }

  @Test
  public void testSmallK(){
    Assert.assertNull(FindKth.findKth(Arrays.asList(1,2,3), 4));
  }

  @Test
  public void testZero(){
    Assert.assertNull(FindKth.findKth(Arrays.asList(1,2,3,4), 0));
  }

}
