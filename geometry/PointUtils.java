package template.geometry;

import template.numbertheory.number.IntUtils;

public class PointUtils {

  private static final double DEFAULT_EPS = 1E-8;

  private final Point tmp1, tmp2;
  private final double eps;

  private Point[] tmpP;

  public PointUtils() {
    this(DEFAULT_EPS);
  }

  public PointUtils(double eps) {
    this.tmp1 = Point.zero();
    this.tmp2 = Point.zero();
    this.eps = eps;
  }

  public boolean equals(Point u, Point v) {
    return sgn(u.x - v.x) == 0 && sgn(u.y - v.y) == 0;
  }

  /** (u - o) * (v - o) */
  public double cross(Point u, Point v, Point o) {
    tmp1.initSub(u, o);
    tmp2.initSub(v, o);
    return tmp1.cross(tmp2);
  }

  /** sgn((u - o) * (v - o)) */
  public int crossSgn(Point u, Point v, Point o) {
    return sgn(cross(u, v, o));
  }

  /**
   * Whether {@code u} and {@code v} are on the same side of the line from {@code l1} to {@code l2}.
   *
   * NOTE: Returns false, if {@code u} or {@code v} on the line.
   */
  public boolean sameSide(Point u, Point v, Point l1, Point l2) {
    return crossSgn(l1, u, l2) * crossSgn(l1, v, l2) > 0;
  }

  /** Intersection point of the line from {@code u1} to {@code u2} and the line from {@code v1} to {@code v2}. */
  public Point intersection(Point u1, Point u2, Point v1, Point v2) {
    tmp1.initSub(v1, v2);
    tmp2.initSub(u1, v1);
    double x = tmp2.cross(tmp1);
    tmp2.initSub(u1, u2);
    double y = tmp2.cross(tmp1);
    Point res = Point.sub(u2, u1);
    res.mul(x / y);
    res.add(u1);
    return res;
  }

  /**
   * Cuts the polygon by the line from {@code l1} to {@code l2}, and returns the polygon on the side of {@code side}.
   */
  public int polygonCut(int n, Point[] p, Point l1, Point l2, Point side) {
    if (n < 3) return 0;
    ensureCapacity(n << 1);
    int m = 0;
    for (int i = 0, j = 1; i < n; ++i, j = j + 1 == n ? 0 : j + 1) {
      if (sameSide(p[i], side, l1, l2)) {
        tmpP[m++] = p[i];
      }
      if (!sameSide(p[i], p[j], l1, l2) && (crossSgn(p[i], l1, l2) != 0 || crossSgn(p[j], l1, l2) != 0)) {
        tmpP[m++] = intersection(p[i], p[j], l1, l2);
      }
    }
    n = 0;
    for (int i = 0; i < m; ++i) if (i == 0 || !equals(tmpP[i - 1], tmpP[i])) {
      p[n++] = tmpP[i];
    }
    if (equals(p[0], p[n - 1])) --n;
    return n < 3 ? 0 : n;
  }

  private int sgn(double x) {
    if (x < -eps) return -1;
    return x > eps ? 1 : 0;
  }

  private void ensureCapacity(int size) {
    if (tmpP != null && tmpP.length >= size) return;
    tmpP = new Point[IntUtils.nextPow2(size)];
  }
}
