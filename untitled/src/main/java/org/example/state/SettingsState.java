package org.example.state;

import org.example.Main;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class SettingsState extends BasicGameState {

    private int stateID;

    public SettingsState(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        // Инициализация настроек
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, container.getWidth(), container.getHeight());

        g.setColor(Color.white);
        g.drawString("Экран настроек", 400, 200);
        g.drawString("Здесь будут настройки игры", 400, 250);
        g.drawString("Нажмите ESC для возврата в меню", 400, 300);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(Main.MAIN_MENU);
        }
    }
}