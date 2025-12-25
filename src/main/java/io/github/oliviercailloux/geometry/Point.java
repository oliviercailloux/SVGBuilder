package io.github.oliviercailloux.geometry;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.MoreObjects;
import java.util.Objects;

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

  public Point {
    checkArgument(x >= 0d);
    checkArgument(y >= 0d);
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

  /** A non negative scaling factor */
  public Point mult(double scale) {
    return mult(Size.square(scale));
  }

  public Point mult(Size scale) {
    return new Point(x * scale.width(), y * scale.height());
  }

  public String coordinates() {
    return "(" + Double.toString(x()) + ", " + Double.toString(y()) + ")";
  }
}
