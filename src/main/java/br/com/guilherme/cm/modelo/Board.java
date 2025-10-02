package br.com.guilherme.cm.modelo;

import br.com.guilherme.cm.excecao.ExplosionExcepction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {

    private int lines;
    private int columns;
    private int mines;

    private final List<Field> fields = new ArrayList<>();

    public Board(int lines, int columns, int mines) {
        this.lines = lines;
        this.columns = columns;
        this.mines = mines;

        generateFields();
        associateTheNeighbors();
        sortTheMines();
    }

    public void open(int line, int column) {
        try {
            fields.parallelStream()
                    .filter(f -> f.getLine() == line  && f.getColumn() == column)
                    .findFirst()
                    .ifPresent(f -> f.open());
        } catch (ExplosionExcepction e) {
            fields.forEach(f -> f.setOpen(true));
            throw e;
        }
    }
    public void toggleMarking(int line, int column) {
        fields.parallelStream()
                .filter(f -> f.getLine() == line  && f.getColumn() == column)
                .findFirst()
                .ifPresent(f -> f.toggleMarking());
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
    public void sortTheMines() {
        long armedMines = 0;
        Predicate<Field> mined = field -> field.isMined();

        do {
            int random = (int) (Math.random() * fields.size());
            fields.get(random).undermine();
            armedMines = fields.stream().filter(mined).count();
        } while(armedMines < mines);
    }
    public boolean objectiveAchieved() {
        return fields.stream().allMatch(f -> f.objectiveAchieved());
    }

    public void restart() {
        fields.stream().forEach(f -> f.restart());
        sortTheMines();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("   ");
        for (int c = 0; c < columns; c++) {
            sb.append(String.format(" %2d ", c));
        }
        sb.append("\n");

        sb.append("   ┌");
        for (int c = 0; c < columns; c++) {
            sb.append("───");
            if (c < columns - 1) {
                sb.append("┬");
            }
        }
        sb.append("┐\n");

        int i = 0;
        for (int l = 0; l < lines; l++) {
            sb.append(String.format("%2d │", l));
            for (int c = 0; c < columns; c++) {
                sb.append(" " + fields.get(i) + " │");
                i++;
            }
            sb.append("\n");

            if (l < lines - 1) {
                sb.append("   ├");
                for (int c = 0; c < columns; c++) {
                    sb.append("───");
                    if (c < columns - 1) {
                        sb.append("┼");
                    }
                }
                sb.append("┤\n");
            }
        }

        sb.append("   └");
        for (int c = 0; c < columns; c++) {
            sb.append("───");
            if (c < columns - 1) {
                sb.append("┴");
            }
        }
        sb.append("┘\n");

        return sb.toString();
    }
}
