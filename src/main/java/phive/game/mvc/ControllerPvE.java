package phive.game.mvc;

import phive.game.models.Field;
import phive.game.models.Player;

import java.util.Scanner;

public class ControllerPvE extends ControllerBase {
    Scanner SCAN = new Scanner(System.in);

    public ControllerPvE(Field field, Player player1, Player player2, ModelBase model) {
        super(field, player1, player2, model);
    }


    public void game() {
        boolean startingPlayer = true;

        while (true) {

            Integer currentTurn = 0; // счетчик количества ходов (необходим для определения ничьи)
            field.init();


            if (startingPlayer) {
                player1.setMark('X');
                player2.setMark('O');
                field.print();

                while (true) {
                    System.out.println("\nТы ходишь КРЕСТИКАМИ :)");
                    System.out.println("Вводи координаты хода через пробел: ");
                    int x, y;
                    do {
                        x = SCAN.nextInt() - 1;
                        y = SCAN.nextInt() - 1;
                    } while (!model.validCellCheck(x, y) || !model.emptyCellCheck(x, y));
                    model.humanTurn(player1.getMark(), x, y);
                    field.print();
                    currentTurn++;

                    if (model.winCheck(player1.getMark())) {
                        player1.winsCounter();
                        System.out.println("Вы победили");
                        break;
                    } else if (model.drawCheck(currentTurn)) {
                        System.out.println("Ничья");
                        break;
                    }

                    model.aiTurn(player1.getMark(), player2.getMark());
                    field.print();
                    currentTurn++;

                    if (model.winCheck(player2.getMark())) {
                        player2.winsCounter();
                        System.out.println("Вы проиграли");
                        break;
                    } else if (model.drawCheck(currentTurn)) {
                        System.out.println("Ничья");
                        break;
                    }
                }
            }

            if (!startingPlayer) {
                player1.setMark('O');
                player2.setMark('X');
                while (true) {
                    model.aiTurn(player1.getMark(), player2.getMark());
                    field.print();
                    currentTurn++;

                    if (model.winCheck(player2.getMark())) {
                        player2.winsCounter();
                        System.out.println("Вы проиграли");
                        break;
                    } else if (model.drawCheck(currentTurn)) {
                        System.out.println("Ничья");
                        break;
                    }

                    System.out.println("\nТы ходишь НОЛИКАМИ :)");
                    System.out.println("Вводи координаты хода через пробел: ");
                    int x, y;
                    do {
                        x = SCAN.nextInt() - 1;
                        y = SCAN.nextInt() - 1;
                    } while (!model.validCellCheck(x, y) || !model.emptyCellCheck(x, y));
                    model.humanTurn(player1.getMark(), x, y);
                    field.print();
                    currentTurn++;

                    if (model.winCheck(player1.getMark())) {
                        player1.winsCounter();
                        System.out.println("Вы победили");
                        break;
                    } else if (model.drawCheck(currentTurn)) {
                        System.out.println("Ничья");
                        break;
                    }
                }
            }

            startingPlayer = !startingPlayer;
            System.out.println("\n### ОБЩИЙ СЧЁТ ###\n(Шкипер) " + player1.getWins() + " : " + player2.getWins() + " (Комп)\n");
            System.out.println("Повторим? (y - да)");
            if (!SCAN.next().equals("y"))
                break;
        }
    }

}
