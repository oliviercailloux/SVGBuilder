package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;
import io.github.oliviercailloux.jaris.exceptions.TryCatchAll;
import io.github.oliviercailloux.jaris.xml.DomHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Strings;

public class SvgHelper {
  public static Optional<String> tryGetString(Element svgElement, String attributeName) {
    return Optional.ofNullable(Strings.emptyToNull(svgElement.getAttribute(attributeName)));
  }

  public static Optional<Double> tryGetDouble(Element svgElement, String attributeName) {
    return tryGetString(svgElement, attributeName).flatMap(SvgHelper::tryParseDouble);
  }

  private static Optional<Double> tryParseDouble(String value) {
    return TryCatchAll.get(() -> Double.parseDouble(value)).getResult();
  }

  public static Optional<PositiveSize> tryGetSize(Element svgElement) {
    Optional<Double> width = tryGetDouble(svgElement, "width");
    Optional<Double> height = tryGetDouble(svgElement, "height");
    if (width.isPresent() && height.isPresent()) {
      return Optional.of(PositiveSize.given(width.orElseThrow(), height.orElseThrow()));
    }
    return Optional.empty();
  }

  public static Element setPosition(Element svgElement, DoublePoint point) {
    if (point.equals(DoublePoint.zero())) {
      svgElement.removeAttribute("x");
      svgElement.removeAttribute("y");
    } else {
      svgElement.setAttribute("x", String.valueOf(point.x()));
      svgElement.setAttribute("y", String.valueOf(point.y()));
    }

    return svgElement;
  }

  public static Element setSize(Element svgElement, PositiveSize size) {
    if (size.equals(PositiveSize.zero())) {
      svgElement.removeAttribute("width");
      svgElement.removeAttribute("height");
    } else {
      svgElement.setAttribute("width", String.valueOf(size.x()));
      svgElement.setAttribute("height", String.valueOf(size.y()));
    }
    return svgElement;
  }

  private SvgHelper() {}
}
