package template.graph.lca;

import template.collections.rmq.IntMinimumRMQ;
import template.graph.tree.TreeInterface;

public class TreeLCA {

  public int[] depth;

  private TreeInterface tree;
  private int dfsCnt;
  private int[] dfsIn, dfsOut, dfsDepth, dfsDepthIdx;
  private IntMinimumRMQ rmq;

  public TreeLCA(int vertexCapacity) {
    depth = new int[vertexCapacity];
    int vertexCapacity3 = vertexCapacity * 3;
    rmq = new IntMinimumRMQ(vertexCapacity3);
    dfsIn = new int[vertexCapacity];
    dfsOut = new int[vertexCapacity];
    dfsDepth = new int[vertexCapacity3];
    dfsDepthIdx = new int[vertexCapacity3];
  }

  public void init(TreeInterface tree, int rootNodeIdx) {
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
}
