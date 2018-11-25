package template.numbertheory.fwht;

import template.numbertheory.number.IntModular;

public class WalshHadamardTransformModular {

  public static Transform OR = (int[] a, int i, int j, IntModular mod) -> a[j] = mod.add(a[j], a[i]);
  public static Transform OR_INVERT = (int[] a, int i, int j, IntModular mod) -> a[j] = mod.sub(a[j], a[i]);

  private final IntModular mod;

  public WalshHadamardTransformModular(IntModular mod) {
    this.mod = mod;
  }

  public void wht(int[] a, Transform transform) {
    wht(a.length, a, transform);
  }

  public void wht(int n, int[] a, Transform transform) {
    for (int l = 1; l < n; l <<= 1) {
      for (int l2 = l << 1, i = 0; i < n; i += l2) {
        for (int j = 0; j < l; ++j) {
          transform.apply(a, i + j, i + j + l, mod);
        }
      }
    }
  }

  public int whtAt(int idx, int[] a, Transform transform) {
    return whtAt(idx, a.length, a, transform);
  }

  public int whtAt(int idx, int n, int[] a, Transform transform) {
    for (int l = 1; l < n; l <<= 1) {
      for (int l2 = l << 1, i = 0; i < n; i += l2) {
        int j = idx & (l - 1);
        transform.apply(a, i + j, i + j + l, mod);
      }
    }
    return a[idx];
  }
  public interface Transform {
    void apply(int[] a, int i, int j, IntModular mod);
  }
}
