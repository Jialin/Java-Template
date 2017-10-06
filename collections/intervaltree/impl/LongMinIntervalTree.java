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
  public void initLeaf(int idxInTree, int idx) {
    minValues[idxInTree] = values[idx];
  }

  @Override
  public void merge(int leftIdxInTree, int rightIdxInTree, int idxInTree) {
    minValues[idxInTree] = Math.min(
        minValues[leftIdxInTree], minValues[rightIdxInTree]);
  }

  @Override
  public void copyForCalc(int fromIdxInTree, int toIdxInTree) {
    minValues[toIdxInTree] = minValues[fromIdxInTree];
  }

  @Override
  public void clearNode(int idxInTree) {
    minValues[idxInTree] = Long.MAX_VALUE;
  }

  @Override
  public String toDisplay(int idxInTree) {
    return String.valueOf(minValues[idxInTree]);
  }

  public void init(int n, long[] values) {
    this.values = values;
    super.init(n);
  }

  public void update(int idx, long value) {
    minValues[idxInTree(idx)] = value;
    populateRangeUpdateToRoot(idx, idx + 1);
  }

  public long calc(int lower, int upper) {
    calcRange(lower, upper);
    return minValues[0];
  }
}
