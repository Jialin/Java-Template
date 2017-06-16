package template.graph.basic;

public interface DirectedGraphInterface {

  void init(int vertexCnt);
  void add(int fromIdx, int toIdx);

  int vertexCnt();
  int currentEdgeCnt();
  int fromIdx(int edgeIdx);
  int toIdx(int edgeIdx);
  int lastIn(int nodeIdx);
  int lastOut(int nodeIdx);
  int nextIn(int edgeIdx);
  int nextOut(int edgeIdx);
  int inDegree(int nodeIdx);
  int outDegree(int nodeIdx);

  Iterable<Integer> outNodes(int nodeIdx);
}
