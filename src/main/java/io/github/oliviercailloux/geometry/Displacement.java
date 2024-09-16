package io.github.oliviercailloux.geometry;

/** Positive or negative. */
public record Displacement (double right, double down) implements Vector {

  public static Displacement noMove() {
    return new Displacement(0d, 0d);
  }

  public static Displacement between(Vector start, Point end) {
    return new Displacement(end.x() - start.x(), end.y() - start.y());
  }

  public static Displacement allDirections(double length) {
    return new Displacement(length, length);
  }

  public static Displacement given(double x, double y) {
    return new Displacement(x, y);
  }

  public static Displacement horizontal(double right) {
    return new Displacement(right, 0d);
  }

  public static Displacement vertical(double down) {
    return new Displacement(0d, down);
  }

  @Override
  public double x() {
    return right;
  }

  @Override
  public double y() {
    return down;
  }

  @Override
  public Displacement moveBy(Vector p) {
    return new Displacement(right + p.x(), down + p.y());
  }

  public Displacement min(Displacement p) {
    return new Displacement(Math.min(right, p.right()), Math.min(down, p.down()));
  }

  public Displacement max(Displacement p) {
    return new Displacement(Math.max(right, p.right()), Math.max(down, p.down()));
  }

  public Displacement mult(double factor) {
    return new Displacement(right * factor, down * factor);
  }

  public Displacement opposite() {
    return mult(-1d);
  }

  public String coords() {
    return "(" + String.valueOf(right) + ", " + String.valueOf(down) + ")";
  }
}
