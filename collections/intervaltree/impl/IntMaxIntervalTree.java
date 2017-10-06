package template.collections.intervaltree.impl;

import template.collections.intervaltree.AbstractIntervalTree;

public class IntMaxIntervalTree extends AbstractIntervalTree {

  public IntMaxIntervalTree(int leafCapacity) {
    super(leafCapacity);
  }

  private int[] values;
  private int[] maxValues;

  @Override
  public void init(int n) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void createStorage(int nodeCapacity) {
    maxValues = new int[nodeCapacity];
  }

  @Override
  public void initLeaf(int idxInTree, int idx) {
    maxValues[idxInTree] = values[idx];
  }

  @Override
  public void merge(int leftIdxInTree, int rightIdxInTree, int idxInTree) {
    maxValues[idxInTree] = Math.max(
        maxValues[leftIdxInTree], maxValues[rightIdxInTree]);
  }

  @Override
  public void copyForCalc(int fromIdxInTree, int toIdxInTree) {
    maxValues[toIdxInTree] = maxValues[fromIdxInTree];
  }

  @Override
  public void clearNode(int idxInTree) {
    maxValues[idxInTree] = Integer.MIN_VALUE;
  }

  @Override
  public String toDisplay(int idxInTree) {
    return String.valueOf(maxValues[idxInTree]);
  }

  public void init(int n, int[] values) {
    this.values = values;
    super.init(n);
  }

  public void update(int idx, int value) {
    maxValues[idxInTree(idx)] = value;
    populateRangeUpdateToRoot(idx, idx + 1);
  }

  public int calc(int lower, int upper) {
    calcRange(lower, upper);
    return maxValues[0];
  }
}
