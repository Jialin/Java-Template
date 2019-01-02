package template.collections.list;

import template.array.LongArrayUtils;
import template.collections.LongCollection;
import template.io.Displayable;
import template.numbertheory.number.IntUtils;

import java.util.Collection;
import java.util.Iterator;

public class LongArrayList implements Displayable, LongCollection, Iterable<Long> {

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

  public void initRange(long start, long end) {
    clear();
    for (long i = start; i < end; ++i) {
      add(i);
    }
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
    addAll(values, 0, values.length);
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

  public void addAll(long[] values, int lower, int upper) {
    ensureCapacity(size + upper - lower);
    for (int i = lower; i < upper; ++i) {
      addInternal(values[i]);
    }
  }

  public long peekLast() {
    return values[size - 1];
  }

  public long pollLast() {
    return values[--size];
  }

  public void remove(int idx) {
    if (size == 0) {
      throw new ArrayIndexOutOfBoundsException();
    }
    idx = fixIdx(idx);
    for (int i = idx, j = i + 1; j < size; ++i, ++j) {
      values[i] = values[j];
    }
    --size;
  }

  public long get(int idx) {
    return values[fixIdx(idx)];
  }

  public void set(int idx, long value) {
    values[fixIdx(idx)] = value;
  }

  public void swap(int x, int y) {
    if (x >= size || y >= size) {
      throw new ArrayIndexOutOfBoundsException();
    }
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

  public boolean isPalindrome() {
    return LongArrayUtils.isPalindrome(values, 0, size);
  }

  public void shuffle() {
    LongArrayUtils.shuffle(values, 0, size);
  }

  public long kth(int kth) {
    return LongArrayUtils.kth(values, 0, size, kth);
  }

  public void update(long delta) {
    LongArrayUtils.update(values, 0, size, delta);
  }

  public boolean nextPermutation() {
    return LongArrayUtils.nextPermutation(values, 0, size);
  }

  public void ensureCapacity(int capacity) {
    if (capacity <= values.length) return;
    long[] newValues = new long[IntUtils.nextPow2(capacity)];
    for (int i = 0; i < size; ++i) {
      newValues[i] = values[i];
    }
    values = newValues;
  }

  @Override
  public String toDisplay() {
    return LongArrayUtils.toDisplay(values, 0, size);
  }

  @Override
  public Iterator<Long> iterator() {
    return new Iterator<Long>() {
      private int i = 0;

      @Override
      public boolean hasNext() {
        return i < size;
      }

      @Override
      public Long next() {
        return values[i++];
      }
    };
  }

  private void addInternal(long value) {
    values[size++] = value;
  }

  private int fixIdx(int idx) {
    if (size == 0) {
      throw new ArrayIndexOutOfBoundsException();
    }
    if (idx >= size) {
      return idx % size;
    } else if (idx < 0) {
      idx %= size;
      return idx < 0 ? idx + size : idx;
    }
    return idx;
  }
}
