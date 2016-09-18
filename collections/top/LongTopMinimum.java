package template.collections.top;

/**
 * Stores the top minimum value with indices.
 */
public class LongTopMinimum {

  private long min1, min2;
  private int idx1;

  public void init() {
    min1 = min2 = Long.MAX_VALUE;
    idx1 = Integer.MIN_VALUE;
  }

  /**
   * Adds {@code value} marked with {@code idx}.
   */
  public void add(long value, int idx) {
    if (value <= min1) {
      min2 = min1;
      min1 = value;
      idx1 = idx;
    } else {
      min2 = Math.min(min2, value);
    }
  }

  /**
   * Calculates the minimum value whose index is NOT {@code idx}.
   */
  public long calc(int idx) {
    return idx == idx1 ? min2 : min1;
  }
}
