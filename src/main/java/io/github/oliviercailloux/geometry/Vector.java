package io.github.oliviercailloux.geometry;

public interface Vector {
  @SuppressWarnings({"checkstyle:MethodName"})
  double x();

  @SuppressWarnings({"checkstyle:MethodName"})
  double y();

  /** The displacement which, added to this vector, yields the origin. */
  Displacement opposite();

  /** Returns the addition of this vector and the given one, if the result is non-negative. */
  Vector plus(Vector p);

  /** Equivalent to <code>plus(p.opposite())</code>. */
  default Vector minus(Vector p) {
    return plus(p.opposite());
  }

  Vector mult(double factor);

  Vector mult(double factorX, double factorY);

  default String coordinates() {
    return "(" + String.valueOf(x()) + ", " + String.valueOf(y()) + ")";
  }
}
