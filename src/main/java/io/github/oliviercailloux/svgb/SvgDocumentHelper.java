package io.github.oliviercailloux.svgb;

import static com.google.common.base.Preconditions.checkNotNull;

import io.github.oliviercailloux.geometry.Point;
import io.github.oliviercailloux.geometry.Zone;
import io.github.oliviercailloux.jaris.xml.DomHelper;
import io.github.oliviercailloux.jaris.xml.XmlName;
import java.net.URI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class SvgDocumentHelper {
  /**
   * “The SVG 2 namespace is http://www.w3.org/2000/svg, which is the same as for earlier versions
   * of SVG.”, https://svgwg.org/svg2-draft/struct.html
   */
  public static final URI SVG_NS_URI = URI.create("http://www.w3.org/2000/svg");
  public static final XmlName SVG_ELEMENT_NAME = XmlName.expandedName(SVG_NS_URI, "svg");
  private static final String SVG_NS = SVG_NS_URI.toString();

  /**
   * Creates a new SVG DOM Document, containing only the SVG document element.
   *
   * @return a helper containing a new {@code Document} object with a document element having
   *         namespace {@link #SVG_NS_URI} and name “{@code svg}”.
   */
  public static SvgDocumentHelper usingNewDocument() {
    return using(DomHelper.domHelper());
  }

  /**
   * Creates a new SVG DOM Document, containing only the SVG document element.
   *
   * @return a helper containing a new {@code Document} object with a document element having
   *         namespace {@link #SVG_NS_URI} and name “{@code svg}”.
   */
  public static SvgDocumentHelper using(DomHelper helper) {
    return using(helper.createDocument(SVG_ELEMENT_NAME.toQName()));
  }

  public static SvgDocumentHelper using(Document document) {
    return new SvgDocumentHelper(document);
  }

  private final Document document;
  private Element root;

  private SvgDocumentHelper(Document document) {
    this.document = checkNotNull(document);
    root = document.getDocumentElement();
  }

  public Document document() {
    return document;
  }

  public void setViewBoxSize(Point size) {
    if (size.equals(Point.origin())) {
      root.removeAttribute("viewBox");
    } else {
      root.setAttribute("viewBox",
          "0 0 " + SvgHelper.format(size.x()) + " " + SvgHelper.format(size.y()));
    }
  }

  public void setSize(Point size) {
    SvgHelper.setSize(root, size);
  }

  public GElement g() {
    final Element g = document.createElementNS(SVG_NS, GElement.NODE_NAME);
    return GElement.using(g);
  }

  public LineElement line(Zone zone) {
    final Element line = document.createElementNS(SVG_NS, LineElement.NODE_NAME);
    return LineElement.using(line).across(zone);
  }

  public SquareElement square(Point start, double length) {
    final Element rect = document.createElementNS(SVG_NS, RectangleElement.NODE_NAME);
    return SquareElement.using(rect).setStart(start).setSize(length);
  }

  public RectangleElement rectangle(Zone zone) {
    final Element rect = document.createElementNS(SVG_NS, RectangleElement.NODE_NAME);
    return RectangleElement.using(rect).setStart(zone.start()).setSize(zone.size());
  }

  public TextElement text() {
    final Element text = document.createElementNS(SVG_NS, TextElement.NODE_NAME);
    final Text content = document.createTextNode("");
    text.appendChild(content);
    return TextElement.using(text, content);
  }

  public StyleElement style() {
    final Element main = document.createElementNS(SVG_NS, StyleElement.NODE_NAME);
    final Text content = document.createTextNode("");
    main.appendChild(content);
    return StyleElement.using(main, content);
  }

  public Element ellipse(Zone zone) {
    final Element ell = document.createElementNS(SVG_NS, "ellipse");
    final Point center = zone.center();
    if (!center.equals(Point.origin())) {
      ell.setAttribute("cx", SvgHelper.format(center.x()));
      ell.setAttribute("cy", SvgHelper.format(center.y()));
    }
    final Point semiSize = zone.size().mult(0.5);
    ell.setAttribute("rx", SvgHelper.format(semiSize.x()));
    ell.setAttribute("ry", SvgHelper.format(semiSize.y()));
    return ell;
  }

  public Element foreign(Zone zone) {
    final Element el = document.createElementNS(SVG_NS, "foreignObject");
    SvgHelper.setPosition(el, zone.start());
    SvgHelper.setSize(el, zone.size());
    return el;
  }

  public Element use(Zone zone) {
    final Element el = document.createElementNS(SVG_NS, "use");
    SvgHelper.setPosition(el, zone.start());
    if (!zone.size().equals(Point.origin())) {
      SvgHelper.setSize(el, zone.size());
    }
    return el;
  }

  public void setStyle(String style) {
    if (style.isEmpty()) {
      root.removeAttribute("style");
    } else {
      root.setAttribute("style", style);
    }
  }
}
