package template.matching;

import template.collections.dancinglink.DancingLink;
import template.collections.queue.IntArrayQueue;
import template.graph.bipartite.BipartiteGraph;

import java.util.Arrays;

public class UnweightedMaximumMatching {

  public int[] leftMatch, rightMatch;

  private int[] leftMark, rightMark;
  private int augmentMark;
  private int[] leftDist, rightDist;
  private IntArrayQueue queue;
  private BipartiteGraph graph;
  private DancingLink leftFree;
  private int leftCnt, rightCnt;

  public UnweightedMaximumMatching(int leftCapacity, int rightCapacity) {
    leftMatch = new int[leftCapacity + 1];
    rightMatch = new int[rightCapacity];
    leftMark = new int[leftCapacity + 1];
    rightMark = new int[rightCapacity];
    leftDist = new int[leftCapacity + 1];
    rightDist = new int[rightCapacity];
    leftFree = new DancingLink(leftCapacity);
    queue = new IntArrayQueue(leftCapacity);
  }

  public int calc(BipartiteGraph graph) {
    this.graph = graph;
    this.leftCnt = graph.leftCnt;
    this.rightCnt = graph.rightCnt;
    leftFree.init(leftCnt);
    Arrays.fill(leftMark, 0, leftCnt + 1, 0);
    Arrays.fill(rightMark, 0, rightCnt, 0);
    augmentMark = 0;
    Arrays.fill(leftMatch, 0, leftCnt + 1, -1);
    Arrays.fill(rightMatch, 0, rightCnt, -1);
    int res = 0;
    res += greedyMatch();
    while (true) {
      calcDist();
      if (leftMark[leftCnt] != augmentMark) break;
      for (int i = 0; i < leftCnt; ++i) if (leftMatch[i] < 0 && augmentDist(i)) {
        leftFree.cover(i);
        ++res;
      }
    }
    return res;
  }

  public boolean augment(int u) {
    if (augmentInternal(u)) return true;
    ++augmentMark;
    return augmentInternal(u);
  }

  private int greedyMatch() {
    int res = 0;
    for (int u = 0; u < leftCnt; ++u) {
      for (int edgeIdx = graph.lastOut(u); edgeIdx >= 0; edgeIdx = graph.nextOut(edgeIdx)) {
        int v = graph.toIdx(edgeIdx);
        if (rightMatch[v] < 0) {
          match(u, v);
          leftFree.cover(u);
          ++res;
          break;
        }
      }
    }
    return res;
  }

  private void calcDist() {
    ++augmentMark;
    queue.clear();
    for (int i = leftFree.first; i >= 0; i = leftFree.next[i]) {
      queue.add(i);
      leftMark[i] = augmentMark;
      leftDist[i] = 0;
    }
    while (!queue.isEmpty()) {
      int u = queue.poll();
      if (u == leftCnt) continue;
      for (int edgeIdx = graph.lastOut(u); edgeIdx >= 0; edgeIdx = graph.nextOut(edgeIdx)) {
        int v = graph.toIdx(edgeIdx);
        if (rightMark[v] != augmentMark) {
          rightMark[v] = augmentMark;
          rightDist[v] = leftDist[u] + 1;
          int newU = rightMatch[v] < 0 ? leftCnt : rightMatch[v];
          if (leftMark[newU] != augmentMark) {
            queue.add(newU);
            leftMark[newU] = augmentMark;
            leftDist[newU] = rightDist[v] + 1;
          }
        }
      }
    }
  }

  private boolean augmentDist(int u) {
    if (leftMark[u] == augmentMark) {
      for (int edgeIdx = graph.lastOut(u); edgeIdx >= 0; edgeIdx = graph.nextOut(edgeIdx)) {
        int v = graph.toIdx(edgeIdx);
        if (rightMark[v] != augmentMark || rightDist[v] != leftDist[u] + 1) continue;
        rightDist[v] = -1;
        int newU = rightMatch[v] < 0 ? leftCnt : rightMatch[v];
        if (newU == leftCnt || augmentDist(newU)) {
          match(u, v);
          leftDist[u] = -1;
          return true;
        }
      }
    }
    leftDist[u] = -1;
    return false;
  }

  private boolean augmentInternal(int u) {
    for (int edgeIdx = graph.lastOut(u); edgeIdx >= 0; edgeIdx = graph.nextOut(edgeIdx)) {
      int v = graph.toIdx(edgeIdx);
      if (rightMark[v] == augmentMark) continue;
      rightMark[v] = augmentMark;
      if (rightMatch[v] < 0 || augmentInternal(rightMatch[v])) {
        match(u, v);
        return true;
      }
    }
    return false;
  }

  private void match(int u, int v) {
    leftMatch[u] = v;
    rightMatch[v] = u;
  }
}
