package template.graph.order;

import template.graph.basic.DirectedGraph;

public class BfsOrder {

  public static int calc(DirectedGraph graph, int startIdx, int[] bfs, boolean[] visited) {
    if (visited[startIdx]) return 0;
    visited[startIdx] = true;
    int res = 0;
    bfs[res++] = startIdx;
    for (int i = 0; i < res; ++i) {
      int u = bfs[i];
      for (int edgeIdx = graph.lastOut[u]; edgeIdx >= 0; edgeIdx = graph.nextOut[edgeIdx]) {
        int v = graph.toIdx[edgeIdx];
        if (!visited[v]) {
          visited[v] = true;
          bfs[res++] = v;
        }
      }
    }
    return res;
  }
}
