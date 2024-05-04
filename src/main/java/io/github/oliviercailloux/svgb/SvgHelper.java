package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import io.github.oliviercailloux.jaris.xml.DomHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Strings;

public class SvgHelper {
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

  public static Optional<String> tryGetString(Element svgElement, String attributeName) {
    return Optional.ofNullable(Strings.emptyToNull(svgElement.getAttribute(attributeName)));
  }

  public static Optional<Double> tryGetDouble(Element svgElement, String attributeName) {
    return tryGetString(svgElement, attributeName).map(Double::parseDouble);
  }

  private SvgHelper() {}
}
