package template.numbertheory.combination;

import template.numbertheory.number.IntModular;

public class BinomialModular {

  private static final int MOD = 1000000007;

  private final IntModular mod;
  private final FactorialModular fact;

  public BinomialModular(int n) {
    this(n, MOD);
  }

  public BinomialModular(int n, int mod) {
    this(n, new IntModular(mod));
  }

  public BinomialModular(int n, IntModular mod) {
    this.mod = mod;
    this.fact = new FactorialModular(n, mod);
  }

  public int get(int n, int m) {
    if (m > n) return 0;
    return mod.mul(mod.mul(
        fact.fact(n),
        fact.invFact(m)),
        fact.invFact(n - m));
  }
}
