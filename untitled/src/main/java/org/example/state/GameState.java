package org.example.state;

import org.example.Main;
import org.example.objects.Bank;
import org.example.objects.Player;
import org.example.objects.Cities;
import org.example.map.HexTile;
import org.example.objects.Village;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameState extends BasicGameState {

    private List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;

    private List<Cities> cities = new ArrayList<>();
    private List<Village> villages = new ArrayList<>();
    private List<HexTile> tiles = new ArrayList<>();
    private int stateID;
    private Bank bank;

    private boolean villagePlacedThisTurn = false;
    private Player selectedPlayer = null; // окно информации о игроке

    public GameState(int id) {
        this.stateID = id;
    }

    private Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        bank = new Bank();

        // создаём игроков
        players.add(new Player("Players 1", 1));
        players.add(new Player("Players 2", 2));

        String[] types = {"wood", "brick", "sheep", "wheat", "ore", "desert"};
        float r = 50; // радиус гекса
        float dx = (float) (Math.sqrt(3) * r);
        float dy = 1.5f * r;

        int rows = 5;
        int cols = 5;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                float x = 200 + col * dx;
                if (row % 2 == 1) x += dx / 2;
                float y = 200 + row * dy;

                String type = types[(int) (Math.random() * types.length)];
                int[] validNumbers = {2,3,4,5,6,8,9,10,11,12};
                int number = validNumbers[(int) (Math.random() * validNumbers.length)];

                HexTile tile = new HexTile(x, y, type, number, null);
                tiles.add(tile);
            }
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

        int width = container.getWidth();
        int height = container.getHeight();

        // --- 1. Рисуем карту (слева) ---
        for (HexTile tile : tiles) {
            tile.render(g);
        }

        // --- 2. Рисуем меню справа ---
        int menuWidth = width / 4;
        int menuX = width - menuWidth - 20; // сдвиг левее правого края
        int menuY = height / 6;
        int lineHeight = 40;

        g.setColor(Color.darkGray);
        g.fillRect(menuX, 0, menuWidth, height);

        g.setColor(Color.white);
        g.drawRect(menuX, 0, menuWidth, height);

        g.drawString("Players:", menuX + 10, menuY - 30);

        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            int iconY = menuY + i * lineHeight;

            if (i == currentPlayerIndex) {
                g.setColor(Color.green);
                g.fillRect(menuX + 5, iconY - 5, menuWidth - 10, lineHeight);
                g.setColor(Color.white);
            }

            g.setColor(Color.blue);
            g.fillOval(menuX + 10, iconY, 20, 20);

            g.setColor(Color.white);
            g.drawString(p.getName(), menuX + 40, iconY);
        }
        g.drawString("Bank", menuX + 10, menuY + 190);

        int startX = menuX + 10;     // начальная позиция
        int startY = menuY + 220;    // первая строка
        int spacing = 80;            // одинаковые промежутки между колонками

// ---- Ore ----
        g.setColor(Color.lightGray);
        g.drawString("ore", startX, startY);
        g.drawString("" + bank.getOre(), startX, startY + 20);

// ---- Wheat ----
        g.setColor(Color.yellow);
        g.drawString("wheat", startX + spacing, startY);
        g.drawString("" + bank.getWheat(), startX + spacing, startY + 20);

// ---- Wood ----
        g.setColor(Color.green);
        g.drawString("wood", startX + spacing*2, startY);
        g.drawString("" + bank.getWood(), startX + spacing*2, startY + 20);

// ---- Brick ----
        g.setColor(Color.orange);
        g.drawString("brick", startX + spacing*3, startY);
        g.drawString("" + bank.getBrick(), startX + spacing*3, startY + 20);

// ---- Sheep ----
        g.setColor(Color.white);
        g.drawString("sheep", startX + spacing*4, startY);
        g.drawString("" + bank.getSheep(), startX + spacing*4, startY + 20);




        // --- 3. Окно информации о игроке ---
        if (selectedPlayer != null) {
            int infoWidth = width / 5;
            int infoHeight = height / 2;
            int infoX = width - infoWidth - menuWidth - 40;
            int infoY = 50;

            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(infoX, infoY, infoWidth, infoHeight);

            g.setColor(Color.white);
            g.drawRect(infoX, infoY, infoWidth, infoHeight);

            int closeSize = infoWidth / 10;
            int closeX = infoX + infoWidth - closeSize - 5;
            int closeY = infoY + 5;

            // крестик
            g.setColor(Color.red);
            g.fillRect(closeX, closeY, closeSize, closeSize);
            g.setColor(Color.white);
            g.drawLine(closeX, closeY, closeX + closeSize, closeY + closeSize);
            g.drawLine(closeX + closeSize, closeY, closeX, closeY + closeSize);

            g.setColor(Color.white);
            g.drawString("Player: " + selectedPlayer.getName(), infoX + 10, infoY + 10);
            g.drawString("Village: " + selectedPlayer.getVillagesCount(), infoX + 10, infoY + 40);
            g.drawString("Cities: " + selectedPlayer.getCitiesCount(), infoX + 10, infoY + 70);
            g.drawString("Score: " + selectedPlayer.getScore(), infoX + 10, infoY + 100);
            g.drawString("Resources:", infoX + 10, infoY + 130);

            int lineHeightRes = 30;
            int line = 0;
            for (Map.Entry<String, Integer> entry : selectedPlayer.getResources().entrySet()) {
                String resLine = entry.getKey() + ": " + entry.getValue();
                g.drawString(resLine, infoX + 10, infoY + 160 + line * lineHeightRes);
                line++;
            }


        }
        // ширина и высота прогресс-бара
        float barWidth = (width / 2f) * ((float) turnTimer / turnTime); // половина ширины окна
        float barHeight = height / 20f; // высота увеличена в 2 раза

// позиция
        float barX = width / 30f;
        float barY = height / 15f;

// заполнение
        g.setColor(Color.red);
        g.fillRect(barX, barY, barWidth, barHeight);

// рамка
        g.setColor(Color.white);
        g.drawRect(barX, barY, width / 2f, barHeight);

        // --- 4. Кнопка "End Turn" ---
        int endTurnWidth = width / 6;
        int endTurnHeight = 50;
        int endTurnX = width / 30;
        int endTurnY = height - endTurnHeight - 50;

        g.setColor(Color.orange);
        g.fillRect(endTurnX, endTurnY, endTurnWidth, endTurnHeight);

        g.setColor(Color.black);
        g.drawRect(endTurnX, endTurnY, endTurnWidth, endTurnHeight);

        g.setColor(Color.black);
        g.drawString("End Turn", endTurnX + endTurnWidth / 4, endTurnY + 15);
    }

    private void endTurn() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) currentPlayerIndex = 0;

        villagePlacedThisTurn = false;
        selectedPlayer = null;
        turnTimer = turnTime;

    }

    private void handleClick(float mx, float my, GameContainer container) {
        int width = container.getWidth();
        int height = container.getHeight();

        // --- клик по End Turn ---
        int endTurnWidth = width / 6;
        int endTurnHeight = 50;
        int endTurnX = width / 30;
        int endTurnY = height - endTurnHeight - 50;

        if (mx >= endTurnX && mx <= endTurnX + endTurnWidth &&
                my >= endTurnY && my <= endTurnY + endTurnHeight) {
            endTurn();

            return;
        }

        // --- клик по крестику окна игрока ---
        if (selectedPlayer != null) {
            int infoWidth = width / 5;
            int infoHeight = height / 2;
            int menuWidth = width / 4;
            int infoX = width - infoWidth - menuWidth - 40;
            int infoY = 50;
            int closeSize = infoWidth / 10;
            int closeX = infoX + infoWidth - closeSize - 5;
            int closeY = infoY + 5;

            if (mx >= closeX && mx <= closeX + closeSize &&
                    my >= closeY && my <= closeY + closeSize) {
                selectedPlayer = null;
                return;
            }
        }

        // --- клик по меню игроков ---
        int menuWidth = width / 4;
        int menuX = width - menuWidth - 20;
        int menuY = height / 6;
        int lineHeight = 40;

        for (int i = 0; i < players.size(); i++) {
            int textY = menuY + i * lineHeight;
            int rectX = menuX + 5;
            int rectY = textY;
            int rectWidth = menuWidth - 10;
            int rectHeight = lineHeight;

            if (i == currentPlayerIndex && mx >= rectX && mx <= rectX + rectWidth &&
                    my >= rectY && my <= rectY + rectHeight) {
                selectedPlayer = players.get(i);
                break;
            }
        }
    }
    // добавляем поле класса
    private final int turnTime = 30_000; // 30 секунд в миллисекундах
    int turnTimer = turnTime;

    // в начале нового хода сбрасываем таймер
    private void startNewTurn() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) currentPlayerIndex = 0;
        villagePlacedThisTurn = false;
        selectedPlayer = null;
        turnTimer = turnTime; // сброс таймера
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();

        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(Main.MAIN_MENU);
        }

        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            handleClick(input.getMouseX(), input.getMouseY(), container);
        }
        // уменьшаем таймер хода
        turnTimer -= delta;
        if (turnTimer <= 0) {
            startNewTurn(); // автоматическое завершение хода
        }
        if (villagePlacedThisTurn) {
            villagePlacedThisTurn = false;
        }
    }
}
