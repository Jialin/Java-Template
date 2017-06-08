package template.collections.intervaltree.impl;

import template.collections.intervaltree.AbstractSimpleIntervalTree;

import java.util.Arrays;

public class IntMinValueIntervalTreeWithSetRange extends AbstractSimpleIntervalTree {

  public IntMinValueIntervalTreeWithSetRange(int leafCapacity) {
    super(leafCapacity, false);
  }

  int[] minValue;
  boolean[] toSet;
  int[] setValue;

  @Override
  public void createSubclass(int nodeCapacity) {
    minValue = new int[nodeCapacity];
    toSet = new boolean[nodeCapacity];
    setValue = new int[nodeCapacity];
  }

  @Override
  public void initLazyPropagation(int nodeCapacity) {
    Arrays.fill(toSet, 0, nodeCapacity, false);
  }

  @Override
  public void initLeaf(int nodeIdx, int idx) {
    minValue[nodeIdx] = initValues[idx];
  }

  @Override
  public void calcNonLeafNode(int nodeIdx) {
    if (toSet[nodeIdx]) {
      minValue[nodeIdx] = setValue[nodeIdx];
    } else {
      minValue[nodeIdx] = Math.min(minValue[nodeIdx << 1], minValue[(nodeIdx << 1) | 1]);
    }
  }

  int res;

  @Override
  public void calcAppend(int nodeIdx) {
    res = Math.min(res, minValue[nodeIdx]);
  }

  @Override
  public void assignFakeLazyPropagation() {
    toSet[0] = true;
    setValue[0] = updateValue;
  }

  @Override
  public void pushLazyPropagation(int fromNodeIdx, int toNodeIdx) {
    if (toSet[fromNodeIdx]) {
      toSet[toNodeIdx] = true;
      setValue[toNodeIdx] = setValue[fromNodeIdx];
      minValue[toNodeIdx] = setValue[toNodeIdx];
    }
  }

  @Override
  public void clearLazyPropagation(int nodeIdx) {
    toSet[nodeIdx] = false;
  }

  @Override
  public String valueToString(int nodeIdx) {
    return String.format("min:%d", minValue[nodeIdx]);
  }

  @Override
  public String lazyPropagationToString(int nodeIdx) {
    return "set:" + (toSet[nodeIdx] ? setValue[nodeIdx] : "X");
  }

  int[] initValues;

  public void init(int n, int[] initValues) {
    this.initValues = initValues;
    super.init(n);
  }

  int updateValue;

  public void update(int lower, int upper, int updateValue) {
    this.updateValue = updateValue;
    updateRange(lower, upper);
  }

  public int calc(int lower, int upper) {
    res = Integer.MAX_VALUE;
    calcRange(lower, upper);
    return res;
  }
}
