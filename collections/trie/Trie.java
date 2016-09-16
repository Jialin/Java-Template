package template.collections.trie;

/**
 * Trie.
 */
public class Trie {

  public int[] parent;
  public int[] parentLetter;
  public int[][] child;
  public int root;
  public int letterCnt;
  public int nodePnt;

  private int[] bfsOrder;

  public Trie(int letterCapacity, int nodeCapacity) {
    this.parent = new int[nodeCapacity];
    this.parentLetter = new int[nodeCapacity];
    this.child = new int[letterCapacity][nodeCapacity];
    this.bfsOrder = new int[nodeCapacity];
  }

  public void init(int letterCnt) {
    this.root = 0;
    this.letterCnt = letterCnt;
    this.nodePnt = 1;
    initNode(0, -1, -1);
    bfsOrder[0] = -1;
  }

  public int add(int[] letters) {
    return add(letters, 0, letters.length);
  }

  public int add(int[] letters, int fromIdx, int toIdx) {
    int resIdx = root;
    for (int i = fromIdx; i < toIdx; ++i) {
      int letter = letters[i];
      if (child[letter][resIdx] < 0) {
        child[letter][resIdx] = nodePnt;
        initNode(nodePnt++, resIdx, letter);
      }
      resIdx = child[letter][resIdx];
    }
    return resIdx;
  }

  public int[] calcBfsOrder() {
    if (bfsOrder[0] < 0) {
      int open = 0, closed = 0;
      for (bfsOrder[closed++] = root; open < closed; ) {
        int u = bfsOrder[open++];
        for (int letter = 0; letter < letterCnt; ++letter) if (child[letter][u] >= 0) {
          bfsOrder[closed++] = child[letter][u];
        }
      }
    }
    return bfsOrder;
  }

  private void initNode(int idx, int parent, int letter) {
    this.parent[idx] = parent;
    this.parentLetter[idx] = letter;
    for (int i = 0; i < letterCnt; ++i) {
      child[i][idx] = -1;
    }
  }
}
