package template.collections.dancinglink;

import template.numbertheory.number.IntUtils;

import java.util.Arrays;
import java.util.function.IntConsumer;

public class DancingLink {

  private boolean[] covered;

  public int first;
  public int[] prev, next;

  public DancingLink() {}

  public DancingLink(int capacity) {
    this(capacity, true);
  }

  public DancingLink(int capacity, boolean initialize) {
    ensureCapacity(capacity);
    if (initialize) init(capacity);
  }

  public void init(int n) {
    ensureCapacity(n);
    Arrays.fill(covered, 0, n, false);
    for (int i = 0; i < n; ++i) {
      prev[i] = i - 1;
      next[i] = i + 1;
    }
    next[n - 1] = -1;
    first = 0;
  }

  public boolean cover(int idx) {
    if (covered[idx]) return false;
    covered[idx] = true;
    int prevIdx = prev[idx], nextIdx = next[idx];
    if (prevIdx >= 0) {
      next[prevIdx] = nextIdx;
    } else {
      first = nextIdx;
    }
    if (nextIdx >= 0) {
      prev[nextIdx] = prevIdx;
    }
    return true;
  }

  public boolean isCovered(int idx) {
    return covered[idx];
  }

  // TODO: verify
  public void uncover(int idx) {
    int prevIdx = prev[idx], nextIdx = next[idx];
    if (prevIdx >= 0) {
      next[prevIdx] = idx;
    } else {
      first = idx;
    }
    if (nextIdx >= 0) {
      prev[nextIdx] = idx;
    }
  }

  public void forEach(IntConsumer consumer) {
    for (int x = first; x >= 0; x = next[x]) {
      consumer.accept(x);
    }
  }

  private void ensureCapacity(int capacity) {
    if (prev != null && prev.length >= capacity) return;
    int size = IntUtils.nextPow2(capacity);
    prev = new int[size];
    next = new int[size];
    covered = new boolean[size];
  }
}
