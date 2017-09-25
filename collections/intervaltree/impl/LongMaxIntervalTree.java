package template.collections.intervaltree.impl;

import template.collections.intervaltree.AbstractIntervalTree;

public class LongMaxIntervalTree extends AbstractIntervalTree {

  public LongMaxIntervalTree(int leafCapacity) {
    super(leafCapacity);
  }

  private long[] values;
  private long[] maxValues;

  @Override
  public void init(int n) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void createStorage(int nodeCapacity) {
    maxValues = new long[nodeCapacity];
  }

  @Override
  public void initLeaf(int nodeIdx, int idx) {
    maxValues[nodeIdx] = values[idx];
  }

  @Override
  public void merge(int idx, int leftIdx, int rightIdx) {
    maxValues[idx] = Math.max(maxValues[leftIdx], maxValues[rightIdx]);
  }

  @Override
  public void copy(int destIdx, int sourceIdx) {
    maxValues[destIdx] = maxValues[sourceIdx];
  }

  @Override
  public void clearNode(int idx) {
    maxValues[idx] = Long.MIN_VALUE;
  }

  @Override
  public String toDisplay(int nodeIdx) {
    return String.valueOf(maxValues[nodeIdx]);
  }

  public void init(int n, long[] values) {
    this.values = values;
    super.init(n);
  }

  public void update(int idx, long value) {
    maxValues[idx + leafCnt()] = value;
    populateRangeUpdateToRoot(idx, idx + 1);
  }

  public long calc(int lower, int upper) {
    calcRange(lower, upper);
    return maxValues[0];
  }
}
