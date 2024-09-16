package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkArgument;

import io.github.oliviercailloux.geometry.Displacement;
import io.github.oliviercailloux.geometry.Point;
import org.w3c.dom.Element;
import org.w3c.dom.css.Rect;

public class SquareElement {
  private final RectangleElement delegate;

  public static SquareElement using(Element rect) {
    return new SquareElement(RectangleElement.using(rect));
  }

  private SquareElement(RectangleElement delegate) {
    this.delegate = delegate;
  }

  public Element getElement() {
    return delegate.getElement();
  }

  public SquareElement setStart(Point start) {
    delegate.setStart(start);
    return this;
  }

  public SquareElement setSize(double size) {
    delegate.setSize(Point.given(size, size));
    return this;
  }

  public SquareElement setRounding(double rx) {
    delegate.setRounding(rx);
    return this;
  }
}
