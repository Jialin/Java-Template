package template.graph;

import template.collections.iterator.IntIterator;

import java.util.Arrays;

/**
 * Directed graph with n edges (i.e. each node with exactly one outgoing edge).
 */
public class OneRegularGraph extends DirectedGraph {

  /**
   * Total number of components.
   */
  public int compCnt;

  /**
   * Size of the i-th component.
   */
  public int[] compSize;

  /**
   * Index of the component which i-th node belongs to.
   */
  public int[] compIdx;

  /**
   * Size of the core of the i-th component.
   */
  public int[] coreSize;

  /**
   * Distance from the i-th node to its core.
   */
  public int[] distToCore;

  /**
   * Closest core node to the i-th node.
   */
  public int[] closestCoreNode;

  private int[] toIdx;
  private int[] cnt;

  public OneRegularGraph(int vertexCapacity) {
    super(vertexCapacity, vertexCapacity);
    compSize = new int[vertexCapacity];
    compIdx = new int[vertexCapacity];
    coreSize = new int[vertexCapacity];
    distToCore = new int[vertexCapacity];
    closestCoreNode = new int[vertexCapacity];

    toIdx = new int[vertexCapacity];
    cnt = new int[vertexCapacity];
  }

  @Override
  public void init(int vertexCnt) {
    super.init(vertexCnt);
    Arrays.fill(toIdx, 0, vertexCnt, -1);
    Arrays.fill(compIdx, 0, vertexCnt, -1);
    Arrays.fill(cnt, 0, vertexCnt, 0);
    Arrays.fill(distToCore, 0, vertexCnt, -1);
  }

  @Override
  public void add(int fromIdx, int toIdx) {
    super.add(fromIdx, toIdx);
    this.toIdx[fromIdx] = toIdx;
  }

  /**
   * Calculates properties of the one regular graph. Returns the number of components.
   */
  public int calc() {
    compCnt = 0;
    for (int i = 0; i < vertexCnt; ++i) {
      if (compIdx[i] >= 0) continue;
      compSize[compCnt] = dfs(i);
      for (int rem = compSize[compCnt] << 1, u = i; rem > 0; rem--, u = toIdx[u]) {
        ++cnt[u];
      }
      ++compCnt;
    }
    Arrays.fill(coreSize, 0, compCnt, 0);
    for (int i = 0; i < vertexCnt; ++i) {
      if (cnt[i] <= 1) continue;
      ++coreSize[compIdx[i]];
      distToCore[i] = 0;
      closestCoreNode[i] = i;
    }
    for (int i = 0; i < vertexCnt; ++i) {
      if (distToCore[i] != 0) continue;
      reverseDfs(1, i, i);
    }
    return compCnt;
  }

  private int dfs(int u) {
    if (compIdx[u] >= 0) return 0;
    compIdx[u] = compCnt;
    int res = 1 + dfs(toIdx[u]);
    for (IntIterator i = incomingVertexIdx(u); i.hasNext(); ) {
      res += dfs(i.next());
    }
    return res;
  }

  private void reverseDfs(int dist, int u, int coreNode) {
    for (IntIterator i = incomingVertexIdx(u); i.hasNext(); ) {
      int v = i.next();
      if (distToCore[v] >= 0) continue;
      distToCore[v] = dist;
      closestCoreNode[v] = coreNode;
      reverseDfs(dist + 1, v, coreNode);
    }
  }
}
