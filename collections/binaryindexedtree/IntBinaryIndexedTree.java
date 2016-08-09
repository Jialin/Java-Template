package template.collections.binaryindexedtree;

import template.operators.IntSumBinaryOperator;

import java.util.Arrays;
import java.util.function.IntBinaryOperator;

public class IntBinaryIndexedTree {

  private int n;
  private IntBinaryOperator operator;
  private int[] values;

  public IntBinaryIndexedTree(int capacity) {
    values = new int[capacity];
  }

  public void init(int n) {
    init(n, IntSumBinaryOperator.INSTANCE);
  }

  public void init(int n, IntBinaryOperator operator) {
    this.n = n;
    this.operator = operator;
    Arrays.fill(values, 0, n, 0);
  }

  public void update(int idx, int value) {
    for ( ; idx < n; idx |= idx + 1) {
      values[idx] = operator.applyAsInt(values[idx], value);
    }
  }

  public int calc(int idx) {
    int res = 0;
    for ( ; idx >= 0; idx = (idx & (idx + 1)) - 1) {
      res = operator.applyAsInt(res, values[idx]);
    }
    return res;
  }
}
