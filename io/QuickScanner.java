package template.io;

import template.collections.list.IntArrayList;

import java.io.InputStream;

public class QuickScanner {

  private static final int BUFFER_SIZE = 1024;

  private InputStream stream;
  private byte[] buffer;
  private int currentPosition;
  private int numberOfChars;

  public QuickScanner(InputStream stream) {
    this.stream = stream;
    this.buffer = new byte[BUFFER_SIZE];
    this.currentPosition = 0;
    this.numberOfChars = 0;
  }

  public String next() {
    int b = nextNonSpaceChar();
    if (b < 0) {
      return "";
    }
    StringBuilder res = new StringBuilder();
    do {
      res.appendCodePoint(b);
      b = nextChar();
    } while (!isSpaceChar(b));
    return res.toString();
  }

  public int next(char[] s) {
    return next(s, 0);
  }

  public int next(char[] s, int startIdx) {
    int b = nextNonSpaceChar();
    int res = 0;
    if (b < 0) {
      return res;
    }
    do {
      s[startIdx++] = (char) b;
      b = nextChar();
      ++res;
    } while (!isSpaceChar(b));
    return res;
  }

  public String[] next(int n) {
    String[] res = new String[n];
    next(n, res);
    return res;
  }

  public void next(int n, String[] res) {
    for (int i = 0; i < n; ++i) {
      res[i] = next();
    }
  }

  public String nextLine(boolean ignoreEmptyLines) {
    if (ignoreEmptyLines) {
      while (true) {
        String res = nextLineInternal();
        if (!res.isEmpty()) {
          return res;
        }
      }
    } else {
      return nextLineInternal();
    }
  }

  public int nextLine(char[] s, boolean ignoreEmptyLines) {
    int res;
    if (ignoreEmptyLines) {
      do {
        res = nextLineInternal(s);
      } while (res == 0);
      return res;
    } else {
      return nextLineInternal(s);
    }
  }

  public int nextInt() {
    return nextIntInternal(nextNonSpaceChar());
  }

  public int nextIntOrDefault(int defaultValue) {
    int c = nextNonSpaceChar();
    if (c < 0) {
      return defaultValue;
    }
    return nextIntInternal(c);
  }

  public int[] nextInt(int n) {
    int[] res = new int[n];
    nextInt(n, res);
    return res;
  }

  public void nextInt(int n, int[] res) {
    for (int i = 0; i < n; ++i) {
      res[i] = nextInt();
    }
  }

  public void nextInts(int n, int[]... res) {
    int m = res.length;
    for (int i = 0; i < n; ++i) for (int j = 0; j < m; ++j) {
      res[j][i] = nextInt();
    }
  }

  public void nextInt(int n, IntArrayList res) {
    for (int i = 0; i < n; ++i) {
      res.add(nextInt());
    }
  }

  public long nextLong() {
    int c = nextNonSpaceChar();
    boolean positive = true;
    if (c == '-') {
      positive = false;
      c = nextChar();
    }
    long res = 0;
    do {
      if (c < '0' || '9' < c) throw new RuntimeException();
      res = res * 10 + (c - '0');
      c = nextChar();
    } while (!isSpaceChar(c));
    return positive ? res : -res;
  }

  public long[] nextLong(int n) {
    long[] res = new long[n];
    nextLong(n, res);
    return res;
  }

  public void nextLong(int n, long[] res) {
    for (int i = 0; i < n; ++i) {
      res[i] = nextLong();
    }
  }

  public double nextDouble() {
    int c = nextNonSpaceChar();
    boolean positive = true;
    if (c == '-') {
      positive = false;
      c = nextChar();
    }
    double res = 0;
    for ( ; c != '.' && !isSpaceChar(c); c = nextChar()) {
      if (c < '0' || c > '9') {
        throw new IllegalArgumentException("Unexpected character. " + c);
      }
      res = res * 10 + (c - '0');
    }
    if (c == '.') {
      c = nextChar();
      double mul = 1;
      for ( ; !isSpaceChar(c); c = nextChar()) {
        if (c < '0' || c > '9') {
          throw new IllegalArgumentException("Unexpected character. " + c);
        }
        mul /= 10;
        if (c != '0') {
          res += (c - '0') * mul;
        }
      }
    }
    return positive ? res : -res;
  }

  public double[] nextDouble(int n) {
    double[] res = new double[n];
    nextDouble(n, res);
    return res;
  }

  public void nextDouble(int n, double[] res) {
    for (int i = 0; i < n; ++i) {
      res[i] = nextDouble();
    }
  }

  public int nextNonSpaceChar() {
    int res = nextChar();
    for ( ; res >= 0 && isSpaceChar(res); res = nextChar()) {}
    return res;
  }

  public int nextChar() {
    if (numberOfChars == -1) {
      return -1;
    }
    if (currentPosition >= numberOfChars) {
      currentPosition = 0;
      try {
        numberOfChars = stream.read(buffer);
      } catch (Exception e) {
        return -1;
      }
      if (numberOfChars <= 0) {
        return -1;
      }
    }
    return buffer[currentPosition++];
  }

  public boolean isSpaceChar(int c) {
    return c == ' ' || c == '\t' || isEndOfLineChar(c);
  }

  public boolean isEndOfLineChar(int c) {
    return c == '\n' || c == '\r' || c < 0;
  }

  private String nextLineInternal() {
    StringBuilder res = new StringBuilder();
    for (int b = nextChar(); !isEndOfLineChar(b); b = nextChar()) {
      res.appendCodePoint(b);
    }
    return res.toString();
  }

  private int nextIntInternal(int c) {
    boolean positive = true;
    if (c == '-') {
      positive = false;
      c = nextChar();
    }
    int res = 0;
    do {
      if (c < '0' || '9' < c) throw new RuntimeException();
      res = res * 10 + (c - '0');
      c = nextChar();
    } while (!isSpaceChar(c));
    return positive ? res : -res;
  }

  private int nextLineInternal(char[] s) {
    int res = 0;
    for (int b = nextChar(); !isEndOfLineChar(b); b = nextChar()) {
      s[res++] = (char) b;
    }
    return res;
  }
}
