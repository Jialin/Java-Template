package template.graph.euler;

import template.array.IntArrayUtils;
import template.graph.blockable.BlockableDirectedGraph;

public class EulerPath {

  public static int calc(int startIdx, BlockableDirectedGraph graph, int[] lastOut, int[] resPath) {
    int res = calcInternal(startIdx, -1, graph, lastOut, resPath, 0);
    IntArrayUtils.reverse(resPath, 0, res);
    return res;
  }

  private static int calcInternal(
      int nodeIdx, int prevEdgeIdx, BlockableDirectedGraph graph, int[] lastOut, int[] resPath, int resCnt) {

    while (lastOut[nodeIdx] >= 0) {
      int edgeIdx = lastOut[nodeIdx];
      lastOut[nodeIdx] = graph.nextOut(edgeIdx);
      if (!graph.blocked[edgeIdx]) {
        graph.block(edgeIdx);
        resCnt = calcInternal(
            graph.toIdx(edgeIdx), edgeIdx, graph, lastOut, resPath, resCnt);
      }
    }
    if (prevEdgeIdx >= 0) {
      resPath[resCnt++] = prevEdgeIdx;
    }
    return resCnt;
  }
}
