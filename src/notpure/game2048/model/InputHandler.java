/*
 * A clone of the popular 2048 game.
 * Copyright (C) 2016 Pure
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
