package template.io;

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

  public String nextLine() {
    StringBuilder res = new StringBuilder();
    for (int b = nextChar(); !isEndOfLineChar(b); b = nextChar()) {
      res.appendCodePoint(b);
    }
    return res.toString();
  }

  public int nextInt() {
    int c = nextNonSpaceChar();
    boolean positive = true;
    if (c == '-') {
      positive = false;
      c = nextChar();
    }
    int res = 0;
    do {
      if (c < '0' || '9' < c) throw new RuntimeException();
      res = res * 10 + c - '0';
      c = nextChar();
    } while (!isSpaceChar(c));
    return positive ? res : -res;
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
      res = res * 10 + c - '0';
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

  public int nextNonSpaceChar() {
    int res = nextChar();
    for ( ; isSpaceChar(res) || res < 0; res = nextChar()) ;
    return res;
  }

  public int nextChar() {
    if (numberOfChars == -1) {
      throw new RuntimeException();
    }
    if (currentPosition >= numberOfChars) {
      currentPosition = 0;
      try {
        numberOfChars = stream.read(buffer);
      } catch (Exception e) {
        throw new RuntimeException(e);
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
}
