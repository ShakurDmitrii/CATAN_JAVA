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
        // Отключаем контроллеры чтобы избежать ошибок
        container.setForceExit(false);

        this.addState(new MainMenuState(MAIN_MENU));
        this.addState(new GameState(GAME));

        this.enterState(MAIN_MENU);
    }

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", "C:\\Users\\Fyodor\\lwjgl-2.9.3\\native\\windows");

        // Отключаем JInput контроллеры
        System.setProperty("net.java.games.input.useDefaultPlugin", "false");

        try {
            AppGameContainer app = new AppGameContainer(new Main("Catan"));
            app.setDisplayMode(1720, 900, false);
            app.setTargetFrameRate(60);
            app.setShowFPS(false);
            app.setAlwaysRender(true);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}