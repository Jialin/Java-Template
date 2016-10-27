package template.graph.blockable;

public interface Blockable {

  void block(int edgeIdx);
  boolean blocked(int edgeIdx);
}
