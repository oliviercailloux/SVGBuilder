package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkArgument;

import org.w3c.dom.Element;

public class LineElement {
  public static final String NODE_NAME = "line";

  private final Element element;

  public static LineElement using(Element line) {
    return new LineElement(line);
  }

  public LineElement(Element line) {
    element = line;
    checkArgument(line.getNodeName().equals(NODE_NAME));
  }

  public Element getElement() {
    return element;
  }

  public LineElement setStart(DoublePoint start) {
    element.setAttribute("x1", String.valueOf(start.x()));
    element.setAttribute("y1", String.valueOf(start.y()));
    return this;
  }

  public LineElement setDestination(DoublePoint destination) {
    element.setAttribute("x2", String.valueOf(destination.x()));
    element.setAttribute("y2", String.valueOf(destination.y()));
    return this;
  }
}
