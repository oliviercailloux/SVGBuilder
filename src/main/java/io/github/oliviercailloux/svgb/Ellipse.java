package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Represents ellipses of a given size. Can be used as a canvas for SVG elements. */
public class Ellipse {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(Ellipse.class);

  public static Ellipse optimal(double semiHeight) {
    /*
     * Consider an ellipse of width 2a and height 2b. The inscribed rectangle with max area and
     * crossing the foci has its upper right corner at (a cos π/4, b sin π/4) = (√2 a / 2, √2 b / 2)
     * with, I think, a = √2 b. Its width is √2 a = 2 b and height is √2 b = a.
     */
    return ab(new PositiveSize(semiHeight * Math.sqrt(2d), semiHeight));
  }

  public static Ellipse ab(PositiveSize semiSize) {
    return new Ellipse(semiSize);
  }

  public static Ellipse fullSize(PositiveSize size) {
    return ab(size.mult(1d / 2d));
  }

  private final PositiveSize semiSize;

  private Ellipse(PositiveSize size) {
    this.semiSize = checkNotNull(size);
  }

  public PositiveSize semiSize() {
    return semiSize;
  }

  public PositiveSize size() {
    return semiSize.mult(2d);
  }

  public PositiveSize inscribedRectangleSize() {
    return size().mult(1d / Math.sqrt(2d));
  }

  @Override
  public boolean equals(Object o2) {
    if (!(o2 instanceof Ellipse)) {
      return false;
    }
    final Ellipse t2 = (Ellipse) o2;
    return semiSize.equals(t2.semiSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(semiSize);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("Semi size", semiSize).toString();
  }
}
