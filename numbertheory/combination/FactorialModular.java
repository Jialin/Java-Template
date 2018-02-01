package template.numbertheory.combination;

import template.numbertheory.number.IntModular;
import template.numbertheory.number.IntUtils;

public class FactorialModular {

  private static final int MOD = 1000000007;

  private final IntModular mod;
  private int[] fact;
  private int[] invFact;

  public FactorialModular(int n) {
    this(n, MOD);
  }

  public FactorialModular(int n, int mod) {
    this(n, new IntModular(mod));
  }

  public FactorialModular(int n, IntModular mod) {
    this.mod = mod;
    fact = new int[n + 1];
    invFact = new int[n + 1];
    fact[0] = 1;
    for (int i = 1; i <= n; ++i) {
      fact[i] = this.mod.mul(fact[i - 1], i);
    }
    invFact[n] = this.mod.inverse(fact[n]);
    for (int i = n; i > 0; --i) {
      invFact[i - 1] = this.mod.mul(invFact[i], i);
    }
  }

  public int fact(int n) {
    ensureCapacity(n + 1);
    return fact[n];
  }

  public int invFact(int n) {
    ensureCapacity(n + 1);
    return invFact[n];
  }

  private void ensureCapacity(int size) {
    if (size <= fact.length) return;
    size = IntUtils.nextPow2(size);
    int[] newFact = new int[size];
    int[] newInvFact = new int[size];
    for (int i = 0; i < fact.length; ++i) {
      newFact[i] = fact[i];
      newInvFact[i] = invFact[i];
    }
    for (int i = fact.length; i < size; ++i) {
      newFact[i] = mod.mul(newFact[i - 1], fact[i]);
    }
    newInvFact[size - 1] = mod.inverse(newFact[size - 1]);
    for (int i = size - 1; i > fact.length; --i) {
      newInvFact[i - 1] = mod.mul(newInvFact[i], i);
    }
    fact = newFact;
    invFact = newInvFact;
  }
}
