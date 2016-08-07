package template.collections.treap;

import java.util.Random;

public abstract class IntAbstractTreapNode<UPDATE, SELF extends IntAbstractTreapNode<UPDATE, SELF>> {

  private static final Random random = new Random();
  private static Object[] splitResult = new Object[2];

  protected int key;
  protected int priority;
  public SELF left, right;

  /**
   * Updates the node with the given {@code update}.
   */
  public abstract void update(UPDATE update);

  /**
   * Pushes the lazy propagation values to its children.
   */
  public abstract void pushDown();

  /**
   * Merges the values of its children.
   */
  public abstract void merge();

  protected IntAbstractTreapNode(int key) {
    this.key = key;
    this.priority = random.nextInt();
  }

  /**
   * Gets the key of the node.
   */
  public int getKey() {
    return key;
  }

  /**
   * Splits the treap into 2 parts (one for {@code <key} and the other for {@code >=key}).
   */
  @SuppressWarnings({"unchecked"})
  public Object[] split(int key) {
    return split((SELF) this, key);
  }

  /**
   * Adds a single node into the treap, and returns the new root of the treap.
   */
  @SuppressWarnings({"unchecked"})
  public SELF add(SELF node) {
    return add((SELF) this, node);
  }

  /**
   * Merges two ordered treap, and returns the new root of the treap.
   */
  @SuppressWarnings({"unchecked"})
  public SELF merge(SELF o) {
    return merge((SELF) this, o);
  }

  /**
   * Gets left most node in the treap.
   */
  @SuppressWarnings({"unchecked"})
  public SELF leftMost() {
    SELF res = (SELF) this;
    while (res.left != null) {
      res.pushDown();
      res = res.left;
    }
    return res;
  }

  /**
   * Gets right most node in the treap.
   */
  @SuppressWarnings({"unchecked"})
  public SELF rightMost() {
    SELF res = (SELF) this;
    while (res.right != null) {
      res.pushDown();
      res = res.right;
    }
    return res;
  }

  @SuppressWarnings({"unchecked"})
  private Object[] split(SELF node, int key) {
    if (node == null) {
      splitResult[0] = splitResult[1] = null;
      return splitResult;
    }
    node.pushDown();
    Object[] res;
    if (key <= node.getKey()) {
      res = split(node.left, key);
      node.left = (SELF) res[1];
      res[1] = node;
    } else {
      res = split(node.right, key);
      node.right = (SELF) res[0];
      res[0] = node;
    }
    node.merge();
    return res;
  }

  @SuppressWarnings({"unchecked"})
  private SELF add(SELF root, SELF node) {
    if (root == null) return node;
    root.pushDown();
    Object[] res = split(root, node.getKey());
    SELF left = (SELF) res[0];
    SELF right = (SELF) res[1];
    return merge(merge(left, node), right);
  }

  private SELF merge(SELF left, SELF right) {
    if (left == null) return right;
    if (right == null) return left;
    if (left.priority >= right.priority) {
      left.pushDown();
      left.right = merge(left.right, right);
      left.merge();
      return left;
    } else {
      right.pushDown();
      right.left = merge(left, right.left);
      right.merge();
      return right;
    }
  }
}
