package template.graph.order;

import template.graph.DirectedGraph;

public class BfsOrder {

  /**
   * Calculates BFS order starting from {@code startIndex} of the given {@link DirectedGraph}.
   */
  public static int[] calc(DirectedGraph graph, int startIndex) {
    int n = graph.vertexCount;
    int[] res = new int[n];
    boolean[] visited = new boolean[n];
    visited[startIndex] = true;
    int vertexIndex = 0;
    res[vertexIndex++] = startIndex;
    for (int i = 0; i < vertexIndex; ++i) {
      int u = res[i];
      for (int v : graph.outgoingVertexIndex(u)) {
        if (!visited[v]) {
          visited[v] = true;
          res[vertexIndex++] = v;
        }
      }
    }
    if (vertexIndex != n) {
      throw new IllegalArgumentException("Given directed graph should be connected.");
    }
    return res;
  }
}
