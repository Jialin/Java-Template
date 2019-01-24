package template.collections.dancinglink;

import template.io.Displayable;
import template.numbertheory.number.IntUtils;

import java.util.Arrays;
import java.util.function.IntConsumer;

public class DancingLink implements Displayable {

  private int n;
  private boolean[] covered;
  private int[] prev, next;

  public DancingLink(int capacity) {
    this(capacity, true);
  }

  public DancingLink(int capacity, boolean initialize) {
    ensureCapacity(capacity);
    if (initialize) {
      init(capacity);
    }
  }

  public void init(int n) {
    this.n = n;
    ensureCapacity(n);
    Arrays.fill(covered, 0, n, false);
    for (int i = 0; i < n + 2; ++i) {
      prev[i] = i - 1;
      next[i] = i + 1;
    }
  }

  public boolean cover(int idx) {
    if (covered[idx]) {
      return false;
    }
    covered[idx] = true;
    ++idx;
    int prevIdx = prev[idx];
    int nextIdx = next[idx];
    next[prevIdx] = nextIdx;
    prev[nextIdx] = prevIdx;
    return true;
  }

  public boolean covered(int idx) {
    return covered[idx];
  }

  public boolean uncover(int idx) {
    if (!covered[idx]) {
      return false;
    }
    covered[idx] = false;
    ++idx;
    int prevIdx = prev[idx];
    int nextIdx = next[idx];
    next[prevIdx] = idx;
    prev[nextIdx] = idx;
    return true;
  }

  public int first() {
    return next[0] - 1;
  }

  public int next(int idx) {
    return next[idx + 1] - 1;
  }

  // TODO(jouyang): to be verify
  public void forEach(IntConsumer consumer) {
    for (int x = first(); x < n; x = next(x)) {
      consumer.accept(x);
    }
  }

  private void ensureCapacity(int capacity) {
    if (prev != null && prev.length >= capacity + 2) {
      return;
    }
    int size = IntUtils.nextPow2(capacity + 2);
    prev = new int[size];
    next = new int[size];
    covered = new boolean[size];
  }

  @Override
  public String toDisplay() {
    final StringBuilder sb = new StringBuilder("[");
    forEach(x -> {
      if (x != first()) {
        sb.append(',');
      }
      sb.append(x);
    });
    return sb.append(']').toString();
  }
}
