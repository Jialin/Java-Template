package template.collections.eertree;

import java.util.HashMap;
import java.util.Map;

/** Paper: https://arxiv.org/pdf/1506.04862.pdf */
public abstract class AbstractEertree {
  /**
   * Next palindrome after adding {@code letter} to the node.
   *
   * NOTE: {@code compress} indicates whether to use {@code childMap} instead of {@code child} to store child links.
   *
   * For example:
   *   1) child['a'][imaginary string] = "a"
   *   2) child['b'][empty string] = "bb"
   *   3) child['c']["aba"] = "cabac"
   *   4) child['d']["dd"] = "ddd"
   */
  private boolean compress;
  private int[][] child;
  private Map[] childMap;

  /**
   * Suffix link is the longest suffix-palindrome of the node.
   *
   * For example:
   *   1) link[imaginary string] = imaginary string
   *   2) link[empty string] = imaginary string
   *   3) link["a"] = empty string
   *   4) link["aba"] = "a"
   *   5) link["aaa"] = "aa"
   */
  protected int[] link;
  /**
   * Length of the palindrome.
   *
   * For example:
   *   1) link[imaginary string] = -1
   *   2) link[empty string] = 0
   *   3) link[string] = string.length()
   */
  protected int[] length;

  /** Letter count in the dictionary. */
  private int letterCnt;
  /** Last added node index in the tree. */
  protected int nodeIdx;
  /** Last added char index. */
  private int charIdx;
  /** Added chars. */
  private int[] s;
  /** Last visited node after add. */
  protected int last;

  public abstract void createSubclass(int letterCapacity, int nodeCapacity);

  public AbstractEertree(int letterCapacity, int charCapacity, boolean initialize) {
    this(letterCapacity, charCapacity, false, initialize);
  }

  public AbstractEertree(int letterCapacity, int charCapacity, boolean compress, boolean initialize) {
    this.compress = compress;
    int nodeCapacity = charCapacity + 2;
    if (compress) {
      childMap = new Map[letterCapacity];
      for (int i = 0; i < letterCapacity; ++i) {
        childMap[i] = new HashMap<Integer, Integer>();
      }
    } else {
      child = new int[letterCapacity][nodeCapacity];
    }
    link = new int[nodeCapacity];
    length = new int[nodeCapacity];
    s = new int[nodeCapacity];
    createSubclass(letterCapacity, nodeCapacity);
    if (initialize) {
      init(letterCapacity);
    }
  }

  public void init(int letterCnt) {
    this.letterCnt = letterCnt;
    if (compress) {
      for (int i = 0; i < letterCnt; ++i) {
        childMap[i].clear();
      }
    }
    nodeIdx = 1;
    // 0: imaginary string
    initNode(0);
    link[0] = 0;
    length[0] = -1;
    // 1: empty string
    initNode(1);
    link[1] = 0;
    length[1] = 0;
    // s[0] = -1
    s[0] = -1;
    charIdx = 0;
    // last node points to empty string
    last = 1;
  }

  /**
   * Appends a character to the tree, returns whether a new node is added.
   *
   * NOTE: char should be mapped to {@code [0, letterCnt)}.
   */
  public boolean append(int c) {
    s[++charIdx] = c;
    last = calcLink(last);
    boolean toAdd = getChild(last, c) < 0;
    if (toAdd) {
      initNode(++nodeIdx);
      length[nodeIdx] = length[last] + 2;
      link[nodeIdx] = length[nodeIdx] == 1 ? 1 : getChild(calcLink(link[last]), c);
      setChild(last, c, nodeIdx);
    }
    last = getChild(last, c);
    return toAdd;
  }

  private int getChild(int idx, int value) {
    if (compress) {
      return (int) childMap[value].getOrDefault(idx, -1);
    } else {
      return child[value][idx];
    }
  }

  private void setChild(int idx, int value, int childIdx) {
    if (compress) {
      childMap[value].put(idx, childIdx);
    } else {
      child[value][idx] = childIdx;
    }
  }

  private int calcLink(int idx) {
    for ( ; s[charIdx - length[idx] - 1] != s[charIdx]; idx = link[idx]) {}
    return idx;
  }

  private void initNode(int idx) {
    if (!compress) {
      for (int i = 0; i < letterCnt; ++i) {
        child[i][idx] = -1;
      }
    }
  }
}
