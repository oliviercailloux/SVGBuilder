package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Optional;

import org.w3c.dom.Element;

import com.google.common.base.Strings;

public class RectangleElement {
  public static final String NODE_NAME = "rect";

  private final Element element;

  public static RectangleElement using(Element rect) {
    return new RectangleElement(rect);
  }

  public RectangleElement(Element rect) {
    element = rect;
    checkArgument(rect.getNodeName().equals(NODE_NAME));
  }

  public Element getElement() {
    return element;
  }

  private double x() {
    return SvgHelper.tryGetDouble(element, "x").orElse(0d);
  }
  
  private double y() {
    return SvgHelper.tryGetDouble(element, "y").orElse(0d);
  }

  public DoublePoint getStart() {
    return DoublePoint.given(x(), y());
  }

  private double width() {
    return SvgHelper.tryGetDouble(element, "width").orElse(0d);
  }
  
  private double height() {
    return SvgHelper.tryGetDouble(element, "height").orElse(0d);
  }

  public PositiveSize getSize() {
    return PositiveSize.given(width(), height());
  }

  public RectangleElement setStart(DoublePoint start) {
    if (start.equals(DoublePoint.zero())) {
      element.removeAttribute("x");
      element.removeAttribute("y");
    } else {
      element.setAttribute("x", String.valueOf(start.x()));
      element.setAttribute("y", String.valueOf(start.y()));
    }
    return this;
  }

  public RectangleElement setSize(PositiveSize size) {
    if (size.equals(PositiveSize.zero())) {
      element.removeAttribute("width");
      element.removeAttribute("height");
    } else {
      element.setAttribute("width", String.valueOf(size.x()));
      element.setAttribute("height", String.valueOf(size.y()));
    }
    return this;
  }

  public RectangleElement setRounding(double rx) {
    checkArgument(rx >= 0d);
    element.setAttribute("rx", String.valueOf(rx));
    return this;
  }

  public RectangleElement setFillOpacity(double opacity) {
    checkArgument(0d <= opacity);
    checkArgument(opacity <= 1d);
    element.setAttribute("fill-opacity", String.valueOf(opacity));
    return this;
  }

  public RectangleElement setStyle(String style) {
    if (style.isEmpty())
      element.removeAttribute("style");
    else
      element.setAttribute("style", style);
    return this;
  }
}
