package template.numbertheory.bit;

public class BitUtils {

  public static int reverse(int x) {
    // swap odd and even bits
    x = ((x >> 1) & 0x55555555) | ((x & 0x55555555) << 1);
    // swap consecutive pairs
    x = ((x >> 2) & 0x33333333) | ((x & 0x33333333) << 2);
    // swap nibbles ...
    x = ((x >> 4) & 0x0F0F0F0F) | ((x & 0x0F0F0F0F) << 4);
    // swap bytes
    x = ((x >> 8) & 0x00FF00FF) | ((x & 0x00FF00FF) << 8);
    // swap 2-byte long pairs
    x = ( x >> 16             ) | ( x               << 16);
    return x;
  }
}
