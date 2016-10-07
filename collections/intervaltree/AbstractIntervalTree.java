package template.collections.intervaltree;

/**
 * Abstract interval tree.
 */
public abstract class AbstractIntervalTree {

  private int n;
  private int left, right;

  /**
   * Initializes the underlying storage.
   */
  public abstract void initStorage(int n4);

  /**
   * Initializes leaf node {@code nodeIdx} with range {@code [idx, idx+1)}.
   */
  public abstract void initLeaf(int nodeIdx, int idx);

  /**
   * Merges node {@code leftNodeIdx} and node {@code rightNodeIdx} into node {@code nodeIdx}.
   */
  public abstract void merge(int nodeIdx, int leftNodeIdx, int rightNodeIdx);

  /**
   * Updates node {@code nodeIdx} with range {@code [lower, upper)}.
   */
  public abstract void updateNode(int nodeIdx, int lower, int upper);

  /**
   * Appends node {@code nodeIdx} with range {@code [lower, upper)} to the calculation result.
   */
  public abstract void calcAppend(int nodeIdx, int lower, int upper);

  /**
   * Pushes lazy propagation from node {@code fromNodeIdx} to node {@code toNodeIdx}.
   */
  public abstract void pushLazyPropagation(int fromNodeIdx, int toNodeIdx);

  /**
   * Clears lazy propagation in node {@code nodeIdx}.
   */
  public abstract void clearLazyPropagation(int nodeIdx);

  /**
   * Initializes the tree with {@code n} leaf nodes.
   */
  public void init(int n) {
    this.n = n;
    initStorage(n << 2);
    init(0, 0, n);
  }

  /**
   * Updates range {@code [lower, upper)}.
   */
  public void updateRange(int lower, int upper) {
    left = lower;
    right = upper;
    updateRange(0, 0, n);
  }

  /**
   * Calculates range {@code [lower, upper)}.
   */
  public void calcRange(int lower, int upper) {
    left = lower;
    right = upper;
    calc(0, 0, n);
  }

  private void init(int nodeIdx, int lower, int upper) {
    if (lower + 1 == upper) {
      initLeaf(nodeIdx, lower);
      return ;
    }
    int medium = (lower + upper) >> 1;
    init(toLeft(nodeIdx), lower, medium);
    init(toRight(nodeIdx), medium, upper);
    merge(nodeIdx, toLeft(nodeIdx), toRight(nodeIdx));
  }

  private void updateRange(int nodeIdx, int lower, int upper) {
    if (left <= lower && upper <= right) {
      updateNode(nodeIdx, lower, upper);
      return ;
    }
    pushLazyPropagation(nodeIdx);
    int medium = (lower + upper) >> 1;
    if (left < medium) {
      updateRange(toLeft(nodeIdx), lower, medium);
    }
    if (medium < right) {
      updateRange(toRight(nodeIdx), medium, upper);
    }
    merge(nodeIdx, toLeft(nodeIdx), toRight(nodeIdx));
  }

  private void calc(int nodeIdx, int lower, int upper) {
    if (left <= lower && upper <= right) {
      calcAppend(nodeIdx, lower, upper);
      return ;
    }
    pushLazyPropagation(nodeIdx);
    int medium = (lower + upper) >> 1;
    if (left < medium) {
      calc(toLeft(nodeIdx), lower, medium);
    }
    if (medium < right) {
      calc(toRight(nodeIdx), medium, upper);
    }
    merge(nodeIdx, toLeft(nodeIdx), toRight(nodeIdx));
  }

  private void pushLazyPropagation(int nodeIdx) {
    pushLazyPropagation(nodeIdx, toLeft(nodeIdx));
    pushLazyPropagation(nodeIdx, toRight(nodeIdx));
    clearLazyPropagation(nodeIdx);
  }

  private int toLeft(int nodeIdx) {
    return (nodeIdx << 1) | 1;
  }

  private int toRight(int nodeIdx) {
    return (nodeIdx + 1) << 1;
  }
}
