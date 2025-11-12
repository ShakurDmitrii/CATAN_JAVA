package org.example.map;

import org.example.Player;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.Color;

import org.newdawn.slick.Graphics; // ✅ правильно


public class HexTile {
    private int id;
    private float x, y;
    private String type;
    private int number;
    private Player player;

    public HexTile(float x, float y, String type, int number, Player player) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.number = number;
        this.player = player;

    }
    public int GetHexPlayer(int id){
        if(id == player.getPlayerId() ){
            return player.getPlayerId();
        }
        else return 0;
    }

    public Color getColorByType() {
        switch (type.toLowerCase()) {
            case "wood":
                return Color.green;
            case "brick":
                return Color.red;
            case "sheep":
                return Color.white;
            case "wheat":
                return Color.yellow;
            case "ore":
                return Color.gray;
            case "desert":
                return Color.orange;
            default:
                return Color.black; // на случай ошибки
        }
    }
    // Создаём шестиугольник как Polygon
    private Polygon createHex(float centerX, float centerY, float radius) {
        Polygon hex = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i - 30); // повернем на -30°
            float px = centerX + radius * (float) Math.cos(angle);
            float py = centerY + radius * (float) Math.sin(angle);
            hex.addPoint((int) px, (int) py);
        }
        return hex;
    }

    public void render(Graphics g) {
        Polygon hex = createHex(x, y, 50);  // создаём шестиугольник
        g.setColor(getColorByType());
        g.fill(hex);                        // ✅ fill(Shape)
        g.setColor(Color.black);
        g.draw(hex);                         // рамка шестиугольника
        g.drawString(""+number, x - 5, y - 5);

    }


}
