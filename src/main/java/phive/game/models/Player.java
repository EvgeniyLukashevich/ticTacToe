package phive.game.models;

public class Player {
    private char mark;
    private int wins;

    public Player(char mark) {
        this.mark = mark;
        wins = 0;
    }

    public char getMark() {
        return mark;
    }

    public int getWins() {
        return wins;
    }

    public void setMark(char mark) {
        this.mark = mark;
    }

    public void winsCounter(){
        wins++;
    }
}
