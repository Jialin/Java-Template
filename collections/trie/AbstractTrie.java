package template.collections.trie;

import template.io.Displayable;
import template.numbertheory.number.IntUtils;

import java.util.Arrays;

/** Abstract trie. */
public abstract class AbstractTrie implements Displayable {

  public static final int ROOT = 0;

  public int[] parent;
  public int[] fromLetter;
  public int[][] child;
  public int letterCnt;
  public int nxtNodeIdx;

  public abstract void createSubclass(int letterCapacity, int nodeCapacity);
  public abstract void reallocateSubclass(int newNodeCapacity);
  public abstract void initNodeSubclass(int idx, int parent, int parentLetter);
  public abstract String toDisplay(int idx);

  public AbstractTrie(int letterCapacity, int nodeCapacity) {
    int size = IntUtils.nextPow2(nodeCapacity);
    parent = new int[size];
    fromLetter = new int[size];
    child = new int[letterCapacity][size];
    createSubclass(letterCapacity, size);
    init(letterCapacity);
  }

  public void init(int letterCnt) {
    this.letterCnt = letterCnt;
    this.nxtNodeIdx = 1;
    initNodeInternal(0, -1, -1);
  }

  public int add(int[] letters) {
    return add(letters, 0, letters.length);
  }

  public int add(int[] letters, int fromIdx, int toIdx) {
    int resIdx = ROOT;
    for (int i = fromIdx; i < toIdx; ++i) {
      resIdx = add(resIdx, letters[i]);
    }
    return resIdx;
  }

  public int add(int nodeIdx, int letter) {
    if (child[letter][nodeIdx] < 0) {
      child[letter][nodeIdx] = nxtNodeIdx;
      initNodeInternal(nxtNodeIdx++, nodeIdx, letter);
    }
    return child[letter][nodeIdx];
  }

  public int access(int[] letters) {
    return access(letters, 0, letters.length);
  }

  public int access(int[] letters, int fromIdx, int toIdx) {
    int resIdx = ROOT;
    for (int i = fromIdx; i < toIdx; ++i) {
      resIdx = child[letters[i]][resIdx];
      if (resIdx < 0) return resIdx;
    }
    return resIdx;
  }

  public int access(int nodeIdx, int letter) {
    return child[letter][nodeIdx];
  }

  @Override
  public String toDisplay() {
    StringBuilder sb = new StringBuilder();
    for (int nodeIdx = 0; nodeIdx < nxtNodeIdx; ++nodeIdx) {
      String s = toDisplay(nodeIdx);
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
    ensureCapacity(idx);
    this.parent[idx] = parent;
    this.fromLetter[idx] = letter;
    for (int i = 0; i < letterCnt; ++i) {
      child[i][idx] = -1;
    }
    initNodeSubclass(idx, parent, letter);
  }

  private void ensureCapacity(int size) {
    if (size < parent.length) {
      return;
    }
    int newSize = IntUtils.nextPow2(size + 1);
    parent = Arrays.copyOf(parent, newSize);
    fromLetter = Arrays.copyOf(fromLetter, newSize);
    for (int i = letterCnt - 1; i >= 0; --i) {
      child[i] = Arrays.copyOf(child[i], newSize);
    }
    reallocateSubclass(newSize);
  }
}
