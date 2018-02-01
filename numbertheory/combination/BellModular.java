package template.numbertheory.combination;

import template.numbertheory.number.IntModular;
import template.numbertheory.number.IntUtils;

/**
 * Number of ways to partition a set of {@code n} labeled elements.
 *
 * <p>Values starting from 0: 1, 1, 2, 5, 15, 52, 203, 877, 4140, 21147, 115975, 678570, 4213597, 27644437, 190899322
 *
 * <p>OEIS link: http://oeis.org/A000110
 */
public class BellModular {

  private static final int MOD = 1000000007;

  private final IntModular mod;
  private final BinomialModular binomial;
  private int[] bell;

  public BellModular(int n) {
    this(n, MOD);
  }

  public BellModular(int n, int mod) {
    this(n, new IntModular(mod));
  }

  public BellModular(int n, IntModular mod) {
    this.mod = mod;
    this.binomial = new BinomialModular(n, mod);
    bell = new int[n + 1];
    bell[0] = 1;
    for (int i = 1; i <= n; ++i) {
      bell[i] = calc(i);
    }
  }

  public int get(int n) {
    ensureCapacity(n + 1);
    return bell[n];
  }

  private int calc(int idx) {
    int res = 0;
    for (int i = 0; i < idx; ++i) {
      res = mod.add(
          res,
          mod.mul(
              bell[i],
              binomial.get(idx - 1, i)));
    }
    return res;
  }

  private void ensureCapacity(int size) {
    if (size <= bell.length) return;
    size = IntUtils.nextPow2(size);
    int[] newBell = new int[size];
    for (int i = 0; i < bell.length; ++i) {
      newBell[i] = bell[i];
    }
    int oldSize = bell.length;
    bell = newBell;
    for (int i = oldSize; i < size; ++i) {
      bell[i] = calc(i);
    }
  }
}
