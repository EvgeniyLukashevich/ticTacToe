package phive.game.mvc;

import phive.game.models.Field;
import phive.game.models.Player;

public abstract class ControllerBase {
    Field field;
    ModelBase model;
    Player player1;
    Player player2;

    public ControllerBase(Field field, Player player1, Player player2, ModelBase model) {
        this.field = field;
        this.player1 = player1;
        this.player2 = player2;
        this.model = model;
    }

    public abstract void game();

}
