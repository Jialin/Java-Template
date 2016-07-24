package template.collections.intervaltree;

/**
 * Node of interval tree.
 *
 * @param <INIT>   Type of initial value
 * @param <UPDATE> Type of update value
 */
public interface IntervalTreeNodeInterface<INIT, UPDATE, SELF extends IntervalTreeNodeInterface<INIT, UPDATE, SELF>> {

  /**
   * Resets the node to the default value.
   */
  void init();

  /**
   * Resets the node with {@code initialValue}.
   */
  void init(INIT initialValue);

  /**
   * Applies {@code updateValue} to the node.
   */
  void update(UPDATE updateValue);

  /**
   * Merges with a node.
   */
  void append(SELF o);
}
