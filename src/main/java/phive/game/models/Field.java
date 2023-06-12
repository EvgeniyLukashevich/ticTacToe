package phive.game.models;

public class Field {
    private char[][] field;
    private static final char EMPTY_CELL = '_';
    private int sizeX = 3;
    private int sizeY = 3;
    private int winCount = 3;


    public Field(int sizeX, int sizeY, int winCount) {
        if (winCount > sizeY && winCount > sizeX) {
            this.sizeX = winCount;
            this.sizeY = winCount;
        } else if (winCount > sizeY)
            this.sizeY = winCount;
        else if (winCount > sizeX)
            this.sizeX = winCount;
        else {
            this.sizeX = sizeX;
            this.sizeY = sizeY;
        }
        this.winCount = winCount;
    }

    public Field() {
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getWinCount() {
        return winCount;
    }

    public char getEmptyCell() {
        return EMPTY_CELL;
    }

    public char[][] getField() {
        return field;
    }


    public boolean emptyCellCheck(int x, int y) {
        return field[x][y] == EMPTY_CELL;
    }

    /**
     * Метод инициализации игрового поля.
     */
    public void init() {
        field = new char[sizeX][sizeY];
        for (int x = 0; x < sizeX; x++)
            for (int y = 0; y < sizeY; y++)
                field[x][y] = EMPTY_CELL;
    }

    /**
     * Метод отрисовки игрового поля.
     */
    public void print() {
        // Рисуем шапку
        System.out.print("+");
        for (int i = 1; i <= sizeX; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        // Во внешнем цикле проставляем номер строки,
        // во внутреннем - заполняем строку символами из нашего массива (игрового поля).
        for (int i = 0; i < sizeY; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < sizeX; j++) {
                System.out.print("|" + field[j][i]);
            }
            System.out.print("|");
            System.out.println();
        }

        // Рисуем подвал
        for (int j = 0; j <= sizeX; j++)
            System.out.print("==");
        System.out.println();
    }

}
