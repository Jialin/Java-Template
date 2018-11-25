package template.string;

import template.numbertheory.number.IntUtils;

/**
 * Computes the starting index of the circular string which is lexicographically minimum.
 *
 * https://en.wikipedia.org/wiki/Lexicographically_minimal_string_rotation#Booth's_Algorithm
 */
public class LeastCircularShift {

  private int[] fail;

  public LeastCircularShift(int maxLength) {
    ensureCapacity(maxLength);
  }

  public int calc(String s) {
    int n = s.length();
    ensureCapacity(n);
    fail[0] = -1;
    int res = 0;
    int n2 = n << 1;
    for (int i = 1; i < n2; ++i) {
      char sj = charAt(s, i);
      int j = fail[i - res - 1];
      char c;
      while (true) {
        c = charAt(s, res + j + 1);
        if (j < 0 || sj == c) {
          break;
        }
        if (sj < c) {
          res = i - j - 1;
        }
        j = fail[j];
      }
      if (sj != c) {
        if (sj < charAt(s, res)) {
          res = i;
        }
        fail[i - res] = -1;
      } else {
        fail[i - res] = j + 1;
      }
    }
    return res;
  }

  private char charAt(String s, int i) {
    int n = s.length();
    return s.charAt(i < n ? i : i - n);
  }

  private void ensureCapacity(int length) {
    length <<= 1;
    if (fail == null || length > fail.length) {
      fail = new int[IntUtils.nextPow2(length)];
    }
  }
}
