package template.collections.intervaltree;

import template.collections.list.IntArrayList;
import template.io.Displayable;

/** Simple and efficient interval tree with lazy propagation. (http://codeforces.com/blog/entry/18051) */
public abstract class AbstractIntervalTreeWithLazyPropagation implements Displayable {

  /** Creates the underlying storage. */
  public abstract void createStorage(int nodeCapacity);

  /** Initializes leaf node {@code idxInTree} with range {@code [idx, idx+1)}. */
  public abstract void initLeaf(int idxInTree, int idx);

  /** Assigns lazy propagation to fake node (i.e. 0-th) for update. */
  public abstract void assignFakeLazyPropagation();

  /** Pushes lazy propagation from {@code fromIdxInTree} to {@code toIdxInTree}. */
  public abstract void pushLazyPropagation(int fromIdxInTree, int toIdxInTree);

  /** Clears lazy propagation in {@code idxInTree}. */
  public abstract void clearLazyPropagation(int idxInTree);

  /** Merges {@code leftIdxInTree} and {@code rightIdxInTree} to {@code idxInTree}. */
  public abstract void merge(int leftIdxInTree, int rightIdxInTree, int idxInTree);

  /** Copies from {@code fromIdxInTree} to {@code toIdxInTree}. */
  public abstract void copyForCalc(int fromIdxInTree, int toIdxInTree);

  /**
   * Clears node with initial result.
   *
   * NOTE: ONLY used when the calculation range is EMPTY.
   */
  public abstract void clearNodeForCalc(int idxInTree);

  /** Text to display for the node value. */
  public abstract String toDisplay(int idxInTree);

  private int nodeCapacity;
  private int leafCnt, height;
  private int[] rangeLower, rangeUpper;
  private IntArrayList idxInTreeToUpdate, idxInTreeToUpdateAppendix;

  public AbstractIntervalTreeWithLazyPropagation(int leafCapacity) {
    this(leafCapacity, true);
  }

  public AbstractIntervalTreeWithLazyPropagation(int leafCapacity, boolean initialize) {
    int height = 32 - Integer.numberOfLeadingZeros(leafCapacity);
    idxInTreeToUpdate = new IntArrayList(height << 1);
    idxInTreeToUpdateAppendix = new IntArrayList(height);
    nodeCapacity = leafCapacity << 1;
    rangeLower = new int[nodeCapacity + 2];
    rangeUpper = new int[nodeCapacity + 2];
    createStorage(nodeCapacity + 2);
    if (initialize) init(leafCapacity);
  }

  public void init(int leafCnt) {
    this.leafCnt = leafCnt;
    this.height = 32 - Integer.numberOfLeadingZeros(leafCnt);
    for (int i = 0; i < leafCnt; ++i) {
      rangeLower[i + leafCnt] = i;
      rangeUpper[i + leafCnt] = i + 1;
      initLeaf(i + leafCnt, i);
      clearLazyPropagation(i);
      clearLazyPropagation(i + leafCnt);
    }
    for (int i = leafCnt - 1; i >= 0; --i) {
      int left = i << 1, right = left | 1;
      if (rangeUpper[left] == rangeLower[right]) {
        rangeLower[i] = rangeLower[left];
        rangeUpper[i] = rangeUpper[right];
        merge(left, right, i);
      } else {
        rangeLower[i] = leafCnt;
        rangeUpper[i] = -1;
      }
    }
  }

  public void updateRange(int lower, int upper) {
    assignFakeLazyPropagation();
    pushLazyPropagationToRange(lower, upper);
    boolean calcLeft = false, calcRight = false;
    for (lower += leafCnt, upper += leafCnt; lower < upper; lower >>= 1, upper >>= 1) {
      if (calcLeft) merge(lower - 1);
      if (calcRight) merge(upper);
      if ((lower & 1) > 0) {
        pushLazyPropagation(0, lower++);
        calcLeft = true;
      }
      if ((upper & 1) > 0) {
        pushLazyPropagation(0, --upper);
        calcRight = true;
      }
    }
    for (--lower; upper > 0; lower >>= 1, upper >>= 1) {
      if (calcLeft) merge(lower);
      if (calcRight && (!calcLeft || lower != upper)) merge(upper);
    }
  }

  /** Calculates range [{@code lower}, {@code upper}) and stores the result at {@code 0}. */
  public void calcRange(int lower, int upper) {
    if (lower >= upper) {
      clearNodeForCalc(0);
      return;
    }
    pushLazyPropagationToRange(lower, upper);
    idxInTreeToUpdate.clear();
    idxInTreeToUpdateAppendix.clear();
    for (lower += leafCnt, upper += leafCnt; lower < upper; lower >>= 1, upper >>= 1) {
      if ((lower & 1) > 0) {
        if (isValid(lower)) idxInTreeToUpdate.add(lower);
        ++lower;
      }
      if ((upper & 1) > 0) {
        if (isValid(--upper)) idxInTreeToUpdateAppendix.add(upper);
      }
    }
    idxInTreeToUpdateAppendix.reverse();
    idxInTreeToUpdate.addAll(idxInTreeToUpdateAppendix);
    int idxInTree = idxInTreeToUpdate.get(0);
    int resIdxInTree = nodeCapacity;
    copyForCalc(idxInTree, resIdxInTree);
    rangeLower[resIdxInTree] = rangeLower[idxInTree];
    rangeUpper[resIdxInTree] = rangeUpper[idxInTree];
    for (int i = 1; i < idxInTreeToUpdate.size; ++i, resIdxInTree ^= 1) {
      idxInTree = idxInTreeToUpdate.get(i);
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

  public boolean isValid(int idxInTree) {
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

  private void pushLazyPropagationToRange(int lower, int upper) {
    lower = (lower + leafCnt) >> 1;
    upper = (upper - 1 + leafCnt) >> 1;
    for (idxInTreeToUpdate.clear(); lower < upper; lower >>= 1, upper >>= 1) {
      if (isValid(lower)) idxInTreeToUpdate.add(lower);
      if (isValid(upper)) idxInTreeToUpdate.add(upper);
    }
    for ( ; lower > 0 && isValid(lower); lower >>= 1) {
      idxInTreeToUpdate.add(lower);
    }
    for (int i = idxInTreeToUpdate.size - 1; i >= 0; --i) {
      pushLazyPropagation(idxInTreeToUpdate.values[i]);
    }
  }

  private void pushLazyPropagation(int idxInTree) {
    pushLazyPropagation(idxInTree, idxInTree << 1);
    pushLazyPropagation(idxInTree, (idxInTree << 1) | 1);
    clearLazyPropagation(idxInTree);
  }
}
