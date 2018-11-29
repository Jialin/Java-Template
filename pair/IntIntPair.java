package template.pair;

public class IntIntPair implements Comparable<IntIntPair> {

  public final int first;
  public final int second;

  public IntIntPair(int first, int second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public int compareTo(IntIntPair o) {
    int cmp = Integer.compare(first, o.first);
    if (cmp != 0) {
      return cmp;
    }
    return Integer.compare(second, o.second);
  }
}
