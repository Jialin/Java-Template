package template.numbertheory.fft;

import template.numbertheory.complex.Complex;
import template.truth.Truth;

public class FourierTransformer {

  private int n;
  private Complex wBase, mul;
  private Complex[] w;
  private int[] rev;

  public FourierTransformer(int capacity) {
    w = new Complex[capacity + 1];
    for (int i = 0; i <= capacity; ++i) {
      w[i] = Complex.zero();
    }
    rev = new int[capacity];
    wBase = Complex.zero();
    mul = Complex.zero();
    init(capacity);
  }

  public void init(int n) {
    if (this.n == n) return;
    Truth.assertTrue(Integer.bitCount(n) == 1);
    this.n = n;
    wBase.initPolar(1, 2 * Math.PI / n);
    w[0].init(1, 0);
    int logN = Integer.numberOfTrailingZeros(n);
    for (int i = 0; i < n; ++i) {
      w[i + 1].initMul(w[i], wBase);
      rev[i] = 0;
      for (int j = i; j > 0; j &= j - 1) {
        rev[i] |= 1 << (logN - 1 - Integer.numberOfTrailingZeros(j));
      }
    }
  }

  public void fft(Complex[] a, boolean invert) {
    swap(a);
    for (int l = 1; l < n; l <<= 1) {
      int l2 = l << 1, step = n / l2;
      for (int i = 0; i < n; i += l2) {
        for (int j = 0, wIdx = invert ? n : 0; j < l; ++j, wIdx += invert ? -step : step) {
          mul.initMul(a[i + j + l], w[wIdx]);
          a[i + j + l].initSub(a[i + j], mul);
          a[i + j].add(mul);
        }
      }
    }
    if (invert) {
      for (int i = 0; i < n; ++i) {
        a[i].shrink(n);
      }
    }
  }

  /** Computes a single coefficient. */
  public Complex fftAt(int idx, Complex[] a, boolean invert) {
    swap(a);
    for (int l = 1; l < n; l <<= 1) {
      int l2 = l << 1, step = n / l2;
      for (int i = 0; i < n; i += l2) {
        int j = idx & (l - 1);
        int wIdx = invert ? n - step * j : step * j;
        mul.initMul(a[i + j + l], w[wIdx]);
        a[i + j + l].initSub(a[i + j], mul);
        a[i + j].add(mul);
      }
    }
    if (invert) {
      a[idx].shrink(n);
    }
    return a[idx];
  }

  public void fft(Complex[][] a, boolean invert) {
    for (int i = 0; i < n; ++i) {
      fft(a[i], invert);
    }
    for (int i = 0; i < n; ++i) for (int j = i + 1; j < n; ++j) {
      Complex tmp = a[i][j];
      a[i][j] = a[j][i];
      a[j][i] = tmp;
    }
    for (int i = 0; i < n; ++i) {
      fft(a[i], invert);
    }
  }

  public void mul(Complex[][] a, Complex[][] b, Complex[][] res) {
    fft(a, false);
    fft(b, false);
    for (int i = 0; i < n; ++i) for (int j = 0; j < n; ++j) {
      res[i][j].initMul(a[i][j], b[i][j]);
    }
    fft(res, true);
  }

  private void swap(Complex[] a) {
    for (int i = 0; i < n; ++i) if (i < rev[i]) {
      Complex tmp = a[i];
      a[i] = a[rev[i]];
      a[rev[i]] = tmp;
    }
  }
}
