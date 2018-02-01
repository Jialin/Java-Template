package template.numbertheory.complex;

import template.io.Displayable;

public class Complex implements Displayable {

  public double real, imag;

  public Complex(double real, double imag) {
    this.real = real;
    this.imag = imag;
  }

  public static Complex zero() {
    return new Complex(0, 0);
  }

  public static Complex polar(double r, double angle) {
    return new Complex(r * Math.cos(angle), r * Math.sin(angle));
  }

  public static Complex mul(Complex a, Complex b) {
    return new Complex(a.real * b.real - a.imag * b.imag, a.real * b.imag + a.imag * b.real);
  }

  /** this = complex(real, imag) */
  public void init(double real, double imag) {
    this.real = real;
    this.imag = imag;
  }

  /** this = o */
  public void init(Complex o) {
    real = o.real;
    imag = o.imag;
  }

  /** this = complex(0, 0) */
  public void initZero() {
    this.real = 0;
    this.imag = 0;
  }

  /** this = complex(r*cos(angle), r*sin(angle)) */
  public void initPolar(double r, double angle) {
    real = r * Math.cos(angle);
    imag = r * Math.sin(angle);
  }

  /** this = a + b */
  public void initAdd(Complex a, Complex b) {
    assign(a.real + b.real, a.imag + b.imag);
  }

  /** this = a - b */
  public void initSub(Complex a, Complex b) {
    assign(a.real - b.real, a.imag - b.imag);
  }

  /** this = a * b */
  public void initMul(Complex a, Complex b) {
    assign(a.real * b.real - a.imag * b.imag, a.real * b.imag + a.imag * b.real);
  }

  /** this += o */
  public void add(Complex o) {
    assign(real + o.real, imag + o.imag);
  }

  /** this /= scale */
  public void shrink(double scale) {
    assign(real / scale, imag / scale);
  }

  /** this *= o */
  public void mul(Complex o) {
    assign(real * o.real - imag * o.imag, real * o.imag + imag * o.real);
  }

  @Override
  public Complex clone() {
    return new Complex(real, imag);
  }

  @Override
  public String toDisplay() {
    return String.format(imag < 0 ? "%f%fi" : "%f+%fi", real, imag);
  }

  private void assign(double real, double imag) {
    this.real = real;
    this.imag = imag;
  }
}
