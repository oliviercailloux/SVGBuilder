package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkNotNull;

import io.github.oliviercailloux.jaris.xml.DomHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
}
