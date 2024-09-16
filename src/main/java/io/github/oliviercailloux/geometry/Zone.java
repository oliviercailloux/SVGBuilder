package io.github.oliviercailloux.geometry;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.collect.Range;
import java.util.stream.Stream;

public class Zone {

  public static Zone centered(Point center, Point size) {
    Point semiSize = size.mult(0.5);
    return new Zone(center.moveBy(semiSize.opposite()), center.moveBy(semiSize));
  }

  public static Zone at(Point... corners) {
    Range<Double> xs = Range.encloseAll(Stream.of(corners).mapToDouble(Point::x)::iterator);
    Range<Double> ys = Range.encloseAll(Stream.of(corners).mapToDouble(Point::y)::iterator);
    Point start = Point.given(xs.lowerEndpoint(), ys.lowerEndpoint());
    Point end = Point.given(xs.upperEndpoint(), ys.upperEndpoint());
    return new Zone(start, end);
  }

  public static Zone cornerMove(Point corner, Displacement move) {
    return Zone.at(corner, corner.moveBy(move));
  }

  private final Point start;
  private final Point end;

  public Zone(Point start, Point end) {
    checkArgument(start.x() <= end.x());
    checkArgument(start.y() <= end.y());
    this.start = start;
    this.end = end;
  }

  public Point start() {
    return start;
  }

  public Displacement across() {
    return Displacement.between(start, end);
  }

  public Point size() {
    return end.moveBy(start.opposite());
  }

  public Point end() {
    return end;
  }

  public Point center() {
    return start.moveBy(size().mult(0.5));
  }
}
