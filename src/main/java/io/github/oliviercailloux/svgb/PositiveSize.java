package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkArgument;

public record PositiveSize (double x, double y) implements MathSize {

  public static PositiveSize zero() {
    return new PositiveSize(0d, 0d);
  }

  public static PositiveSize given(double x, double y) {
    return new PositiveSize(x, y);
  }

  public static PositiveSize square(double length) {
    return new PositiveSize(length, length);
  }  

  public static PositiveSize between(DoublePoint start, DoublePoint end) {
    return new PositiveSize(end.x() - start.x(), end.y() - start.y());
  }
  
  public static PositiveSize horizontal(double length) {
    return new PositiveSize(length, 0d);
  }  
  
  public static PositiveSize vertical(double height) {
    return new PositiveSize(0d, height);
  }  

  public PositiveSize {
    checkArgument(Double.isFinite(x));
    checkArgument(Double.isFinite(y));
    checkArgument(x >= 0d);
    checkArgument(y >= 0d);
  }

  public PositiveSize plus(MathSize p) {
    return new PositiveSize(x + p.x(), y + p.y());
  }

  public MathSize multMath(double factor) {
    return new GeneralSize(x * factor, y * factor);
  }
  
  public PositiveSize mult(double factor) {
    checkArgument(factor >= 0d);
    return new PositiveSize(x * factor, y * factor);
  }
  
    public MathSize opposite() {
      return multMath(-1d);
    }
}
