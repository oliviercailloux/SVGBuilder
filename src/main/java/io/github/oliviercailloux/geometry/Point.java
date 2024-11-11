package io.github.oliviercailloux.geometry;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.MoreObjects;
import java.util.Objects;

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

  @Override
  public Point horizontal() {
    return Point.horizontal(x);
  }

  @Override
  public Point vertical() {
    return Point.vertical(y);
  }

  /** Only if the result is non negative. */
  @Override
  public Point plus(Vector p) {
    return new Point(x + p.x(), y + p.y());
  }

  /** Only if the result is non negative. */
  @Override
  public Point minus(Vector p) {
    return new Point(x - p.x(), y - p.y());
  }

  /** A positive scaling factor */
  @Override
  public Point mult(double scale) {
    return mult(scale, scale);
  }

  @Override
  public Point mult(double scaleX, double scaleY) {
    checkArgument(scaleX >= 0d);
    checkArgument(scaleY >= 0d);
    return new Point(x * scaleX, y * scaleY);
  }

  @Override
  public Displacement opposite() {
    return Displacement.between(this, Point.origin());
  }
}
