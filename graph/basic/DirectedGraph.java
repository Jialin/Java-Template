package template.graph.basic;

public class DirectedGraph extends AbstractDirectedGraph {

  public DirectedGraph(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity);
  }

  public DirectedGraph(int vertexCapacity, int edgeCapacity, boolean initialize) {
    super(vertexCapacity, edgeCapacity, initialize);
  }

  @Override
  public void createSubclass(int vertexCapacity, int edgeCapacity) {}

  @Override
  public void initSubclass(int vertexCnt) {}
}
