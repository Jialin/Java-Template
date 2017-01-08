package template.collections.trie;

/**
 * Abstract trie.
 */
public abstract class AbstractTrie {

  public int[] parent;
  public int[] parentLetter;
  public int[][] child;
  public int root;
  public int letterCnt;
  public int nodePnt;

  public abstract void createSubclass(int letterCapacity, int nodeCapacity);
  public abstract void initNode(int idx, int parent, int parentLetter);

  public AbstractTrie(int letterCapacity, int nodeCapacity) {
    parent = new int[nodeCapacity];
    parentLetter = new int[nodeCapacity];
    child = new int[letterCapacity][nodeCapacity];
    createSubclass(letterCapacity, nodeCapacity);
    init(letterCapacity);
  }

  public void init(int letterCnt) {
    this.root = 0;
    this.letterCnt = letterCnt;
    this.nodePnt = 1;
    initNodeInternal(0, -1, -1);
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
        initNodeInternal(nodePnt++, resIdx, letter);
      }
      resIdx = child[letter][resIdx];
    }
    return resIdx;
  }

  public int access(int[] letters) {
    return access(letters, 0, letters.length);
  }

  public int access(int[] letters, int fromIdx, int toIdx) {
    int resIdx = root;
    for (int i = fromIdx; i < toIdx; ++i) {
      resIdx = child[letters[i]][resIdx];
      if (resIdx < 0) return resIdx;
    }
    return resIdx;
  }

  private void initNodeInternal(int idx, int parent, int letter) {
    this.parent[idx] = parent;
    this.parentLetter[idx] = letter;
    for (int i = 0; i < letterCnt; ++i) {
      child[i][idx] = -1;
    }
    initNode(idx, parent, letter);
  }
}
