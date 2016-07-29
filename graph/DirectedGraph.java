package template.graph;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Directed graph.
 */
public class DirectedGraph {

  public int vertexCount;
  public int[] fromIndex;
  public int[] toIndex;

  protected int edgeIndex;

  private int[] nextOutgoing;
  private int[] lastOutgoing;

  public DirectedGraph(int vertexCount, int edgeCapacity) {
    this.vertexCount = vertexCount;
    this.fromIndex = new int[edgeCapacity];
    this.toIndex = new int[edgeCapacity];
    this.nextOutgoing = new int[edgeCapacity];
    this.lastOutgoing = new int[vertexCount];
    Arrays.fill(lastOutgoing, -1);
    this.edgeIndex = 0;
  }

  /**
   * Adds a directed edge from {@code fromIndex} to {@code toIndex}.
   */
  public void add(int fromIndex, int toIndex) {
    this.fromIndex[edgeIndex] = fromIndex;
    this.toIndex[edgeIndex] = toIndex;
    nextOutgoing[edgeIndex] = lastOutgoing[fromIndex];
    lastOutgoing[fromIndex] = edgeIndex++;
  }

  /**
   * Returns an {@link Iterable} outgoing edge indices from {@code vertexIndex}.
   */
  public Iterable<Integer> outgoingEdgeIndex(int vertexIndex) {
    return () -> new Iterator<Integer>() {
      private int edgeIndex = lastOutgoing[vertexIndex];

      @Override
      public boolean hasNext() {
        return edgeIndex >= 0;
      }

      @Override
      public Integer next() {
        int currentEdgeIndex = edgeIndex;
        edgeIndex = nextOutgoing[edgeIndex];
        return currentEdgeIndex;
      }
    };
  }

  /**
   * Returns an {@link Iterable} outgoing edge destinations from {@code vertexIndex}.
   */
  public Iterable<Integer> outgoingVertexIndex(int vertexIndex) {
    return () -> new Iterator<Integer>() {
      private int edgeIndex = lastOutgoing[vertexIndex];

      @Override
      public boolean hasNext() {
        return edgeIndex >= 0;
      }

      @Override
      public Integer next() {
        int currentVertexIndex = toIndex[edgeIndex];
        edgeIndex = nextOutgoing[edgeIndex];
        return currentVertexIndex;
      }
    };
  }
}
