package org.example.state;

import org.example.Main;
import org.example.Player;
import org.example.map.HexTile;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

public class GameState extends BasicGameState {
    private List<HexTile> tiles = new ArrayList<>();
    private int stateID;

    public GameState(int id) {
        this.stateID = id;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        String[] types = {"wood", "brick", "sheep", "wheat", "ore", "desert"};
        float r = 50;

        float dx = (float) (Math.sqrt(3) * r);
        float dy = (float) (1.5 * r);

        int rows = 3; // Уменьшим для теста
        int cols = 3;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                float x = 100 + col * dx;
                if (row % 2 == 1) {
                    x += dx / 2;
                }
                float y = 100 + row * dy;

                String type = types[(int) (Math.random() * types.length)];

                int[] validNumbers = {2, 3, 4, 5, 6, 8, 9, 10, 11, 12};
                int number = validNumbers[(int) (Math.random() * validNumbers.length)];

                Player player = null;
                HexTile tile = new HexTile(x, y, type, number, player);
                tiles.add(tile);
            }
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        g.setColor(Color.black);
        g.drawString("GAME IS RUNNING", 50, 30);
        g.drawString("Press ESC to return to menu", 50, 50);

        for (HexTile tile : tiles) {
            tile.render(g);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(Main.MAIN_MENU);
        }
    }
}