package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import io.github.oliviercailloux.geometry.Point;
import io.github.oliviercailloux.jaris.xml.DomHelper;
import io.github.oliviercailloux.jaris.xml.XmlName;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class TextElement {
  public static final String NODE_NAME = "text";
  public static final XmlName XML_NAME =
      XmlName.expandedName(SvgDocumentHelper.SVG_NS_URI, NODE_NAME);

  private final Element element;
  private final Text content;

  public static TextElement using(Element text, Text content) {
    return new TextElement(text, content);
  }

  private TextElement(Element text, Text content) {
    element = checkNotNull(text);
    this.content = checkNotNull(content);
    checkArgument(DomHelper.xmlName(text).equals(XML_NAME));
  }

  public Element getElement() {
    return element;
  }

  public TextElement setContent(String content) {
    this.content.setNodeValue(content);
    return this;
  }

  public TextElement setBaselineStart(Point start) {
    element.setAttribute("x", SvgHelper.format(start.x()));
    element.setAttribute("y", SvgHelper.format(start.y()));
    return this;
  }
}
