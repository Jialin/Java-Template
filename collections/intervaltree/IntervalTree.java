package template.collections.intervaltree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Interval tree.
 *
 * @param <INIT>   Type of initial value
 * @param <UPDATE> Type of update value
 * @param <NODE>   Node of interval tree
 */
public class IntervalTree<INIT, UPDATE, NODE extends IntervalTreeNodeInterface<INIT, UPDATE, NODE>> {

  public IntervalTree(int n, Supplier<NODE> nodeFactory) {
    this.n = n;
    this.nodeFactory = nodeFactory;
    this.nodes = new ArrayList<>(n << 2);
    for (int i = n << 2; i > 0; --i) {
      NODE node = nodeFactory.get();
      node.init();
      nodes.add(node);
    }
  }

  public IntervalTree(INIT[] initValues, Supplier<NODE> nodeFactory) {
    this(initValues, 0, initValues.length, nodeFactory);
  }

  public IntervalTree(
      INIT[] initValues,
      int fromIndex,
      int toIndex,
      Supplier<NODE> nodeFactory) {

    this.n = toIndex - fromIndex;
    this.nodeFactory = nodeFactory;
    this.nodes = new ArrayList<>(n << 2);
    for (int i = n << 2; i > 0; --i) {
      nodes.add(nodeFactory.get());
    }
    this.initValues = initValues;
    this.fromIndex = fromIndex;
    init(1, 0, n);
  }

  /**
   * Updates position {@code x} with {@code updateValue}.
   */
  public void update(int x, UPDATE updateValue) {
    this.x = x;
    this.updateValue = updateValue;
    update(1, 0, n);
  }

  /**
   * Calculates the result from {@code left} to {@code right}.
   */
  public NODE calc(int left, int right) {
    NODE res = nodeFactory.get();
    res.init();
    this.left = left;
    this.right = right;
    calc(res, 1, 0, n);
    return res;
  }

  private int n;
  private List<NODE> nodes;
  private Supplier<NODE> nodeFactory;
  private INIT[] initValues;
  private int fromIndex;
  private int x;
  private UPDATE updateValue;
  private int left, right;

  private void init(int nodeIndex, int lower, int upper) {
    if (lower + 1 == upper) {
      nodes.get(nodeIndex).init(initValues[fromIndex + lower]);
      return;
    }
    int medium = (lower + upper) >> 1;
    init(toLeft(nodeIndex), lower, medium);
    init(toRight(nodeIndex), medium, upper);
    merge(nodeIndex);
  }

  private void update(int nodeIndex, int lower, int upper) {
    if (lower + 1 == upper) {
      nodes.get(nodeIndex).update(updateValue);
      return;
    }
    int medium = (lower + upper) >> 1;
    if (x < medium) {
      update(toLeft(nodeIndex), lower, medium);
    } else {
      update(toRight(nodeIndex), medium, upper);
    }
    merge(nodeIndex);
  }

  private void calc(NODE res, int nodeIndex, int lower, int upper) {
    if (left <= lower && upper <= right) {
      res.append(nodes.get(nodeIndex));
      return;
    }
    int medium = (lower + upper) >> 1;
    if (left < medium) {
      calc(res, toLeft(nodeIndex), lower, medium);
    }
    if (medium < right) {
      calc(res, toRight(nodeIndex), medium, upper);
    }
  }

  private void merge(int nodeIndex) {
    NODE node = nodes.get(nodeIndex);
    node.init();
    node.append(nodes.get(toLeft(nodeIndex)));
    node.append(nodes.get(toRight(nodeIndex)));
  }

  private int toLeft(int nodeIndex) {
    return nodeIndex << 1;
  }

  private int toRight(int nodeIndex) {
    return (nodeIndex << 1) | 1;
  }
}
