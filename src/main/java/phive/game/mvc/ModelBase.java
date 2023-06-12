package phive.game.mvc;

public abstract class ModelBase {
    abstract boolean validCellCheck(int x, int y);

    abstract boolean emptyCellCheck(int x, int y);

    abstract void humanTurn(char mark, int x, int y);

    abstract boolean winCheck(char mark);

    abstract boolean drawCheck(Integer currentTurn);

    abstract boolean horizontalWinCheck(char mark);

    abstract boolean verticalWinCheck(char mark);

    abstract boolean diagonalWinCheck(char mark);

    abstract void aiTurn(char playerMark, char aiMark);

    abstract void aiRandomTurn(char mark);

    abstract boolean aiHorizontalTurn(char playerMark, char aiMark, int bingo);

    abstract boolean aiVerticalTurn(char playerMark, char aiMark, int bingo);

    abstract boolean aiDiagonalTurn(char playerMark, char aiMark, int bingo);

    abstract boolean checkCombo(char playerMark, char aiMark, int bingo, int i, int j, int directionX, int directionY);
}