package org.example.map;

import org.example.Player;

import java.awt.*;

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
        // отрисовать шестиугольник с текстурой или цветом
        g.setColor(getColorByType());
        g.fillPolygon(createHex(x, y, 50)); // 50 — радиус
        g.setColor(Color.black);
        g.drawString(""+number, (int) (x-5), (int) (y-5)); // номер гекса
    }


}
