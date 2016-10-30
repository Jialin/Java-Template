package template.io;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

public class QuickWriter {

  private final PrintWriter writer;

  public QuickWriter(OutputStream outputStream) {
    this.writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
  }

  public QuickWriter(Writer writer) {
    this.writer = new PrintWriter(writer);
  }

  public void print(Object... objects) {
    for (int i = 0; i < objects.length; ++i) {
      if (i > 0) {
        writer.print(' ');
      }
      writer.print(objects[i]);
    }
  }

  public void println(Object... objects) {
    print(objects);
    writer.print('\n');
  }

  public void print(int[] objects) {
    print(objects, ' ');
  }

  public void print(int[] objects, char delimiter) {
    for (int i = 0; i < objects.length; ++i) {
      if (i > 0) {
        writer.print(delimiter);
      }
      writer.print(objects[i]);
    }
  }

  public void println(int[] objects) {
    print(objects);
    writer.print('\n');
  }

  public void println(int[] objects, char delimiter) {
    print(objects, delimiter);
    writer.print('\n');
  }

  public void print(long[] objects) {
    print(objects, ' ');
  }

  public void print(long[] objects, char delimiter) {
    for (int i = 0; i < objects.length; ++i) {
      if (i > 0) {
        writer.print(delimiter);
      }
      writer.print(objects[i]);
    }
  }

  public void println(long[] objects) {
    print(objects);
    writer.print('\n');
  }

  public void println(long[] objects, char delimiter) {
    print(objects, delimiter);
    writer.print('\n');
  }

  public void print(char[] s) {
    print(s, 0);
  }

  public void print(char[] s, int startIdx) {
    for (int i = startIdx; i < s.length && s[i] > 0; ++i) {
      writer.print(s[i]);
    }
  }

  public void println(char[] s) {
    println(s, 0);
  }

  public void println(char[] s, int startIdx) {
    print(s, startIdx);
    writer.print('\n');
  }

  public void printf(String format, Object... objects) {
    writer.printf(format, objects);
  }

  public void close() {
    writer.close();
  }

  public void flush() {
    writer.flush();
  }
}
