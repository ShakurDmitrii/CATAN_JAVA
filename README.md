# CATAN_JAVA
Catan game Java version 
# Slick2d
Slick2D - это упрощенная Java-библиотека для создания 2D игр, построенная на основе LWJGL. Вот как начать писать игры на Slick2D:
# Maven зависимость:
xml
<dependency>
    <groupId>org.slick2d</groupId>
    <artifactId>slick2d-core</artifactId>
    <version>1.0.2</version>
</dependency>

# Базовая структура игры
java
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class GameExample extends StateBasedGame {
    
    public GameExample(String name) {
        super(name);
    }
    
    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        // Добавляем игровые состояния
        addState(new MenuState());
        addState(new PlayState());
    }
    
    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new GameExample("Моя игра"));
            app.setDisplayMode(800, 600, false);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}

# Структура каталогов проекта

MyGame/

├── src/
|
│   └── com/
|
│       └── mygame/
|
│           ├── Main.java
|
│           ├── MenuState.java
|
│           └── PlayState.java
|
├── res/
|
│   ├── player.png
|
│   ├── background.png
|
│   ├── shoot.wav
|
│   └── music.ogg
|
|
└── lib/

    ├── slick.jar
    |
    └── lwjgl.jar
    
    
# Советы по разработке
Используйте StateBasedGame для управления разными экранами игры

Всегда обрабатывайте SlickException при загрузке ресурсов

Используйте delta для плавной анимации независимо от FPS

Освобождайте ресурсы в методе close()

Тестируйте на разных разрешениях
