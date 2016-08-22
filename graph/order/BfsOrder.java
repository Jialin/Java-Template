package template.graph.order;

import template.graph.DirectedGraph;
import template.graph.DirectedGraphEdge;

public class BfsOrder {

  /**
   * Calculates BFS order starting from {@code startIndex} of the given {@link DirectedGraph}.
   */
  public static int[] calc(DirectedGraph<DirectedGraphEdge> graph, int startIndex) {
    int n = graph.vertexCnt;
    int[] res = new int[n];
    boolean[] visited = new boolean[n];
    visited[startIndex] = true;
    int vertexIndex = 0;
    res[vertexIndex++] = startIndex;
    for (int i = 0; i < vertexIndex; ++i) {
      int u = res[i];
      for (DirectedGraphEdge edge = graph.lastOutgoingEdge(u); edge != null; edge = edge.nextOutgoing) {
        int v = edge.toIdx;
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
