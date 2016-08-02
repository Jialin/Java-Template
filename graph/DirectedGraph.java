package template.graph;

import template.collections.iterator.IntIterator;

import java.util.Arrays;

/**
 * Directed graph.
 */
public class DirectedGraph {

  public int vertexCnt;
  public int[] fromIdx;
  public int[] toIdx;

  protected int edgeIdx;

  private int[] nextIncoming;
  private int[] lastIncoming;
  private int[] nextOutgoing;
  private int[] lastOutgoing;

  public DirectedGraph(int vertexCapacity, int edgeCapacity) {
    this.fromIdx = new int[edgeCapacity];
    this.toIdx = new int[edgeCapacity];
    this.nextIncoming = new int[edgeCapacity];
    this.lastIncoming = new int[vertexCapacity];
    this.nextOutgoing = new int[edgeCapacity];
    this.lastOutgoing = new int[vertexCapacity];
  }

  public void init(int vertexCnt) {
    this.vertexCnt = vertexCnt;
    Arrays.fill(lastIncoming, 0, vertexCnt, -1);
    Arrays.fill(lastOutgoing, 0, vertexCnt, -1);
    edgeIdx = 0;
  }

  /**
   * Adds a directed edge from {@code fromIdx} to {@code toIdx}.
   */
  public void add(int fromIdx, int toIdx) {
    this.fromIdx[edgeIdx] = fromIdx;
    this.toIdx[edgeIdx] = toIdx;
    nextIncoming[edgeIdx] = lastIncoming[toIdx];
    lastIncoming[toIdx] = edgeIdx;
    nextOutgoing[edgeIdx] = lastOutgoing[fromIdx];
    lastOutgoing[fromIdx] = edgeIdx++;
  }

  /**
   * Returns an {@link IntIterator} outgoing edge indices from {@code vertexIdx}.
   */
  public IntIterator outgoingEdgeIdx(int vertexIdx) {
    return new IntIterator() {
      int edgeIdx = lastOutgoing[vertexIdx];

      @Override
      public boolean hasNext() {
        return edgeIdx >= 0;
      }

      @Override
      public int next() {
        int currentEdgeIdx = edgeIdx;
        edgeIdx = nextOutgoing[edgeIdx];
        return currentEdgeIdx;
      }
    };
  }

  /**
   * Returns an {@link IntIterator} outgoing edge destinations from {@code vertexIdx}.
   */
  public IntIterator outgoingVertexIdx(int vertexIdx) {
    IntIterator iterator = outgoingEdgeIdx(vertexIdx);
    return new IntIterator() {
      @Override
      public boolean hasNext() {
        return iterator.hasNext();
      }

      @Override
      public int next() {
        return toIdx[iterator.next()];
      }
    };
  }

  /**
   * Returns an {@link IntIterator} incoming edge indices from {@code vertexIdx}.
   */
  public IntIterator incomingEdgeIdx(int vertexIdx) {
    return new IntIterator() {
      int edgeIdx = lastIncoming[vertexIdx];

      @Override
      public boolean hasNext() {
        return edgeIdx >= 0;
      }

      @Override
      public int next() {
        int currentEdgeIdx = edgeIdx;
        edgeIdx = nextIncoming[edgeIdx];
        return currentEdgeIdx;
      }
    };
  }

  /**
   * Returns an {@link IntIterator} incoming edge destinations from {@code vertexIdx}.
   */
  public IntIterator incomingVertexIdx(int vertexIdx) {
    IntIterator iterator = incomingEdgeIdx(vertexIdx);
    return new IntIterator() {
      @Override
      public boolean hasNext() {
        return iterator.hasNext();
      }

      @Override
      public int next() {
        return fromIdx[iterator.next()];
      }
    };
  }
}
