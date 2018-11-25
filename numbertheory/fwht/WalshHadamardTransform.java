package template.numbertheory.fwht;

public class WalshHadamardTransform {

  public static Transform OR = (int[] a, int i, int j) -> a[j] += a[i];
  public static Transform OR_INVERT = (int[] a, int i, int j) -> a[j] -= a[i];

  public void wht(int[] a, Transform transform) {
    wht(a.length, a, transform);
  }

  public void wht(int n, int[] a, Transform transform) {
    for (int l = 1; l < n; l <<= 1) {
      for (int l2 = l << 1, i = 0; i < n; i += l2) {
        for (int j = 0; j < l; ++j) {
          transform.apply(a, i + j, i + j + l);
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
        transform.apply(a, i + j, i + j + l);
      }
    }
    return a[idx];
  }

  public interface Transform {
    void apply(int[] a, int i, int j);
  }
}
