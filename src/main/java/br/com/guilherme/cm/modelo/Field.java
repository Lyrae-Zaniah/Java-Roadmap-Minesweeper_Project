package br.com.guilherme.cm.modelo;

import br.com.guilherme.cm.excecao.ExplosionExcepction;

import java.awt.*;
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

    void undermine() {
        mined = true;
    }

    public boolean isMarked() {
        return marked;
    }

    void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isClosed() {
        return !open;
    }
    public boolean isMined() {
        return mined;
    }

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }

    boolean objectiveAchieved() {
        boolean unveiled = !mined && open;
        boolean secure = mined && marked;
        return unveiled || secure;
    }

    long minesNeighborhood() {
        return neighbors.stream().filter(v -> v.mined).count();
    }

    void restart() {
        open = false;
        mined = false;
        marked = false;
    }

    public String toString(){
        // Códigos ANSI de cores
        final String RESET = "\u001B[0m";
        final String RED = "\u001B[31m";
        final String GREEN = "\u001B[32m";
        final String YELLOW = "\u001B[33m";
        final String BLUE = "\u001B[34m";

        if (marked) { // marcado pelo jogador
            return RED + "M" + RESET;
        } else if (!open) { // fechado
            return BLUE + "?" + RESET;
        } else if (mined) { // aberto e era mina
            return RED + "x" + RESET;
        } else if (minesNeighborhood() > 0) { // aberto e tem minas vizinhas
            // Diferente cor para cada número, por exemplo:
            long count = minesNeighborhood();
            switch ((int) count) {
                case 1: return BLUE + count + RESET;
                case 2: return GREEN + count + RESET;
                case 3: return RED + count + RESET;
                default: return YELLOW + count + RESET;
            }
        } else { // aberto e sem minas vizinhas
            return " ";
        }
    }
}
