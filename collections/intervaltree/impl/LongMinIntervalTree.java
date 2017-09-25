package template.collections.intervaltree.impl;

import template.collections.intervaltree.AbstractIntervalTree;

public class LongMinIntervalTree extends AbstractIntervalTree {

  public LongMinIntervalTree(int leafCapacity) {
    super(leafCapacity);
  }

  private long[] values;
  private long[] minValues;

  @Override
  public void init(int n) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void createStorage(int nodeCapacity) {
    minValues = new long[nodeCapacity];
  }

  @Override
  public void initLeaf(int nodeIdx, int idx) {
    minValues[nodeIdx] = values[idx];
  }

  @Override
  public void merge(int idx, int leftIdx, int rightIdx) {
    minValues[idx] = Math.min(minValues[leftIdx], minValues[rightIdx]);
  }

  @Override
  public void copy(int destIdx, int sourceIdx) {
    minValues[destIdx] = minValues[sourceIdx];
  }

  @Override
  public void clearNode(int idx) {
    minValues[idx] = Long.MAX_VALUE;
  }

  @Override
  public String toDisplay(int nodeIdx) {
    return String.valueOf(minValues[nodeIdx]);
  }

  public void init(int n, long[] values) {
    this.values = values;
    super.init(n);
  }

  public void update(int idx, long value) {
    minValues[idx + leafCnt()] = value;
    populateRangeUpdateToRoot(idx, idx + 1);
  }

  public long calc(int lower, int upper) {
    calcRange(lower, upper);
    return minValues[0];
  }
}
