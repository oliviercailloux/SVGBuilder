package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkArgument;

import io.github.oliviercailloux.geometry.Point;
import io.github.oliviercailloux.geometry.Zone;
import org.w3c.dom.Element;

public class RectangleElement {
  public static final String NODE_NAME = "rect";

  private final Element element;

  public static RectangleElement using(Element rect) {
    return new RectangleElement(rect);
  }

  private RectangleElement(Element rect) {
    element = rect;
    checkArgument(rect.getNodeName().equals(NODE_NAME));
  }

  public Element getElement() {
    return element;
  }

  private double x() {
    return SvgHelper.getDouble(element, "x", 0d);
  }

  private double y() {
    return SvgHelper.getDouble(element, "y", 0d);
  }

  public Point getStart() {
    return Point.given(x(), y());
  }

  private double width() {
    return SvgHelper.getDouble(element, "width", 0d);
  }

  private double height() {
    return SvgHelper.getDouble(element, "height", 0d);
  }

  public Point getSize() {
    return Point.given(width(), height());
  }

  public RectangleElement across(Zone zone) {
    return setStart(zone.start()).setSize(zone.size());
  }

  public RectangleElement setStart(Point start) {
    SvgHelper.setPosition(element, start);
    return this;
  }

  public RectangleElement setSize(Point size) {
    if (size.equals(Point.origin())) {
      element.removeAttribute("width");
      element.removeAttribute("height");
    } else {
      element.setAttribute("width", SvgHelper.format(size.x()));
      element.setAttribute("height", SvgHelper.format(size.y()));
    }
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
