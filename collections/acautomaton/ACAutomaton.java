package template.collections.acautomaton;

import template.collections.trie.Trie;

import java.util.Arrays;

/**
 * Aho-Corasick automaton.
 */
public class ACAutomaton extends Trie {

  private int[] suffixLink;
  private int[][] next;

  public ACAutomaton(int letterCapacity, int nodeCapacity) {
    super(letterCapacity, nodeCapacity);
    this.suffixLink = new int[nodeCapacity];
    this.next = new int[letterCapacity][nodeCapacity];
  }

  @Override
  public void init(int letterCnt) {
    super.init(letterCnt);
    suffixLink[root] = -1;
    for (int letter = 0; letter < letterCnt; ++letter) {
      next[letter][root] = -1;
    }
  }

  @Override
  public int add(int[] letters) {
    return add(letters, 0, letters.length);
  }

  @Override
  public int add(int[] letters, int fromIdx, int toIdx) {
    int prevNodePnt = nodePnt;
    int pnt = super.add(letters, fromIdx, toIdx);
    Arrays.fill(suffixLink, prevNodePnt, nodePnt, -1);
    for (int letter = 0; letter < letterCnt; ++letter) {
      Arrays.fill(next[letter], prevNodePnt, nodePnt, -1);
    }
    return pnt;
  }

  public int calcSuffixLink(int u) {
    if (suffixLink[u] < 0) {
      suffixLink[u] = u == root || parent[u] == root
          ? root
          : calcNext(calcSuffixLink(parent[u]), parentLetter[u]);
    }
    return suffixLink[u];
  }

  public int calcNext(int u, int letter) {
    if (next[letter][u] < 0) {
      next[letter][u] = child[letter][u] >= 0
          ? child[letter][u]
          : u == root
              ? root
              : calcNext(calcSuffixLink(u), letter);
    }
    return next[letter][u];
  }
}
