package io.github.oliviercailloux.svgb;

public record DoublePoint (double x, double y) {

  public static DoublePoint zero() {
    return new DoublePoint(0d, 0d);
  }

  public DoublePoint plus(DoublePoint p) {
    return new DoublePoint(x + p.x, y + p.y);
  }

  public DoublePoint plus(PositiveSize p) {
    return new DoublePoint(x + p.x(), y + p.y());
  }

  public DoublePoint max(DoublePoint p) {
    return new DoublePoint(Math.max(x, p.x()), Math.max(y, p.y()));
  }

  public String coords() {
    return "(" + String.valueOf(x) + ", " + String.valueOf(y) + ")";
  }
}
