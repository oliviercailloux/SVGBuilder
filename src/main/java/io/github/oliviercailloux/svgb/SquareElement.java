package io.github.oliviercailloux.svgb;

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

  /** Changes the start but not the size, thus, both start and end move (unless the given start equals the current start). */
  public SquareElement setStart(Point start) {
    delegate.setZone(Zone.cornered(start, delegate.zone().size()));
    return this;
  }
  
  public SquareElement setSize(double size) {
    delegate.setZone(Zone.cornered(delegate.zone().start(), Point.square(size)));
    return this;
  }

  public SquareElement setRounding(double rx) {
    delegate.setRounding(rx);
    return this;
  }
}
