package br.com.guilherme.cm.modelo;

import br.com.guilherme.cm.excecao.ExplosionExcepction;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int line;
    private final int column;

    private boolean mined = false;
    private boolean open = false;
    private boolean marked = false;

    private List<Field> neighbors = new ArrayList<>();

    Field(int line, int column) {
        this.line = line;
        this.column = column;
    }

    boolean addNeighbor(Field neighbor) {
        boolean differentLine = line != neighbor.line;
        boolean differentColumn = column != neighbor.column;
        boolean diagonal = differentLine && differentColumn;

        int deltaLine = Math.abs(line - neighbor.line);
        int deltaColumn = Math.abs(column - neighbor.column);
        int generalDelta = deltaLine + deltaColumn;

        if(generalDelta == 1 && !diagonal) {
            neighbors.add(neighbor);
            return true;
        } else if(generalDelta == 2 && diagonal) {
            neighbors.add(neighbor);
            return true;
        } else {
            return false;
        }
    }
    void toggleMarking() {
        if(!open) {
            marked = !marked;
        }
    }
    boolean open() {
        if(!open && !marked) {
            open = true;

            if(mined) {
                throw new ExplosionExcepction();
            }
            if(safeNeighborhood()) {
                neighbors.forEach(v -> v.open());
            }
            return true;
        } else {
            return false;
        }
    }

    boolean safeNeighborhood() {
        return neighbors.stream().noneMatch(v -> v.mined);
    }

    public boolean isMarked() {
        return marked;
    }
}
