package template.geometry;

import template.io.Displayable;

public class Point implements Displayable {

  public double x, y;

  public Point(double x, double y) {
    assign(x, y);
  }

  public static Point zero() {
    return new Point(0, 0);
  }

  public static Point polar(double r, double angle) {
    return new Point(r * Math.cos(angle), Math.sin(angle));
  }

  /** a - b */
  public static Point sub(Point a, Point b) {
    return new Point(a.x - b.x, a.y - b.y);
  }

  /** this = a - b */
  public void initSub(Point a, Point b) {
    assign(a.x - b.x, a.y - b.y);
  }

  /** this += o */
  public void add(Point o) {
    x += o.x;
    y += o.y;
  }

  /** this *= scale */
  public void mul(double scale) {
    x *= scale;
    y *= scale;
  }

  /** this * o */
  public double cross(Point o) {
    return x * o.y - o.x * y;
  }

  @Override
  public String toDisplay() {
    return String.format("(%f,%f)", x, y);
  }

  private void assign(double x, double y) {
    this.x = x;
    this.y = y;
  }
}
