package template.collections.intervaltree;

import template.collections.list.IntArrayList;
import template.io.Displayable;

/** Simple and efficient interval tree. (http://codeforces.com/blog/entry/18051) */
public abstract class AbstractIntervalTree implements Displayable {

  /** Creates the underlying storage. */
  public abstract void createStorage(int nodeCapacity);

  /** Initializes leaf node {@code nodeIdx} with range {@code [idx, idx+1)}. */
  public abstract void initLeaf(int nodeIdx, int idx);

  /** Merges {@code leftIdx} and {@code rightIdx} to {@code idx}. */
  public abstract void merge(int idx, int leftIdx, int rightIdx);

  /** Copies from {@code sourceIdx} to {@code destIdx}. */
  public abstract void copy(int destIdx, int sourceIdx);

  /**
   * Clears node with initial result.
   *
   * NOTE: ONLY used when the calculation range is EMPTY.
   */
  public abstract void clearNode(int idx);

  /** Text to display for the node value. */
  public abstract String toDisplay(int idx);

  private int nodeCapacity;
  private int leafCnt;
  private int[] rangeLower, rangeUpper;
  private IntArrayList calcIdx, calcIdxAppendix;

  public AbstractIntervalTree(int leafCapacity) {
    calcIdx = new IntArrayList();
    calcIdxAppendix = new IntArrayList();
    nodeCapacity = leafCapacity << 1;
    rangeLower = new int[nodeCapacity + 2];
    rangeUpper = new int[nodeCapacity + 2];
    createStorage(nodeCapacity + 2);
  }

  public void init(int n) {
    this.leafCnt = n;
    for (int i = 0; i < n; ++i) {
      rangeLower[n + i] = i;
      rangeUpper[n + i] = i + 1;
      initLeaf(n + i, i);
    }
    for (int i = n - 1; i > 0; --i) {
      int left = i << 1, right = left | 1;
      if (rangeUpper[left] == rangeLower[right]) {
        rangeLower[i] = rangeLower[left];
        rangeUpper[i] = rangeUpper[right];
        merge(i, left, right);
      } else {
        rangeLower[i] = n;
        rangeUpper[i] = -1;
      }
    }
  }

  public void populateRangeUpdateToRoot(int lower, int upper) {
    lower = (lower + leafCnt) >> 1;
    upper = (upper - 1 + leafCnt) >> 1;
    for ( ; lower > 0 && lower <= upper; lower >>= 1, upper >>= 1) {
      for (int nodeIdx = lower; nodeIdx <= upper; ++nodeIdx) if (isValidNode(nodeIdx)) {
        merge(nodeIdx);
      }
    }
    for ( ; upper > 0 && isValidNode(upper); upper >>= 1) {
      merge(upper);
    }
  }

  /** Calculates range [{@code lower}, {@code upper}) and stores the result at {@code 0}. */
  public void calcRange(int lower, int upper) {
    if (lower >= upper) {
      clearNode(0);
      return;
    }
    calcIdx.clear();
    calcIdxAppendix.clear();
    for (lower += leafCnt, upper += leafCnt; lower < upper; lower >>= 1, upper >>= 1) {
      if ((lower & 1) > 0) {
        if (isValidNode(lower)) calcIdx.add(lower);
        ++lower;
      }
      if ((upper & 1) > 0) {
        if (isValidNode(--upper)) calcIdxAppendix.add(upper);
      }
    }
    calcIdxAppendix.reverse();
    calcIdx.addAll(calcIdxAppendix);
    int idx = calcIdx.get(0);
    int resIdx = nodeCapacity;
    copy(resIdx, idx);
    rangeLower[resIdx] = rangeLower[idx];
    rangeUpper[resIdx] = rangeUpper[idx];
    for (int i = 1; i < calcIdx.size; ++i, resIdx ^= 1) {
      idx = calcIdx.get(i);
      merge(resIdx ^ 1, resIdx, idx);
      rangeLower[resIdx ^ 1] = rangeLower[resIdx];
      rangeUpper[resIdx ^ 1] = rangeUpper[idx];
    }
    copy(0, resIdx);
  }

  public int leafCnt() {
    return leafCnt;
  }

  public int rangeLower(int idx) {
    return rangeLower[idx];
  }

  public int rangeUpper(int idx) {
    return rangeUpper[idx];
  }

  public int rangeLength(int idx) {
    return rangeUpper[idx] - rangeLower[idx];
  }

  public boolean isValidNode(int idx) {
    return rangeLower[idx] < rangeUpper[idx];
  }

  @Override
  public String toDisplay() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < leafCnt << 1; ++i) if (rangeLower[i] < rangeUpper[i]) {
      sb.append(String.format("@%d[%d,%d):%s", i, rangeLower[i], rangeUpper[i], toDisplay(i)));
      if (i + 1 < leafCnt << 1) sb.append('\n');
    }
    return sb.toString();
  }

  private void merge(int idx) {
    merge(idx, idx << 1, (idx << 1) | 1);
  }
}
