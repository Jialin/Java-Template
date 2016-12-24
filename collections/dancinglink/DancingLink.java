package template.collections.dancinglink;

public class DancingLink {

  public int first;
  public int[] prev, next;

  public DancingLink(int capacity) {
    prev = new int[capacity];
    next = new int[capacity];
    init(capacity);
  }

  public void init(int n) {
    for (int i = 0; i < n; ++i) {
      prev[i] = i - 1;
      next[i] = i + 1;
    }
    next[n - 1] = -1;
    first = 0;
  }

  public void cover(int idx) {
    int prevIdx = prev[idx], nextIdx = next[idx];
    if (prevIdx >= 0) {
      next[prevIdx] = nextIdx;
    } else {
      first = nextIdx;
    }
    if (nextIdx >= 0) {
      prev[nextIdx] = prevIdx;
    }
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
}
