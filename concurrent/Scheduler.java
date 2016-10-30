package template.concurrent;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler {

  private static final long DELAY_TIME_MILLIS = 10;

  private final AtomicInteger remainingTests;
  private final AtomicInteger remainingThreads;

  public Scheduler(QuickScanner in, QuickWriter out, TaskFactory factory, int threadNumber) {
    try {
      int taskNumber = in.nextInt();
      remainingTests = new AtomicInteger(taskNumber);
      remainingThreads = new AtomicInteger(threadNumber);
      Task[] tasks = new Task[taskNumber];
      for (int i = 0; i < taskNumber; ++i) {
        tasks[i] = factory.createTask();
      }
      for (int i = 0; i < taskNumber; ++i) {
        Task task = tasks[i];
        final int taskIdx = i + 1;
        task.input(in, taskIdx);
        new Thread(() -> {
          boolean freeThread = false;
          synchronized (this) {
            do {
              try {
                wait(DELAY_TIME_MILLIS);
              } catch (InterruptedException ex) {}
              if (remainingThreads.get() != 0) {
                synchronized (remainingThreads) {
                  if (remainingThreads.get() != 0) {
                    remainingThreads.decrementAndGet();
                    freeThread = true;
                  }
                }
              }
            } while (!freeThread);
          }
          task.process(taskIdx);
          System.err.println(remainingTests.decrementAndGet());
          remainingThreads.incrementAndGet();
        }).start();
      }
      synchronized (this) {
        while (remainingTests.get() > 0) {
          wait(DELAY_TIME_MILLIS);
        }
      }
      for (int i = 0; i < taskNumber; ++i) {
        tasks[i].output(out, i + 1);
      }
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }
  }
}
