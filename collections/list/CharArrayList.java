package template.collections.list;

import template.array.CharArrayUtils;
import template.collections.CharCollection;

public class CharArrayList implements CharCollection {

  private static final char[] EMPTY = {};

  public char[] values;
  public int size;

  public CharArrayList() {
    values = EMPTY;
    clear();
  }

  public CharArrayList(int capacity) {
    values = new char[Integer.highestOneBit(capacity) << 1];
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
  public void add(char value) {
    ensureCapacity(size + 1);
    values[size++] = value;
  }

  public char peekLast() {
    return values[size - 1];
  }

  public char pollLast() {
    return values[--size];
  }

  public char get(int idx) {
    if (idx >= size) throw new ArrayIndexOutOfBoundsException();
    return values[idx];
  }

  public void set(int idx, char value) {
    if (idx >= size) throw new ArrayIndexOutOfBoundsException();
    values[idx] = value;
  }

  public void swap(int x, int y) {
    if (x >= size || y >= size) throw new ArrayIndexOutOfBoundsException();
    CharArrayUtils.swap(values, x, y);
  }

  public void sort() {
    CharArrayUtils.sort(values, 0, size);
  }

  public void unique() {
    size = CharArrayUtils.unique(values, 0, size);
  }

  public void unique(IntArrayList cnt) {
    cnt.clear();
    cnt.ensureCapacity(size);
    int newSize = CharArrayUtils.unique(values, 0, size, cnt.values, 0);
    size = cnt.size = newSize;
  }

  public void sortAndUnique() {
    sort();
    unique();
  }

  public int lowerBound(char value) {
    return CharArrayUtils.lowerBound(values, 0, size, value);
  }

  public int upperBound(char value) {
    return CharArrayUtils.upperBound(values, 0, size, value);
  }

  public void reverse() {
    CharArrayUtils.reverse(values, 0, size);
  }

  public void shuffle() {
    CharArrayUtils.shuffle(values, 0, size);
  }

  @Override
  public String toString() {
    return CharArrayUtils.toString(values, 0, size);
  }

  public void ensureCapacity(int capacity) {
    if (capacity <= values.length) return;
    char[] newValues = new char[Integer.highestOneBit(capacity) << 1];
    for (int i = 0; i < size; ++i) {
      newValues[i] = values[i];
    }
    values = newValues;
  }
}
