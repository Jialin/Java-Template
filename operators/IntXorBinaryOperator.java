package template.operators;

import java.util.function.IntBinaryOperator;

public class IntXorBinaryOperator implements IntBinaryOperator {

  public static final IntXorBinaryOperator INSTANCE = new IntXorBinaryOperator();

  private IntXorBinaryOperator() {}

  @Override
  public int applyAsInt(int u, int v) {
    return u ^ v;
  }
}
