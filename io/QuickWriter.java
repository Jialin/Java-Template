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
    boolean printSpace = false;
    for (Object object : objects) {
      if (printSpace) {
        writer.print(' ');
      } else {
        printSpace = true;
      }
      writer.print(object);
    }
  }

  public void println(Object... objects) {
    print(objects);
    writer.print('\n');
  }

  public void printf(String format, Object... objects) {
    writer.printf(format, objects);
  }

  public void close() {
    writer.close();
  }
}
