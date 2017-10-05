package template.graph.lca;

import template.collections.rmq.IntMinimumRMQ;
import template.graph.tree.TreeInterface;
import template.numbertheory.number.IntUtils;

public class TreeLCA {

  public int[] depth;

  private TreeInterface tree;
  private int dfsCnt;
  private int[] dfsIn, dfsOut, dfsDepth, dfsDepthIdx;
  private IntMinimumRMQ rmq;

  public TreeLCA() {}

  public TreeLCA(int vertexCapacity) {
    ensureCapacity(vertexCapacity);
  }

  public void init(TreeInterface tree, int rootNodeIdx) {
    ensureCapacity(tree.vertexCnt());
    this.tree = tree;
    dfsCnt = 0;
    dfsInternal(rootNodeIdx, -1, 0);
    rmq.init(dfsCnt, dfsDepth);
  }

  public int calc(int x, int y) {
    return dfsDepthIdx[rmq.calcIdx(Math.min(dfsIn[x], dfsIn[y]), Math.max(dfsOut[x], dfsOut[y]) + 1)];
  }

  private void dfsInternal(int u, int parent, int depth) {
    this.depth[u] = depth;
    dfsDepth[dfsCnt] = depth;
    dfsDepthIdx[dfsCnt] = u;
    dfsIn[u] = dfsCnt++;
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) continue;
      dfsInternal(v, u, depth + 1);
      dfsDepth[dfsCnt] = depth;
      dfsDepthIdx[dfsCnt++] = u;
    }
    dfsDepth[dfsCnt] = depth;
    dfsDepthIdx[dfsCnt] = u;
    dfsOut[u] = dfsCnt++;
  }

  private void ensureCapacity(int capacity) {
    if (depth != null && depth.length >= capacity) return;
    int size = IntUtils.nextPow2(capacity);
    int size3 = IntUtils.nextPow2(capacity * 3);
    depth = new int[size];
    dfsIn = new int[size];
    dfsOut = new int[size];
    rmq = new IntMinimumRMQ(size3);
    dfsDepth = new int[size3];
    dfsDepthIdx = new int[size3];
  }
}
