package io.github.oliviercailloux.geometry;

interface IntVector {
  @SuppressWarnings({"checkstyle:MethodName"})
  int x();

  @SuppressWarnings({"checkstyle:MethodName"})
  int y();

  IntVector moveBy(IntVector p);
}
