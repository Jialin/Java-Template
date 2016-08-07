package template.collections.treap;

public class IntTreap<UPDATE, NODE extends IntAbstractTreapNode<UPDATE, NODE>> {

  public NODE root;

  /**
   * Initializes the treap.
   */
  public void init() {
    root = null;
  }

  /**
   * Adds a single node to the treap.
   */
  public void add(NODE node) {
    root = root == null ? node : root.add(node);
  }

  /**
   * Merges the given treap into the current one.
   * NOTE: the given treap should be not less than the current one.
   */
  public void merge(NODE node) {
    root = root == null ? node : root.merge(node);
  }

  /**
   * Pushes all lazy propagation info to leaf nodes.
   */
  public void pushAllDown() {
    pushAllDown(root);
  }

  private void pushAllDown(NODE node) {
    if (node == null) return;
    node.pushDown();
    pushAllDown(node.left);
    pushAllDown(node.right);
  }
}
