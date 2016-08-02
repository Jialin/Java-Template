package template.io;

import java.io.InputStream;

public class QuickScanner {

  private static final int BUFFER_SIZE = 1024;

  private InputStream stream;
  private byte[] buffer;
  private int currentPostion;
  private int numberOfChars;

  public QuickScanner(InputStream stream) {
    this.stream = stream;
    this.buffer = new byte[BUFFER_SIZE];
    this.currentPostion = 0;
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
    for (int i = 0; i < n; ++i) {
      res[i] = nextInt();
    }
    return res;
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

  public int nextNonSpaceChar() {
    int res = nextChar();
    for ( ; isSpaceChar(res) || res < 0; res = nextChar()) ;
    return res;
  }

  public int nextChar() {
    if (numberOfChars == -1) {
      throw new RuntimeException();
    }
    if (currentPostion >= numberOfChars) {
      currentPostion = 0;
      try {
        numberOfChars = stream.read(buffer);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      if (numberOfChars <= 0) {
        return -1;
      }
    }
    return buffer[currentPostion++];
  }

  public boolean isSpaceChar(int c) {
    return c == ' '
        || c == '\n'
        || c == '\r'
        || c == '\t'
        || c < 0;
  }
}
