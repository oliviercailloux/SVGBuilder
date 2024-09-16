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

/**
 * From https://stackoverflow.com/q/26088839/: “
 * 
 * Asking if a browser supports SVG 2 is like asking if a browser supports HTML5. It seems like a
 * totally reasonable question, but it doesn't work like that.
 * 
 * The best we can do is test if a browser supports a feature, rather than a version of spec. ”
 * 
 * Jan 2016: “Browsers are implementing individual features as the spec stabilizes and interest
 * exists, they are not waiting to implement "SVG 2" all at once.”
 * https://github.com/Fyrd/caniuse/issues/1143
 * 
 * Nov 2016: “SVG 2 is on life support.” http://tavmjong.free.fr/svg2_status.html
 * 
 * May 2018: “After a near death experience, SVG 2 is back.”,
 * https://www.patreon.com/posts/svg-2-status-19078074, Tavmjong Bah
 * 
 * Sep 2022: “It seems chromium suppports ~40-50% of SVG 2 which is huge and enable many impacting
 * usecases.” https://github.com/Fyrd/caniuse/issues/1143#issuecomment-1233975910
 * 
 * Jan 2023: some features of SVG 2 work, but nobody seem to know which ones.
 * https://cloudfour.com/thinks/so-you-can-set-an-svg-circles-radius-in-css/
 * 
 * March 2023: https://svgwg.org/svg2-draft/ (but the three commits of 2023 are one line each,
 * https://github.com/w3c/svgwg/commits/main/)
 * 
 * 
 * https://github.com/Fyrd/caniuse/issues/1143
 * 
 * We follow MDN (eg do not use version and baseProfile attributes, “Both version and baseProfile
 * attributes are deprecated in SVG 2”,
 * https://developer.mozilla.org/en-US/docs/Web/SVG/Tutorial/Getting_Started).
 * 
 */
public class SvgDocumentHelper {
  /**
   * “The SVG 2 namespace is http://www.w3.org/2000/svg, which is the same as for earlier versions
   * of SVG.”, https://svgwg.org/svg2-draft/struct.html
   */
  public static final URI SVG_NS_URI = URI.create("http://www.w3.org/2000/svg");
  private static final String SVG_NS = SVG_NS_URI.toString();

  public static SvgDocumentHelper usingNewDocument() {
    return using(DomHelper.domHelper());
  }

  public static SvgDocumentHelper using(DomHelper helper) {
    return using(helper.createDocument(XmlName.expandedName(SVG_NS_URI, "svg").toQName()));
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
