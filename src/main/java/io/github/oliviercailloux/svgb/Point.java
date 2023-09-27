package io.github.oliviercailloux.svgb;

public record Point (int x, int y) {

  public Point plus(Point p) {
    return new Point(x + p.x, y + p.y);
  }
}
