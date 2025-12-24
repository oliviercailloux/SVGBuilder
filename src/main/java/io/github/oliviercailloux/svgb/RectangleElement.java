package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkArgument;

import io.github.oliviercailloux.geometry.Point;
import io.github.oliviercailloux.geometry.Size;
import io.github.oliviercailloux.geometry.Zone;
import io.github.oliviercailloux.jaris.xml.DomHelper;
import io.github.oliviercailloux.jaris.xml.XmlName;
import org.w3c.dom.Element;

public class RectangleElement {
  public static final String NODE_NAME = "rect";
  public static final XmlName XML_NAME =
      XmlName.expandedName(SvgDocumentHelper.SVG_NS_URI, NODE_NAME);

  private final Element element;

  public static RectangleElement using(Element rect) {
    return new RectangleElement(rect);
  }

  private RectangleElement(Element rect) {
    element = rect;
    checkArgument(DomHelper.xmlName(rect).equals(XML_NAME));
  }

  public Element element() {
    return element;
  }

  private double x() {
    return SvgHelper.getDouble(element, "x", 0d);
  }

  private double y() {
    return SvgHelper.getDouble(element, "y", 0d);
  }

  private Point start() {
    return Point.given(x(), y());
  }

  private double width() {
    return SvgHelper.getDouble(element, "width", 0d);
  }

  private double height() {
    return SvgHelper.getDouble(element, "height", 0d);
  }

  private Size size() {
    return Size.given(width(), height());
  }

  @SuppressWarnings("unused")
  private Point end() {
    return start().plus(size().asDisplacement());
  }

  public Zone zone() {
    return Zone.at(start()).resizedFixedCenter(size());
  }

  public RectangleElement setZone(Zone zone) {
    SvgHelper.setZone(element, zone);
    return this;
  }

  public RectangleElement setRounding(double rx) {
    checkArgument(rx >= 0d);
    element.setAttribute("rx", SvgHelper.format(rx));
    return this;
  }

  public RectangleElement setFillOpacity(double opacity) {
    checkArgument(0d <= opacity);
    checkArgument(opacity <= 1d);
    element.setAttribute("fill-opacity", SvgHelper.format(opacity));
    return this;
  }

  public RectangleElement setStyle(String style) {
    if (style.isEmpty()) {
      element.removeAttribute("style");
    } else {
      element.setAttribute("style", style);
    }
    return this;
  }
}
