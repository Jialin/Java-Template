package template.collections.priorityqueue;

import template.collections.list.IntArrayList;
import template.comparators.IntComparator;

public class IntPriorityQueue extends IntArrayList {

  private final IntComparator comparator;

  public IntPriorityQueue(int capacity) {
    this(capacity, (x, y) -> x - y);
  }

  public IntPriorityQueue(int capacity, IntComparator comparator) {
    super(capacity);
    this.comparator = comparator;
    clear();
  }

  @Override
  public void add(int value) {
    super.add(value);
    int i = size - 1;
    while (i > 0) {
      int parent = (i - 1) >> 1;
      int parentValue = values[parent];
      if (comparator.compare(value, parentValue) >= 0) break;
      values[i] = parentValue;
      i = parent;
    }
    values[i] = value;
  }

  public int peek() {
    if (size == 0) throw new ArrayIndexOutOfBoundsException();
    return values[0];
  }

  public int poll() {
    if (size == 0) throw new ArrayIndexOutOfBoundsException();
    int res = values[0];
    int value = values[--size];
    int half = size >> 1;
    int i = 0;
    while (i < half) {
      int child = (i << 1) | 1;
      int childValue = values[child];
      int right = child + 1;
      if (right < size && comparator.compare(childValue, values[right]) > 0) {
        child = right;
        childValue = values[child];
      }
      if (comparator.compare(value, childValue) <= 0) break;
      values[i] = childValue;
      i = child;
    }
    values[i] = value;
    return res;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < size; ++i) {
      if (i > 0) sb.append(',').append(' ');
      sb.append(values[i]);
    }
    return sb.append(']').toString();
  }
}
