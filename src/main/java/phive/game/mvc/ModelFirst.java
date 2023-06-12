package phive.game.mvc;

import phive.game.models.Field;
import phive.game.models.Player;

import java.util.Random;

public class ModelFirst extends ModelBase {
    Field field;
    private static final Random RANDOM = new Random();
    private static final int DIFFICULT_VALUE = 10;

    public ModelFirst(Field field) {
        this.field = field;
    }

    @Override
    public boolean validCellCheck(int x, int y) {
        return x >= 0 && x < field.getSizeX() && y >= 0 && y < field.getSizeY();
    }

    @Override
    public boolean emptyCellCheck(int x, int y) {
        return field.getField()[x][y] == field.getEmptyCell();
    }

    @Override
    public void humanTurn(char mark, int x, int y) {
        field.getField()[x][y] = mark;
    }

    @Override
    public boolean winCheck(char mark) {
        return horizontalWinCheck(mark) || verticalWinCheck(mark) || diagonalWinCheck(mark);
    }

    @Override
    public boolean drawCheck(Integer currentTurn) {
        return currentTurn == field.getSizeX() * field.getSizeY();
    }

    @Override
    public boolean horizontalWinCheck(char mark) {
        for (int i = 0; i < field.getSizeX(); i++) {
            for (int j = 0; j < field.getSizeY(); j++) {
                int comboCount = 1; // Инициализируем счетчик победной комбинации.

                // В цикле проходимся от 1 до необходимого для победы числа стоящих подряд символов,
                // ограничивая сумму координаты по горизонтали и k размером поля по горизонтали.
                for (int k = 1; k <= field.getWinCount() && i + k < field.getSizeX(); k++)
                    if (mark == field.getField()[i][j] && mark == field.getField()[i + k][j])
                        // Увеличиваем счетчик победной комбинации,
                        // в случае стоящих подряд двух одинаковых символов.
                        comboCount++;
                    else
                        break;
                if (comboCount == field.getWinCount())
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean verticalWinCheck(char mark) {
        for (int i = 0; i < field.getSizeX(); i++) {
            for (int j = 0; j < field.getSizeY(); j++) {
                int comboCount = 1; // Инициализируем счетчик победной комбинации.

                // В цикле проходимся от 1 до необходимого для победы числа стоящих подряд символов,
                // ограничивая сумму координаты по вертикали и k размером поля по вертикали.
                for (int k = 1; k <= field.getWinCount() && k + j < field.getSizeY(); k++)
                    if (mark == field.getField()[i][j] && mark == field.getField()[i][j + k])
                        // Увеличиваем счетчик победной комбинации,
                        // в случае стоящих подряд двух одинаковых символов.
                        comboCount++;
                    else
                        break;
                if (comboCount == field.getWinCount())
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean diagonalWinCheck(char mark) {
        for (int i = 0; i < field.getSizeX(); i++)
            for (int j = 0; j < field.getSizeY(); j++) {

                // Инициализируем счетчик победной комбинации для диагонали сверху-вперёд.
                // В цикле проходимся от 1 до необходимого для победы числа стоящих подряд символов,
                // ограничивая сумму координаты по вертикали и k размером поля по вертикали
                // и сумму координаты по горизонтали и k размером поля по горизонтали.
                int comboCountForward = 1;
                for (int k = 1; k <= field.getWinCount() && i + k < field.getSizeX() && j + k < field.getSizeY(); k++) {
                    if (mark == field.getField()[i][j] && mark == field.getField()[i + k][j + k])
                        comboCountForward++;
                    else
                        break;
                    if (comboCountForward == field.getWinCount())
                        return true;
                }

                // Инициализируем счетчик победной комбинации для диагонали сверху-назад.
                // В цикле проходимся от 1 до необходимого для победы числа стоящих подряд символов,
                // ограничивая сумму координаты по вертикали и k размером поля по вертикали
                // и разность координаты по горизонтали и k нулём.
                int comboCountBack = 1;
                for (int k = 1; k <= field.getWinCount() && i - k >= 0 && j + k < field.getSizeY(); k++) {
                    if (mark == field.getField()[i][j] && mark == field.getField()[i - k][j + k])
                        comboCountBack++;
                    else
                        break;
                    if (comboCountBack == field.getWinCount())
                        return true;
                }
            }
        return false;
    }

    @Override
    public void aiTurn(char playerMark, char aiMark) {
//        // Первоочередно комп будет проверять и развивать свою или блокировать чужую предвыигрышную комбинацию
//        if (aiHorizontalTurn(aiMark, aiMark, field.getWinCount() - 1) ||
//                aiVerticalTurn(aiMark, aiMark, field.getWinCount() - 1) ||
//                aiDiagonalTurn(aiMark, aiMark, field.getWinCount() - 1))
//            return;
//
//        else if (aiHorizontalTurn(playerMark, aiMark, field.getWinCount() - 1) ||
//                aiVerticalTurn(playerMark, aiMark, field.getWinCount() - 1) ||
//                aiDiagonalTurn(playerMark, aiMark, field.getWinCount() - 1))
//            return;
//
//            // Во вторую очередь будет проверять и развивать свою или блокировать чужую блокировать
//            // предпредвыигрышную комбинацию, чтобы, если игрок строит комбинацию в середине поля,
//            // была возможность заблокировать его с двух сторон.
//        else if (aiHorizontalTurn(aiMark, aiMark, field.getWinCount() - 2) ||
//                aiVerticalTurn(aiMark, aiMark, field.getWinCount() - 2) ||
//                aiDiagonalTurn(aiMark, aiMark, field.getWinCount() - 2))
//            return;
//
//        else if (aiHorizontalTurn(playerMark, aiMark, field.getWinCount() - 2) ||
//                aiVerticalTurn(playerMark, aiMark, field.getWinCount() - 2) ||
//                aiDiagonalTurn(playerMark, aiMark, field.getWinCount() - 2))
//            return;
//
//        // В остальных случаях - рандомный ход
//        else
//            aiRandomTurn(aiMark);

        for (int i = 1; i < field.getWinCount(); i++) {
            if (aiHorizontalTurn(aiMark, aiMark, field.getWinCount() - i) ||
                    aiVerticalTurn(aiMark, aiMark, field.getWinCount() - i) ||
                    aiDiagonalTurn(aiMark, aiMark, field.getWinCount() - i))
                return;

            else if (aiHorizontalTurn(playerMark, aiMark, field.getWinCount() - i) ||
                    aiVerticalTurn(playerMark, aiMark, field.getWinCount() - i) ||
                    aiDiagonalTurn(playerMark, aiMark, field.getWinCount() - i))
                return;
        }
        aiRandomTurn(aiMark);
    }

    @Override
    public void aiRandomTurn(char aiMark) {
        int x, y;
        do {
            x = RANDOM.nextInt(field.getSizeX());
            y = RANDOM.nextInt(field.getSizeY());
        } while (!emptyCellCheck(x, y));
        field.getField()[x][y] = aiMark;
    }

    @Override
    public boolean aiHorizontalTurn(char playerMark, char aiMark, int bingo) {
        for (int i = 0; i < field.getSizeX(); i++) {
            for (int j = 0; j < field.getSizeY(); j++) {
                if (checkCombo(playerMark, aiMark, bingo, i, j, 1, 0) ||
                        checkCombo(playerMark, aiMark, bingo, i, j, -1, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean aiVerticalTurn(char playerMark, char aiMark, int bingo) {
        for (int i = 0; i < field.getSizeY(); i++) {
            for (int j = 0; j < field.getSizeY(); j++) {
                if (checkCombo(playerMark, aiMark, bingo, i, j, 0, 1) ||
                        checkCombo(playerMark, aiMark, bingo, i, j, 0, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean aiDiagonalTurn(char playerMark, char aiMark, int bingo) {
        for (int i = 0; i < field.getSizeX(); i++) {
            for (int j = 0; j < field.getSizeY(); j++) {
                if (checkCombo(playerMark, aiMark, bingo, i, j, 1, 1) ||
                        checkCombo(playerMark, aiMark, bingo, i, j, -1, 1)
                        || checkCombo(playerMark, aiMark, bingo, i, j, 1, -1)
                        || checkCombo(playerMark, aiMark, bingo, i, j, -1, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkCombo(char playerMark, char aiMark, int bingo, int i, int j, int directionX, int directionY) {
        int combo = 0;
        // Прощупываем на победные комбинации по направлениям, заданным аргументами
        for (int k = 0; k < bingo &&
                i + k * directionX >= 0 &&
                i + k * directionX < field.getSizeX() &&
                j + k * directionY >= 0
                && j + k * directionY < field.getSizeY(); k++) {
            if (playerMark == field.getField()[i][j] && playerMark == field.getField()[i + k * directionX][j + k * directionY]) {
                combo++;
                if (combo == bingo &&
                        i + (k + 1) * directionX >= 0 && i + (k + 1) * directionX < field.getSizeX() &&
                        j + (k + 1) * directionY >= 0 && j + (k + 1) * directionY < field.getSizeY() &&
                        field.getField()[i + (k + 1) * directionX][j + (k + 1) * directionY] == field.getEmptyCell()) {

                    // При совпадении двух рандомов будет сделан невнимательный ход.
                    // В противном случае - блокировка
                    if (RANDOM.nextInt(DIFFICULT_VALUE) == RANDOM.nextInt(DIFFICULT_VALUE)) {
                        aiRandomTurn(aiMark);
                    } else {
                        field.getField()[i + (k + 1) * directionX][j + (k + 1) * directionY] = aiMark;
                    }
                    return true;
                }
            } else {
                break;
            }
        }
        return false;
    }

}
