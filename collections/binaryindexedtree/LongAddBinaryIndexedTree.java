package template.collections.binaryindexedtree;

public class LongAddBinaryIndexedTree extends AbstractLongBinaryIndexedTree {

  public LongAddBinaryIndexedTree(int capacity) {
    super(capacity);
    init(capacity);
  }

  @Override
  public long merge(long x, long y) {
    return x + y;
  }

  @Override
  public void init(int n, long initValue) {
    throw new UnsupportedOperationException();
  }

  @Override
  public long calc(int idx, long initValue) {
    throw new UnsupportedOperationException();
  }

  public void init(int n) {
    super.init(n, 0);
  }

  public long calc(int idx) {
    return super.calc(idx, 0);
  }

  public long calcRange(int lower, int upper) {
    if (lower >= upper) return 0;
    return calc(upper - 1) - (lower > 0 ? calc(lower - 1) : 0);
  }
}
