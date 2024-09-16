package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkArgument;

import io.github.oliviercailloux.geometry.Vector;
import org.w3c.dom.Element;

public class GElement {
  public static final String NODE_NAME = "g";

  private final Element element;

  public static GElement using(Element g) {
    return new GElement(g);
  }

  private GElement(Element g) {
    element = g;
    checkArgument(g.getNodeName().equals(NODE_NAME));
  }

  public Element getElement() {
    return element;
  }

  public GElement translate(Vector offset) {
    element.setAttribute("transform", "translate(%s, %s)".formatted(SvgHelper.format(offset.x()), SvgHelper.format(offset.y())));
    return this;
  }
}
