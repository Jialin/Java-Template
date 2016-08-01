package template.collections;

public interface LongCollection {
  void clear();
  int size();
  void add(long value);

  default boolean isEmpty() {
    return size() == 0;
  }

  default void addAll(long[] values) {
    for (long value : values) {
      add(value);
    }
  }
}
