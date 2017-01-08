package template.geometry;

public class Point {

  double x, y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public static Point polar(double r, double angle) {
    return new Point(r * Math.cos(angle), Math.sin(angle));
  }

  public double cross(Point o) {
    return x * o.y - o.x * y;
  }
}
