package template.collections.intervaltree;

import template.collections.list.IntArrayList;

/**
 * Simple and efficient interval tree. (http://codeforces.com/blog/entry/18051?)
 */
public abstract class AbstractSimpleIntervalTree {

  public abstract void createSubclass(int nodeCapacity);

  public abstract void initLeaf(int nodeIdx, int idx);

  public abstract void merge(int nodeIdx, int leftNodeIdx, int rightNodeIdx);

  public abstract void calcAppend(int nodeIdx, int lower, int upper);

  private int n;
  private int[] lower, upper;
  private IntArrayList calcLeftIdx, calcRightIdx;

  public AbstractSimpleIntervalTree(int leafCapacity) {
    calcLeftIdx = new IntArrayList();
    calcRightIdx = new IntArrayList();
    int leafCapacity2 = leafCapacity << 1;
    lower = new int[leafCapacity2];
    upper = new int[leafCapacity2];
    createSubclass(leafCapacity2);
    init(leafCapacity);
  }

  public void init(int n) {
    this.n = n;
    for (int i = 0; i < n; ++i) {
      lower[n + i] = i;
      upper[n + i] = i + 1;
      initLeaf(n + i, i);
    }
    for (int i = n - 1; i > 0; --i) {
      int left = i << 1, right = left | 1;
      if (upper[left] == lower[right]) {
        lower[i] = lower[left];
        upper[i] = upper[right];
        merge(i, left, right);
      } else {
        lower[i] = -1;
      }
    }
  }

  public void calcRange(int lower, int upper) {
    calcLeftIdx.clear();
    calcRightIdx.clear();
    for (lower += n, upper += n; lower < upper; lower >>= 1, upper >>= 1) {
      if ((lower & 1) > 0) calcLeftIdx.add(lower++);
      if ((upper & 1) > 0) calcRightIdx.add(--upper);
    }
    for (int i = 0; i < calcLeftIdx.size; ++i) {
      int idx = calcLeftIdx.get(i);
      calcAppend(idx, this.lower[idx], this.upper[idx]);
    }
    for (int i = calcRightIdx.size - 1; i >= 0; --i) {
      int idx = calcRightIdx.get(i);
      calcAppend(idx, this.lower[idx], this.upper[idx]);
    }
  }
}
