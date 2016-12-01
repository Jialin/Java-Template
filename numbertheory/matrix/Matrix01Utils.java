package template.numbertheory.matrix;

import java.util.BitSet;

public class Matrix01Utils {

  private static BitSet[] tmp;

  public static void inverse(int n, BitSet[] a, BitSet[] res) {
    if (a == res) {
      initInternal(n);
    }
    BitSet[] tmp = a == res ? Matrix01Utils.tmp : res;
    for (int i = 0; i < n; ++i) {
      tmp[i].clear();
      tmp[i].set(i);
    }
    for (int column = 0; column < n; ++column) {
      if (!a[column].get(column)) {
        int rowIdx = -1;
        for (int row = column + 1; row < n; ++row) {
          if (a[row].get(column)) {
            rowIdx = row;
            break;
          }
        }
        if (rowIdx < 0) throw new IllegalArgumentException("Not invertible.");
        BitSet tmpBS;
        tmpBS = a[column];
        a[column] = a[rowIdx];
        a[rowIdx] = tmpBS;
        tmpBS = tmp[column];
        tmp[column] = tmp[rowIdx];
        tmp[rowIdx] = tmpBS;
      }
      for (int row = column + 1; row < n; ++row) if (a[row].get(column)) {
        a[row].xor(a[column]);
        tmp[row].xor(tmp[column]);
      }
    }
    for (int column = n - 1; column >= 0; --column) {
      for (int row = column - 1; row >= 0; --row) if (a[row].get(column)) {
        a[row].xor(a[column]);
        tmp[row].xor(tmp[column]);
      }
    }
    if (res != tmp) {
      for (int i = 0; i < n; ++i) {
        res[i] = tmp[i];
      }
    }
  }

  public static String toString(int n, BitSet[] a) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        sb.append(a[i].get(j) ? 1 : 0);
      }
      if (i + 1 != n) {
        sb.append('\n');
      }
    }
    return sb.toString();
  }

  private static void initInternal(int n) {
    if (tmp == null || tmp.length < n || tmp[0].size() < n) {
      int n2 = Integer.highestOneBit(n) << 1;
      tmp = new BitSet[n2];
      for (int i = 0; i < n2; ++i) {
        tmp[i] = new BitSet(n2);
      }
    }
  }
}
