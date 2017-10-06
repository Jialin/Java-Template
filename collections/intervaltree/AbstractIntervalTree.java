package template.collections.intervaltree;

import template.collections.list.IntArrayList;
import template.io.Displayable;

/** Simple and efficient interval tree. (http://codeforces.com/blog/entry/18051) */
public abstract class AbstractIntervalTree implements Displayable {

  /** Creates the underlying storage. */
  public abstract void createStorage(int nodeCapacity);

  /** Initializes leaf node {@code idxInTree} with range {@code [idx, idx+1)}. */
  public abstract void initLeaf(int idxInTree, int idx);

  /** Merges {@code leftIdxInTree} and {@code rightIdxInTree} to {@code idxInTree}. */
  public abstract void merge(int leftIdxInTree, int rightIdxInTree, int idxInTree);

  /** Copies from {@code fromIdxInTree} to {@code toIdxInTree}. */
  public abstract void copyForCalc(int fromIdxInTree, int toIdxInTree);

  /**
   * Clears node with initial result.
   *
   * NOTE: ONLY used when the calculation range is EMPTY.
   */
  public abstract void clearNode(int idxInTree);

  /** Text to display for the node value. */
  public abstract String toDisplay(int idxInTree);

  private int nodeCapacity;
  private int leafCnt;
  private int[] rangeLower, rangeUpper;
  private IntArrayList calcIdxInTree, calcIdxInTreeAppendix;

  public AbstractIntervalTree(int leafCapacity) {
    calcIdxInTree = new IntArrayList();
    calcIdxInTreeAppendix = new IntArrayList();
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
      initLeaf(i + leafCnt, i);
    }
    for (int i = n - 1; i > 0; --i) {
      int left = i << 1, right = left | 1;
      if (rangeUpper[left] == rangeLower[right]) {
        rangeLower[i] = rangeLower[left];
        rangeUpper[i] = rangeUpper[right];
        merge(left, right, i);
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
      for (int idxInTree = lower; idxInTree <= upper; ++idxInTree) if (isValidNode(idxInTree)) {
        merge(idxInTree);
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
    calcIdxInTree.clear();
    calcIdxInTreeAppendix.clear();
    for (lower += leafCnt, upper += leafCnt; lower < upper; lower >>= 1, upper >>= 1) {
      if ((lower & 1) > 0) {
        if (isValidNode(lower)) calcIdxInTree.add(lower);
        ++lower;
      }
      if ((upper & 1) > 0) {
        if (isValidNode(--upper)) calcIdxInTreeAppendix.add(upper);
      }
    }
    calcIdxInTreeAppendix.reverse();
    calcIdxInTree.addAll(calcIdxInTreeAppendix);
    int idxInTree = calcIdxInTree.get(0);
    int resIdxInTree = nodeCapacity;
    copyForCalc(idxInTree, resIdxInTree);
    rangeLower[resIdxInTree] = rangeLower[idxInTree];
    rangeUpper[resIdxInTree] = rangeUpper[idxInTree];
    for (int i = 1; i < calcIdxInTree.size; ++i, resIdxInTree ^= 1) {
      idxInTree = calcIdxInTree.get(i);
      merge(resIdxInTree, idxInTree, resIdxInTree ^ 1);
      rangeLower[resIdxInTree ^ 1] = rangeLower[resIdxInTree];
      rangeUpper[resIdxInTree ^ 1] = rangeUpper[idxInTree];
    }
    copyForCalc(resIdxInTree, 0);
  }

  public int leafCnt() {
    return leafCnt;
  }

  public int idxInTree(int idx) {
    return idx + leafCnt;
  }

  public int rangeLower(int idxInTree) {
    return rangeLower[idxInTree];
  }

  public int rangeUpper(int idxInTree) {
    return rangeUpper[idxInTree];
  }

  public int rangeLength(int idxInTree) {
    return rangeUpper[idxInTree] - rangeLower[idxInTree];
  }

  public boolean isValidNode(int idxInTree) {
    return rangeLower[idxInTree] < rangeUpper[idxInTree];
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

  private void merge(int idxInTree) {
    merge(idxInTree << 1, (idxInTree << 1) | 1, idxInTree);
  }
}
