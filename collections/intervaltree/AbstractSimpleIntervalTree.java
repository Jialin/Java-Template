package template.collections.intervaltree;

import template.collections.list.IntArrayList;

/**
 * Simple and efficient interval tree. (http://codeforces.com/blog/entry/18051)
 */
public abstract class AbstractSimpleIntervalTree {

  /** Creates the underlying storage. */
  public abstract void createSubclass(int nodeCapacity);

  /** Initializes the lazy propagation. */
  public abstract void initLazyPropagation(int nodeCapacity);

  /** Initializes leaf node {@code nodeIdx} with range {@code [idx, idx+1)}. */
  public abstract void initLeaf(int nodeIdx, int idx);

  /**
   * Calculates the non-leaf node {@code nodeIdx} from its children.
   *
   * NOTE: remember to consider the lazy propagation in node {@code nodeIdx}.
   */
  public abstract void calcNonLeafNode(int nodeIdx);

  /** Appends node {@code nodeIdx} to the calculation result. */
  public abstract void calcAppend(int nodeIdx);

  /**
   * Assigns lazy propagation to fake node (i.e. 0-th).
   *
   * NOTE: assign the value to update to the 0-th node.
   */
  public abstract void assignFakeLazyPropagation();

  /** Pushes lazy propagation from node {@code fromNodeIdx} to node {@code toNodeIdx}. */
  public abstract void pushLazyPropagation(int fromNodeIdx, int toNodeIdx);

  /** Clears lazy propagation in the node. */
  public abstract void clearLazyPropagation(int nodeIdx);

  /** Text to display for the node value. */
  public abstract String valueToString(int nodeIdx);

  /** Text to display for the node lazy propagation. */
  public abstract String lazyPropagationToString(int nodeIdx);

  public int[] lower, upper;

  private int n, height;
  private IntArrayList calcLeftIdx, calcRightIdx;

  public AbstractSimpleIntervalTree(int leafCapacity, boolean initialize) {
    calcLeftIdx = new IntArrayList();
    calcRightIdx = new IntArrayList();
    int leafCapacity2 = leafCapacity << 1;
    lower = new int[leafCapacity2];
    upper = new int[leafCapacity2];
    createSubclass(leafCapacity2);
    if (initialize) init(leafCapacity);
  }

  public void init(int n) {
    this.n = n;
    this.height = 32 - Integer.numberOfLeadingZeros(n);
    initLazyPropagation(n << 1);
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
        calcNonLeafNode(i);
      } else {
        lower[i] = n;
        upper[i] = -1;
      }
    }
  }

  public void updateRange(int lower, int upper) {
    assignFakeLazyPropagation();
    pushInternal(lower);
    pushInternal(upper - 1);
    boolean toCalcLeft = false, toCalcRight = false;
    for (lower += n, upper += n; lower < upper; lower >>= 1, upper >>= 1) {
      if (toCalcLeft) calcNonLeafNode(lower - 1);
      if (toCalcRight) calcNonLeafNode(upper);
      if ((lower & 1) > 0) {
        pushLazyPropagation(0, lower++);
        toCalcLeft = true;
      }
      if ((upper & 1) > 0) {
        pushLazyPropagation(0, --upper);
        toCalcRight = true;
      }
    }
    for (--lower; upper > 0; lower >>= 1, upper >>= 1) {
      if (toCalcLeft) calcNonLeafNode(lower);
      if (toCalcRight && (!toCalcLeft || lower != upper)) calcNonLeafNode(upper);
    }
  }

  public void calcRange(int lower, int upper) {
    pushInternal(lower);
    pushInternal(upper - 1);
    calcLeftIdx.clear();
    calcRightIdx.clear();
    for (lower += n, upper += n; lower < upper; lower >>= 1, upper >>= 1) {
      if ((lower & 1) > 0) calcLeftIdx.add(lower++);
      if ((upper & 1) > 0) calcRightIdx.add(--upper);
    }
    for (int i = 0; i < calcLeftIdx.size; ++i) {
      int idx = calcLeftIdx.get(i);
      calcAppend(idx);
    }
    for (int i = calcRightIdx.size - 1; i >= 0; --i) {
      int idx = calcRightIdx.get(i);
      calcAppend(idx);
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n << 1; ++i) if (lower[i] < upper[i]) {
      sb.append(String.format("@%d[%d,%d):%s", i, lower[i], upper[i], valueToString(i)));
      if (lower[i] + 1 < upper[i]) sb.append(String.format(" | %s", lazyPropagationToString(i)));
      if (i + 1 < n << 1) sb.append('\n');
    }
    return sb.toString();
  }

  private void pushInternal(int nodeIdx) {
    for (int s = height, l = nodeIdx + n, r = l; s > 0; --s) {
      for (int i = l >> s; i <= r >> s; ++i) if (lower[i] < upper[i]) {
//if (i == 0) System.out.printf("pushInternal 0: nodeIdx(%d)\n", nodeIdx);
        pushLazyPropagation(i, i << 1);
        pushLazyPropagation(i, (i << 1) | 1);
        clearLazyPropagation(i);
      }
    }
  }
}
