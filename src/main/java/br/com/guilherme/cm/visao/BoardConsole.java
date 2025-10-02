package br.com.guilherme.cm.visao;

import br.com.guilherme.cm.excecao.ExitExcepction;
import br.com.guilherme.cm.excecao.ExplosionExcepction;
import br.com.guilherme.cm.modelo.Board;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class BoardConsole {

    private Board board;
    private Scanner input = new Scanner(System.in);

    public BoardConsole(Board board) {
        this.board = board;
        runGame();
    }

    private void runGame() {
        try {
            boolean proceed = true;

            while (proceed) {
                gameCycle();


                System.out.println("Another match ? (Y/n) ");
                String answer = input.nextLine();

                if ("n".equalsIgnoreCase(answer)) {
                    proceed = false;
                } else {
                    board.restart();
                }
            }
        } catch (ExitExcepction e) {
            System.out.printf("Bye!!!");
        } finally {
            input.close();
        }
    }

    private void gameCycle() {
        try {

            while(!board.objectiveAchieved()) {
                System.out.println(board);
                String typed = captureTypedValue("Enter (x, y): ");

                Iterator<Integer> xy = Arrays.stream(typed.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();

                typed = captureTypedValue("1 - Open or 2 - (Un)mark: ");
                if("1".equals(typed)) {
                    board.open(xy.next(), xy.next());
                } else if("2".equals(typed)) {
                    board.toggleMarking(xy.next(), xy.next());
                }
            }
            System.out.println(board);
            System.out.println("You Won!!!");
        } catch (ExplosionExcepction e) {
            System.out.println(board);
            System.out.println("You lost!");
        }
    }

    private String captureTypedValue(String text) {
        System.out.print(text);
        String typed = input.nextLine();

        if("exit".equalsIgnoreCase(typed)) {
            throw new ExitExcepction();
        }
        return typed;
    }
}
