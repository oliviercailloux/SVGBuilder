package io.github.oliviercailloux.svgb;

import static com.google.common.base.Verify.verify;

import io.github.oliviercailloux.geometry.Point;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;
import org.w3c.dom.Element;

public class SvgHelper {
  /** US format used for decimal numbers, max 4 decimal places */
  public static DecimalFormat DECIMAL_FORMAT =
      new DecimalFormat("0.####", new DecimalFormatSymbols(Locale.US));

  public static String format(double value) {
    return DECIMAL_FORMAT.format(value);
  }

  /**
   * default value iff attribute absent or empty string. Throws iff attribute value is a string that
   * cannot be parsed.
   */
  public static double getDouble(Element svgElement, String attributeName, double defaultValue)
      throws NumberFormatException {
    String value = svgElement.getAttribute(attributeName);
    if (value.isEmpty()) {
      return defaultValue;
    }
    return Double.parseDouble(value);
  }

  public static Point getPosition(Element svgElement) {
    double x = getDouble(svgElement, "x", 0d);
    double y = getDouble(svgElement, "y", 0d);
    return Point.given(x, y);
  }

  public static Element setPosition(Element svgElement, Point point) {
    if (point.equals(Point.zero())) {
      svgElement.removeAttribute("x");
      svgElement.removeAttribute("y");
    } else {
      svgElement.setAttribute("x", format(point.x()));
      svgElement.setAttribute("y", format(point.y()));
    }

    return svgElement;
  }

  /**
   * Empty iff both direction attributes satisfy: absent or empty string. Throws iff either
   * direction attribute value is a string that cannot be parsed. Throws iff one is absent or empty
   * and the other one can be parsed.
   */
  public static Optional<Point> tryGetSize(Element svgElement) throws NumberFormatException {
    double width = getDouble(svgElement, "width", Double.NaN);
    double height = getDouble(svgElement, "height", Double.NaN);
    if (Double.isNaN(width) != Double.isNaN(height)) {
      throw new IllegalStateException(
          "One of the two attributes is absent or empty, the other one is not.");
    }
    if (Double.isNaN(width) && Double.isNaN(height)) {
      return Optional.empty();
    }
    verify(!Double.isNaN(width));
    verify(!Double.isNaN(height));
    return Optional.of(Point.given(width, height));
  }

  public static Element setSize(Element svgElement, Point size) {
    svgElement.setAttribute("width", format(size.x()));
    svgElement.setAttribute("height", format(size.y()));
    return svgElement;
  }

  private SvgHelper() {}
}
