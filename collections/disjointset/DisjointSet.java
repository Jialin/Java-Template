package template.collections.disjointset;

import java.util.Arrays;

public class DisjointSet {

  public int[] parent;
  public int setCnt;

  public DisjointSet(int vertexCapacity) {
    this.parent = new int[vertexCapacity];
    init(vertexCapacity);
  }

  public void init(int n) {
    Arrays.fill(parent, 0, n, -1);
    setCnt = n;
  }

  public int calcRoot(int x) {
    int res = x;
    for ( ; parent[res] >= 0; res = parent[res]) {}
    shrink(x, res);
    return res;
  }

  public boolean isFriend(int x, int y) {
    return calcRoot(x) == calcRoot(y);
  }

  public void setFriend(int x, int y) {
    int rootX = calcRoot(x);
    int rootY = calcRoot(y);
    if (rootX != rootY) {
      parent[rootX] += parent[rootY];
      parent[rootY] = rootX;
      --setCnt;
    }
  }

  private void shrink(int x, int root) {
    while (parent[x] >= 0) {
      int y = parent[x];
      parent[x] = root;
      x = y;
    }
  }
}
