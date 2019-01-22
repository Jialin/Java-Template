package template.collections.top;

import template.collections.priorityqueue.IntPriorityQueue;

public class IntTopValues {

  private final IntPriorityQueue heap;
  private int topN;

  public static IntTopValues topMin(int topN) {
    return new IntTopValues(new IntPriorityQueue(topN + 1, (x, y) -> y - x), topN);
  }

  public static IntTopValues topMax(int topN) {
    return new IntTopValues(new IntPriorityQueue(topN + 1, (x, y) -> x - y), topN);
  }

  private IntTopValues(IntPriorityQueue heap, int topN) {
    this.heap = heap;
    init(topN);
  }

  public void init(int topN) {
    this.topN = topN;
    heap.clear();
  }

  public void add(int value) {
    heap.add(value);
    for ( ; heap.size > topN; heap.poll()) {}
  }

  public IntPriorityQueue heap() {
    return heap;
  }
}
