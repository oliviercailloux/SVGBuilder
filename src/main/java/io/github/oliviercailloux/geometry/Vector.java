package io.github.oliviercailloux.geometry;

public interface Vector {
  @SuppressWarnings({"checkstyle:MethodName"})
  double x();

  @SuppressWarnings({"checkstyle:MethodName"})
  double y();

  Vector plus(Vector p);

  /** Equivalent to <code>plus(p.opposite())</code>. */
  default Vector minus(Vector p) {
    return plus(p.opposite());
  }

  /** The displacement which, added to this vector, yields the origin. */
  Displacement opposite();
}
