package template.collections.rmq;

public class IntRangeMaximumQuery2D {

  private int[][][][] rmq;

  public IntRangeMaximumQuery2D(int nCapacity, int mCapacity) {
    int nHighBit = 32 - Integer.numberOfLeadingZeros(nCapacity);
    int mHighBit = 32 - Integer.numberOfLeadingZeros(mCapacity);
    rmq = new int[nHighBit][mHighBit][][];
    for (int i = 0; i < nHighBit; ++i) for (int j = 0; j < mHighBit; ++j) {
      rmq[i][j] = new int[nCapacity - (1 << i) + 1][mCapacity - (1 << j) + 1];
    }
  }

  public void init(int n, int m, int[][] value) {
    for (int bitI = 0; 1 << bitI <= n; ++bitI) {
      for (int bitJ = 0; 1 << bitJ <= m; ++bitJ) {
        for (int i = n - (1 << bitI); i >= 0; --i) for (int j = m - (1 << bitJ); j >= 0; --j) {
          if (bitI > 0) {
            rmq[bitI][bitJ][i][j] = Math.max(
                rmq[bitI - 1][bitJ][i][j],
                rmq[bitI - 1][bitJ][i + (1 << (bitI - 1))][j]);
          } else if (bitJ > 0) {
            rmq[bitI][bitJ][i][j] = Math.max(
                rmq[bitI][bitJ - 1][i][j],
                rmq[bitI][bitJ - 1][i][j + (1 << (bitJ - 1))]);
          } else {
            rmq[bitI][bitJ][i][j] = value[i][j];
          }
        }
      }
    }
  }

  public int calc(int x1, int y1, int x2, int y2) {
    int bitX = 31 - Integer.numberOfLeadingZeros(x2 - x1);
    int bitY = 31 - Integer.numberOfLeadingZeros(y2 - y1);
    return Math.max(Math.max(Math.max(
        rmq[bitX][bitY][x1][y1],
        rmq[bitX][bitY][x2 - (1 << bitX)][y1]),
        rmq[bitX][bitY][x1][y2 - (1 << bitY)]),
        rmq[bitX][bitY][x2 - (1 << bitX)][y2 - (1 << bitY)]);
  }
}
