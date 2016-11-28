package template.collections.rmq;

public abstract class AbstractRMQ {

  /** Abstract methods to allocate memory. */
  public abstract void createArrays(int capacityHighBit);
  public abstract void createArray(int bit, int capacity);

  /** Abstract methods to initialize. */
  public abstract void initSubclass(int n);
  public abstract void initMerge(int targetBit, int targetIdx, int sourceBit, int sourceIdx1, int sourceIdx2);

  /** Abstract methods to calculate. */
  public abstract void calcMerge(int bit, int x, int y);

  public AbstractRMQ(int capacity) {
    int highBit = 32 - Integer.numberOfLeadingZeros(capacity);
    createArrays(highBit);
    for (int bit = 0; bit < highBit; ++bit) {
      createArray(bit, capacity - (1 << bit) + 1);
    }
  }

  protected void initInternal(int n) {
    initSubclass(n);
    for (int bitI = 1; 1 << bitI <= n; ++bitI) {
      for (int i = n - (1 << bitI), j = i + (1 << (bitI - 1)); i >= 0; --i, --j) {
        initMerge(bitI, i, bitI - 1, i, j);
      }
    }
  }

  protected void calcInternal(int fromIdx, int toIdx) {
    int bit = 31 - Integer.numberOfLeadingZeros(toIdx - fromIdx);
    calcMerge(bit, fromIdx, toIdx - (1 << bit));
  }
}
