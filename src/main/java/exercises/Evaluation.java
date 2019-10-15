package exercises;

import java.util.Stack;
import java.util.function.IntBinaryOperator;

public class Evaluation {

  public static void main(String[] args) {
    String expression = "-((1 + 2) - 3)";
    System.out.println(evaluate(expression));
  }

  enum Operator {
    ADD((x,y) -> x + y),
    SUB((x,y) -> x - y),
    LEFTPAREN((x,y) -> 0),
    RIGHTPAREN((x,y) -> 0);

    private IntBinaryOperator operator;
    Operator(IntBinaryOperator operator) {
      this.operator = operator;
    }

    public int apply(int left, int right) {
      return operator.applyAsInt(left, right);
    }
  }

  public static int evaluate(String expression){
    expression = expression.replaceAll(" ", "");

    Stack<Operator> operatorStack = new Stack<>();
    Stack<Integer> operandStack = new Stack<>();

    for (int index = 0; index < expression.length(); index++){
      char character = expression.charAt(index);
      if (Character.isDigit(character)) {
        //calculate if there is operator and is not '('
        if (!operatorStack.empty() && !isControlOperator(operatorStack.peek())) {
          int left = operandStack.pop();
          int right = Character.getNumericValue(character);
          Operator operator = operatorStack.pop();
          operandStack.push(operator.apply(left, right));
        } else {
          operandStack.push(Character.getNumericValue(character));
        }
      } else {
        if (character == ')') {
          //pop the '('
          operatorStack.pop();
          //calculate the operation saved before
          if (!operatorStack.empty() && !isControlOperator(operatorStack.peek())) {
            int right = operandStack.pop();
            int left = operandStack.pop();
            Operator operator = operatorStack.pop();
            operandStack.push(operator.apply(left, right));
          }
        } else {
          operatorStack.push(charToOperator(character));
          if (index == 0 && character == '-') {
            operandStack.push(0);
          }
        }
      }
    }
    return operandStack.pop();

  }

  private static Operator charToOperator(char c) {
    switch(c) {
      case '+':
        return Operator.ADD;
      case '-':
        return Operator.SUB;
      case '(':
        return Operator.LEFTPAREN;
      case ')':
        return Operator.RIGHTPAREN;
      default:
        return null;
    }
  }

  private static boolean isControlOperator(Operator operator){
    return (operator == Operator.LEFTPAREN) || (operator == Operator.RIGHTPAREN);
  }

}
