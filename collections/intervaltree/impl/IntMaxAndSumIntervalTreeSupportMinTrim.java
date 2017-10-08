package template.collections.intervaltree.impl;

import template.collections.intervaltree.AbstractIntervalTreeWithLazyPropagation;

/**
 * Interval tree supports:
 * <ul>
 *   <li>Trim value in a range (i.e. update value to <code>min(x_i, value))</code></li>
 *   <li>Calculating the max value in a range</li>
 *   <li>Calculating sum in a range</li>
 * </ul>
 */
public class IntMaxAndSumIntervalTreeSupportMinTrim extends AbstractIntervalTreeWithLazyPropagation {

  private int[] maxValue;
  private int[] maxValueCnt;
  private int[] secondMaxValue;
  private long[] sum;

  public IntMaxAndSumIntervalTreeSupportMinTrim(int leafCapacity) {
    super(leafCapacity);
  }

  public IntMaxAndSumIntervalTreeSupportMinTrim(int leafCapacity, boolean initialize) {
    super(leafCapacity, initialize);
  }

  @Override
  public void createStorage(int nodeCapacity) {
    maxValue = new int[nodeCapacity];
    maxValueCnt = new int[nodeCapacity];
    secondMaxValue = new int[nodeCapacity];
    sum = new long[nodeCapacity];
  }

  @Override
  public void initLeaf(int idxInTree, int idx) {
    maxValue[idxInTree] = initValues[idx];
    maxValueCnt[idxInTree] = 1;
    secondMaxValue[idxInTree] = Integer.MIN_VALUE;
    sum[idxInTree] = initValues[idx];
  }

  private int updateValue;

  @Override
  public void assignFakeLazyPropagation() {
    maxValue[0] = updateValue;
  }

  @Override
  public void pushLazyPropagation(int fromIdxInTree, int toIdxInTree) {
    if (maxValue[toIdxInTree] <= maxValue[fromIdxInTree]) return;
    if (secondMaxValue[toIdxInTree] < maxValue[fromIdxInTree]) {
      sum[toIdxInTree] -= (long) (maxValue[toIdxInTree] - maxValue[fromIdxInTree]) * maxValueCnt[toIdxInTree];
      maxValue[toIdxInTree] = maxValue[fromIdxInTree];
      return;
    }
    int leftIdxInTree = toIdxInTree << 1, rightIdxInTree = leftIdxInTree | 1;
    pushLazyPropagation(fromIdxInTree, leftIdxInTree);
    pushLazyPropagation(fromIdxInTree, rightIdxInTree);
    merge(leftIdxInTree, rightIdxInTree, toIdxInTree);
  }

  @Override
  public void clearLazyPropagation(int idxInTree) {}

  @Override
  public void merge(int leftIdxInTree, int rightIdxInTree, int idxInTree) {
    maxValue[idxInTree] = Math.max(maxValue[leftIdxInTree], maxValue[rightIdxInTree]);
    maxValueCnt[idxInTree] = (maxValue[idxInTree] == maxValue[leftIdxInTree] ? maxValueCnt[leftIdxInTree] : 0)
        + (maxValue[idxInTree] == maxValue[rightIdxInTree] ? maxValueCnt[rightIdxInTree] : 0);
    if (maxValue[leftIdxInTree] == maxValue[rightIdxInTree]) {
      secondMaxValue[idxInTree] = Math.max(secondMaxValue[leftIdxInTree], secondMaxValue[rightIdxInTree]);
    } else if (maxValue[leftIdxInTree] > maxValue[rightIdxInTree]) {
      secondMaxValue[idxInTree] = Math.max(secondMaxValue[leftIdxInTree], maxValue[rightIdxInTree]);
    } else {
      secondMaxValue[idxInTree] = Math.max(maxValue[leftIdxInTree], secondMaxValue[rightIdxInTree]);
    }
    sum[idxInTree] = sum[leftIdxInTree] + sum[rightIdxInTree];
  }

  @Override
  public void copyForCalc(int fromIdxInTree, int toIdxInTree) {
    maxValue[toIdxInTree] = maxValue[fromIdxInTree];
    maxValueCnt[toIdxInTree] = maxValueCnt[fromIdxInTree];
    secondMaxValue[toIdxInTree] = secondMaxValue[fromIdxInTree];
    sum[toIdxInTree] = sum[fromIdxInTree];
  }

  @Override
  public void clearNodeForCalc(int idxInTree) {
    maxValue[idxInTree] = secondMaxValue[idxInTree] = Integer.MIN_VALUE;
    maxValueCnt[idxInTree] = 0;
    sum[idxInTree] = 0;
  }

  @Override
  public String toDisplay(int idxInTree) {
    return String.format(
        "maxValue:%d maxValueCnt:%d secondMaxValue:%d sum:%d",
        maxValue[idxInTree], maxValueCnt[idxInTree], secondMaxValue[idxInTree], sum[idxInTree]);
  }

  private int[] initValues;

  public void init(int leafCnt, int[] initValues) {
    this.initValues = initValues;
    super.init(leafCnt);
  }

  public void update(int lower, int upper, int updateValue) {
    this.updateValue = updateValue;
    updateRange(lower, upper);
  }

  public int calcMaxValue(int lower, int upper) {
    calcRange(lower, upper);
    return maxValue[0];
  }

  public long calcSum(int lower, int upper) {
    calcRange(lower, upper);
    return sum[0];
  }
}
