package template.graph.basic;

import java.util.function.IntConsumer;

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

  void forEachInNodes(int nodeIdx, IntConsumer consumer);
  void forEachOutNodes(int nodeIdx, IntConsumer consumer);

  Iterable<Integer> inNodes(int nodeIdx);
  Iterable<Integer> outNodes(int nodeIdx);
}
