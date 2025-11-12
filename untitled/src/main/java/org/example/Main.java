package org.example;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Main extends BasicGame {

    public Main(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        // Инициализация ресурсов, текстур и т.д.
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
