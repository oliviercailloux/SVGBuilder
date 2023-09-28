package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkArgument;

import org.w3c.dom.Element;

public class SquareElement {
  public static final String NODE_NAME = "rect";

  private final Element element;

  public static SquareElement using(Element rect) {
    return new SquareElement(rect);
  }

  public SquareElement(Element rect) {
    element = rect;
    checkArgument(rect.getNodeName().equals(NODE_NAME));
  }

  public Element getElement() {
    return element;
  }
  
  public SquareElement setStart(DoublePoint start) {
    element.setAttribute("x", String.valueOf(start.x()));
    element.setAttribute("y", String.valueOf(start.y()));
    return this;
  }

  public SquareElement setSize(double size) {
    checkArgument(size >= 0d);
    element.setAttribute("width", String.valueOf(size));
    element.setAttribute("height", String.valueOf(size));
    return this;
  }

  public SquareElement setRounding(double rx) {
    checkArgument(rx >= 0d);
    element.setAttribute("rx", String.valueOf(rx));
    return this;
  }
}
