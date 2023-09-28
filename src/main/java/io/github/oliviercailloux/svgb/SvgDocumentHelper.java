package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkNotNull;

import io.github.oliviercailloux.jaris.xml.DomHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * From https://stackoverflow.com/q/26088839/ https://www.patreon.com/posts/svg-2-status-19078074
 * near death experience http://tavmjong.free.fr/svg2_status.html SVG 2 is on life support
 * https://github.com/Fyrd/caniuse/issues/1143
 * 
 * So let’s go for SVG 1.1.
 */
public class SvgDocumentHelper {
  private static final String SVG = DomHelper.SVG_NS_URI.toString();

  public static SvgDocumentHelper using(Document document) {
    return new SvgDocumentHelper(document);
  }

  private final Document document;

  private SvgDocumentHelper(Document document) {
    this.document = checkNotNull(document);
  }

  public void setSize(PositiveSize size) {
    //"210"+" "+"297"
    document.getDocumentElement().setAttribute("viewBox", "0 0 "+String.valueOf(size.x())+" "+String.valueOf(size.y()));
  }

  public LineElement line() {
    final Element line = document.createElementNS(SVG, LineElement.NODE_NAME);
    return LineElement.using(line);
  }

  public Element ellipse(DoublePoint position, PositiveSize semiSize) {
    final Element ell = document.createElementNS(SVG, "ellipse");
    if (!position.equals(DoublePoint.zero())) {
      ell.setAttribute("cx", String.valueOf(position.x()));
      ell.setAttribute("cy", String.valueOf(position.y()));
    }
    ell.setAttribute("rx", String.valueOf(semiSize.x()));
    ell.setAttribute("ry", String.valueOf(semiSize.y()));
    return ell;
  }

  public Element foreignCenteredAt(DoublePoint center, PositiveSize size) {
    final Element foreignForDescription = document.createElementNS(SVG, "foreignObject");
    foreignForDescription.setAttribute("x", String.valueOf(center.x() - size.x() / 2d));
    foreignForDescription.setAttribute("y", String.valueOf(center.y() - size.y() / 2d));
    foreignForDescription.setAttribute("width", String.valueOf(size.x()));
    foreignForDescription.setAttribute("height", String.valueOf(size.y()));
    return foreignForDescription;
  }

  public Element useCenteredAt(DoublePoint center, PositiveSize size) {
    final DoublePoint corner =
        new DoublePoint(center.x() - size.x() / 2d, center.y() - size.y() / 2d);
    return useCorneredAt(corner, size);
  }

  public Element useCorneredAt(DoublePoint corner, PositiveSize size) {
    final Element foreignForDescription = document.createElementNS(SVG, "use");
    foreignForDescription.setAttribute("x", String.valueOf(corner.x()));
    foreignForDescription.setAttribute("y", String.valueOf(corner.y()));
    foreignForDescription.setAttribute("width", String.valueOf(size.x()));
    foreignForDescription.setAttribute("height", String.valueOf(size.y()));
    return foreignForDescription;
  }

  public Element setSize(Element svgElement, PositiveSize size) {
    svgElement.setAttribute("width", String.valueOf(size.x()));
    svgElement.setAttribute("height", String.valueOf(size.y()));
    return svgElement;
  }
}
