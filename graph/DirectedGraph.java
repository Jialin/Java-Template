package template.graph;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;

/**
 * Directed graph.
 *
 * @param <INFO> Edge information
 */
public class DirectedGraph<INFO> {

  public int vertexCount;
  public int[] fromIndex;
  public int[] toIndex;
  public INFO[] info;

  private int[] nextOutgoing;
  private int[] lastOutgoing;
  private int edgeIndex;

  public DirectedGraph(int vertexCount, int edgeCapacity, Function<Integer, INFO[]> infoArrayFactory) {
    this.vertexCount = vertexCount;
    this.fromIndex = new int[edgeCapacity];
    this.toIndex = new int[edgeCapacity];
    this.info = infoArrayFactory.apply(edgeCapacity);
    this.nextOutgoing = new int[edgeCapacity];
    this.lastOutgoing = new int[vertexCount];
    Arrays.fill(lastOutgoing, -1);
    this.edgeIndex = 0;
  }

  /**
   * Adds a directed edge from {@code fromIndex} to {@code toIndex} with {@code info}.
   */
  public void add(int fromIndex, int toIndex, INFO info) {
    this.fromIndex[edgeIndex] = fromIndex;
    this.toIndex[edgeIndex] = toIndex;
    this.info[edgeIndex] = info;
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
