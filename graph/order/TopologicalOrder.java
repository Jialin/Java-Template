package template.graph.order;

import template.collections.queue.IntArrayQueue;
import template.graph.basic.DirectedGraphInterface;

public class TopologicalOrder {

  private static int[] remOutDegree;
  private static IntArrayQueue q;

  public static int[] order;

  public static int calc(DirectedGraphInterface graph) {
    int n = graph.vertexCnt();
    ensureCapacity(n);
    int res = 0;
    for (int i = 0; i < n; ++i) {
      remOutDegree[i] = graph.outDegree(i);
      if (remOutDegree[i] == 0) {
        order[res++] = i;
      }
    }
    for (int i = 0; i < res; ++i) {
      int u = order[i];
      for (int edgeIdx = graph.lastIn(u); edgeIdx >= 0; edgeIdx = graph.nextIn(edgeIdx)) {
        int v = graph.fromIdx(edgeIdx);
        --remOutDegree[v];
        if (remOutDegree[v] == 0) {
          order[res++] = v;
        }
      }
    }
    return res;
  }

  private static void ensureCapacity(int n) {
    if (remOutDegree == null || remOutDegree.length < n) {
      int size = Integer.highestOneBit(n) << 1;
      remOutDegree = new int[size];
      order = new int[size];
      q = new IntArrayQueue(size);
    }
  }
}
