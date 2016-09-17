package template.graph.flow;

import java.util.Arrays;

/**
 * A max flow system with lower bound.
 */
public class IntBoundedMaxFlow extends IntMaxFlow {

  public int[] lowerBound, upperBound;

  private int fakeSource, fakeSink;
  private int[] inFlow, outFlow;
  private boolean noSolution;

  public IntBoundedMaxFlow(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity + 2, edgeCapacity + vertexCapacity + 1);
    this.lowerBound = new int[(edgeCapacity + vertexCapacity + 1) << 1];
    this.upperBound = new int[(edgeCapacity + vertexCapacity + 1) << 1];
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
  public int add(int fromIdx, int toIdx, int upperBound) {
    return add(fromIdx, toIdx, 0, upperBound);
  }

  @Override
  public int addInf(int fromIdx, int toIdx) {
    return add(fromIdx, toIdx, 0, INF);
  }

  /**
   * Adds an edge from {@code fromIdx} to {@code toIdx} with {@code lowerBound} and {@code upperBound}.
   */
  public int add(int fromIdx, int toIdx, int lowerBound, int upperBound) {
    noSolution |= lowerBound > upperBound;
    this.lowerBound[edgeCnt] = lowerBound;
    this.upperBound[edgeCnt] = upperBound;
    if (lowerBound > 0) {
      outFlow[fromIdx] += lowerBound;
      inFlow[toIdx] += lowerBound;
    }
    return super.add(fromIdx, toIdx, upperBound - lowerBound);
  }

  /**
   * Calculates the max flow from {@code source} to {@code sink}.
   *
   * NOTE: Returns -1 for no solution.
   */
  @Override
  public int calc(int source, int sink) {
    if (noSolution) return -1;
    int infEdgeIdx = super.addInf(sink, source);
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
    block(infEdgeIdx);
    for (int edgeIdx = lastOut[fakeSource]; edgeIdx >= 0; edgeIdx = nextOut[edgeIdx]) {
      block(edgeIdx);
    }
    for (int edgeIdx = lastIn[fakeSink]; edgeIdx >= 0; edgeIdx = nextIn[edgeIdx]) {
      block(edgeIdx);
    }
    res = super.calc(source, sink);
    if (res == INF) return INF;
    res = outFlow[source];
    for (int edgeIdx = lastOut[source]; edgeIdx >= 0; edgeIdx = nextOut[edgeIdx]) {
      res += flow[edgeIdx];
    }
    return res;
  }

  private void block(int edgeIdx) {
    flow[edgeIdx] = capacity[edgeIdx] = 0;
    flow[edgeIdx ^ 1] = capacity[edgeIdx ^ 1] = 0;
  }
}
