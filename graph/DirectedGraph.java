package template.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A directed graph.
 */
public class DirectedGraph<EDGE extends DirectedGraphEdge> {

  public int vertexCnt;
  public List<EDGE> edges;
  public int[] inDegree, outDegree;

  private List<EDGE> lastOutgoingEdge;
  private List<EDGE> lastIncomingEdge;

  public DirectedGraph(int vertexCapacity, int edgeCapacity) {
    edges = new ArrayList<>(edgeCapacity);
    lastOutgoingEdge = new ArrayList<>(vertexCapacity);
    lastIncomingEdge = new ArrayList<>(vertexCapacity);
    inDegree = new int[vertexCapacity];
    outDegree = new int[vertexCapacity];
  }

  /**
   * Initializes an empty graph with {@code vertexCnt} nodes.
   */
  public void init(int vertexCnt) {
    this.vertexCnt = vertexCnt;
    edges.clear();
    lastOutgoingEdge.clear();
    lastIncomingEdge.clear();
    Arrays.fill(inDegree, 0, vertexCnt, 0);
    Arrays.fill(outDegree, 0, vertexCnt, 0);
    for (int i = 0; i < vertexCnt; ++i) {
      lastOutgoingEdge.add(null);
      lastIncomingEdge.add(null);
    }
  }

  /**
   * Adds a directed edge from {@code fromIdx} to {@code toIdx}.
   */
  public void add(EDGE edge) {
    edges.add(edge);
    int fromIdx = edge.fromIdx;
    int toIdx = edge.toIdx;
    edge.nextOutgoing = lastOutgoingEdge.get(fromIdx);
    lastOutgoingEdge.set(fromIdx, edge);
    edge.nextIncoming = lastIncomingEdge.get(toIdx);
    lastIncomingEdge.set(toIdx, edge);
    ++inDegree[toIdx];
    ++outDegree[fromIdx];
  }

  public EDGE lastOutgoingEdge(int vertexIdx) {
    return lastOutgoingEdge.get(vertexIdx);
  }

  public EDGE lastIncomingEdge(int vertexIdx) {
    return lastIncomingEdge.get(vertexIdx);
  }
}
