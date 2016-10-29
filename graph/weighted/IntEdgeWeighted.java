package template.graph.weighted;

public interface IntEdgeWeighted {

  void add(int fromIdx, int toIdx, int weight);

  int edgeWeight(int edgeIdx);
}
