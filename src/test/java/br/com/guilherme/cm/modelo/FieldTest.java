package br.com.guilherme.cm.modelo;

import br.com.guilherme.cm.excecao.ExplosionExcepction;
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
        assertFalse(field.open());
    }
    @Test
    void testToggleMarkingMined() {
        field.toggleMarking();
        field.undermine();
        assertFalse(field.open());
    }
    @Test
    void testToggleMarkingMinedNotMarked() {
        field.undermine();
        assertThrows(ExplosionExcepction.class, () -> {
            field.open();
        });
    }
    @Test
    void testToggleMarkingNeighbor1() {
        Field neighbor1 = new Field(2,2);
        Field neighborOfNeighbor1 = new Field(1,1);
        neighbor1.addNeighbor(neighborOfNeighbor1);
        field.addNeighbor(neighbor1);
        field.open();
        assertTrue(neighbor1.isOpen() && neighborOfNeighbor1.isOpen());
    }
    @Test
    void testToggleMarkingNeighbor2() {
        Field field11 = new Field(1,1);
        Field field12 = new Field(1,1);
        field11.undermine();

        Field field22 = new Field(2,2);
        field12.addNeighbor(field11);

        field.addNeighbor(field22);
        field.open();
        assertTrue(field22.isOpen() && field11.isClosed());
    }
}
