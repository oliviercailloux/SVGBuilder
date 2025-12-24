package io.github.oliviercailloux.geometry;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Range;
import java.util.Objects;
import java.util.stream.Stream;

public class Zone {
  public static Zone at(Point singular) {
    return new Zone(singular, singular);
  }

  @SuppressWarnings("unused")
  private static Zone cornered(Point corner, Displacement move) {
    return new Zone(corner, corner.plus(move));
  }

  private static Zone centered(Point center, Size size) {
    Displacement semiSize = size.mult(0.5).asDisplacement();
    return new Zone(center.plus(semiSize.opposite()), center.plus(semiSize));
  }

  private static Zone enclosingStatic(Point... corners) {
    Range<Double> xs = Range.encloseAll(Stream.of(corners).mapToDouble(Point::x)::iterator);
    Range<Double> ys = Range.encloseAll(Stream.of(corners).mapToDouble(Point::y)::iterator);
    Point start = Point.given(xs.lowerEndpoint(), ys.lowerEndpoint());
    Point end = Point.given(xs.upperEndpoint(), ys.upperEndpoint());
    return new Zone(start, end);
  }

  @SuppressWarnings("unused")
  private static Zone cornerMove(Point corner, Displacement move) {
    return Zone.enclosingStatic(corner, corner.plus(move));
  }

  private final Point start;
  private final Point end;

  private Zone(Point start, Point end) {
    checkArgument(start.x() <= end.x());
    checkArgument(start.y() <= end.y());
    this.start = start;
    this.end = end;
  }

  public Point start() {
    return start;
  }

  public Size size() {
    return Size.between(start, end);
  }

  public Point end() {
    return end;
  }

  public Point center() {
    return start.plus(Displacement.between(start, end).mult(0.5));
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
    return Zone.enclosingStatic(start, end, start.plus(extension), end.plus(extension));
  }

  public Zone enclosing(Point... corners) {
    return Zone.enclosingStatic(
        Stream.concat(Stream.of(corners), Stream.of(start, end)).toArray(Point[]::new));
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
