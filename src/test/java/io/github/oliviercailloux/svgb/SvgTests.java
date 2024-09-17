package io.github.oliviercailloux.svgb;

import static com.google.common.base.Verify.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.io.Resources;
import com.google.common.math.DoubleMath;
import io.github.oliviercailloux.geometry.Displacement;
import io.github.oliviercailloux.geometry.Point;
import io.github.oliviercailloux.geometry.Zone;
import io.github.oliviercailloux.jaris.xml.DomHelper;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SvgTests {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(SvgTests.class);

  /** 96d / 2.54d, see https://developer.mozilla.org/en-US/docs/Web/CSS/length */
  public static final double PIXELS_PER_CM = 96d / 2.54d;

  @Test
  void testEllipse() throws Exception {
    DomHelper d = DomHelper.domHelper();
    SvgDocumentHelper h = SvgDocumentHelper.using(d);
    Element e = h.ellipse(Zone.cornerMove(Point.origin(), Displacement.allDirections(200)));
    h.document().getDocumentElement().appendChild(e);

    String actual = d.toString(h.document());
    // Files.writeString(Path.of("out.svg"), actual);
    String expected =
        Resources.toString(SvgTests.class.getResource("Ellipse.svg"), StandardCharsets.UTF_8);
    assertEquals(expected, actual);
  }

  @Test
  void testG() throws Exception {
    DomHelper d = DomHelper.domHelper();
    SvgDocumentHelper h = SvgDocumentHelper.using(d);
    SvgHelper.setSize(h.document().getDocumentElement(), Point.square(500));
    Element g = h.g().translate(Point.square(200)).getElement();
    h.document().getDocumentElement().appendChild(g);
    Element e = h.ellipse(Zone.cornerMove(Point.square(50), Displacement.allDirections(100)));
    g.appendChild(e);

    String actual = d.toString(h.document());
    String expected = Resources.toString(SvgTests.class.getResource("Translated ellipse.svg"),
        StandardCharsets.UTF_8);
    assertEquals(expected, actual);
  }

  @Test
  void testDrawingLine() throws Exception {
    DomHelper d = DomHelper.domHelper();
    SvgDocumentHelper h = SvgDocumentHelper.using(d);
    Point start = Point.given(1d, 1d).mult(PIXELS_PER_CM);
    /*
     * Scaling for my bigger screen (27 ″, 2560×1440 pixels). Real diag is 68.2 cm = 26.85 ″.
     */
    double dpi = (2560d / 16d) / (26.85d / Math.sqrt(256d + 81d));
    verify(DoubleMath.fuzzyEquals(dpi, 109.39d, 1e-2d));
    /*
     * In Firefox, this line seems to measure about 50 cm indeed (up to my measurement
     * approximation, about ± 2 mm)
     */
    LineElement line = h.line(Zone.cornerMove(start, Displacement.given(50d * dpi / 2.54d, 0d)))
        .setStroke("black");
    h.document().getDocumentElement().appendChild(line.getElement());

    String actual = d.toString(h.document());
    String expected =
        Resources.toString(SvgTests.class.getResource("Line.svg"), StandardCharsets.UTF_8);
    assertEquals(expected, actual);
  }

  @Test
  void testDrawing() throws Exception {
    DomHelper d = DomHelper.domHelper();
    SvgDocumentHelper h = SvgDocumentHelper.using(d);
    StyleElement style = h.style().setContent("""
        rect {
          fill-opacity: 0;
          stroke: black;
          stroke-width: 1px;
        }
        text{
          text-anchor: middle;
        }
        """);
    Document doc = h.document();
    doc.getDocumentElement().appendChild(style.getElement());
    RectangleElement rect =
        h.rectangle(Zone.cornerMove(Point.origin(), Displacement.allDirections(100)));
    doc.getDocumentElement().appendChild(rect.getElement());
    TextElement text = h.text().setBaselineStart(Point.given(50d, 50d)).setContent("Hello");
    doc.getDocumentElement().appendChild(text.getElement());

    String actual = d.toString(doc);
    // Files.writeString(Path.of("out.svg"), actual);
    String expected = Resources.toString(SvgTests.class.getResource("Rectangle complex.svg"),
        StandardCharsets.UTF_8);
    assertEquals(expected, actual);
  }
}
