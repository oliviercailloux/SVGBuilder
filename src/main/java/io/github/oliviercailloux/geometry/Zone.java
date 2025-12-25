package io.github.oliviercailloux.geometry;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Range;
import java.util.Objects;
import java.util.stream.Stream;

public class Zone {
  public static Zone at(Point corner, Displacement move) {
    return new Zone(corner, corner.plus(move));
  }

  public static Zone at(Point corner, Size size) {
    return Zone.at(corner, size.asDisplacement());
  }

  private static Zone centered(Point center, Size size) {
    Displacement semiSize = size.mult(0.5).asDisplacement();
    return new Zone(center.plus(semiSize.opposite()), center.plus(semiSize));
  }

  public static Zone enclosing(Point... corners) {
    Range<Double> xs = Range.encloseAll(Stream.of(corners).mapToDouble(Point::x)::iterator);
    Range<Double> ys = Range.encloseAll(Stream.of(corners).mapToDouble(Point::y)::iterator);
    Point start = Point.given(xs.lowerEndpoint(), ys.lowerEndpoint());
    Point end = Point.given(xs.upperEndpoint(), ys.upperEndpoint());
    return new Zone(start, end);
  }

  public static Zone enclosing(Zone... zones) {
    Point[] corners = Stream.of(zones).flatMap(z -> Stream.of(z.topLeft(), z.bottomRight()))
        .toArray(Point[]::new);
    return enclosing(corners);
  }

  @SuppressWarnings("unused")
  private static Zone cornerMove(Point corner, Displacement move) {
    return Zone.enclosing(corner, corner.plus(move));
  }

  private final Point start;
  private final Point end;

  private Zone(Point start, Point end) {
    checkArgument(start.x() <= end.x());
    checkArgument(start.y() <= end.y());
    this.start = start;
    this.end = end;
  }

  public Point topLeft() {
    return start;
  }

  public Point topRight() {
    return Point.given(end.x(), start.y());
  }

  public Point bottomLeft() {
    return Point.given(start.x(), end.y());
  }

  public Point bottomRight() {
    return end;
  }

  public Size size() {
    return Size.between(start, end);
  }

  public Point center() {
    return start.plus(size().asDisplacement().mult(0.5));
  }

  /** Only if the resulting upper left corner is non negative. */
  public Zone move(Displacement move) {
    return new Zone(start.plus(move), end.plus(move));
  }

  public Zone resizedFixedCenter(Size newSize) {
    return Zone.centered(center(), newSize);
  }

  /**
   * Moves the bottom right corner iff given extension is positive, moves the upper left corner iff
   * given extension is negative. Equivalently, the smallest zone that encloses each of its current
   * corners and each of its current corners plus the given extension.
   */
  public Zone extend(Displacement extension) {
    return Zone.enclosing(start, end, start.plus(extension), end.plus(extension));
  }

  public Zone andEnclosing(Point... corners) {
    return Zone
        .enclosing(Stream.concat(Stream.of(corners), Stream.of(start, end)).toArray(Point[]::new));
  }

  /** Mainly conceived as a debug string, more compact (but less explicit) than toString(). */
  public String coordinates() {
    return start.coordinates() + " → " + end.coordinates();
  }

  @Override
  public boolean equals(Object o2) {
    if (!(o2 instanceof Zone)) {
      return false;
    }
    final Zone t2 = (Zone) o2;
    return start.equals(t2.start) && end.equals(t2.end);
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, end);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("start", start).add("end", end).toString();
  }
}
