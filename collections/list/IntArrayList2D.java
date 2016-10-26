package template.collections.list;

import java.util.Arrays;

public class IntArrayList2D {

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
    init(listCapacity);
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
}
