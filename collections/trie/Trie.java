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

  public Trie(int letterCapacity, int nodeCapacity) {
    this.parent = new int[nodeCapacity];
    this.parentLetter = new int[nodeCapacity];
    this.child = new int[letterCapacity][nodeCapacity];
  }

  public void init(int letterCnt) {
    this.root = 0;
    this.letterCnt = letterCnt;
    this.nodePnt = 1;
    initNode(0, -1, -1);
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

  private void initNode(int idx, int parent, int letter) {
    this.parent[idx] = parent;
    this.parentLetter[idx] = letter;
    for (int i = 0; i < letterCnt; ++i) {
      child[i][idx] = -1;
    }
  }
}
