package org.example.state;


import org.example.Main;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {

    private int stateID;

    public MainMenuState(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        g.drawString("Главное меню", 420, 200);
        g.drawString("1. Начать игру", 450, 250);
        g.drawString("2. Выйти", 450, 280);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Input input = gameContainer.getInput();

        if (input.isKeyPressed(Input.KEY_1)) {
            stateBasedGame.enterState(Main.GAME); // перейти в игру
        }

        if (input.isKeyPressed(Input.KEY_2)) {
            gameContainer.exit(); // выйти
        }
    }

}
