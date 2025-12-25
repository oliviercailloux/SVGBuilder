package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Verify.verify;

import io.github.oliviercailloux.geometry.Point;
import io.github.oliviercailloux.geometry.Size;
import io.github.oliviercailloux.geometry.Zone;
import io.github.oliviercailloux.jaris.xml.DomHelper;
import io.github.oliviercailloux.jaris.xml.XmlName;
import org.w3c.dom.Element;

public class LineElement {
  public static final String NODE_NAME = "line";
  public static final XmlName XML_NAME =
      XmlName.expandedName(SvgDocumentHelper.SVG_NS_URI, NODE_NAME);

  private final Element element;

  public static LineElement using(Element line) {
    return new LineElement(line);
  }

  private LineElement(Element line) {
    element = line;
    checkArgument(DomHelper.xmlName(line).equals(XML_NAME));
  }

  public Element element() {
    return element;
  }

  public LineElement across(Zone zone) {
    return setStart(zone.topLeft()).setDestination(zone.bottomRight());
  }

  /** Does not support length or percentage. */
  public Point getStart() {
    checkState(element.getAttribute("x1").isEmpty() == element.getAttribute("y1").isEmpty());
    String xStr = element.getAttribute("x1");
    double x = xStr.isEmpty() ? 0d : Double.parseDouble(xStr);
    String yStr = element.getAttribute("y1");
    double y = yStr.isEmpty() ? 0d : Double.parseDouble(yStr);
    return Point.given(x, y);
  }

  public LineElement setStart(Point start) {
    element.setAttribute("x1", SvgHelper.format(start.x()));
    element.setAttribute("y1", SvgHelper.format(start.y()));
    return this;
  }

  /** Does not support length or percentage. */
  public Point getDestination() {
    checkState(element.getAttribute("x2").isEmpty() == element.getAttribute("y2").isEmpty());
    String xStr = element.getAttribute("x2");
    double x = xStr.isEmpty() ? 0d : Double.parseDouble(xStr);
    String yStr = element.getAttribute("y2");
    double y = yStr.isEmpty() ? 0d : Double.parseDouble(yStr);
    return Point.given(x, y);
  }

  public LineElement setDestination(Point destination) {
    element.setAttribute("x2", SvgHelper.format(destination.x()));
    element.setAttribute("y2", SvgHelper.format(destination.y()));
    return this;
  }

  public LineElement setSize(Size size) {
    checkState(element.getAttribute("x1").isEmpty() == element.getAttribute("y1").isEmpty());
    boolean startSet = !element.getAttribute("x1").isEmpty();
    checkState(element.getAttribute("x2").isEmpty() == element.getAttribute("y2").isEmpty());
    boolean destSet = !element.getAttribute("x2").isEmpty();
    checkArgument(!startSet || !destSet);
    if (!destSet) {
      Point start = getStart();
      setDestination(start.plus(size.asDisplacement()));
    } else {
      verify(!startSet);
      setStart(getDestination().plus(size.asDisplacement().opposite()));
    }
    return this;
  }

  public LineElement setStroke(String stroke) {
    if (stroke.isEmpty()) {
      element.removeAttribute("stroke");
    } else {
      element.setAttribute("stroke", stroke);
    }
    return this;
  }
}
