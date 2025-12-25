package io.github.oliviercailloux.geometry;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Range;
import java.util.Objects;
import java.util.stream.Stream;

public record Size (double width, double height) {

  public static Size zero() {
    return new Size(0d, 0d);
  }

  static Size between(Point... corners) {
    Range<Double> xs = Range.encloseAll(Stream.of(corners).mapToDouble(Point::x)::iterator);
    Range<Double> ys = Range.encloseAll(Stream.of(corners).mapToDouble(Point::y)::iterator);
    Point start = Point.given(xs.lowerEndpoint(), ys.lowerEndpoint());
    Point end = Point.given(xs.upperEndpoint(), ys.upperEndpoint());
    return new Size(end.x() - start.x(), end.y() - start.y());
  }

  public static Size given(double width, double height) {
    return new Size(width, height);
  }

  public static Size square(double length) {
    return new Size(length, length);
  }

  public Size {
    checkArgument(width >= 0d);
    checkArgument(height >= 0d);
  }

  public Size plus(Size p) {
    return new Size(width + p.width(), height + p.height());
  }

  /** Only if the result is non negative. */
  public Size minus(Size p) {
    return new Size(width - p.width(), height - p.height());
  }

  /** A non negative scaling factor */
  public Size mult(double scale) {
    return mult(Size.square(scale));
  }

  public Size mult(Size scale) {
    return new Size(width * scale.width(), height * scale.height());
  }

  public Displacement asDisplacement() {
    return new Displacement(width, height);
  }

  public String asString() {
    return Double.toString(width()) + " × " + Double.toString(height());
  }
}
