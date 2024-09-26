package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkArgument;

import io.github.oliviercailloux.geometry.Vector;
import io.github.oliviercailloux.jaris.xml.DomHelper;
import io.github.oliviercailloux.jaris.xml.XmlName;
import org.w3c.dom.Element;

public class GElement {
  public static final String NODE_NAME = "g";
  public static final XmlName XML_NAME = XmlName.expandedName(SvgDocumentHelper.SVG_NS_URI, NODE_NAME);

  private final Element element;

  public static GElement using(Element g) {
    return new GElement(g);
  }

  private GElement(Element g) {
    element = g;
    checkArgument(DomHelper.xmlName(g).equals(XML_NAME));
  }

  public Element getElement() {
    return element;
  }

  public GElement translate(Vector offset) {
    element.setAttribute("transform",
        "translate(%s, %s)".formatted(SvgHelper.format(offset.x()), SvgHelper.format(offset.y())));
    return this;
  }
}
