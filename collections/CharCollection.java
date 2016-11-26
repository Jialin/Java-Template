package template.collections;

public interface CharCollection {
  void clear();
  int size();
  void add(char value);

  default boolean isEmpty() {
    return size() == 0;
  }

  default void addAll(char[] values) {
    for (char value : values) {
      add(value);
    }
  }
}
