package template.collections.eertree;

/**
 * Calculates minimum odd / even palindrome factorization of all prefix.
 *
 * Paper: https://arxiv.org/pdf/1506.04862.pdf [Section 4 Factorizations into Palindromes]
 */
public class OddEvenFactorizationEertree extends AbstractEertree {

  private static final int INF = Integer.MAX_VALUE;

  /** {@code diff[u] = length[u] - length[link[u]]} */
  private int[] diff;
  /** The longest suffix-palindrome of {@code u} having the difference unequal to {@code diff[u]}. */
  private int[] seriesLink;

  /** Minimum odd palindrome factorization. If not found, assign to {@code INF}. */
  public int[] odd;
  /** Minimum even palindrome factorization. If not found, assign to {@code INF}. */
  public int[] even;

  /** Index of the aggregates value of {@code odd} and {@code even}. */
  private int[] seriesOddIdx;
  private int[] seriesEvenIdx;

  public OddEvenFactorizationEertree(int letterCapacity, int charCapacity) {
    super(letterCapacity, charCapacity, false);
  }

  public OddEvenFactorizationEertree(int letterCapacity, int charCapacity, boolean compress) {
    super(letterCapacity, charCapacity, compress, false);
  }

  @Override
  public void createSubclass(int letterCapacity, int nodeCapacity) {
    diff = new int[nodeCapacity];
    seriesLink = new int[nodeCapacity];
    odd = new int[nodeCapacity];
    even = new int[nodeCapacity];
    seriesOddIdx = new int[nodeCapacity];
    seriesEvenIdx = new int[nodeCapacity];
  }

  public void calc(int n, int[] values) {
    init(26);
    odd[0] = INF;
    even[0] = 0;
    for (int i = 1; i <= n; ++i) {
      if (append(values[i - 1])) {
        diff[nodeIdx] = length[nodeIdx] - length[link[nodeIdx]];
        seriesLink[nodeIdx] = diff[nodeIdx] == diff[link[nodeIdx]] ? seriesLink[link[nodeIdx]] : link[nodeIdx];
      }
      odd[i] = even[i] = INF;
      for (int idx = last; length[idx] > 0; idx = seriesLink[idx]) {
        seriesOddIdx[idx] = seriesEvenIdx[idx] = i - length[seriesLink[idx]] - diff[idx];
        if (seriesLink[idx] != link[idx]) {
          seriesOddIdx[idx] =
              odd[seriesOddIdx[idx]] <= odd[seriesOddIdx[link[idx]]] ? seriesOddIdx[idx] : seriesOddIdx[link[idx]];
          seriesEvenIdx[idx] =
              even[seriesEvenIdx[idx]] <= even[seriesEvenIdx[link[idx]]]
                  ? seriesEvenIdx[idx]
                  : seriesEvenIdx[link[idx]];
        }
        odd[i] = Math.min(odd[i], even[seriesEvenIdx[idx]]);
        even[i] = Math.min(even[i], odd[seriesOddIdx[idx]]);
      }
      odd[i] = odd[i] == INF ? INF : odd[i] + 1;
      even[i] = even[i] == INF ? INF : even[i] + 1;
    }
  }
}
