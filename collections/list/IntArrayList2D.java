package template.collections.list;

import template.io.Displayable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.IntConsumer;

public class IntArrayList2D implements Displayable {

  public int listCnt, close;
  public int[] size, first, last;
  public int[] prev, next, values;

  public IntArrayList2D(int listCapacity, int elementCapacity) {
    size = new int[listCapacity];
    first = new int[listCapacity];
    last = new int[listCapacity];
    prev = new int[elementCapacity];
    next = new int[elementCapacity];
    values = new int[elementCapacity];
  }

  public void init(int listCnt) {
    this.listCnt = listCnt;
    close = 0;
    Arrays.fill(size, 0, listCnt, 0);
    Arrays.fill(first, 0, listCnt, -1);
    Arrays.fill(last, 0, listCnt, -1);
  }

  public void add(int listIdx, int value) {
    ++size[listIdx];
    prev[close] = last[listIdx];
    if (last[listIdx] >= 0) next[last[listIdx]] = close;
    next[close] = -1;
    values[close] = value;
    if (first[listIdx] < 0) first[listIdx] = close;
    last[listIdx] = close;
    ++close;
  }

  public int pollLast(int listIdx) {
    if (size[listIdx] == 0) throw new IllegalArgumentException();
    --size[listIdx];
    int idx = last[listIdx];
    last[listIdx] = prev[idx];
    int newIdx = last[listIdx];
    if (newIdx >= 0) {
      next[newIdx] = -1;
    } else {
      first[listIdx] = -1;
    }
    return values[idx];
  }

  public Iterable<Integer> values(int listIdx) {
    return () -> new Iterator<Integer>() {
      private int idx = first[listIdx];

      @Override
      public boolean hasNext() {
        return idx >= 0;
      }

      @Override
      public Integer next() {
        int res = values[idx];
        idx = next[idx];
        return res;
      }
    };
  }

  public void forEach(int listIdx, IntConsumer consumer) {
    for (int idx = first[listIdx]; idx >= 0; idx = next[idx]) {
      consumer.accept(values[idx]);
    }
  }

  @Override
  public String toDisplay() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < listCnt; ++i) {
      sb.append(String.format("list[%d]:", i));
      boolean first = true;
      for (int idx = this.first[i]; idx >= 0; idx = next[idx]) {
        if (!first) sb.append(',');
        sb.append(values[idx]);
      }
      sb.append('\n');
    }
    return sb.toString();
  }
}
