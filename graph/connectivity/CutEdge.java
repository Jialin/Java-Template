package template.graph.connectivity;

import template.graph.basic.BidirectionalGraphInterface;

import java.util.Arrays;

public class CutEdge {

  public static int calc(BidirectionalGraphInterface graph, int[] cutEdges, int[] dfn, int[] low) {
    int n = graph.vertexCnt();
    Arrays.fill(dfn, 0, n, 0);
    Arrays.fill(low, 0, n, 0);
    int res = 0;
    for (int i = 0; i < n; ++i) if (dfn[i] == 0) {
      res = tarjan(i, 1, -1, graph, cutEdges, res, dfn, low);
    }
    return res;
  }

  private static int tarjan(
      int u,
      int dfs,
      int prevEdgeIdx,
      BidirectionalGraphInterface graph,
      int[] cutEdges,
      int res,
      int[] dfn,
      int[] low) {

    dfn[u] = low[u] = dfs;
    for (int edgeIdx = graph.lastOut(u); edgeIdx >= 0; edgeIdx = graph.nextOut(edgeIdx)) {
      int v = graph.toIdx(edgeIdx);
      if (dfn[v] == 0) {
        res = tarjan(v, dfs + 1, edgeIdx, graph, cutEdges, res, dfn, low);
        low[u] = Math.min(low[u], low[v]);
        if (low[v] > dfn[u]) cutEdges[res++] = edgeIdx;
      } else if (prevEdgeIdx < 0 || (prevEdgeIdx ^ 1) != edgeIdx) {
        low[u] = Math.min(low[u], dfn[v]);
      }
    }
    return res;
  }
}
