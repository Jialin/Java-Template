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
    for (Object object : objects) {
      writer.print(object);
    }
  }

  public void println(Object... objects) {
    print(objects);
    writer.print('\n');
  }

  public void close() {
    writer.close();
  }
}
