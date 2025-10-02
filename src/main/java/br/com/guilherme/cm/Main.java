package br.com.guilherme.cm;

import br.com.guilherme.cm.modelo.Board;
import br.com.guilherme.cm.visao.BoardConsole;

public class Main {

    public static void main(String[] args) {

        Board board = new Board(6,6,6);
        new BoardConsole(board);
    }
}
