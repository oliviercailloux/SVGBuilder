package io.github.oliviercailloux.geometry;

import static com.google.common.base.Preconditions.checkArgument;

/** Or size */
public record Point (double x, double y) implements Vector {

  public static Point origin() {
    return new Point(0d, 0d);
  }

  public static Point zero() {
    return new Point(0d, 0d);
  }

  public static Point given(double x, double y) {
    return new Point(x, y);
  }

  public static Point square(double length) {
    return new Point(length, length);
  }

  public static Point horizontal(double length) {
    return new Point(length, 0d);
  }

  public static Point vertical(double height) {
    return new Point(0d, height);
  }

  public Point {
    checkArgument(x >= 0d);
    checkArgument(y >= 0d);
  }

  /** Only if the result is positive. */
  @Override
  public Point moveBy(Vector p) {
    return new Point(x + p.x(), y + p.y());
  }

  /** A positive scaling factor */
  public Point mult(double scale) {
    checkArgument(scale >= 0d);
    return new Point(x * scale, y * scale);
  }

  @Override
  public Displacement opposite() {
    return Displacement.between(this, Point.origin());
  }
}
