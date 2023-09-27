package io.github.oliviercailloux.svgb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.io.Resources;
import io.github.oliviercailloux.jaris.xml.DomHelper;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MyTests {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(MyTests.class);

  @Test
  void testSomething() throws Exception {
    LOGGER.info("Started tests.");
    DomHelper d = DomHelper.domHelper();
    Document doc = d.svg();
    SvgHelper h = SvgHelper.using(doc);
    Element e = h.ellipse(SvgPoint.zero(), SvgSize.square(100));
    doc.getDocumentElement().appendChild(e);

    String actual = d.toString(doc);
    // Files.writeString(Path.of("out.svg"), actual);
    String expected =
        Resources.toString(MyTests.class.getResource("Ellipse.svg"), StandardCharsets.UTF_8);
    assertEquals(expected, actual);
  }
}
