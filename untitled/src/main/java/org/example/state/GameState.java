package org.example.state;

import org.example.Main;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class GameState extends BasicGameState {

    private int stateID;

    public GameState(int id) {
        this.stateID = id;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {}

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        g.drawString("Игра запущена!", 480, 300);
        g.drawString("Нажмите ESC чтобы вернуться в меню", 400, 330);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(Main.MAIN_MENU);
        }
    }
}
