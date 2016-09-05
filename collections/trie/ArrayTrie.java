package template.collections.trie;

/**
 * Trie (array implementation).
 */
public class ArrayTrie {

  public int[] parent;
  public int[][] child;
  public int root;
  public int letterCnt;

  private int nodePnt;

  public ArrayTrie(int letterCapacity, int nodeCapacity) {
    this.parent = new int[nodeCapacity];
    this.child = new int[letterCapacity][nodeCapacity];
  }

  public void init(int letterCnt) {
    this.root = 0;
    this.letterCnt = letterCnt;
    this.nodePnt = 1;
    initNode(0, -1);
  }

  public int add(int[] letters) {
    int resIdx = root;
    for (int letter : letters) {
      createChildrenIfNeeded(resIdx);
      resIdx = child[letter][resIdx];
    }
    return resIdx;
  }

  private void initNode(int idx, int parent) {
    this.parent[idx] = parent;
    for (int i = 0; i < letterCnt; ++i) {
      child[i][idx] = -1;
    }
  }

  private void createChildrenIfNeeded(int idx) {
    if (child[0][idx] >= 0) return;
    for (int i = 0; i < letterCnt; ++i) {
      child[i][idx] = nodePnt;
      initNode(nodePnt++, idx);
    }
  }
}
