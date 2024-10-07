package io.github.oliviercailloux.svgb;

import io.github.oliviercailloux.geometry.Displacement;
import io.github.oliviercailloux.geometry.Point;
import io.github.oliviercailloux.geometry.Zone;
import org.w3c.dom.Element;

public class SquareElement {
  private final RectangleElement delegate;

  public static SquareElement using(Element rect) {
    return new SquareElement(RectangleElement.using(rect));
  }

  private SquareElement(RectangleElement delegate) {
    this.delegate = delegate;
  }

  public Element element() {
    return delegate.element();
  }

  public SquareElement setStart(Point start) {
    delegate.setZone(Zone.cornerMove(start, delegate.zone().across()));
    return this;
  }
  
  public SquareElement setSize(double size) {
    delegate.setZone(Zone.cornerMove(delegate.zone().start(), Displacement.allDirections(size)));
    return this;
  }

  public SquareElement setRounding(double rx) {
    delegate.setRounding(rx);
    return this;
  }
}
