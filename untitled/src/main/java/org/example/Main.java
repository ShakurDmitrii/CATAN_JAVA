package org.example;

import org.example.state.MainMenuState;
import org.example.state.GameState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {

    public static final int MAIN_MENU = 0;
    public static final int GAME = 1;

    public Main(String title) {
        super(title);
    }

    @Override
    public void initStatesList(org.newdawn.slick.GameContainer container) throws SlickException {
        this.addState(new MainMenuState(MAIN_MENU));
        this.addState(new GameState(GAME));

        this.enterState(MAIN_MENU);
    }

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", "C:\\lwjgl-2.9.3\\lwjgl-2.9.3\\native\\windows");

        try {
            AppGameContainer app = new AppGameContainer(new Main("Моя первая игра на Slick2D"));
            app.setDisplayMode(2160, 720, false); // ширина, высота, полноэкранный режим
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
