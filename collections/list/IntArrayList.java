package template.collections.list;

import template.array.IntArrayUtils;
import template.collections.IntCollection;
import template.io.Displayable;
import template.numbertheory.number.IntUtils;

import java.util.Collection;
import java.util.Iterator;

public class IntArrayList implements Displayable, IntCollection, Iterable<Integer> {

  private static final int[] EMPTY = {};

  public int[] values;
  public int size;

  public IntArrayList() {
    values = EMPTY;
    clear();
  }

  public IntArrayList(int capacity) {
    values = new int[IntUtils.nextPow2(capacity)];
    clear();
  }

  public IntArrayList(Collection<Integer> collection) {
    this(collection.size());
    addAll(collection);
  }

  public void initRange(int start, int end) {
    clear();
    for (int i = start; i < end; ++i) {
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
  public void add(int value) {
    ensureCapacity(size + 1);
    addInternal(value);
  }

  @Override
  public void addAll(int[] values) {
    addAll(values, 0, values.length);
  }

  @Override
  public void addAll(Collection<Integer> values) {
    ensureCapacity(size + values.size());
    for (int value : values) {
      addInternal(value);
    }
  }

  public void addAll(IntArrayList values) {
    ensureCapacity(size + values.size);
    for (int i = 0; i < values.size; ++i) {
      addInternal(values.get(i));
    }
  }

  public void addAll(int[] values, int lower, int upper) {
    ensureCapacity(size + upper - lower);
    for (int i = lower; i < upper; ++i) {
      addInternal(values[i]);
    }
  }

  public int peekLast() {
    return values[size - 1];
  }

  public int pollLast() {
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

  public int get(int idx) {
    return values[fixIdx(idx)];
  }

  public void set(int idx, int value) {
    values[fixIdx(idx)] = value;
  }

  public void swap(int x, int y) {
    if (x >= size || y >= size) {
      throw new ArrayIndexOutOfBoundsException();
    }
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

  public void sortAndUnique(IntArrayList cnt) {
    sort();
    unique(cnt);
  }

  public int lowerBound(int value) {
    return IntArrayUtils.lowerBound(values, 0, size, value);
  }

  public int upperBound(int value) {
    return IntArrayUtils.upperBound(values, 0, size, value);
  }

  public boolean binarySearch(int value) {
    int idx = lowerBound(value);
    return idx < size && get(idx) == value;
  }

  public int find(int value) {
    return IntArrayUtils.find(values, 0, size, value);
  }

  public void reverse() {
    IntArrayUtils.reverse(values, 0, size);
  }

  public boolean isPalindrome() {
    return IntArrayUtils.isPalindrome(values, 0, size);
  }

  public void shuffle() {
    IntArrayUtils.shuffle(values, 0, size);
  }

  public int kth(int kth) {
    return IntArrayUtils.kth(values, 0, size, kth);
  }

  public void update(int delta) {
    IntArrayUtils.update(values, 0, size, delta);
  }

  public boolean nextPermutation() {
    return IntArrayUtils.nextPermutation(values, 0, size);
  }

  public void ensureCapacity(int capacity) {
    if (capacity <= values.length) return;
    int[] newValues = new int[IntUtils.nextPow2(capacity)];
    for (int i = 0; i < size; ++i) {
      newValues[i] = values[i];
    }
    values = newValues;
  }

  @Override
  public String toDisplay() {
    return IntArrayUtils.toDisplay(values, 0, size);
  }

  @Override
  public Iterator<Integer> iterator() {
    return new Iterator<Integer>() {
      private int i = 0;

      @Override
      public boolean hasNext() {
        return i < size;
      }

      @Override
      public Integer next() {
        return values[i++];
      }
    };
  }

  private void addInternal(int value) {
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
