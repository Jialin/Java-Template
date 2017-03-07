package template.numbertheory.hash;

import template.collections.list.IntArrayList;
import template.numbertheory.number.IntModular;

public class DoubleHash implements Comparable<DoubleHash> {
  private static final int BASE1 = 99999989;
  private static final int BASE2 = 99999971;
  private static final IntModular MOD1 = new IntModular(1000000007);
  private static final IntModular MOD2 = new IntModular(1000000009);

  private static final IntArrayList POW1 = new IntArrayList();
  private static final IntArrayList POW2 = new IntArrayList();

  private int length;
  private int hash1, hash2;

  public DoubleHash(int length, int hash1, int hash2) {
    this.length = length;
    this.hash1 = hash1;
    this.hash2 = hash2;
  }

  public static DoubleHash empty() {
    return new DoubleHash(0, 0, 0);
  }

  public void init(int value) {
    assign(1, MOD1.fix(value), MOD2.fix(value));
  }

  public void init(DoubleHash l, DoubleHash r) {
    ensurePowCapacity(r.length);
    assign(
        l.length + r.length,
        MOD1.add(MOD1.mul(l.hash1, POW1.get(r.length)), r.hash1),
        MOD2.add(MOD2.mul(l.hash2, POW2.get(r.length)), r.hash2));
  }

  public void addLast(int value) {
    hash1 = MOD1.add(MOD1.mul(hash1, BASE1), value);
    hash2 = MOD2.add(MOD2.mul(hash2, BASE2), value);
    ++length;
  }

  public void addLast(DoubleHash o) {
    ensurePowCapacity(o.length);
    length += o.length;
    hash1 = MOD1.add(MOD1.mul(hash1, POW1.get(o.length)), o.hash1);
    hash2 = MOD2.add(MOD2.mul(hash2, POW2.get(o.length)), o.hash2);
  }

  @Override
  public int compareTo(DoubleHash o) {
    if (length != o.length) return length - o.length;
    if (hash1 != o.hash1) return hash1 - o.hash1;
    return hash2 - o.hash2;
  }

  @Override
  public String toString() {
    return String.format("length:%d hash:(%d,%d)", length, hash1, hash2);
  }

  private void assign(int length, int hash1, int hash2) {
    this.length = length;
    this.hash1 = hash1;
    this.hash2 = hash2;
  }

  private void ensurePowCapacity(int length) {
    if (POW1.size > length) return;
    if (POW1.isEmpty()) {
      POW1.add(1);
      POW2.add(1);
    }
    for (int i = POW1.size; i <= length; ++i) {
      POW1.add(MOD1.mul(POW1.peekLast(), BASE1));
      POW2.add(MOD2.mul(POW2.peekLast(), BASE2));
    }
  }
}
