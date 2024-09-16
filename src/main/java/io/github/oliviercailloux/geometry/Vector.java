package io.github.oliviercailloux.geometry;

public interface Vector {
  @SuppressWarnings({"checkstyle:MethodName"})
  double x();
  
  @SuppressWarnings({"checkstyle:MethodName"})
  double y();

  Vector moveBy(Vector p);

  /** The displacement which, added to this vector, yields the origin. */
  Displacement opposite();
}
