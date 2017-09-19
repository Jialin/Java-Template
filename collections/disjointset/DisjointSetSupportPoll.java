package template.collections.disjointset;

import template.collections.list.IntArrayList;
import template.truth.Truth;

/** Disjoint set supports poll edges in stack order. */
public class DisjointSetSupportPoll extends DisjointSetNoCompress {

  private IntArrayList records;
  private IntArrayList recordSizes;

  public DisjointSetSupportPoll() {
    super();
  }

  public DisjointSetSupportPoll(int n) {
    super(n);
  }

  @Override
  public void init(int n) {
    super.init(n);
    if (records == null) {
      records = new IntArrayList(n);
      recordSizes = new IntArrayList(n);
    }
    records.clear();
    recordSizes.clear();
  }

  @Override
  protected void setFriendAtRoot(int rootX, int rootY, int distance) {
    if (height[rootX] <= height[rootY]) {
      recordSizes.add(parent[rootX]);
      records.add((rootX << 1) | (height[rootX] == height[rootY] ? 1 : 0));
    } else {
      recordSizes.add(parent[rootY]);
      records.add(rootY << 1);
    }
    super.setFriendAtRoot(rootX, rootY, distance);
  }

  public void poll() {
    Truth.assertThat(records.isNotEmpty());
    int record = records.pollLast();
    int child = record >> 1;
    int parent = this.parent[child];
    this.parent[child] = recordSizes.pollLast();
    this.parent[parent] -= this.parent[child];
    height[parent] -= record & 1;
    distance[child] = 0;
  }
}
