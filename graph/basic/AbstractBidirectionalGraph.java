package template.graph.basic;

import template.collections.bitset.BitSet;
import template.collections.list.IntArrayList;

public abstract class AbstractBidirectionalGraph extends AbstractDirectedGraph implements BidirectionalGraphInterface {

  private int vertexCapacity;
  private IntArrayList stack;
  private BitSet inStack;
  private BitSet visited;

  public AbstractBidirectionalGraph(int vertexCapacity, int edgeCapacity) {
    this(vertexCapacity, edgeCapacity << 1, true);
  }

  public AbstractBidirectionalGraph(int vertexCapacity, int edgeCapacity, boolean initialize) {
    super(vertexCapacity, edgeCapacity << 1, initialize);
    this.vertexCapacity = vertexCapacity;
  }

  @Override
  public void add(int u, int v) {
    super.add(u, v);
    super.add(v, u);
  }

  // TODO(jouyang): to be verified
  public boolean containsCycle() {
    if (stack == null) {
      stack = new IntArrayList(vertexCapacity);
      inStack = new BitSet(vertexCapacity);
    }
    if (visited == null) {
      visited = new BitSet(vertexCapacity);
    }
    stack.clear();
    inStack.clear();
    visited.clear();
    for (int i = 0; i < vertexCnt; ++i) if (!visited.get(i)) {
      if (dfsContainsCycle(i, -1)) {
        return true;
      }
    }
    return false;
  }

  private boolean dfsContainsCycle(int u, int prevNode) {
    stack.add(u);
    inStack.set(u);
    for (int edgeIdx = lastOut[u]; edgeIdx >= 0; edgeIdx = nextOut[edgeIdx]) {
      int v = toIdx[edgeIdx];
      if (v == prevNode) {
        continue;
      }
      if (inStack.get(v)) {
        return true;
      }
      dfsContainsCycle(v, u);
    }
    stack.pollLast();
    inStack.clear(u);
    return false;
  }
}
