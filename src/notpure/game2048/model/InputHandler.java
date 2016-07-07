package notpure.game2048.model;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;

/**
 * An input handler.
 *
 * @author Pure_
 */
public abstract class InputHandler {

    private final BasicGame game;

    public InputHandler(BasicGame game) {
        this.game = game;
    }

    public abstract void handle(GameContainer gc);

    protected BasicGame getGame() {
        return game;
    }
}
