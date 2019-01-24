package template.graph.flow;

import template.array.IntArrayUtils;
import template.collections.queue.IntArrayQueue;
import template.graph.basic.AbstractBidirectionalGraph;

import java.util.Arrays;

/** A max flow system. */
public class IntMaxFlow extends AbstractBidirectionalGraph {

  protected static final int INF = Integer.MAX_VALUE;

  public int[] flow, capacity;

  private int source, sink;
  private IntArrayQueue q;
  private int[] level;
  private int[] edgePnt;

  public IntMaxFlow(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity);
  }

  public IntMaxFlow(int vertexCapacity, int edgeCapacity, boolean initialize) {
    super(vertexCapacity, edgeCapacity, initialize);
  }

  @Override
  public void createVertexStorage(int vertexCapacity) {
    this.q = new IntArrayQueue(vertexCapacity);
    this.level = new int[vertexCapacity];
    this.edgePnt = new int[vertexCapacity];
  }

  @Override
  public void expandEdgeStorage(int edgeCapacity) {
    this.flow = IntArrayUtils.expand(flow, edgeCapacity);
    this.capacity = IntArrayUtils.expand(capacity, edgeCapacity);
  }

  @Override
  public void initVertexStorage(int vertexCnt) {}

  @Override
  public void add(int fromIdx, int toIdx) {
    throw new UnsupportedOperationException();
  }

  /** Adds an edge from {@code fromIdx} to {@code toIdx} with {@code capacity}. */
  public int add(int fromIdx, int toIdx, int capacity) {
    super.add(fromIdx, toIdx);
    flow[currentEdgeCnt - 2] = flow[currentEdgeCnt - 1] = 0;
    this.capacity[currentEdgeCnt - 2] = capacity;
    this.capacity[currentEdgeCnt - 1] = 0;
    return currentEdgeCnt - 2;
  }

  /** Adds an edge from {@code fromIdx} to {@code toIdx} with infinite capacity. */
  public int addInf(int fromIdx, int toIdx) {
    return add(fromIdx, toIdx, INF);
  }

  /** Calculates the max flow from {@code source} to {@code sink}. */
  public int calc(int source, int sink) {
    this.source = source;
    this.sink = sink;
    int res = 0;
    while (bfs()) {
      for (int i = 0; i < vertexCnt; ++i) edgePnt[i] = lastOut[i];
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
      for (int edgeIdx = lastOut[u]; edgeIdx >= 0; edgeIdx = nextOut[edgeIdx]) {
        int v = toIdx[edgeIdx];
        if (level[v] < 0 && flow[edgeIdx] < capacity[edgeIdx]) {
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
    for ( ; edgePnt[u] >= 0; edgePnt[u] = nextOut[edgePnt[u]]) {
      int edgeIdx = edgePnt[u];
      int v = toIdx[edgeIdx];
      if (level[v] != level[u] + 1) continue;
      int res = dfs(v, Math.min(flow, capacity[edgeIdx] - this.flow[edgeIdx]));
      if (res > 0) {
        this.flow[edgeIdx] += res;
        this.flow[edgeIdx ^ 1] -= res;
        return res;
      }
    }
    return 0;
  }
}
