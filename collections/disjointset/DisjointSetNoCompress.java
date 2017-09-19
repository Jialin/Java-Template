package template.collections.disjointset;

import template.array.IntArrayUtils;
import template.io.Displayable;
import template.numbertheory.number.IntUtils;

import java.util.Arrays;

/** Disjoint set without path compress. */
public class DisjointSetNoCompress implements Displayable {

  private int n;

  protected int[] height;
  protected int[] parent;
  protected int[] distance;

  public DisjointSetNoCompress() {}

  public DisjointSetNoCompress(int n) {
    init(n);
  }

  public void init(int n) {
    this.n = n;
    ensureCapacity(n);
    Arrays.fill(parent, 0, n, -1);
    Arrays.fill(height, 0, n, 0);
    Arrays.fill(distance, 0, n, 0);
  }

  public boolean isFriend(int x, int y) {
    return calcRoot(x) == calcRoot(y);
  }

  /** Returns whether the friend setting proceeded (i.e. {@code x} and {@code y} are not friends before) */
  public boolean setFriend(int x, int y) {
    int rootX = calcRoot(x);
    int rootY = calcRoot(y);
    if (rootX == rootY) return false;
    setFriendAtRoot(rootX, rootY, calcDistance(x) + calcDistance(y) + 1);
    return true;
  }

  protected void setFriendAtRoot(int rootX, int rootY, int distance) {
    if (height[rootX] <= height[rootY]) {
      parent[rootY] += parent[rootX];
      attachRoot(rootX, rootY, distance, height[rootX] == height[rootY]);
    } else {
      parent[rootX] += parent[rootY];
      attachRoot(rootY, rootX, distance, false);
    }
  }

  public int calcDistance(int x) {
    int res = 0;
    for ( ; parent[x] >= 0; x = parent[x]) res += distance[x];
    return res;
  }

  @Override
  public String toDisplay() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("parent:%s\n", IntArrayUtils.toString(parent, 0, n)));
    sb.append(String.format("height:%s\n", IntArrayUtils.toString(height, 0, n)));
    sb.append(String.format("distance:%s", IntArrayUtils.toString(distance, 0, n)));
    return sb.toString();
  }

  private int calcRoot(int x) {
    for ( ; parent[x] >= 0; x = parent[x]) {}
    return x;
  }

  private void attachRoot(int rootX, int rootY, int distance, boolean heightIncrease) {
    parent[rootX] = rootY;
    this.distance[rootX] = distance;
    if (heightIncrease) ++height[rootY];
  }

  private void ensureCapacity(int capacity) {
    if (parent != null && parent.length >= capacity) return;
    int size = IntUtils.nextPow2(capacity);
    parent = new int[size];
    height = new int[size];
    distance = new int[size];
  }
}
