package template.graph.tree;

import template.graph.basic.AbstractBidirectionalGraph;

public abstract class AbstractTree extends AbstractBidirectionalGraph implements TreeInterface {

  public AbstractTree(int vertexCapacity) {
    super(vertexCapacity, vertexCapacity - 1);
  }
}
