package template.graph.weighted;

public interface BooleanEdgeWeighted {

  void add(int fromIdx, int toIdx, boolean weight);

  boolean edgeWeight(int edgeIdx);
}
