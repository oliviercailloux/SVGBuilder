package io.github.oliviercailloux.svgb;

import com.thaiopensource.relaxng.jaxp.XMLSyntaxSchemaFactory;
import io.github.oliviercailloux.jaris.xml.ConformityChecker;
import io.github.oliviercailloux.jaris.xml.DomHelper;
import io.github.oliviercailloux.jaris.xml.SchemaHelper;
import io.github.oliviercailloux.jaris.xml.XmlConfiguredTransformer;
import io.github.oliviercailloux.jaris.xml.XmlException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.junit.jupiter.api.Test;

public class SvgValidatorTests {
  public static final StreamSource SCHEMA_SOURCE =
      new StreamSource(DocBookConformityChecker.class.getResource("docbook.rng").toString());

  @Test
  void testValidate() throws Exception {
    cc(new XMLSyntaxSchemaFactory(), SCHEMA_SOURCE);
  }

  static ConformityChecker cc(SchemaFactory schemaFactory, StreamSource schemaSource)
      throws XmlException {
    final SchemaHelper schemaHelper = SchemaHelper.schemaHelper(schemaFactory);
    final Schema schema = schemaHelper.asSchema(schemaSource);
    return schemaHelper.conformityChecker(schema);
  }
}
