package template.collections.acautomaton;

import template.collections.trie.AbstractTrie;

import java.util.Arrays;

/** Aho-Corasick automaton. */
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
  public void reallocateSubclass(int newNodeCapacity) {
    suffixLink = Arrays.copyOf(suffixLink, newNodeCapacity);
    for (int i = letterCnt - 1; i >= 0; --i) {
      next[i] = Arrays.copyOf(next[i], newNodeCapacity);
    }
  }

  @Override
  public void initNodeSubclass(int idx, int parent, int parentLetter) {
    suffixLink[idx] = -1;
    for (int letter = 0; letter < letterCnt; ++letter) {
      next[letter][idx] = -1;
    }
  }

  @Override
  public String toDisplay(int nodeIdx) {
    return null;
  }

  public int calcSuffixLink(int u) {
    if (suffixLink[u] < 0) {
      suffixLink[u] = u == ROOT || parent[u] == ROOT
          ? ROOT
          : calcNext(calcSuffixLink(parent[u]), fromLetter[u]);
    }
    return suffixLink[u];
  }

  public int calcNext(int u, int letter) {
    if (next[letter][u] < 0) {
      next[letter][u] = child[letter][u] >= 0
          ? child[letter][u]
          : u == ROOT
              ? ROOT
              : calcNext(calcSuffixLink(u), letter);
    }
    return next[letter][u];
  }
}
