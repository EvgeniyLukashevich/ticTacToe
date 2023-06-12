package phive.game;

import java.util.Random;
import java.util.Scanner;

public class Origin {
    private static final char HUMAN_MARK = '▲';
    private static final char AI_MARK = '●';
    private static final char EMPTY_CELL = '□';
    private static final int BINGO_COUNT = 4;

    // Попробовал придумать переменную для симуляции случайной ошибки компьютера.
    // Чем больше значение этой переменной, тем менее вероятна ситуация ошибки при ходе компьютера.
    // Можно попробовать внедрить в метод хода ИИ, при блокировке хода человека,
    // два рандома (с этой переменной в качестве аргумента),
    // при совпадении значения которых компьютер будет делать "невнимательный" ход
    private static final int DIFFICULT_VALUE = 5;
    private static final Scanner SCAN = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static char[][] field;
    private static int fieldSizeX;
    private static int fieldSizeY;


    public static void main(String[] args) {
        Integer currentTurn = 0; // счетчик количества ходов (необходим для определения ничьи)
        while (true) {
            fieldInit();
            fieldPrint();
            while (true) {
                humanTurn();
                fieldPrint();
                currentTurn++;
                if (endGameCheck(HUMAN_MARK, currentTurn, "Поздравляю! Вы победили! :)"))
                    break;
                aiTurn();
                fieldPrint();
                currentTurn++;
                if (endGameCheck(AI_MARK, currentTurn, "Поздравляю! Вы проиграли! :D"))
                    break;
            }
            System.out.println("Повторим? (y - да)");
            if (!SCAN.next().equals("y"))
                break;
        }
    }

    /**
     * Метод инициализации игрового поля.
     */
    private static void fieldInit() {
        fieldSizeX = 8;
        fieldSizeY = 8;

        field = new char[fieldSizeX][fieldSizeY];
        for (int x = 0; x < fieldSizeX; x++)
            for (int y = 0; y < fieldSizeY; y++)
                field[x][y] = EMPTY_CELL;
    }

    /**
     * Метод отрисовки игрового поля.
     */
    private static void fieldPrint() {
        // Рисуем шапку
        System.out.print("+");
        for (int i = 1; i <= fieldSizeX; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        // Во внешнем цикле проставляем номер строки,
        // во внутреннем - заполняем строку символами из нашего массива (игрового поля).
        for (int i = 0; i < fieldSizeY; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < fieldSizeX; j++) {
                System.out.print("|" + field[j][i]);
            }
            System.out.print("|");
            System.out.println();
        }

        // Рисуем подвал
        for (int j = 0; j <= fieldSizeX; j++)
            System.out.print("==");
        System.out.println();
    }

    /**
     * Метод проверки ячейки на валидность.
     *
     * @param x координата по горизонтали.
     * @param y координата по вертикали.
     * @return истина/ложь в случае, если проверка пройдена/не пройдена.
     */
    private static boolean validCellCheck(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Метод проверки, является ли ячейка пустой.
     *
     * @param x координата по горизонтали.
     * @param y координата по вертикали.
     * @return истина/ложь в случае, если проверка пройдена/не пройдена.
     */
    private static boolean emptyCellCheck(int x, int y) {
        return field[x][y] == EMPTY_CELL;
    }

    /**
     * Метод обработки хода игрока.
     */
    private static void humanTurn() {
        int x, y;
        do {
            System.out.print("Введите координаты хода через пробел: ");
            x = SCAN.nextInt() - 1;
            y = SCAN.nextInt() - 1;
        } while (!validCellCheck(x, y) || !emptyCellCheck(x, y));
        field[x][y] = HUMAN_MARK;
    }


    /**
     * Метод проверки состояния игры на предмет победы одного из играющих (человек/комп).
     *
     * @param mark метка, отмечающая ход.
     * @return истина в случае победы, ложь в случае отсутсвия победы.
     */
    private static boolean winCheck(char mark) {
        return horizontalWinCheck(mark) || verticalWinCheck(mark) || diagonalWinCheck(mark);
    }

    /**
     * Метод проверки состояния игры на предмет ничьи.
     *
     * @param currentTurn порядковый номер текущего хода.
     * @return истина в случае ничьей, ложь в случае отсутсвия ничьей.
     */
    private static boolean drawCheck(Integer currentTurn) {
        return currentTurn == fieldSizeX * fieldSizeY;
    }

    /**
     * Общий метод проверки состояния игры.
     *
     * @param mark        метка, отмечающая ход.
     * @param currentTurn порядковый номер текущего хода.
     * @param message     поздравительное сообщение.
     * @return истина, если игра окончена, ложь - игра продолжается.
     */
    private static boolean endGameCheck(char mark, Integer currentTurn, String message) {
        if (winCheck(mark)) {
            System.out.println(message);
            return true;
        } else if (drawCheck(currentTurn)) {
            System.out.println("Игра закончена, и в ней никто не проиграл :)");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Метод проверки победы по горизонтали.
     *
     * @param mark метка, отмечающая ход.
     * @return истина в случае победы, ложь в случае отсутсвия победы.
     */
    private static boolean horizontalWinCheck(char mark) {
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                int comboCount = 1; // Инициализируем счетчик победной комбинации.

                // В цикле проходимся от 1 до необходимого для победы числа стоящих подряд символов,
                // ограничивая сумму координаты по горизонтали и k размером поля по горизонтали.
                for (int k = 1; k <= BINGO_COUNT && i + k < fieldSizeX; k++)
                    if (mark == field[i][j] && mark == field[i + k][j])
                        // Увеличиваем счетчик победной комбинации,
                        // в случае стоящих подряд двух одинаковых символов.
                        comboCount++;
                    else
                        break;
                if (comboCount == BINGO_COUNT)
                    return true;
            }
        }
        return false;
    }

    /**
     * Метод проверки победы по вертикали.
     *
     * @param mark метка, отмечающая ход.
     * @return истина в случае победы, ложь в случае отсутсвия победы.
     */
    private static boolean verticalWinCheck(char mark) {
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                int comboCount = 1; // Инициализируем счетчик победной комбинации.

                // В цикле проходимся от 1 до необходимого для победы числа стоящих подряд символов,
                // ограничивая сумму координаты по вертикали и k размером поля по вертикали.
                for (int k = 1; k <= BINGO_COUNT && k + j < fieldSizeY; k++)
                    if (mark == field[i][j] && mark == field[i][j + k])
                        // Увеличиваем счетчик победной комбинации,
                        // в случае стоящих подряд двух одинаковых символов.
                        comboCount++;
                    else
                        break;
                if (comboCount == BINGO_COUNT)
                    return true;
            }
        }
        return false;
    }

    /**
     * Метод проверки победы по диагонали.
     *
     * @param mark метка, отмечающая ход.
     * @return истина в случае победы, ложь в случае отсутсвия победы.
     */
    private static boolean diagonalWinCheck(char mark) {
        for (int i = 0; i < fieldSizeX; i++)
            for (int j = 0; j < fieldSizeY; j++) {

                // Инициализируем счетчик победной комбинации для диагонали сверху-вперёд.
                // В цикле проходимся от 1 до необходимого для победы числа стоящих подряд символов,
                // ограничивая сумму координаты по вертикали и k размером поля по вертикали
                // и сумму координаты по горизонтали и k размером поля по горизонтали.
                int comboCountForward = 1;
                for (int k = 1; k <= BINGO_COUNT && i + k < fieldSizeX && j + k < fieldSizeY; k++) {
                    if (mark == field[i][j] && mark == field[i + k][j + k])
                        comboCountForward++;
                    else
                        break;
                    if (comboCountForward == BINGO_COUNT)
                        return true;
                }

                // Инициализируем счетчик победной комбинации для диагонали сверху-назад.
                // В цикле проходимся от 1 до необходимого для победы числа стоящих подряд символов,
                // ограничивая сумму координаты по вертикали и k размером поля по вертикали
                // и разность координаты по горизонтали и k нулём.
                int comboCountBack = 1;
                for (int k = 1; k <= BINGO_COUNT && i - k >= 0 && j + k < fieldSizeY; k++) {
                    if (mark == field[i][j] && mark == field[i - k][j + k])
                        comboCountBack++;
                    else
                        break;
                    if (comboCountBack == BINGO_COUNT)
                        return true;
                }
            }
        return false;
    }

    private static void aiTurn() {
        // Первоочередно комп будет проверять и блокировать предвыигрышную комбинацию
        if (aiHorizontalBlock(BINGO_COUNT - 1) ||
                aiVerticalBlock(BINGO_COUNT - 1) ||
                aiDiagonalBlock(BINGO_COUNT - 1))
            return;

            // Во вторую очередь будет проверять и блокировать предпредвыигрышную комбинацию,
            // чтобы, если игрок строит комбинацию в середине поля,
            // была возможность заблокировать его с двух сторон.
        else if (aiHorizontalBlock(BINGO_COUNT - 2) ||
                aiVerticalBlock(BINGO_COUNT - 2) ||
                aiDiagonalBlock(BINGO_COUNT - 2))
            return;

            // В остальных случаях - рандомный ход
        else
            aiRandomTurn();
    }

    /**
     * Метод "невнимательного" хода компьютера.
     */
    private static void aiRandomTurn() {
        int x, y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!emptyCellCheck(x, y));
        field[x][y] = AI_MARK;
    }

    /**
     * Метод блокировки победы игрока по горизонтали.
     *
     * @param bingo число стоящих подряд символов, которое компьютер будет принимать за выигрышную комбинацию.
     * @return истину при обнаружении победной комбинации.
     */
    private static boolean aiHorizontalBlock(int bingo) {
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (checkCombo(bingo, i, j, 1, 0) ||
                        checkCombo(bingo, i, j, -1, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Метод блокировки победы игрока по вертикали.
     *
     * @param bingo число стоящих подряд символов, которое компьютер будет принимать за выигрышную комбинацию.
     * @return истину при обнаружении победной комбинации.
     */
    private static boolean aiVerticalBlock(int bingo) {
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (checkCombo(bingo, i, j, 0, 1) ||
                        checkCombo(bingo, i, j, 0, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Метод блокировки победы игрока по диагоналям.
     *
     * @param bingo число стоящих подряд символов, которое компьютер будет принимать за выигрышную комбинацию.
     * @return истину при обнаружении победной комбинации.
     */
    private static boolean aiDiagonalBlock(int bingo) {
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (checkCombo(bingo, i, j, 1, 1) ||
                        checkCombo(bingo, i, j, -1, 1)
                        || checkCombo(bingo, i, j, 1, -1)
                        || checkCombo(bingo, i, j, -1, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Попробовал написать вспомогательный метод для блокировок компьютера, когда код начал сильно разрастаться.
     *
     * @param bingo      число стоящих подряд символов, которое компьютер будет принимать за выигрышную комбинацию.
     * @param i          индекс внешнего слоя массива (горизонталь игрового поля).
     * @param j          индекс внутренного слоя массива (вертикаль игрового поля).
     * @param directionX направление по горизонтали (1 - вперед, -1 - назад).
     * @param directionY направление по вертикали (1 - вперед, -1 - назад).
     * @return истину при обнаружении победной комбинации.
     */
    private static boolean checkCombo(int bingo, int i, int j, int directionX, int directionY) {
        int combo = 0;
        // Прощупываем на победные комбинации по направлениям, заданным аргументами
        for (int k = 0; k < bingo &&
                i + k * directionX >= 0 &&
                i + k * directionX < fieldSizeX &&
                j + k * directionY >= 0
                && j + k * directionY < fieldSizeY; k++) {
            if (HUMAN_MARK == field[i][j] && HUMAN_MARK == field[i + k * directionX][j + k * directionY]) {
                combo++;
                if (combo == bingo &&
                        i + (k + 1) * directionX >= 0 && i + (k + 1) * directionX < fieldSizeX &&
                        j + (k + 1) * directionY >= 0 && j + (k + 1) * directionY < fieldSizeY &&
                        field[i + (k + 1) * directionX][j + (k + 1) * directionY] == EMPTY_CELL) {

                    // При совпадении двух рандомов будет сделан невнимательный ход.
                    // В противном случае - блокировка
                    if (RANDOM.nextInt(DIFFICULT_VALUE) == RANDOM.nextInt(DIFFICULT_VALUE)) {
                        aiRandomTurn();
                    } else {
                        field[i + (k + 1) * directionX][j + (k + 1) * directionY] = AI_MARK;
                    }
                    return true;
                }
            } else {
                break;
            }
        }
        return false;
    }


    /* Первый вариант методов блокировки

    private static boolean aiHorizontalBlock(int bingo) {
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                int comboForward = 1;
                // Щупаем на возможность блокировки с правой стороны
                for (int k = 1; k <= bingo && i + k < fieldSizeX; k++) {
                    if (HUMAN_MARK == field[i][j] && HUMAN_MARK == field[i + k][j]) {
                        comboForward++;
                        if (comboForward == bingo
                                && i + k + 1 < fieldSizeX
                                && field[i + k + 1][j] == EMPTY_CELL) {

                            // При совпадении двух рандомов будет сделан невнимательный ход.
                            // В противном случае - блокировка
                            if (RANDOM.nextInt(DIFFICULT_VALUE) == RANDOM.nextInt(DIFFICULT_VALUE))
                                aiRandomTurn();
                            else
                                field[i + k + 1][j] = AI_MARK;
                            return true;
                        }
                    } else
                        break;
                }
                int comboBack = 1;
                // Щупаем на возможность блокировки с левой стороны
                for (int k = 1; k <= bingo && i - k >= 0; k++) {
                    if (HUMAN_MARK == field[i][j] && HUMAN_MARK == field[i - k][j]) {
                        comboBack++;
                        if (comboBack == bingo
                                && i - k - 1 >= 0
                                && field[i - k - 1][j] == EMPTY_CELL) {

                            // При совпадении двух рандомов будет сделан невнимательный ход.
                            // В противном случае - блокировка
                            if (RANDOM.nextInt(DIFFICULT_VALUE) == RANDOM.nextInt(DIFFICULT_VALUE))
                                aiRandomTurn();
                            else
                                field[i - k - 1][j] = AI_MARK;
                            return true;
                        }
                    } else
                        break;
                }
            }
        }
        return false;
    }

    private static boolean aiVerticalBlock(int bingo) {
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {

                int comboForward = 1;
                // Щупаем на возможность блокировки снизу
                for (int k = 1; k <= bingo && k + j < fieldSizeY; k++)
                    if (HUMAN_MARK == field[i][j] && HUMAN_MARK == field[i][j + k]) {
                        comboForward++;
                        if (comboForward == bingo
                                && j + k + 1 < fieldSizeY
                                && field[i][j + k + 1] == EMPTY_CELL) {

                            // При совпадении двух рандомов будет сделан невнимательный ход.
                            // В противном случае - блокировка
                            if (RANDOM.nextInt(DIFFICULT_VALUE) == RANDOM.nextInt(DIFFICULT_VALUE))
                                aiRandomTurn();
                            else
                                field[i][j + k + 1] = AI_MARK;
                            return true;
                        }
                    } else
                        break;

                int comboBack = 1;
                // Щупаем на возможность блокировки сверху
                for (int k = 1; k <= bingo && j - k >= 0; k++)
                    if (HUMAN_MARK == field[i][j] && HUMAN_MARK == field[i][j - k]) {
                        comboBack++;
                        if (comboBack == bingo
                                && j - k - 1 >= 0
                                && field[i][j - k - 1] == EMPTY_CELL) {

                            // При совпадении двух рандомов будет сделан невнимательный ход.
                            // В противном случае - блокировка
                            if (RANDOM.nextInt(DIFFICULT_VALUE) == RANDOM.nextInt(DIFFICULT_VALUE))
                                aiRandomTurn();
                            else
                                field[i][j - k - 1] = AI_MARK;
                            return true;
                        }
                    } else
                        break;
            }
        }
        return false;
    }
     */

}
