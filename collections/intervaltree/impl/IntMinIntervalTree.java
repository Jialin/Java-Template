package template.collections.intervaltree.impl;

import template.collections.intervaltree.AbstractIntervalTree;

public class IntMinIntervalTree extends AbstractIntervalTree {

  public IntMinIntervalTree(int leafCapacity) {
    super(leafCapacity);
  }

  private int[] values;
  private int[] minValues;

  @Override
  public void init(int n) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void createStorage(int nodeCapacity) {
    minValues = new int[nodeCapacity];
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
    minValues[idx] = Integer.MAX_VALUE;
  }

  @Override
  public String toDisplay(int nodeIdx) {
    return String.valueOf(minValues[nodeIdx]);
  }

  public void init(int n, int[] values) {
    this.values = values;
    super.init(n);
  }

  public void update(int idx, int value) {
    minValues[idx + leafCnt()] = value;
    populateRangeUpdateToRoot(idx, idx + 1);
  }

  public int calc(int lower, int upper) {
    calcRange(lower, upper);
    return minValues[0];
  }
}
