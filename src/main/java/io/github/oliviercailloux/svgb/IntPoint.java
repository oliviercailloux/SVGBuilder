package io.github.oliviercailloux.svgb;

public record IntPoint (int x, int y) {

  public IntPoint plus(IntPoint p) {
    return new IntPoint(x + p.x, y + p.y);
  }
}
