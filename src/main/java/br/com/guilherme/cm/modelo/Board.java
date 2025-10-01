package br.com.guilherme.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {

    private int lines;
    private int columns;
    private int mines;

    private final List<Field> fields = new ArrayList<>();

    public Board(int lines, int collums, int mines) {
        this.lines = lines;
        this.columns = columns;
        this.mines = mines;

        generateFields();
        associateTheNeighbors();
        sortTheMines();
    }
    private void generateFields() {
        for(int line = 0; line < lines; line++) {
            for(int column = 0; column < columns; column++) {
                fields.add(new Field(line, column));
            }
        }
    }
    private void associateTheNeighbors() {
        for(Field c1: fields) {
            for(Field c2: fields) {
                c1.addNeighbor(c2);
            }
        }
    }
    private void sortTheMines() {
        long armedMines = 0;
        Predicate<Field> mined = field -> field.isMined();

        do {
            armedMines = fields.stream().filter(mined).count();
            int random = (int) (Math.random() * fields.size());
            fields.get(random).undermine();
        } while(armedMines < mines);
    }


}
