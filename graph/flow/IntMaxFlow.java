package template.graph.flow;

import template.collections.queue.IntArrayQueue;
import template.graph.BidirectionalGraph;

import java.util.Arrays;

/**
 * A max flow system.
 */
public class IntMaxFlow extends BidirectionalGraph<IntMaxFlowEdge> {

  protected static final int INF = Integer.MAX_VALUE;

  private int source, sink;
  private IntArrayQueue q;
  private int[] level;
  private IntMaxFlowEdge[] edgePnt;

  public IntMaxFlow(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity);
    this.q = new IntArrayQueue(vertexCapacity);
    this.level = new int[vertexCapacity];
    this.edgePnt = new IntMaxFlowEdge[vertexCapacity];
  }

  /**
   * Adds an edge from {@code fromIdx} to {@code toIdx} with {@code capacity}.
   */
  public IntMaxFlowEdge add(int fromIdx, int toIdx, int capacity) {
    IntMaxFlowEdge forward = new IntMaxFlowEdge(fromIdx, toIdx, capacity);
    IntMaxFlowEdge backward = new IntMaxFlowEdge(toIdx, fromIdx, 0);
    super.add(forward, backward);
    return forward;
  }

  /**
   * Adds an edge from {@code fromIdx} to {@code toIdx} with infinite capacity.
   */
  public IntMaxFlowEdge addInf(int fromIdx, int toIdx) {
    return add(fromIdx, toIdx, INF);
  }

  /**
   * Calculates the max flow from {@code source} to {@code sink}.
   */
  public int calc(int source, int sink) {
    this.source = source;
    this.sink = sink;
    int res = 0;
    while (bfs()) {
      for (int i = 0; i < vertexCnt; ++i) edgePnt[i] = lastOutgoingEdge(i);
      while (true) {
        int flow = dfs(source, INF);
        if (flow == INF) return INF;
        if (flow == 0) break;
        res += flow;
      }
    }
    return res;
  }

  private boolean bfs() {
    q.clear();
    q.add(source);
    Arrays.fill(level, 0, vertexCnt, -1);
    level[source] = 0;
    while (!q.isEmpty() && level[sink] < 0) {
      int u = q.poll();
      for (IntMaxFlowEdge edge = lastOutgoingEdge(u); edge != null; edge = (IntMaxFlowEdge) edge.nextOutgoing) {
        int v = edge.toIdx;
        if (level[v] < 0 && edge.flow < edge.capacity) {
          q.add(v);
          level[v] = level[u] + 1;
        }
      }
    }
    return level[sink] >= 0;
  }

  private int dfs(int u, int flow) {
    if (flow == 0) return 0;
    if (u == sink) return flow;
    for ( ; edgePnt[u] != null; edgePnt[u] = (IntMaxFlowEdge) edgePnt[u].nextOutgoing) {
      IntMaxFlowEdge edge = edgePnt[u];
      int v = edge.toIdx;
      if (level[v] != level[u] + 1) continue;
      int res = dfs(v, Math.min(flow, edge.capacity - edge.flow));
      if (res > 0) {
        edge.flow += res;
        ((IntMaxFlowEdge) edge.reverse).flow -= res;
        return res;
      }
    }
    return 0;
  }
}
