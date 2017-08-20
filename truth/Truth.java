package template.truth;

public class Truth {

  public static void assertThat(boolean o) {
    if (!o) throw new IllegalArgumentException();
  }

  public static void fail() {
    throw new IllegalArgumentException();
  }
}
