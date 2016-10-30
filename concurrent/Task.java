package template.concurrent;

import template.io.QuickScanner;
import template.io.QuickWriter;

public interface Task {

  void input(QuickScanner in, int taskIdx);

  void process(int taskIdx);

  void output(QuickWriter out, int taskIdx);
}
