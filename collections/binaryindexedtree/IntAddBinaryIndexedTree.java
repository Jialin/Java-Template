package template.collections.binaryindexedtree;

public class IntAddBinaryIndexedTree extends AbstractIntBinaryIndexedTree {

  public IntAddBinaryIndexedTree(int capacity) {
    super(capacity);
    init(capacity);
  }

  @Override
  public int merge(int x, int y) {
    return x + y;
  }

  @Override
  public void init(int n, int initValue) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int calc(int idx, int initValue) {
    throw new UnsupportedOperationException();
  }

  public void init(int n) {
    super.init(n, 0);
  }

  public int calc(int idx) {
    return super.calc(idx, 0);
  }

  public int calcRange(int lower, int upper) {
    if (lower >= upper) return 0;
    return calc(upper - 1) - (lower > 0 ? calc(lower - 1) : 0);
  }
}
