package phive.game;

import phive.game.models.Field;
import phive.game.models.Player;
import phive.game.mvc.ControllerBase;
import phive.game.mvc.ControllerPvE;
import phive.game.mvc.ModelBase;
import phive.game.mvc.ModelFirst;

public class Main {
    public static void main(String[] args) {
        Field field = new Field(6, 6, 4);
        Player human = new Player('X');
        Player ai = new Player('O');
        ModelBase model = new ModelFirst(field);
        ControllerBase controller = new ControllerPvE(field, human, ai, model);
        controller.game();

    }
}
