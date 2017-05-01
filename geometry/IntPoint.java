package template.geometry;

import template.io.Displayable;

import java.util.Comparator;

public class IntPoint implements Displayable {

  public static final Comparator<IntPoint> XY = (a, b) -> a.x != b.x ? a.x - b.x : a.y - b.y;

  public int x, y;

  public IntPoint(int x, int y) {
    assign(x, y);
  }

  public static IntPoint zero() {
    return new IntPoint(0, 0);
  }

  /** this = a - b */
  public void initSub(IntPoint a, IntPoint b) {
    assign(a.x - b.x, a.y - b.y);
  }

  /** this * o */
  public long cross(IntPoint o) {
    return (long) x * o.y - (long) o.x * y;
  }

  @Override
  public String toDisplay() {
    return String.format("(%d, %d)", x, y);
  }

  private void assign(int x, int y) {
    this.x = x;
    this.y = y;
  }
}
