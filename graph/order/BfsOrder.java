package template.graph.order;

import template.graph.basic.DirectedGraphInterface;
import template.graph.blockable.Blockable;
import template.graph.blockable.BlockableDirectedGraphInterface;

public class BfsOrder {

  public static int calc(DirectedGraphInterface graph, int startIdx, int[] bfs, boolean[] visited) {
    if (visited[startIdx]) return 0;
    BlockableDirectedGraphInterface blockableGraph = null;
    if (graph instanceof Blockable) {
      blockableGraph = (BlockableDirectedGraphInterface) graph;
    }
    visited[startIdx] = true;
    int res = 0;
    bfs[res++] = startIdx;
    for (int i = 0; i < res; ++i) {
      int u = bfs[i];
      for (int edgeIdx = graph.lastOut(u); edgeIdx >= 0; edgeIdx = graph.nextOut(edgeIdx)) {
        if (blockableGraph == null || !blockableGraph.blocked(edgeIdx)) {
          int v = graph.toIdx(edgeIdx);
          if (!visited[v]) {
            visited[v] = true;
            bfs[res++] = v;
          }
        }
      }
    }
    return res;
  }
}
