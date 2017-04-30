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
  public abstract String toString(int nodeIdx);

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
      resIdx = add(resIdx, letters[i]);
    }
    return resIdx;
  }

  public int add(int nodeIdx, int letter) {
    if (child[letter][nodeIdx] < 0) {
      child[letter][nodeIdx] = nodePnt;
      initNodeInternal(nodePnt++, nodeIdx, letter);
    }
    return child[letter][nodeIdx];
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int nodeIdx = 0; nodeIdx < nodePnt; ++nodeIdx) {
      String s = toString(nodeIdx);
      if (s == null || s.isEmpty()) {
        sb.append(String.format("@%d\n", nodeIdx));
      } else {
        sb.append(String.format("@%d[%s]\n", nodeIdx, s));
      }
      for (int letter = 0; letter < letterCnt; ++letter) {
        int childIdx = child[letter][nodeIdx];
        if (childIdx == -1) continue;
        sb.append(String.format(" %d@%d", letter, childIdx));
      }
      sb.append('\n');
    }
    return sb.toString();
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
