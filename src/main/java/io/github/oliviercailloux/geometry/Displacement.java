package io.github.oliviercailloux.geometry;

import com.google.common.base.MoreObjects;
import java.util.Objects;

/**
 * Positive or negative.
 */
public record Displacement (double right, double down) {

  public static Displacement noMove() {
    return new Displacement(0d, 0d);
  }

  public static Displacement between(Point start, Point end) {
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

  public Displacement horizontal() {
    return Displacement.horizontal(right);
  }

  public Displacement vertical() {
    return Displacement.vertical(down);
  }

  public Displacement min(Displacement p) {
    return new Displacement(Math.min(right, p.right()), Math.min(down, p.down()));
  }

  public Displacement max(Displacement p) {
    return new Displacement(Math.max(right, p.right()), Math.max(down, p.down()));
  }

  public Displacement plus(Displacement p) {
    return new Displacement(right + p.right(), down + p.down());
  }

  /** Equivalent to <code>plus(p.opposite())</code>. */
  public Displacement minus(Displacement p) {
    return new Displacement(right - p.right(), down - p.down());
  }

  public Displacement mult(double factor) {
    return mult(factor, factor);
  }

  public Displacement mult(double factorX, double factorY) {
    return new Displacement(right * factorX, down * factorY);
  }

  /** The displacement which, added to this displacement, yields the origin. */
  public Displacement opposite() {
    return mult(-1d);
  }

  public String coordinates() {
    return "Δ(" + Double.toString(right()) + ", " + Double.toString(down()) + ")";
  }
}
