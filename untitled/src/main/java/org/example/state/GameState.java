package org.example.state;

import org.example.Main;
import org.example.objects.*;
import org.example.map.HexTile;
import org.example.render.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import java.util.ArrayList;
import java.util.List;

public class GameState extends BasicGameState {

    private List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private HexTile selectedTile = null;

    private BankRender bankRender;
    private MenuBarRender menuBarRender;
    private PlayerInfoRender playerInfoRender;
    private TimeBarRender timeBarRender;
    private ShopRender shopRender;

    private List<HexTile> tiles = new ArrayList<>();
    private int stateID;
    private Bank bank;
    private Shop shop;

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

    private void startNewTurn() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) currentPlayerIndex = 0;
        villagePlacedThisTurn = false;
        selectedPlayer = null;
        selectedTile = null;
        turnTimer = turnTime;
    }

    private void handleClick(float mx, float my, GameContainer container) {
        int width = container.getWidth();
        int height = container.getHeight();

        // кнопка End Turn
        int endTurnWidth = width / 6;
        int endTurnHeight = 50;
        int endTurnX = width / 30;
        int endTurnY = height - endTurnHeight - 50;
        if (mx >= endTurnX && mx <= endTurnX + endTurnWidth &&
                my >= endTurnY && my <= endTurnY + endTurnHeight) {
            startNewTurn();
            return;
        }

        // клик по крестику окна игрока
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

        // клик по меню игроков
        int menuWidth = width / 4;
        int menuX = width - menuWidth - 20;
        int menuY = height / 6;
        int lineHeight = 40;
        for (int i = 0; i < players.size(); i++) {
            int rectX = menuX + 5;
            int rectY = menuY + i * lineHeight;
            int rectWidth = menuWidth - 10;
            int rectHeight = lineHeight;

            if (mx >= rectX && mx <= rectX + rectWidth &&
                    my >= rectY && my <= rectY + rectHeight) {
                selectedPlayer = players.get(i);
                break;
            }
        }
    }


    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        menuBarRender = new MenuBarRender();
        bankRender = new BankRender();
        playerInfoRender = new PlayerInfoRender();
        timeBarRender = new TimeBarRender();
        shopRender = new ShopRender();

        bank = new Bank();
        shop = new Shop();

        players.add(new Player("Player 1", 1));
        players.add(new Player("Player 2", 2));

        // создаем тайлы карты
        String[] types = {"wood", "brick", "sheep", "wheat", "ore", "desert"};
        float r = 50;
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

        for (HexTile tile : tiles) {
            tile.render(g);
        }

        menuBarRender.render(width, height, players, g, currentPlayerIndex);
        playerInfoRender.render(selectedPlayer, g, width, height, menuWidth);
        timeBarRender.render(width, height, g, turnTimer, turnTime);
        bankRender.render(g, bank, menuX, menuY);
        shopRender.render(g, shop, menuX, menuY + 150);

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

            // Выбор тайла на карте
            selectedTile = null;
            for (HexTile tile : tiles) {
                if (tile.contains(mouseX, mouseY)) {
                    selectedTile = tile;
                    System.out.println("Выбран тайл: " + tile.getType());
                    break;
                }
            }

            // Клик по банку
            bankRender.handleClick(getCurrentPlayer(), bank, mouseX, mouseY);

            // Клик по магазину, передаем выбранный тайл
            shopRender.handleClick(getCurrentPlayer(), shop, mouseX, mouseY, selectedTile);

            // Остальные клики
            handleClick(mouseX, mouseY, container);
        }

        turnTimer -= delta;
        if (turnTimer <= 0) startNewTurn();
    }
}
