package template.collections.acautomaton;

import template.collections.trie.AbstractTrie;

/**
 * Aho-Corasick automaton.
 */
public class ACAutomaton extends AbstractTrie {

  private int[] suffixLink;
  private int[][] next;

  public ACAutomaton(int letterCapacity, int nodeCapacity) {
    super(letterCapacity, nodeCapacity);
  }

  @Override
  public void createSubclass(int letterCapacity, int nodeCapacity) {
    suffixLink = new int[nodeCapacity];
    next = new int[letterCapacity][nodeCapacity];
  }

  @Override
  public void initNode(int idx, int parent, int parentLetter) {
    suffixLink[idx] = -1;
    for (int letter = 0; letter < letterCnt; ++letter) {
      next[letter][idx] = -1;
    }
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
