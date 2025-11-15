package org.example.state;

import org.example.Main;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {

    private int stateID;
    private int selectedOption = 0;
    private String[] menuOptions = {"START GAME", "SETTINGS", "EXIT"};
    private boolean inSettings = false;

    // Координаты и размеры кнопок для мыши
    private int[] buttonX = new int[3];
    private int[] buttonY = new int[3];
    private int buttonWidth = 200;
    private int buttonHeight = 40;

    // Анимация кнопок
    private float[] buttonScale = new float[3];
    private float[] targetScale = new float[3];
    private final float NORMAL_SCALE = 1.0f;
    private final float HOVER_SCALE = 1.1f;

    // Звуки
    private Sound hoverSound;
    private Sound clickSound;
    private Sound selectSound;

    // Для отслеживания предыдущего состояния мыши
    private int lastMouseOverButton = -1;
    private boolean wasMousePressed = false;

    public MainMenuState(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        System.out.println("Main menu initialized");

        // Инициализируем позиции кнопок
        updateButtonPositions(container);

        // Инициализируем анимацию
        for (int i = 0; i < buttonScale.length; i++) {
            buttonScale[i] = NORMAL_SCALE;
            targetScale[i] = NORMAL_SCALE;
        }

        // Загружаем звуки
        try {
            hoverSound = new Sound("assets/hover.wav");
            clickSound = new Sound("assets/click.wav");
            selectSound = new Sound("assets/select.wav");
            System.out.println("Sounds loaded successfully");
        } catch (SlickException e) {
            System.out.println("Could not load sound files: " + e.getMessage());
        }
    }

    private void updateButtonPositions(GameContainer container) {
        for (int i = 0; i < menuOptions.length; i++) {
            buttonX[i] = container.getWidth()/2 - buttonWidth/2;
            buttonY[i] = 200 + i * 50;
        }
    }

    private void updateAnimations(int delta) {
        float animationSpeed = 0.005f * delta; // Скорость анимации

        for (int i = 0; i < buttonScale.length; i++) {
            if (buttonScale[i] < targetScale[i]) {
                buttonScale[i] = Math.min(buttonScale[i] + animationSpeed, targetScale[i]);
            } else if (buttonScale[i] > targetScale[i]) {
                buttonScale[i] = Math.max(buttonScale[i] - animationSpeed, targetScale[i]);
            }
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        // Фон с градиентом
        drawGradientBackground(container, g);

        if (inSettings) {
            renderSettingsScreen(container, g);
        } else {
            renderMainMenu(container, g);
        }
    }

    private void drawGradientBackground(GameContainer container, Graphics g) {
        // Верхняя часть - темнее
        g.setColor(new Color(20, 40, 80));
        g.fillRect(0, 0, container.getWidth(), container.getHeight() / 2);

        // Нижняя часть - светлее
        g.setColor(new Color(30, 60, 120));
        g.fillRect(0, container.getHeight() / 2, container.getWidth(), container.getHeight() / 2);

        // Декоративные элементы
        g.setColor(new Color(255, 255, 255, 30));
        for (int i = 0; i < 5; i++) {
            g.drawOval(i * 150, 50, 100, 100);
            g.drawOval(container.getWidth() - i * 150 - 100, 400, 80, 80);
        }
    }

    private void renderMainMenu(GameContainer container, Graphics g) {
        // Заголовок с тенью
        g.setColor(new Color(0, 0, 0, 100));
        g.drawString("CATAN", container.getWidth()/2 - 28, 102);
        g.setColor(new Color(255, 255, 200));
        g.drawString("CATAN", container.getWidth()/2 - 30, 100);

        // Пункты меню с анимацией
        for (int i = 0; i < menuOptions.length; i++) {
            renderAnimatedButton(container, g, i);
        }
    }

    private void renderAnimatedButton(GameContainer container, Graphics g, int buttonIndex) {
        int centerX = container.getWidth() / 2;
        int baseY = 200 + buttonIndex * 50;

        // Вычисляем анимированную позицию и размер
        float scale = buttonScale[buttonIndex];
        int width = (int)(buttonWidth * scale);
        int height = (int)(buttonHeight * scale);
        int x = centerX - width / 2;
        int y = baseY - (height - buttonHeight) / 2;

        // Фон кнопки с анимацией
        if (scale > NORMAL_SCALE) {
            // Пульсирующий цвет при наведении
            float pulse = (float)(Math.sin(System.currentTimeMillis() * 0.01) * 0.2 + 0.8);
            g.setColor(new Color(255, 255, 0, (int)(80 * pulse)));
            g.fillRoundRect(x, y, width, height, 10);

            // Обводка
            g.setLineWidth(2);
            g.setColor(new Color(255, 200, 0, (int)(200 * pulse)));
            g.drawRoundRect(x, y, width, height, 10);
            g.setLineWidth(1);
        }

        // Текст кнопки
        String text = menuOptions[buttonIndex];
        int textWidth = g.getFont().getWidth(text);
        int textX = centerX - textWidth / 2;
        int textY = baseY + 10;

        if (buttonIndex == selectedOption || scale > NORMAL_SCALE) {
            g.setColor(Color.yellow);
            // Легкая тень для текста
            g.setColor(new Color(0, 0, 0, 100));
            g.drawString(text, textX + 1, textY + 1);
            g.setColor(Color.yellow);
        } else {
            g.setColor(Color.white);
        }

        g.drawString(text, textX, textY);
    }

    private void renderSettingsScreen(GameContainer container, Graphics g) {
        g.setColor(Color.white);
        g.drawString("SETTINGS", container.getWidth()/2 - 40, 100);
        g.drawString("Sound: ON", container.getWidth()/2 - 40, 200);
        g.drawString("Back to Main Menu [B]", container.getWidth()/2 - 100, 300);
    }

    private boolean isMouseOverButton(GameContainer container, int buttonIndex) {
        Input input = container.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        return mouseX >= buttonX[buttonIndex] &&
                mouseX <= buttonX[buttonIndex] + buttonWidth &&
                mouseY >= buttonY[buttonIndex] &&
                mouseY <= buttonY[buttonIndex] + buttonHeight;
    }

    private int getMouseOverButton(GameContainer container) {
        for (int i = 0; i < menuOptions.length; i++) {
            if (isMouseOverButton(container, i)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();

        // Обновляем позиции кнопок
        updateButtonPositions(container);

        // Обновляем анимации
        updateAnimations(delta);

        if (inSettings) {
            updateSettings(input, game, container);
        } else {
            updateMainMenu(container, game, input);
        }
    }

    private void updateSettings(Input input, StateBasedGame game, GameContainer container) {
        if (input.isKeyPressed(Input.KEY_B) || input.isKeyPressed(Input.KEY_ESCAPE)) {
            inSettings = false;
            playSound(selectSound);
        }
    }

    private void updateMainMenu(GameContainer container, StateBasedGame game, Input input) {
        // Определяем кнопку под мышью
        int mouseOverButton = getMouseOverButton(container);

        // Обработка наведения мыши и анимации
        handleMouseHover(mouseOverButton);

        // Обработка клавиатуры
        handleKeyboardInput(input);

        // Обработка мыши
        handleMouseInput(container, game, input, mouseOverButton);

        // Выход по ESC
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            container.exit();
        }
    }

    private void handleMouseHover(int mouseOverButton) {
        // Обновляем анимацию для всех кнопок
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == mouseOverButton || i == selectedOption) {
                targetScale[i] = HOVER_SCALE;
            } else {
                targetScale[i] = NORMAL_SCALE;
            }
        }

        // Воспроизводим звук при наведении на новую кнопку
        if (mouseOverButton != lastMouseOverButton) {
            if (mouseOverButton != -1) {
                playSound(hoverSound);
            }
            lastMouseOverButton = mouseOverButton;
        }
    }

    private void handleKeyboardInput(Input input) {
        if (input.isKeyPressed(Input.KEY_DOWN)) {
            selectedOption = (selectedOption + 1) % menuOptions.length;
            playSound(hoverSound);
        }

        if (input.isKeyPressed(Input.KEY_UP)) {
            selectedOption = (selectedOption - 1 + menuOptions.length) % menuOptions.length;
            playSound(hoverSound);
        }

        if (input.isKeyPressed(Input.KEY_ENTER)) {
            playSound(clickSound);
            // Небольшая задержка для звука перед переходом
            new Thread(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {}
            }).start();
        }
    }

    private void handleMouseInput(GameContainer container, StateBasedGame game, Input input, int mouseOverButton) {
        // Обновляем выбор при наведении мыши
        if (mouseOverButton != -1) {
            selectedOption = mouseOverButton;
        }

        // Обработка клика мыши
        boolean isMousePressed = input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
        if (isMousePressed && !wasMousePressed && mouseOverButton != -1) {
            playSound(clickSound);
            handleMenuSelection(game, container);
        }
        wasMousePressed = isMousePressed;
    }

    private void playSound(Sound sound) {
        if (sound != null) {
            try {
                sound.play();
            } catch (Exception e) {
                System.out.println("Error playing sound: " + e.getMessage());
            }
        }
    }

    private void handleMenuSelection(StateBasedGame game, GameContainer container) {
        switch (selectedOption) {
            case 0: // START GAME
                System.out.println("Starting game...");
                playSound(selectSound);
                game.enterState(Main.GAME);
                break;
            case 1: // SETTINGS
                System.out.println("Opening settings...");
                playSound(selectSound);
                inSettings = true;
                break;
            case 2: // EXIT
                System.out.println("Exiting game...");
                playSound(selectSound);
                container.exit();
                break;
        }
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) {
        // Сбрасываем состояние при входе в меню
        inSettings = false;
        selectedOption = 0;
        lastMouseOverButton = -1;

        // Сбрасываем анимации
        for (int i = 0; i < buttonScale.length; i++) {
            buttonScale[i] = NORMAL_SCALE;
            targetScale[i] = NORMAL_SCALE;
        }
    }
}