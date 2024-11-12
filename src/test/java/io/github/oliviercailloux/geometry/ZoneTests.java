package io.github.oliviercailloux.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ZoneTests {
  @Test
  void testFromOrigin() throws Exception {
    Zone origin = Zone.at(Point.origin());
    assertEquals("(0.0, 0.0) → (0.0, 0.0)", origin.coordinates());
    assertEquals("(0.0, 0.0) → (0.0, 0.0)", origin.sizeCentered(Point.origin()).coordinates());
    assertEquals("(0.0, 0.0) → (10.0, 10.0)",
        origin.extend(Displacement.allDirections(10d)).coordinates());
    assertEquals("(0.0, 0.0) → (10.0, 10.0)", origin.extend(Point.square(10d)).coordinates());
    assertEquals("(0.0, 0.0) → (10.0, 10.0)", origin.enclosing(Point.square(10d)).coordinates());
  }

  @Test
  void testFromNotOrigin() throws Exception {
    Zone start = Zone.at(Point.square(10d));
    assertEquals("(10.0, 10.0) → (10.0, 10.0)", start.coordinates());
    assertEquals("(6.0, 6.0) → (14.0, 14.0)", start.sizeCentered(Point.square(8d)).coordinates());
    assertEquals("(5.0, 5.0) → (10.0, 10.0)",
        start.extend(Displacement.allDirections(-5d)).coordinates());
    assertEquals("(10.0, 10.0) → (15.0, 15.0)",
        start.extend(Displacement.allDirections(5d)).coordinates());
    assertEquals("(10.0, 10.0) → (20.0, 20.0)", start.extend(Point.square(10d)).coordinates());
    assertEquals("(10.0, 10.0) → (10.0, 10.0)", start.enclosing(Point.square(10d)).coordinates());
    assertEquals("(10.0, 10.0) → (20.0, 20.0)", start.enclosing(Point.square(20d)).coordinates());
  }
}
