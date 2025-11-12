package org.example;

import org.example.state.MainMenuState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.*;

public class Main extends BasicGame {

    public static final int MAIN_MENU = 0;
    public static final int GAME = 1;

    public Main(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        // Инициализация ресурсов, текстур и т.д.
        this.addState(new MainMenuState(MAIN_MENU));
        this.addState(new GameState(GAME));

        this.enterState(MAIN_MENU);
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        // Логика игры
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        // Рисуем текст
        graphics.drawString("Привет, Slick2D!", 100, 100);
    }

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", "C:\\lwjgl-2.9.3\\lwjgl-2.9.3\\native\\windows");

        try {
            AppGameContainer app = new AppGameContainer(new Main("Моя первая игра на Slick2D"));
            app.setDisplayMode(1080, 720, false); // ширина, высота, полноэкранный режим (false)
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
