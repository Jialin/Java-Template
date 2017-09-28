package template.collections.list;

import template.array.CharArrayUtils;
import template.collections.CharCollection;
import template.io.Displayable;
import template.numbertheory.number.IntUtils;

import java.util.Collection;
import java.util.Iterator;

public class CharArrayList implements Displayable, CharCollection, Iterable<Character> {

  private static final char[] EMPTY = {};

  public char[] values;
  public int size;

  public CharArrayList() {
    values = EMPTY;
    clear();
  }

  public CharArrayList(int capacity) {
    values = new char[IntUtils.nextPow2(capacity)];
    clear();
  }

  public CharArrayList(Collection<Character> collection) {
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
  public void add(char value) {
    ensureCapacity(size + 1);
    addInternal(value);
  }

  @Override
  public void addAll(char[] values) {
    addAll(values, 0, values.length);
  }

  @Override
  public void addAll(Collection<Character> values) {
    ensureCapacity(size + values.size());
    for (char value : values) {
      addInternal(value);
    }
  }

  public void addAll(CharArrayList values) {
    ensureCapacity(size + values.size);
    for (int i = 0; i < values.size; ++i) {
      addInternal(values.get(i));
    }
  }

  public void addAll(char[] values, int lower, int upper) {
    ensureCapacity(size + upper - lower);
    for (int i = lower; i < upper; ++i) {
      addInternal(values[i]);
    }
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

  public void sortAndUnique(IntArrayList cnt) {
    sort();
    unique(cnt);
  }

  public int lowerBound(char value) {
    return CharArrayUtils.lowerBound(values, 0, size, value);
  }

  public int upperBound(char value) {
    return CharArrayUtils.upperBound(values, 0, size, value);
  }

  public boolean binarySearch(char value) {
    int idx = lowerBound(value);
    return idx < size && get(idx) == value;
  }

  public int find(char value) {
    return CharArrayUtils.find(values, 0, size, value);
  }

  public void reverse() {
    CharArrayUtils.reverse(values, 0, size);
  }

  public void shuffle() {
    CharArrayUtils.shuffle(values, 0, size);
  }

  public char kth(int kth) {
    return CharArrayUtils.kth(values, 0, size, kth);
  }

  public void update(char delta) {
    CharArrayUtils.update(values, 0, size, delta);
  }

  public void ensureCapacity(int capacity) {
    if (capacity <= values.length) return;
    char[] newValues = new char[IntUtils.nextPow2(capacity)];
    for (int i = 0; i < size; ++i) {
      newValues[i] = values[i];
    }
    values = newValues;
  }

  @Override
  public String toDisplay() {
    return CharArrayUtils.toDisplay(values, 0, size);
  }

  @Override
  public Iterator<Character> iterator() {
    return new Iterator<Character>() {
      private int i = 0;

      @Override
      public boolean hasNext() {
        return i < size;
      }

      @Override
      public Character next() {
        return values[i++];
      }
    };
  }

  private void addInternal(char value) {
    values[size++] = value;
  }
}
