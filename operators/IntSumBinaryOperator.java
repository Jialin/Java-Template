package template.operators;

import java.util.function.IntBinaryOperator;

public class IntSumBinaryOperator implements IntBinaryOperator {

  public static final IntSumBinaryOperator INSTANCE = new IntSumBinaryOperator();

  private IntSumBinaryOperator() {}

  @Override
  public int applyAsInt(int u, int v) {
    return u + v;
  }
}
