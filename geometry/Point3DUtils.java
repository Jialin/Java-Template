package template.geometry;

public class Point3DUtils {

  private static final double DEFAULT_EPS = 1E-8;

  private final double eps;
  private final Point3D tmp1, tmp2;

  public Point3DUtils() {
    this(DEFAULT_EPS);
  }

  public Point3DUtils(double eps) {
    this.eps = eps;
    this.tmp1 = Point3D.zero();
    this.tmp2 = Point3D.zero();
  }

  /** sgn(a .* b) */
  public int dotSgn(Point3D a, Point3D b) {
    return sgn(a.dot(b));
  }

  /** Normal vector of a plan */
  public void normal(Point3D a, Point3D b, Point3D c, Point3D normal) {
    tmp1.initSub(a, b);
    tmp2.initSub(b, c);
    normal.initCross(tmp1, tmp2);
  }

  private int sgn(double x) {
    if (x < -eps) return -1;
    return x > eps ? 1 : 0;
  }
}
