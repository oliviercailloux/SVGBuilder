package io.github.oliviercailloux.geometry;

import static com.google.common.base.Preconditions.checkArgument;

/** Or size */
public record IntPoint (int x, int y) implements IntVector {

  public static IntPoint origin() {
    return new IntPoint(0, 0);
  }

  public static IntPoint zero() {
    return new IntPoint(0, 0);
  }

  public static IntPoint given(int x, int y) {
    return new IntPoint(x, y);
  }

  public static IntPoint square(int length) {
    return new IntPoint(length, length);
  }

  public static IntPoint horizontal(int length) {
    return new IntPoint(length, 0);
  }

  public static IntPoint vertical(int height) {
    return new IntPoint(0, height);
  }

  public IntPoint {
    checkArgument(x >= 0);
    checkArgument(y >= 0);
  }

  /** Only if the result is positive. */
  @Override
  public IntPoint moveBy(IntVector p) {
    return new IntPoint(x + p.x(), y + p.y());
  }

  /** A positive scaling factor */
  public IntPoint mult(int scale) {
    checkArgument(scale >= 0d);
    return new IntPoint(x * scale, y * scale);
  }
}
