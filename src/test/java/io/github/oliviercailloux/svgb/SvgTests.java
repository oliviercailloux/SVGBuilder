package io.github.oliviercailloux.svgb;

import static com.google.common.base.Verify.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.io.Resources;
import com.google.common.math.DoubleMath;

import io.github.oliviercailloux.jaris.xml.DomHelper;

public class SvgTests {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(SvgTests.class);

  @Test
  void testEllipse() throws Exception {
    LOGGER.info("Started tests.");
    DomHelper d = DomHelper.domHelper();
    Document doc = d.svg();
    SvgDocumentHelper h = SvgDocumentHelper.using(doc);
    Element e = h.ellipse(DoublePoint.zero(), PositiveSize.square(100));
    doc.getDocumentElement().appendChild(e);

    String actual = d.toString(doc);
    // Files.writeString(Path.of("out.svg"), actual);
    String expected =
        Resources.toString(SvgTests.class.getResource("Ellipse.svg"), StandardCharsets.UTF_8);
    assertEquals(expected, actual);
  }

  @Test
  void testDrawingLine() throws Exception {
    DomHelper d = DomHelper.domHelper();
    Document doc = d.svg();
    SvgDocumentHelper h = SvgDocumentHelper.using(doc);
    DoublePoint start = DoublePoint.given(1d, 1d).mult(DomHelper.PIXELS_PER_CM);
    /*
     * Scaling for my bigger screen (27 ″, 2560×1440 pixels). Real diag is 68.2 cm = 26.85 ″.
     */
    double dpi = (2560d / 16d) / (26.85d / Math.sqrt(256d + 81d));
    verify(DoubleMath.fuzzyEquals(dpi, 109.39d, 1e-2d));
    /*
     * In Firefox, this line seems to measure about 50 cm indeed (up to my measurement approximation, about ± 2 mm)
     */
    LineElement line = h.line().setStart(start).setSize(PositiveSize.given(50d * dpi / 2.54d, 0d))
        .setStroke("black");
    doc.getDocumentElement().appendChild(line.getElement());

    String actual = d.toString(doc);
    String expected =
        Resources.toString(SvgTests.class.getResource("Line.svg"), StandardCharsets.UTF_8);
    assertEquals(expected, actual);
  }

  @Test
  void testDrawing() throws Exception {
    DomHelper d = DomHelper.domHelper();
    Document doc = d.svg();
    SvgDocumentHelper h = SvgDocumentHelper.using(doc);
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
    doc.getDocumentElement().appendChild(style.getElement());
    RectangleElement rect =
        h.rectangle().setStart(DoublePoint.zero()).setSize(PositiveSize.square(100));
    doc.getDocumentElement().appendChild(rect.getElement());
    TextElement text = h.text().setBaselineStart(DoublePoint.given(50d, 50d)).setContent("Hello");
    doc.getDocumentElement().appendChild(text.getElement());

    String actual = d.toString(doc);
    // Files.writeString(Path.of("out.svg"), actual);
    String expected = Resources.toString(SvgTests.class.getResource("Rectangle complex.svg"),
        StandardCharsets.UTF_8);
    assertEquals(expected, actual);
  }
}
