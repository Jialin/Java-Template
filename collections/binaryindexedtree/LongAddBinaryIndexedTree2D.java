package template.collections.binaryindexedtree;

public class LongAddBinaryIndexedTree2D extends AbstractLongBinaryIndexedTree2D {

  public LongAddBinaryIndexedTree2D(int capacity1, int capacity2) {
    super(capacity1, capacity2);
    init(capacity1, capacity2);
  }

  @Override
  public long merge(long x, long y) {
    return x + y;
  }

  @Override
  public void init(int n, int m, long initValue) {
    throw new UnsupportedOperationException();
  }

  @Override
  public long calc(int x, int y, long initValue) {
    throw new UnsupportedOperationException();
  }

  public void init(int n, int m) {
    super.init(n, m, 0);
  }

  public long calc(int x, int y) {
    return super.calc(x, y, 0);
  }

  public long calcRange(int x1, int y1, int x2, int y2) {
    if (x1 >= x2 || y1 >= y2) return 0;
    long res = calc(x2 - 1, y2 - 1);
    if (x1 > 0) res -= calc(x1 - 1, y2 - 1);
    if (y1 > 0) res -= calc(x2 - 1, y1 - 1);
    if (x1 > 0 && y1 > 0) res += calc(x1 - 1, y1 - 1);
    return res;
  }
}
