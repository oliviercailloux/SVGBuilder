package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class TextElement {
  public static final String NODE_NAME = "text";

  private final Element element;
  private final Node content;


  public static TextElement using(Element text, Node content) {
    return new TextElement(text, content);
  }

  public TextElement(Element text, Node content) {
    element = checkNotNull(text);
    this.content = checkNotNull(content);
    checkArgument(text.getNodeName().equals(NODE_NAME));
  }

  public Element getElement() {
    return element;
  }

  public TextElement setContent(String content) {
    this.content.setNodeValue(content);
    return this;
  }

  public TextElement setBaselineStart(DoublePoint start) {
    element.setAttribute("x", String.valueOf(start.x()));
    element.setAttribute("y", String.valueOf(start.y()));
    return this;
  }

}
