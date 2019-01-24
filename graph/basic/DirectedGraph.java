package template.graph.basic;

public class DirectedGraph extends AbstractDirectedGraph {

  public DirectedGraph(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity);
  }

  public DirectedGraph(int vertexCapacity, int edgeCapacity, boolean initialize) {
    super(vertexCapacity, edgeCapacity, initialize);
  }

  @Override
  public void createVertexStorage(int vertexCapacity) {}

  @Override
  public void expandEdgeStorage(int edgeCapacity) {}

  @Override
  public void initVertexStorage(int vertexCnt) {}
}
