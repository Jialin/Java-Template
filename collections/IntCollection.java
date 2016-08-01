package template.collections;

public interface IntCollection {
  void clear();
  int size();
  void add(int value);

  default boolean isEmpty() {
    return size() == 0;
  }

  default void addAll(int[] values) {
    for (int value : values) {
      add(value);
    }
  }
}
