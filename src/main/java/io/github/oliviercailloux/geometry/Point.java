package io.github.oliviercailloux.geometry;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.MoreObjects;
import java.util.Objects;

@SuppressWarnings("checkstyle:OverloadMethodsDeclarationOrder")
public record Point (double x, double y) {

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

  public Point horizontal() {
    return Point.horizontal(x);
  }

  public Point vertical() {
    return Point.vertical(y);
  }

  /** Only if the result is non negative. */
  public Point plus(Displacement p) {
    return new Point(x + p.right(), y + p.down());
  }

  /** Equivalent to <code>plus(p.opposite())</code>. */
  /** Only if the result is non negative. */
  public Point minus(Displacement p) {
    return new Point(x - p.right(), y - p.down());
  }

  /** A positive scaling factor */
  public Point mult(double scale) {
    return mult(scale, scale);
  }

  public Point mult(double scaleX, double scaleY) {
    checkArgument(scaleX >= 0d);
    checkArgument(scaleY >= 0d);
    return new Point(x * scaleX, y * scaleY);
  }

  public String coordinates() {
    return "(" + Double.toString(x()) + ", " + Double.toString(y()) + ")";
  }
}
