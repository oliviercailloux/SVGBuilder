package io.github.oliviercailloux.geometry;

import com.google.common.base.MoreObjects;
import java.util.Objects;

/**
 * Positive or negative. Note that (I think that) there is no reason to request a Displacement as a
 * parameter to a method, rather accept a Vector.
 */
@SuppressWarnings("checkstyle:OverloadMethodsDeclarationOrder")
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
  public Displacement horizontal() {
    return Displacement.horizontal(right);
  }

  @Override
  public Displacement vertical() {
    return Displacement.vertical(down);
  }

  public Displacement min(Displacement p) {
    return new Displacement(Math.min(right, p.right()), Math.min(down, p.down()));
  }

  public Displacement max(Displacement p) {
    return new Displacement(Math.max(right, p.right()), Math.max(down, p.down()));
  }

  @Override
  public Displacement plus(Vector p) {
    return new Displacement(right + p.x(), down + p.y());
  }

  @Override
  public Displacement minus(Vector p) {
    return new Displacement(right - p.x(), down - p.y());
  }

  @Override
  public Displacement mult(double factor) {
    return mult(factor, factor);
  }

  @Override
  public Displacement mult(double factorX, double factorY) {
    return new Displacement(right * factorX, down * factorY);
  }

  @Override
  public Displacement opposite() {
    return mult(-1d);
  }
}
