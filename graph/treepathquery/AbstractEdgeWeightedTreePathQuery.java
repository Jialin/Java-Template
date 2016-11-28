package template.graph.treepathquery;

import template.graph.lca.TreeLCA;
import template.graph.tree.TreeInterface;

public abstract class AbstractEdgeWeightedTreePathQuery {

  public abstract void createSubclass(int capacityHighBit, int capacity);
  public abstract void initSubclass(int u, int toParentEdgeIdx);
  public abstract void initMerge(int targetBit, int targetIdx, int sourceBit, int sourceIdx1, int sourceIdx2);
  public abstract void calcAppend(int bit, int idx);

  private TreeLCA lca;
  private TreeInterface tree;
  private int[] toParentEdgeIdx;
  private int[][] ancestor;

  private int calcCnt;
  private int[] calcBit;
  private int[] calcIdx;

  public AbstractEdgeWeightedTreePathQuery(int capacity) {
    lca = new TreeLCA(capacity);
    toParentEdgeIdx = new int[capacity];
    int highBit = 32 - Integer.numberOfLeadingZeros(capacity);
    ancestor = new int[highBit][capacity];
    calcBit = new int[highBit];
    calcIdx = new int[highBit];
    createSubclass(highBit, capacity);
  }

  public void init(TreeInterface tree, int rootNodeIdx) {
    this.tree = tree;
    lca.init(tree, rootNodeIdx);
    dfsInternal(rootNodeIdx, -1);
    int n = tree.vertexCnt();
    for (int i = 0; i < n; ++i) {
      int edgeIdx = toParentEdgeIdx[i];
      ancestor[0][i] = edgeIdx < 0 ? -1 : tree.toIdx(edgeIdx);
      if (edgeIdx >= 0) {
        initSubclass(i, edgeIdx);
      }
    }
    int highBit = 32 - Integer.numberOfLeadingZeros(n);
    for (int bit = 1; bit < highBit; ++bit) {
      for (int i = 0; i < n; ++i) {
        int j = ancestor[bit - 1][i];
        ancestor[bit][i] = j < 0 ? -1 : ancestor[bit - 1][j];
        if (j >= 0) {
          initMerge(bit, i, bit - 1, i, j);
        }
      }
    }
  }

  public void calc(int fromIdx, int toIdx) {
    int lca = this.lca.calc(fromIdx, toIdx);
    int lcaDepth = this.lca.depth[lca];
    int n = calcInternal(fromIdx, this.lca.depth[fromIdx] - lcaDepth);
    for (int i = 0; i < n; ++i) {
      calcAppend(calcBit[i], calcIdx[i]);
    }
    n = calcInternal(toIdx, this.lca.depth[toIdx] - lcaDepth);
    for (int i = n - 1; i >= 0; --i) {
      calcAppend(calcBit[i], calcIdx[i]);
    }
  }

  private void dfsInternal(int u, int toParentEdgeIdx) {
    this.toParentEdgeIdx[u] = toParentEdgeIdx;
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      if (edgeIdx != toParentEdgeIdx) {
        dfsInternal(tree.toIdx(edgeIdx), edgeIdx ^ 1);
      }
    }
  }

  private int calcInternal(int fromIdx, int step) {
    calcCnt = 0;
    for (int bit = 0; step > 0; ++bit, step >>= 1) if ((step & 1) > 0) {
      calcBit[calcCnt] = bit;
      calcIdx[calcCnt++] = fromIdx;
      fromIdx = ancestor[bit][fromIdx];
    }
    return calcCnt;
  }
}
