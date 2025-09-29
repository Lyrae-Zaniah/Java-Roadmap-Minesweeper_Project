package br.com.guilherme.cm.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {

    private Field field;

    @BeforeEach
    void startField() {
         field = new Field(3, 3);
    }
    @Test
    void testNeighborDistance1Left() {
        Field f1 = new Field(3, 2);
        boolean result = field.addNeighbor(f1);
        assertTrue(result);
    }
    @Test
    void testNeighborDistance1Right() {
        Field f1 = new Field(3, 4);
        boolean result = field.addNeighbor(f1);
        assertTrue(result);
    }
    @Test
    void testNeighborDistance1Top() {
        Field f1 = new Field(2, 3);
        boolean result = field.addNeighbor(f1);
        assertTrue(result);
    }
    @Test
    void testNeighborDistance1Bottom() {
        Field f1 = new Field(4, 3);
        boolean result = field.addNeighbor(f1);
        assertTrue(result);
    }
    @Test
    void testNeighborDistance2() {
        Field f1 = new Field(2, 2);
        boolean result = field.addNeighbor(f1);
        assertTrue(result);
    }
    @Test
    void testNoNeighbor() {
        Field f1 = new Field(1, 1);
        boolean result = field.addNeighbor(f1);
        assertFalse(result);
    }

    @Test
    void testToggleDefaultMarkup() {
        assertFalse(field.isMarked());
    }
    @Test
    void testToggleMarking() {
        field.toggleMarking();
        assertTrue(field.isMarked());
    }
    @Test
    void testToggleMarkingTwoCalls() {
        field.toggleMarking();
        field.toggleMarking();
        assertFalse(field.isMarked());
    }
    @Test
    void testOpen() {
        field.toggleMarking();
        field.toggleMarking();
        assertFalse(field.isMarked());
    }
}
