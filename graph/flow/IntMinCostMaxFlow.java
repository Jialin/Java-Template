package template.graph.flow;

import template.array.IntArrayUtils;
import template.collections.queue.IntArrayQueue;
import template.graph.basic.AbstractBidirectionalGraph;

import java.util.Arrays;

public class IntMinCostMaxFlow extends AbstractBidirectionalGraph {

  private int[] flow, capacity, cost;

  private int globalMaxCap;
  private int[] prevEdge, dist, maxCap;
  private long[] minCost;
  private boolean[] tag, minCostUpdated;
  private IntArrayQueue q;

  public IntMinCostMaxFlow() {}

  public IntMinCostMaxFlow(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity);
  }

  public IntMinCostMaxFlow(int vertexCapacity, int edgeCapacity, boolean initialize) {
    super(vertexCapacity, edgeCapacity, initialize);
  }

  @Override
  public void createVertexStorage(int vertexCapacity) {
    q = new IntArrayQueue(vertexCapacity);
    prevEdge = new int[vertexCapacity];
    dist = new int[vertexCapacity];
    maxCap = new int[vertexCapacity];
    minCost = new long[vertexCapacity];
    tag = new boolean[vertexCapacity];
    minCostUpdated = new boolean[vertexCapacity];
  }

  @Override
  public void expandEdgeStorage(int edgeCapacity) {
    flow = IntArrayUtils.expand(flow, edgeCapacity);
    capacity = IntArrayUtils.expand(capacity, edgeCapacity);
    cost = IntArrayUtils.expand(cost, edgeCapacity);
  }

  @Override
  public void initVertexStorage(int vertexCnt) {}

  @Override
  public void add(int fromIdx, int toIdx) {
    throw new UnsupportedOperationException();
  }

  /** Adds an edge from {@code fromIdx} to {@code toIdx} with {@code capacity} and {@code cost}. */
  public void add(int fromIdx, int toIdx, int capacity, int cost) {
    globalMaxCap = currentEdgeCnt > 0 ? Math.max(globalMaxCap, capacity) : capacity;
    super.add(fromIdx, toIdx);
    flow[currentEdgeCnt - 2] = flow[currentEdgeCnt - 1] = 0;
    this.capacity[currentEdgeCnt - 2] = capacity;
    this.capacity[currentEdgeCnt - 1] = 0;
    this.cost[currentEdgeCnt - 2] = cost;
    this.cost[currentEdgeCnt - 1] = -cost;
  }

  public int resFlow;
  public long resCost;

  /**
   * Calculates the min cost - max flow and stores the result in {@code resFlow} and {@code resCost}.
   *
   * NOTE: Returns false if the graph contains negative cost circuit.
   */
  public boolean calc(int source, int sink) {
    resFlow = 0;
    resCost = 0;
    while (true) {
      if (!bfs(source)) {
        // Negative Cost Circuit.
        return false;
      } else if (maxCap[sink] == 0) {
        break;
      } else {
        int cap = maxCap[sink];
        resFlow += cap;
        resCost += (long) cap * minCost[sink];
        int nodeIdx = sink;
        while (nodeIdx != source) {
          int idx = prevEdge[nodeIdx];
          capacity[idx] -= cap;
          capacity[idx ^ 1] += cap;
          nodeIdx = toIdx(idx ^ 1);
        }
      }
    }
    return true;
  }

  private boolean bfs(int source) {
    Arrays.fill(tag, 0, vertexCnt, false);
    Arrays.fill(minCostUpdated, 0, vertexCnt, false);
    Arrays.fill(maxCap, 0, vertexCnt, 0);
    Arrays.fill(prevEdge, 0, vertexCnt, -1);
    Arrays.fill(dist, 0, vertexCnt, 0);
    tag[source] = true;
    maxCap[source] = globalMaxCap;
    minCost[source] = 0;
    minCostUpdated[source] = true;
    q.clear();
    for (q.add(source); q.isNotEmpty(); ) {
      int nodeIdx = q.poll();
      tag[nodeIdx] = false;
      for (int edgeIdx = lastOut(nodeIdx); edgeIdx >= 0; edgeIdx = nextOut(edgeIdx)) {
        int cap = Math.min(maxCap[nodeIdx], capacity[edgeIdx]);
        if (cap == 0) continue;
        int pnt = toIdx(edgeIdx);
        long cost = minCost[nodeIdx] + this.cost[edgeIdx];
        if (!minCostUpdated[pnt] || minCost[pnt] > cost || (minCost[pnt] == cost && maxCap[pnt] < cap)) {
          maxCap[pnt] = cap;
          minCostUpdated[pnt] = true;
          minCost[pnt] = cost;
          prevEdge[pnt] = edgeIdx;
          dist[pnt] = dist[nodeIdx] + 1;
          if (dist[pnt] >= vertexCnt) return false;
          if (!tag[pnt]) {
            tag[pnt] = true;
            q.add(pnt);
          }
        }
      }
    }
    return true;
  }
}
