package org.example.state;

import org.example.Main;
import org.example.objects.Bank;
import org.example.objects.Player;
import org.example.objects.Cities;
import org.example.map.HexTile;
import org.example.objects.Village;
import org.example.render.MenuBarRender;
import org.example.render.PlayerInfoRender;
import org.example.render.TimeBarRender;
import org.example.render.BankRender;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import java.util.ArrayList;
import java.util.List;

public class GameState extends BasicGameState {

    private List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;

    private BankRender bankRenderer;
    private MenuBarRender menuBarRender;
    private PlayerInfoRender playerInfoRender;
    private TimeBarRender timeBarRender;

    private List<Cities> cities = new ArrayList<>();
    private List<Village> villages = new ArrayList<>();
    private List<HexTile> tiles = new ArrayList<>();
    private int stateID;
    private Bank bank;

    private boolean villagePlacedThisTurn = false;
    private Player selectedPlayer = null;

    private final int turnTime = 30_000;
    private int turnTimer = turnTime;

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
        menuBarRender = new MenuBarRender();
        bankRenderer = new BankRender();
        playerInfoRender = new PlayerInfoRender();
        timeBarRender = new TimeBarRender();

        bank = new Bank();

        // создаём игроков
        players.add(new Player("Player 1", 1));
        players.add(new Player("Player 2", 2));

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
        int menuWidth = width / 4;
        int menuX = width - menuWidth - 20;
        int menuY = height / 6;

        // Рисуем карту
        for (HexTile tile : tiles) {
            tile.render(g);
        }

        // Меню игроков
        menuBarRender.render(width, height, players, g, currentPlayerIndex);

        // Информация о выбранном игроке
        playerInfoRender.render(selectedPlayer, g, width, height, menuWidth);

        // Таймер
        timeBarRender.render(width, height, g, turnTimer, turnTime);

        // Банк
        bankRenderer.render(g, bank, menuX, menuY);

        // Кнопка "End Turn"
        int endTurnWidth = width / 6;
        int endTurnHeight = 50;
        int endTurnX = width / 30;
        int endTurnY = height - endTurnHeight - 50;

        g.setColor(Color.orange);
        g.fillRect(endTurnX, endTurnY, endTurnWidth, endTurnHeight);
        g.setColor(Color.black);
        g.drawRect(endTurnX, endTurnY, endTurnWidth, endTurnHeight);
        g.drawString("End Turn", endTurnX + endTurnWidth / 4, endTurnY + 15);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();

        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(Main.MAIN_MENU);
        }

        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            int mouseX = input.getMouseX();
            int mouseY = input.getMouseY();

            // Клик по банку
            bankRenderer.handleClick(getCurrentPlayer(), bank, mouseX, mouseY);

            // Остальные клики
            handleClick(mouseX, mouseY, container);
        }

        // Таймер хода
        turnTimer -= delta;
        if (turnTimer <= 0) startNewTurn();
        if (villagePlacedThisTurn) villagePlacedThisTurn = false;
    }

    private void startNewTurn() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) currentPlayerIndex = 0;
        villagePlacedThisTurn = false;
        selectedPlayer = null;
        turnTimer = turnTime;
    }

    private void endTurn() {
        startNewTurn();
    }

    private void handleClick(float mx, float my, GameContainer container) {
        int width = container.getWidth();
        int height = container.getHeight();

        // Клик по кнопке End Turn
        int endTurnWidth = width / 6;
        int endTurnHeight = 50;
        int endTurnX = width / 30;
        int endTurnY = height - endTurnHeight - 50;

        if (mx >= endTurnX && mx <= endTurnX + endTurnWidth &&
                my >= endTurnY && my <= endTurnY + endTurnHeight) {
            endTurn();
            return;
        }

        // Клик по крестику окна игрока
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

        // Клик по меню игроков
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

            if (i == currentPlayerIndex &&
                    mx >= rectX && mx <= rectX + rectWidth &&
                    my >= rectY && my <= rectY + rectHeight) {
                selectedPlayer = players.get(i);
                break;
            }
        }
    }
}
