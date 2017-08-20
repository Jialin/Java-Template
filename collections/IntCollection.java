package template.collections;

import java.util.Collection;

public interface IntCollection {
  void clear();
  int size();
  void add(int value);

  default boolean isEmpty() {
    return size() == 0;
  }

  default boolean isNotEmpty() {
    return size() > 0;
  }

  default void addAll(int[] values) {
    for (int value : values) {
      add(value);
    }
  }

  default void addAll(Collection<Integer> values) {
    for (int value : values) {
      add(value);
    }
  }
}
