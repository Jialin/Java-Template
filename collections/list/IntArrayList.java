package template.collections.list;

import template.array.IntArrayUtils;
import template.collections.IntCollection;

public class IntArrayList implements IntCollection {

  private static final int[] EMPTY = {};

  public int[] values;
  public int size;

  public IntArrayList() {
    values = EMPTY;
    clear();
  }

  public IntArrayList(int capacity) {
    values = new int[Integer.highestOneBit(capacity) << 1];
    clear();
  }

  @Override
  public void clear() {
    size = 0;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void add(int value) {
    ensureCapacity(size + 1);
    values[size++] = value;
  }

  public int peekLast() {
    return values[size - 1];
  }

  public int pollLast() {
    return values[--size];
  }

  public int get(int idx) {
    if (idx >= size) throw new ArrayIndexOutOfBoundsException();
    return values[idx];
  }

  public void set(int idx, int value) {
    if (idx >= size) throw new ArrayIndexOutOfBoundsException();
    values[idx] = value;
  }

  public void swap(int x, int y) {
    if (x >= size || y >= size) throw new ArrayIndexOutOfBoundsException();
    IntArrayUtils.swap(values, x, y);
  }

  public void sort() {
    IntArrayUtils.sort(values, 0, size);
  }

  public void unique() {
    size = IntArrayUtils.unique(values, 0, size);
  }

  public void unique(IntArrayList cnt) {
    cnt.clear();
    cnt.ensureCapacity(size);
    int newSize = IntArrayUtils.unique(values, 0, size, cnt.values, 0);
    size = cnt.size = newSize;
  }

  public void sortAndUnique() {
    sort();
    unique();
  }

  public int lowerBound(int value) {
    return IntArrayUtils.lowerBound(values, 0, size, value);
  }

  public int upperBound(int value) {
    return IntArrayUtils.upperBound(values, 0, size, value);
  }

  public void reverse() {
    IntArrayUtils.reverse(values, 0, size);
  }

  public void shuffle() {
    IntArrayUtils.shuffle(values, 0, size);
  }

  @Override
  public String toString() {
    return IntArrayUtils.toString(values, 0, size);
  }

  public void ensureCapacity(int capacity) {
    if (capacity <= values.length) return;
    int[] newValues = new int[Integer.highestOneBit(capacity) << 1];
    for (int i = 0; i < size; ++i) {
      newValues[i] = values[i];
    }
    values = newValues;
  }
}
