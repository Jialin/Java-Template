package template.collections.intervaltree.impl;

import template.collections.intervaltree.AbstractIntervalTreeWithLazyPropagation;

/**
 * Interval tree supports:
 * <ul>
 *   <li>Setting value in a range</li>
 *   <li>Calculating the minimum value in a range</li>
 * </ul>
 */
public class IntMinIntervalTreeSupportSet extends AbstractIntervalTreeWithLazyPropagation {

  public IntMinIntervalTreeSupportSet(int leafCapacity) {
    super(leafCapacity);
  }

  public IntMinIntervalTreeSupportSet(int leafCapacity, boolean initialize) {
    super(leafCapacity, initialize);
  }

  private int[] minValue;
  private boolean[] toSet;
  private int[] setValue;

  @Override
  public void createStorage(int nodeCapacity) {
    minValue = new int[nodeCapacity];
    toSet = new boolean[nodeCapacity];
    setValue = new int[nodeCapacity];
  }

  @Override
  public void initLeaf(int idxInTree, int idx) {
    minValue[idxInTree] = initValues[idx];
  }

  @Override
  public void assignFakeLazyPropagation() {
    toSet[0] = true;
    setValue[0] = updateValue;
  }

  @Override
  public void pushLazyPropagation(int fromIdxInTree, int toIdxInTree) {
    if (toSet[fromIdxInTree]) {
      toSet[toIdxInTree] = true;
      setValue[toIdxInTree] = setValue[fromIdxInTree];
      minValue[toIdxInTree] = setValue[toIdxInTree];
    }
  }

  @Override
  public void clearLazyPropagation(int idxInTree) {
    toSet[idxInTree] = false;
  }

  @Override
  public void merge(int leftIdxInTree, int rightIdxInTree, int idxInTree) {
    minValue[idxInTree] = Math.min(minValue[leftIdxInTree], minValue[rightIdxInTree]);
  }

  @Override
  public void copyForCalc(int fromIdxInTree, int toIdxInTree) {
    minValue[toIdxInTree] = minValue[fromIdxInTree];
  }

  @Override
  public void clearNodeForCalc(int idxInTree) {
    minValue[idxInTree] = Integer.MAX_VALUE;
  }

  @Override
  public String toDisplay(int idxInTree) {
    return String.format(
        "min:%d set:%s",
        minValue[idxInTree],
        toSet[idxInTree] ? String.valueOf(setValue[idxInTree]) : "X");
  }

  private int[] initValues;

  public void init(int n, int[] initValues) {
    this.initValues = initValues;
    super.init(n);
  }

  private int updateValue;

  public void update(int lower, int upper, int updateValue) {
    this.updateValue = updateValue;
    updateRange(lower, upper);
  }

  public int calc(int lower, int upper) {
    calcRange(lower, upper);
    return minValue[0];
  }
}
