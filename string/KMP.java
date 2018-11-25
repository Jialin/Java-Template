package template.string;

import template.numbertheory.number.IntUtils;

public class KMP {

  public int[] fail;

  private char[] pattern;
  private int offset;
  private int length;

  public KMP(int capacity) {
    fail = new int[capacity];
  }

  public void initPattern(char[] pattern) {
    initPattern(pattern, 0, pattern.length);
  }

  public void initPattern(char[] pattern, int fromIdx, int toIdx) {
    this.pattern = pattern;
    offset = fromIdx;
    length = toIdx - fromIdx;
    ensureCapacity(length);
    fail[0] = -1;
    for (int i = 1; i < length; ++i) {
      fail[i] = calcNextPosition(fail[i - 1], pattern[offset + i]);
    }
  }

  public int calcNextPosition(int pos, char c) {
    for (pos = pos == length ? fail[pos] : pos; pos >= 0 && pattern[offset + pos + 1] != c; pos = fail[pos]) {}
    return pattern[offset + pos + 1] == c ? pos + 1 : -1;
  }

  private void ensureCapacity(int length) {
    if (fail.length < length) {
      fail = new int[IntUtils.nextPow2(length)];
    }
  }
}
