package template.geometry;

public class Point3D {

  public double x, y, z;

  public Point3D(double x, double y, double z) {
    assign(x, y, z);
  }

  public static Point3D zero() {
    return new Point3D(0, 0, 0);
  }

  /** a .* b */
  public double dot(Point3D o) {
    return x * o.x + y * o.y + z * o.z;
  }

  /** this = a - b */
  public void initSub(Point3D a, Point3D b) {
    assign(a.x - b.x, a.y - b.y, a.z - b.z);
  }

  public void initCross(Point3D a, Point3D b) {
    assign(
        a.y * b.z - b.y * a.z,
        a.z * b.x - b.z * a.x,
        a.x * b.y - b.x * a.y);
  }

  /** a * b */
  public Point3D cross(Point3D o) {
    return new Point3D(
        y * o.z - o.y * z,
        z * o.x - o.z * x,
        x * o.y - o.x * y);
  }

  private void assign(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
}
