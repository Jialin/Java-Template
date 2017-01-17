package template.string;

/**
 * TODO: Verify needed
 */
public class CharKMP {

  public int[] fail;

  public CharKMP(int capacity) {
    fail = new int[capacity];
  }

  public void init(char[] pattern) {
    init(pattern, 0, pattern.length);
  }

  public void init(char[] pattern, int fromIdx, int toIdx) {
    ensureCapacity(toIdx - fromIdx);
    fail[0] = -1;
    for (int i = 1, j = fromIdx + 1; j < toIdx; ++i, ++j) {
      int pos;
      for (pos = fail[i - 1]; pos >= 0 && pattern[fromIdx + pos + 1] != pattern[j]; pos = fail[pos]) {}
      fail[i] = pattern[fromIdx + pos + 1] == pattern[j] ? pos + 1 : -1;
    }
  }

  private void ensureCapacity(int capacity) {
    if (fail.length >= capacity) return;
    fail = new int[Integer.highestOneBit(capacity) << 1];
  }
}
