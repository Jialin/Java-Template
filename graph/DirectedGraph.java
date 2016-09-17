package template.graph;

import java.util.Arrays;

/**
 * A directed graph.
 */
public class DirectedGraph {

  public int vertexCnt, edgeCnt;
  public int[] fromIdx, toIdx;
  public int[] nextIn, nextOut;
  public int[] lastIn, lastOut;
  public int[] inDegree, outDegree;

  public DirectedGraph(int vertexCapacity, int edgeCapacity) {
    this.fromIdx = new int[edgeCapacity];
    this.toIdx = new int[edgeCapacity];
    this.lastIn = new int[vertexCapacity];
    this.lastOut = new int[vertexCapacity];
    this.nextIn = new int[edgeCapacity];
    this.nextOut = new int[edgeCapacity];
    this.inDegree = new int[vertexCapacity];
    this.outDegree = new int[vertexCapacity];
  }

  public void init(int vertexCnt) {
    this.vertexCnt = vertexCnt;
    this.edgeCnt = 0;
    Arrays.fill(inDegree, 0, vertexCnt, 0);
    Arrays.fill(outDegree, 0, vertexCnt, 0);
    Arrays.fill(lastIn, 0, vertexCnt, -1);
    Arrays.fill(lastOut, 0, vertexCnt, -1);
  }

  public void add(int fromIdx, int toIdx) {
    this.fromIdx[edgeCnt] = fromIdx;
    this.toIdx[edgeCnt] = toIdx;
    ++outDegree[fromIdx];
    ++inDegree[toIdx];
    nextOut[edgeCnt] = lastOut[fromIdx];
    lastOut[fromIdx] = edgeCnt;
    nextIn[edgeCnt] = lastIn[toIdx];
    lastIn[toIdx] = edgeCnt;
    ++edgeCnt;
  }
}
