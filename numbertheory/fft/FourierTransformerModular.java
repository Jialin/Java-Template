package template.numbertheory.fft;

import template.array.IntArrayUtils;
import template.numbertheory.number.IntModular;
import template.numbertheory.number.IntUtils;

import java.util.Arrays;

public class FourierTransformerModular {

  private static final int MAXBIT = 30;
  private static final int[][] REV = new int[MAXBIT][];

  private final int[] roots, invRoots;
  private final IntModular MOD;

  private int[] tmpA, tmpB, tmpAB;

  public FourierTransformerModular(int mod, int rootStart) {
    this(new IntModular(mod), rootStart);
  }

  public FourierTransformerModular(int mod) {
    this(new IntModular(mod));
  }

  public FourierTransformerModular(IntModular MOD) {
    this(MOD, rootStart(MOD));
  }

  public FourierTransformerModular(IntModular MOD, int rootStart) {
    if (MOD.pow(rootStart, (MOD.mod - 1) >> 1) == 1) throw new IllegalArgumentException();
    this.MOD = MOD;
    int rootCnt = Integer.numberOfTrailingZeros(MOD.mod - 1);
    roots = new int[rootCnt];
    invRoots = new int[rootCnt];
    for (int i = 0, exp = MOD.mod - 1; i < rootCnt; ++i, exp >>= 1) {
      roots[i] = MOD.pow(rootStart, exp);
      invRoots[i] = MOD.inverse(roots[i]);
    }
  }

  public void fft(int n, int[] a, boolean invert) {
    if (!IntUtils.isPow2(n)) throw new IllegalArgumentException();
    int[] rev = initRev(n);
    for (int i = 1; i < n; ++i) if (i < rev[i]) {
      IntArrayUtils.swap(a, i, rev[i]);
    }
    for (int len = 2, idx = 1; len <= n; len <<= 1, ++idx) {
      int len2 = len >> 1;
      int wBase = invert ? invRoots[idx] : roots[idx];
      for (int i = 0; i < n; i += len) {
        int w = 1;
        for (int j = 0; j < len2; ++j) {
          int u = a[i + j], v = MOD.mul(a[i + j + len2], w);
          a[i + j] = MOD.add(u, v);
          a[i + j + len2] = MOD.sub(u, v);
          w = MOD.mul(w, wBase);
        }
      }
    }
    if (invert) {
      int invN = MOD.inverse(n);
      for (int i = 0; i < n; ++i) {
        a[i] = MOD.mul(a[i], invN);
      }
    }
  }

  public void mul(int[] a, int[] b, int[] res) {
    mul(a.length, a, b.length, b, res.length, res);
  }

  public void mul(int[] a, int[] b, int resSize, int[] res) {
    mul(a.length, a, b.length, b, resSize, res);
  }

  public void mul(int n, int[] a, int m, int[] b, int[] res) {
    mul(n, a, m, b, res.length, res);
  }

  public void mul(int n, int[] a, int m, int[] b, int resSize, int[] res) {
    int nm = IntUtils.nextPow2(n + m - 1);
    ensureCapacity(nm);
    for (int i = 0; i < n; ++i) tmpA[i] = a[i];
    for (int i = 0; i < m; ++i) tmpB[i] = b[i];
    Arrays.fill(tmpA, n, nm, 0);
    Arrays.fill(tmpB, m, nm, 0);
    fft(nm, tmpA, false);
    fft(nm, tmpB, false);
    pointwiseProduct(nm, tmpA, tmpB, tmpAB);
    fft(nm, tmpAB, true);
    for (int i = Math.min(nm, resSize) - 1; i >= 0; --i) {
      res[i] = tmpAB[i];
    }
  }

  public void sqr(int[] a, int resSize, int[] res) {
    sqr(a.length, a, resSize, res);
  }

  public void sqr(int n, int[] a, int resSize, int[] res) {
    int nn = IntUtils.nextPow2((n << 1) - 1);
    ensureCapacity(nn);
    for (int i = 0; i < n; ++i) tmpA[i] = a[i];
    Arrays.fill(tmpA, n, nn, 0);
    fft(nn, tmpA, false);
    pointwiseProduct(nn, tmpA, tmpA, tmpAB);
    fft(nn, tmpAB, true);
    for (int i = Math.min(nn, resSize) - 1; i >= 0; --i) {
      res[i] = tmpAB[i];
    }
  }

  public void pointwiseProduct(int n, int[] a, int[] b, int[] res) {
    for (int i = 0; i < n; ++i) {
      res[i] = MOD.mul(a[i], b[i]);
    }
  }

  private int[] initRev(int n) {
    int nBit = Integer.numberOfTrailingZeros(n);
    if (REV[nBit] != null) return REV[nBit];
    REV[nBit] = new int[n];
    int[] rev = REV[nBit];
    for (int i = 1, j = 0; i < n; ++i) {
      int bit = n >> 1;
      for ( ; j >= bit; bit >>= 1) j -= bit;
      j += bit;
      rev[i] = j;
    }
    return rev;
  }

  private void ensureCapacity(int capacity) {
    if (tmpA == null || tmpA.length < capacity) {
      tmpA = new int[capacity];
      tmpB = new int[capacity];
      tmpAB = new int[capacity];
    }
  }

  private static int rootStart(IntModular MOD) {
    int exp = (MOD.mod - 1) >> 1;
    for (int i = 2; ; ++i) if (MOD.pow(i, exp) != 1) {
      System.err.printf("mod:%d rootStart:%d\n", MOD.mod, i);
      return i;
    }
  }
}
