package template.geometry;

import java.util.Arrays;

public class IntPointUtils {

  private final IntPoint tmp1, tmp2;

  public IntPointUtils() {
    tmp1 = IntPoint.zero();
    tmp2 = IntPoint.zero();
  }

  public boolean parallel(IntPoint u1, IntPoint u2, IntPoint v1, IntPoint v2) {
    tmp1.initSub(u1, u2);
    tmp2.initSub(v1, v2);
    return tmp1.cross(tmp2) == 0;
  }

  public int graham(int n, IntPoint[] p, IntPoint[] ch) {
    if (n < 3) {
      for (int i = 0; i < n; ++i) ch[i] = p[i];
      return n;
    }
    Arrays.sort(p, 0, n, IntPoint.XY);
    ch[0] = p[0];
    ch[1] = p[1];
    int i, j;
    for (i = j = 2; i < n; ch[j++] = p[i++]) {
      for ( ; j > 1 && grahamSubMul(ch[j - 2], p[i], ch[j - 1]) >= 0; --j) {}
    }
    int k = j++;
    ch[k] = p[n - 2];
    for (i = n - 3; i > 0; ch[j++] = p[i--]) {
      for ( ; j > k && grahamSubMul(ch[j - 2], p[i], ch[j - 1]) >= 0; --j) {}
    }
    for ( ; j > k && grahamSubMul(ch[j - 2], ch[0], ch[j - 1]) >= 0; --j) {}
    return j;
  }

  private long grahamSubMul(IntPoint a, IntPoint b, IntPoint o) {
    tmp1.initSub(a, o);
    tmp2.initSub(b, o);
    return tmp1.cross(tmp2);
  }
}
