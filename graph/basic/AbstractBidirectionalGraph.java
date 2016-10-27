package template.graph.basic;

public abstract class AbstractBidirectionalGraph extends AbstractDirectedGraph implements BidirectionalGraphInterface {

  public AbstractBidirectionalGraph(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity << 1);
  }

  public AbstractBidirectionalGraph(int vertexCapacity, int edgeCapacity, boolean initialize) {
    super(vertexCapacity, edgeCapacity << 1, initialize);
  }

  @Override
  public void add(int u, int v) {
    super.add(u, v);
    super.add(v, u);
  }
}
