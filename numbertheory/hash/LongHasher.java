package template.numbertheory.hash;

public class LongHasher {

  private static long START_SEED = 100000000000000003L;
  private static long BASE = 1000000007;
  private static long END_SEED = 1000000000000000003L;

  private final long startSeed, base, endSeed;

  public LongHasher() {
    this(START_SEED, BASE, END_SEED);
  }

  public LongHasher(long startSeed, long base, long endSeed) {
    this.startSeed = startSeed;
    this.base = base;
    this.endSeed = endSeed;
  }

  public long calc1(long value) {
    return (startSeed ^ value) * endSeed;
  }

  public long calc2(long value1, long value2) {
    return ((startSeed
        ^ calc1(value1)) * base
        ^ calc1(value2)) * endSeed;
  }
}
