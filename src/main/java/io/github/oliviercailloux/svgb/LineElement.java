package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Verify.verify;

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

  /** Does not support length or percentage. */
  public DoublePoint getStart() {
    checkState(element.getAttribute("x1").isEmpty() == element.getAttribute("y1").isEmpty());
    String xStr = element.getAttribute("x1");
    double x = xStr.isEmpty() ? 0d : Double.parseDouble(xStr);
    String yStr = element.getAttribute("y1");
    double y = yStr.isEmpty() ? 0d : Double.parseDouble(yStr);
    return DoublePoint.given(x, y);
  }

  public LineElement setStart(DoublePoint start) {
    element.setAttribute("x1", String.valueOf(start.x()));
    element.setAttribute("y1", String.valueOf(start.y()));
    return this;
  }
  
  /** Does not support length or percentage. */
  public DoublePoint getDestination() {
    checkState(element.getAttribute("x2").isEmpty() == element.getAttribute("y2").isEmpty());
    String xStr = element.getAttribute("x2");
    double x = xStr.isEmpty() ? 0d : Double.parseDouble(xStr);
    String yStr = element.getAttribute("y2");
    double y = yStr.isEmpty() ? 0d : Double.parseDouble(yStr);
    return DoublePoint.given(x, y);
  }

  public LineElement setDestination(DoublePoint destination) {
    element.setAttribute("x2", String.valueOf(destination.x()));
    element.setAttribute("y2", String.valueOf(destination.y()));
    return this;
  }

  public LineElement setSize(PositiveSize size) {
    checkState(element.getAttribute("x1").isEmpty() == element.getAttribute("y1").isEmpty());
    boolean startSet = !element.getAttribute("x1").isEmpty();
    checkState(element.getAttribute("x2").isEmpty() == element.getAttribute("y2").isEmpty());
    boolean destSet = !element.getAttribute("x2").isEmpty();
    checkArgument(!startSet || !destSet);
    if (!destSet) {
      DoublePoint start = getStart();
      setDestination(start.plus(size));
    } else {
      verify(!startSet);
      setStart(getDestination().plus(size.opposite()));
    }
    return this;
  }

  public LineElement setStroke(String stroke) {
    if (stroke.isEmpty())
      element.removeAttribute("stroke");
    else
      element.setAttribute("stroke", stroke);
    return this;
  }
}
