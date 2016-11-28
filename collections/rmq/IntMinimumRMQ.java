package template.collections.rmq;

public class IntMinimumRMQ extends AbstractRMQ {

  private int[][] rmq;
  private int[][] rmqIdx;

  private int[] initValues;
  private int resIdx;

  public IntMinimumRMQ(int capacity) {
    super(capacity);
  }

  @Override
  public void createArrays(int capacityHighBit) {
    rmq = new int[capacityHighBit][];
    rmqIdx = new int[capacityHighBit][];
  }

  @Override
  public void createArray(int bit, int capacity) {
    rmq[bit] = new int[capacity];
    rmqIdx[bit] = new int[capacity];
  }

  @Override
  public void initSubclass(int n) {
    for (int i = 0; i < n; ++i) {
      rmq[0][i] = initValues[i];
      rmqIdx[0][i] = i;
    }
  }

  @Override
  public void initMerge(int targetBit, int targetIdx, int sourceBit, int sourceIdx1, int sourceIdx2) {
    if (rmq[sourceBit][sourceIdx1] <= rmq[sourceBit][sourceIdx2]) {
      rmq[targetBit][targetIdx] = rmq[sourceBit][sourceIdx1];
      rmqIdx[targetBit][targetIdx] = rmqIdx[sourceBit][sourceIdx1];
    } else {
      rmq[targetBit][targetIdx] = rmq[sourceBit][sourceIdx2];
      rmqIdx[targetBit][targetIdx] = rmqIdx[sourceBit][sourceIdx2];
    }
  }

  @Override
  public void calcMerge(int bit, int x, int y) {
    resIdx = rmq[bit][x] <= rmq[bit][y] ? rmqIdx[bit][x] : rmqIdx[bit][y];
  }

  public void init(int n, int[] initValues) {
    this.initValues = initValues;
    initInternal(n);
  }

  public int calc(int fromIdx, int toIdx) {
    return rmq[0][calcIdx(fromIdx, toIdx)];
  }

  public int calcIdx(int fromIdx, int toIdx) {
    calcInternal(fromIdx, toIdx);
    return resIdx;
  }
}
