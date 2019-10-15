package exercises;

import org.junit.Assert;
import org.junit.Test;

public class EvaluationTest {
  @Test
  public void testEvaluation() {
    Assert.assertEquals(1, Evaluation.evaluate("1"));
    Assert.assertEquals(2, Evaluation.evaluate("(1+4)-3"));
    Assert.assertEquals(2, Evaluation.evaluate("(2+3)-3"));
    Assert.assertEquals(-5, Evaluation.evaluate("-(1+3)-(2-1)"));
    Assert.assertEquals(3, Evaluation.evaluate("6-(4-1)"));
    Assert.assertEquals(0, Evaluation.evaluate("(0)"));
    Assert.assertEquals(3, Evaluation.evaluate("1+1+1"));
    Assert.assertEquals(4, Evaluation.evaluate("1-5+8"));
  }

}
