package template.collections.partitiontree;

import template.numbertheory.number.IntUtils;

import java.util.Arrays;

public class IntPartitionTree {

  private int n;
  private int[] sortedValues;
  private int[][] values;
  private int[][] goLeftSum;

  public void init(int[] values) {
    ensureCapacity(values.length);
    n = values.length;
    for (int i = 0; i < n; ++i) {
      sortedValues[i] = values[i];
      this.values[0][i] = values[i];
    }
    Arrays.sort(sortedValues, 0, n);
    build(0, 0, n);
  }

  /** Counts the number of elements in [{@code fromIdx}, {@code toIdx}) which are less than {@code value}. */
  public int calcLess(int fromIdx, int toIdx, int value) {
    int res = 0;
    int depth = 0, lower = 0, upper = n;
    for ( ; lower + 1 < upper && fromIdx < toIdx; ++depth) {
      int medium = (lower + upper) >> 1;
      int subGoLeftCnt = fromIdx == lower ? 0 : goLeftSum[depth][fromIdx - 1];
      int totGoLeftCnt = goLeftSum[depth][toIdx - 1];
      if (value <= sortedValues[medium]) {
        fromIdx = lower + subGoLeftCnt;
        toIdx = lower + totGoLeftCnt;
        upper = medium;
      } else {
        fromIdx += medium - lower - subGoLeftCnt;
        toIdx += medium - lower - totGoLeftCnt;
        lower = medium;
        res += totGoLeftCnt - subGoLeftCnt;
      }
    }
    if (fromIdx < toIdx && values[depth][lower] < value) ++res;
    return res;
  }

  private void build(int depth, int fromIdx, int toIdx) {
    if (fromIdx + 1 >= toIdx) return;
    int mediumIdx = (fromIdx + toIdx) >> 1, mediumValue = sortedValues[mediumIdx];
    Arrays.fill(goLeftSum[depth], fromIdx, toIdx, 0);
    int leftCnt = mediumIdx - fromIdx;
    for (int i = fromIdx; i < toIdx; ++i) if (values[depth][i] < mediumValue) {
      goLeftSum[depth][i] = 1;
      --leftCnt;
    }
    if (leftCnt > 0) {
      for (int i = fromIdx; i < toIdx; ++i) if (values[depth][i] == mediumValue) {
        goLeftSum[depth][i] = 1;
        --leftCnt;
        if (leftCnt == 0) break;
      }
    }
    int leftPnt = fromIdx, rightPnt = mediumIdx;
    for (int i = fromIdx; i < toIdx; ++i) {
      int j = goLeftSum[depth][i] > 0 ? leftPnt++ : rightPnt++;
      values[depth + 1][j] = values[depth][i];
      if (i > fromIdx) goLeftSum[depth][i] += goLeftSum[depth][i - 1];
    }
    if (fromIdx < mediumIdx) {
      build(depth + 1, fromIdx, mediumIdx);
      build(depth + 1, mediumIdx, toIdx);
    }
  }

  private void ensureCapacity(int size) {
    if (sortedValues != null && sortedValues.length >= size) return;
    int size2 = IntUtils.nextPow2(size);
    int bit = Integer.numberOfTrailingZeros(size2);
    sortedValues = new int[size2];
    values = new int[bit + 1][size2];
    goLeftSum = new int[bit + 1][size2];
    for (int i = 0; i <= bit; ++i) {
      values[i] = new int[size2];
      goLeftSum[i] = new int[size2];
    }
  }
}
