package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkNotNull;

import io.github.oliviercailloux.jaris.xml.DomHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

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
  private Element root;

  private SvgDocumentHelper(Document document) {
    this.document = checkNotNull(document);
    root = document.getDocumentElement();
  }

  public void setViewBoxSize(PositiveSize size) {
    if (size.equals(PositiveSize.zero()))
      root.removeAttribute("viewBox");
    else {
      root.setAttribute("viewBox",
          "0 0 " + String.valueOf(size.x()) + " " + String.valueOf(size.y()));
    }
  }

  public void setSize(PositiveSize size) {
    SvgHelper.setSize(root, size);
  }

  public LineElement line() {
    final Element line = document.createElementNS(SVG, LineElement.NODE_NAME);
    return LineElement.using(line);
  }

  public SquareElement square() {
    final Element rect = document.createElementNS(SVG, SquareElement.NODE_NAME);
    return SquareElement.using(rect);
  }

  public RectangleElement rectangle() {
    final Element rect = document.createElementNS(SVG, RectangleElement.NODE_NAME);
    return RectangleElement.using(rect);
  }

  public TextElement text() {
    final Element text = document.createElementNS(SVG, TextElement.NODE_NAME);
    final Text content = document.createTextNode("");
    text.appendChild(content);
    return TextElement.using(text, content);
  }

  public StyleElement style() {
    final Element main = document.createElementNS(SVG, StyleElement.NODE_NAME);
    final Text content = document.createTextNode("");
    main.appendChild(content);
    return StyleElement.using(main, content);
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

  public void setStyle(String style) {
    if (style.isEmpty()) {
      root.removeAttribute("style");
    } else
      root.setAttribute("style", style);
  }
}
