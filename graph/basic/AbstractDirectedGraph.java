package template.graph.basic;

import java.util.Arrays;

public abstract class AbstractDirectedGraph implements DirectedGraphInterface {

  public int vertexCnt, currentEdgeCnt;
  public int[] fromIdx, toIdx;
  public int[] nextIn, nextOut;
  public int[] lastIn, lastOut;
  public int[] inDegree, outDegree;

  public abstract void createSubclass(int vertexCapacity, int edgeCapacity);
  public abstract void initSubclass(int vertexCnt);

  public AbstractDirectedGraph(int vertexCapacity, int edgeCapacity) {
    this(vertexCapacity, edgeCapacity, true);
  }

  public AbstractDirectedGraph(int vertexCapacity, int edgeCapacity, boolean initialize) {
    fromIdx = new int[edgeCapacity];
    toIdx = new int[edgeCapacity];
    lastIn = new int[vertexCapacity];
    lastOut = new int[vertexCapacity];
    nextIn = new int[edgeCapacity];
    nextOut = new int[edgeCapacity];
    inDegree = new int[vertexCapacity];
    outDegree = new int[vertexCapacity];
    createSubclass(vertexCapacity, edgeCapacity);
    if (initialize) init(vertexCapacity);
  }

  @Override
  public void init(int vertexCnt) {
    this.vertexCnt = vertexCnt;
    currentEdgeCnt = 0;
    Arrays.fill(inDegree, 0, vertexCnt, 0);
    Arrays.fill(outDegree, 0, vertexCnt, 0);
    Arrays.fill(lastIn, 0, vertexCnt, -1);
    Arrays.fill(lastOut, 0, vertexCnt, -1);
    initSubclass(vertexCnt);
  }

  @Override
  public void add(int fromIdx, int toIdx) {
    this.fromIdx[currentEdgeCnt] = fromIdx;
    this.toIdx[currentEdgeCnt] = toIdx;
    ++outDegree[fromIdx];
    ++inDegree[toIdx];
    nextOut[currentEdgeCnt] = lastOut[fromIdx];
    lastOut[fromIdx] = currentEdgeCnt;
    nextIn[currentEdgeCnt] = lastIn[toIdx];
    lastIn[toIdx] = currentEdgeCnt;
    ++currentEdgeCnt;
  }

  @Override
  public int vertexCnt() {
    return vertexCnt;
  }

  @Override
  public int currentEdgeCnt() {
    return currentEdgeCnt;
  }

  @Override
  public int fromIdx(int edgeIdx) {
    return fromIdx[edgeIdx];
  }

  @Override
  public int toIdx(int edgeIdx) {
    return toIdx[edgeIdx];
  }

  @Override
  public int lastIn(int nodeIdx) {
    return lastIn[nodeIdx];
  }

  @Override
  public int lastOut(int nodeIdx) {
    return lastOut[nodeIdx];
  }

  @Override
  public int nextIn(int edgeIdx) {
    return nextIn[edgeIdx];
  }

  @Override
  public int nextOut(int edgeIdx) {
    return nextOut[edgeIdx];
  }

  @Override
  public int inDegree(int nodeIdx){
    return inDegree[nodeIdx];
  }

  @Override
  public int outDegree(int nodeIdx) {
    return outDegree[nodeIdx];
  }

}
