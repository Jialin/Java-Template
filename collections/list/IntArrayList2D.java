package template.collections.list;

import template.io.Displayable;
import template.numbertheory.number.IntUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.IntConsumer;

public class IntArrayList2D implements Displayable {

  public int listCnt, close;
  public int[] size, first, last;
  public int[] prev, next, values;

  public IntArrayList2D() {}

  public IntArrayList2D(int listCapacity, int elementCapacity) {
    this(listCapacity, elementCapacity, true);
  }

  public IntArrayList2D(int listCapacity, int elementCapacity, boolean initialize) {
    ensureListCapacity(listCapacity);
    ensureElementCapacity(elementCapacity);
    if (initialize) init(listCapacity);
  }

  public void init(int listCnt) {
    ensureListCapacity(listCnt);
    this.listCnt = listCnt;
    close = 0;
    Arrays.fill(size, 0, listCnt, 0);
    Arrays.fill(first, 0, listCnt, -1);
    Arrays.fill(last, 0, listCnt, -1);
  }

  public void add(int listIdx, int value) {
    ensureElementCapacity(close + 1);
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

  private void ensureListCapacity(int capacity) {
    if (size != null && size.length >= capacity) return;
    int size = IntUtils.nextPow2(capacity);
    this.size = new int[size];
    first = new int[size];
    last = new int[size];
  }

  private void ensureElementCapacity(int capacity) {
    if (prev != null && prev.length >= capacity) return;
    int size = IntUtils.nextPow2(capacity);
    prev = new int[size];
    next = new int[size];
    values = new int[size];
  }
}
