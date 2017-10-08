package template.collections.intervaltree.impl;

import template.collections.intervaltree.AbstractIntervalTree;

/**
 * Interval tree supports:
 * <ul>
 *   <li>Setting value in a node</li>
 *   <li>Calculating the max value in a range</li>
 * </ul>
 */
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
    maxValues[idxInTree] = Long.MIN_VALUE;
  }

  @Override
  public String toDisplay(int idxInTree) {
    return String.valueOf(maxValues[idxInTree]);
  }

  public void init(int n, long[] values) {
    this.values = values;
    super.init(n);
  }

  public void update(int idx, long value) {
    maxValues[idxInTree(idx)] = value;
    populateRangeUpdateToRoot(idx, idx + 1);
  }

  public long calc(int lower, int upper) {
    calcRange(lower, upper);
    return maxValues[0];
  }
}
