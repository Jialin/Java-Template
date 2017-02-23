package template.collections.list;

import template.array.LongArrayUtils;
import template.collections.LongCollection;
import template.numbertheory.number.IntUtils;

import java.util.Collection;

public class LongArrayList implements LongCollection {

  private static final long[] EMPTY = {};

  public long[] values;
  public int size;

  public LongArrayList() {
    values = EMPTY;
    clear();
  }

  public LongArrayList(int capacity) {
    values = new long[IntUtils.nextPow2(capacity)];
    clear();
  }

  public LongArrayList(Collection<Long> collection) {
    this(collection.size());
    addAll(collection);
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
  public void add(long value) {
    ensureCapacity(size + 1);
    addInternal(value);
  }

  @Override
  public void addAll(long[] values) {
    ensureCapacity(size + values.length);
    for (long value : values) {
      addInternal(value);
    }
  }

  @Override
  public void addAll(Collection<Long> values) {
    ensureCapacity(size + values.size());
    for (long value : values) {
      addInternal(value);
    }
  }

  public void addAll(LongArrayList values) {
    ensureCapacity(size + values.size);
    for (int i = 0; i < values.size; ++i) {
      addInternal(values.get(i));
    }
  }

  public long peekLast() {
    return values[size - 1];
  }

  public long pollLast() {
    return values[--size];
  }

  public long get(int idx) {
    if (idx >= size) throw new ArrayIndexOutOfBoundsException();
    return values[idx];
  }

  public void set(int idx, long value) {
    if (idx >= size) throw new ArrayIndexOutOfBoundsException();
    values[idx] = value;
  }

  public void swap(int x, int y) {
    if (x >= size || y >= size) throw new ArrayIndexOutOfBoundsException();
    LongArrayUtils.swap(values, x, y);
  }

  public void sort() {
    LongArrayUtils.sort(values, 0, size);
  }

  public void unique() {
    size = LongArrayUtils.unique(values, 0, size);
  }

  public void unique(IntArrayList cnt) {
    cnt.clear();
    cnt.ensureCapacity(size);
    int newSize = LongArrayUtils.unique(values, 0, size, cnt.values, 0);
    size = cnt.size = newSize;
  }

  public void sortAndUnique() {
    sort();
    unique();
  }

  public void sortAndUnique(IntArrayList cnt) {
    sort();
    unique(cnt);
  }

  public int lowerBound(long value) {
    return LongArrayUtils.lowerBound(values, 0, size, value);
  }

  public int upperBound(long value) {
    return LongArrayUtils.upperBound(values, 0, size, value);
  }

  public boolean binarySearch(long value) {
    int idx = lowerBound(value);
    return idx < size && get(idx) == value;
  }

  public int find(long value) {
    return LongArrayUtils.find(values, 0, size, value);
  }

  public void reverse() {
    LongArrayUtils.reverse(values, 0, size);
  }

  public void shuffle() {
    LongArrayUtils.shuffle(values, 0, size);
  }

  public long kth(int kth) {
    return LongArrayUtils.kth(values, 0, size, kth);
  }

  @Override
  public String toString() {
    return LongArrayUtils.toString(values, 0, size);
  }

  public void ensureCapacity(int capacity) {
    if (capacity <= values.length) return;
    long[] newValues = new long[IntUtils.nextPow2(capacity)];
    for (int i = 0; i < size; ++i) {
      newValues[i] = values[i];
    }
    values = newValues;
  }

  private void addInternal(long value) {
    values[size++] = value;
  }
}
