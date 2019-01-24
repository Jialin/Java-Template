package template.graph.basic;

import template.array.IntArrayUtils;
import template.numbertheory.number.IntUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.IntConsumer;

public abstract class AbstractDirectedGraph implements DirectedGraphInterface {

  protected int vertexCnt, currentEdgeCnt;
  protected int[] fromIdx, toIdx;
  protected int[] nextIn, nextOut;
  protected int[] lastIn, lastOut;
  protected int[] inDegree, outDegree;

  public abstract void createVertexStorage(int vertexCapacity);
  public abstract void expandEdgeStorage(int edgeCapacity);
  public abstract void initVertexStorage(int vertexCnt);

  public AbstractDirectedGraph(int vertexCapacity, int edgeCapacity) {
    this(vertexCapacity, edgeCapacity, true);
  }

  public AbstractDirectedGraph(int vertexCapacity, int edgeCapacity, boolean initialize) {
    ensureVertexCapacity(vertexCapacity);
    ensureEdgeCapacity(edgeCapacity);
    if (initialize) init(vertexCapacity);
  }

  @Override
  public void init(int vertexCnt) {
    ensureVertexCapacity(vertexCnt);
    this.vertexCnt = vertexCnt;
    currentEdgeCnt = 0;
    Arrays.fill(inDegree, 0, vertexCnt, 0);
    Arrays.fill(outDegree, 0, vertexCnt, 0);
    Arrays.fill(lastIn, 0, vertexCnt, -1);
    Arrays.fill(lastOut, 0, vertexCnt, -1);
    initVertexStorage(vertexCnt);
  }

  @Override
  public void add(int fromIdx, int toIdx) {
    ensureEdgeCapacity(currentEdgeCnt + 1);
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

  @Override
  public void forEachInNodes(int nodeIdx, IntConsumer consumer) {
    for (int edgeIdx = lastIn[nodeIdx]; edgeIdx >= 0; edgeIdx = nextIn[edgeIdx]) {
      consumer.accept(fromIdx[edgeIdx]);
    }
  }

  @Override
  public Iterable<Integer> inNodes(int nodeIdx) {
    return () -> new Iterator<Integer>() {
      private int edgeIdx = lastIn[nodeIdx];

      @Override
      public boolean hasNext() {
        return edgeIdx >= 0;
      }

      @Override
      public Integer next() {
        int res = fromIdx[edgeIdx];
        edgeIdx = nextIn(edgeIdx);
        return res;
      }
    };
  }

  @Override
  public void forEachOutNodes(int nodeIdx, IntConsumer consumer) {
    for (int edgeIdx = lastOut[nodeIdx]; edgeIdx >= 0; edgeIdx = nextOut[edgeIdx]) {
      consumer.accept(toIdx[edgeIdx]);
    }
  }

  @Override
  public Iterable<Integer> outNodes(int nodeIdx) {
    return () -> new Iterator<Integer>() {
      private int edgeIdx = lastOut[nodeIdx];

      @Override
      public boolean hasNext() {
        return edgeIdx >= 0;
      }

      @Override
      public Integer next() {
        int res = toIdx[edgeIdx];
        edgeIdx = nextOut(edgeIdx);
        return res;
      }
    };
  }

  @Override
  public void forEachOutEdges(int nodeIdx, IntConsumer consumer) {
    for (int edgeIdx = lastOut[nodeIdx]; edgeIdx >= 0; edgeIdx = nextOut[edgeIdx]) {
      consumer.accept(edgeIdx);
    }
  }

  private void ensureVertexCapacity(int vertexCapacity) {
    if (lastIn != null && lastIn.length >= vertexCapacity) return;
    int capacity = IntUtils.nextPow2(vertexCapacity);
    lastIn = new int[capacity];
    lastOut = new int[capacity];
    inDegree = new int[capacity];
    outDegree = new int[capacity];
    createVertexStorage(capacity);
  }

  private void ensureEdgeCapacity(int edgeCapacity) {
    if (fromIdx != null && fromIdx.length >= edgeCapacity) return;
    int capacity = IntUtils.nextPow2(edgeCapacity);
    fromIdx = IntArrayUtils.expand(fromIdx, capacity);
    toIdx = IntArrayUtils.expand(toIdx, capacity);
    nextIn = IntArrayUtils.expand(nextIn, capacity);
    nextOut = IntArrayUtils.expand(nextOut, capacity);
    expandEdgeStorage(capacity);
  }
}
