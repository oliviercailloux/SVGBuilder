package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import io.github.oliviercailloux.jaris.xml.DomHelper;
import io.github.oliviercailloux.jaris.xml.XmlName;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class StyleElement {
  public static final String NODE_NAME = "style";
  public static final XmlName XML_NAME = XmlName.expandedName(SvgDocumentHelper.SVG_NS_URI, NODE_NAME);

  private final Element element;
  private final Text content;

  public static StyleElement using(Element text, Text content) {
    return new StyleElement(text, content);
  }

  private StyleElement(Element text, Text content) {
    element = checkNotNull(text);
    this.content = checkNotNull(content);
    checkArgument(DomHelper.xmlName(text).equals(XML_NAME));
  }

  public Element getElement() {
    return element;
  }

  public StyleElement setContent(String content) {
    this.content.setNodeValue(content);
    return this;
  }
}
