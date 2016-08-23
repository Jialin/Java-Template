package template.graph.flow;

import java.util.Arrays;

/**
 * A max flow system with lower bound.
 */
public class IntBoundedMaxFlow extends IntMaxFlow {

  private int fakeSource, fakeSink;
  private int[] inFlow, outFlow;
  private boolean noSolution;

  public IntBoundedMaxFlow(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity + 2, edgeCapacity + vertexCapacity);
    this.inFlow = new int[vertexCapacity];
    this.outFlow = new int[vertexCapacity];
  }

  @Override
  public void init(int vertexCnt) {
    super.init(vertexCnt + 2);
    fakeSource = vertexCnt;
    fakeSink = vertexCnt + 1;
    Arrays.fill(inFlow, 0, vertexCnt, 0);
    Arrays.fill(outFlow, 0, vertexCnt, 0);
    noSolution = false;
  }

  @Override
  public IntBoundedMaxFlowEdge add(int fromIdx, int toIdx, int upperBound) {
    return add(fromIdx, toIdx, 0, upperBound);
  }

  @Override
  public IntBoundedMaxFlowEdge addInf(int fromIdx, int toIdx) {
    return add(fromIdx, toIdx, 0, INF);
  }

  /**
   * Adds an edge from {@code fromIdx} to {@code toIdx} with {@code lowerBound} and {@code upperBound}.
   */
  public IntBoundedMaxFlowEdge add(int fromIdx, int toIdx, int lowerBound, int upperBound) {
    noSolution |= lowerBound > upperBound;
    IntBoundedMaxFlowEdge forward = new IntBoundedMaxFlowEdge(fromIdx, toIdx, lowerBound, upperBound);
    IntMaxFlowEdge backward = new IntMaxFlowEdge(toIdx, fromIdx, 0);
    super.add(forward, backward);
    if (lowerBound > 0) {
      outFlow[fromIdx] += lowerBound;
      inFlow[toIdx] += lowerBound;
    }
    return forward;
  }

  /**
   * Calculates the max flow from {@code source} to {@code sink}.
   *
   * NOTE: Returns -1 for no solution.
   */
  @Override
  public int calc(int source, int sink) {
    if (noSolution) return -1;
    IntMaxFlowEdge infEdge = super.addInf(sink, source);
    int sum = 0;
    for (int i = 0; i + 2 < vertexCnt; ++i) if (inFlow[i] != outFlow[i]) {
      if (inFlow[i] > outFlow[i]) {
        sum += inFlow[i] - outFlow[i];
        super.add(fakeSource, i, inFlow[i] - outFlow[i]);
      } else {
        super.add(i, fakeSink, outFlow[i] - inFlow[i]);
      }
    }
    int res = super.calc(fakeSource, fakeSink);
    if (res != sum) return -1;
    block(infEdge);
    for (IntMaxFlowEdge edge = lastOutgoingEdge(fakeSource); edge != null; edge = (IntMaxFlowEdge) edge.nextOutgoing) {
      block(edge);
    }
    for (IntMaxFlowEdge edge = lastIncomingEdge(fakeSink); edge != null; edge = (IntMaxFlowEdge) edge.nextIncoming) {
      block(edge);
    }
    res = super.calc(source, sink);
    if (res == INF) return INF;
    res = outFlow[source];
    for (IntMaxFlowEdge edge = lastOutgoingEdge(source); edge != null; edge = (IntMaxFlowEdge) edge.nextOutgoing) {
      res += edge.flow;
    }
    return res;
  }

  private void block(IntMaxFlowEdge edge) {
    edge.flow = edge.capacity = 0;
    IntMaxFlowEdge reverseEdge = (IntMaxFlowEdge) edge.reverse;
    reverseEdge.flow = reverseEdge.capacity = 0;
  }
}
