package org.example.render;

import org.example.map.HexTile;
import org.example.objects.Player;
import org.example.objects.Shop;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.Map;

public class ShopRender {

    private final int spacing = 100;
    private final int lineHeight = 20;
    private final int boxPadding = 6;
    private final int verticalOffset = 200;

    private int[] boxX;
    private int[] boxY;
    private int[] boxWidth;
    private int[] boxHeight;
    private final String[] buildings = {"road", "village", "city"};

    public ShopRender() {
        int n = buildings.length;
        boxX = new int[n];
        boxY = new int[n];
        boxWidth = new int[n];
        boxHeight = new int[n];
    }

    /** Рендер магазина */
    public void render(Graphics g, Shop shop, int startX, int startY) {
        startY += verticalOffset;

        for (int i = 0; i < buildings.length; i++) {
            String building = buildings[i];
            int x = startX + spacing * i;
            int y = startY;

            Map<String, Integer> cost = shop.getBuildingCost(building);
            if (cost == null) continue;

            int height = lineHeight * (cost.size() + 1) + boxPadding * 2;

            // сохраняем координаты рамки для клика
            boxX[i] = x - boxPadding;
            boxY[i] = y - boxPadding;
            boxWidth[i] = spacing;
            boxHeight[i] = height;

            // рамка
            g.setColor(Color.gray);
            g.drawRect(boxX[i], boxY[i], boxWidth[i], boxHeight[i]);

            // название постройки
            g.setColor(Color.white);
            g.drawString(building, x, y);

            // стоимость ресурсов
            int offsetY = lineHeight;
            for (Map.Entry<String, Integer> entry : cost.entrySet()) {
                g.drawString(entry.getKey() + ": " + entry.getValue(), x, y + offsetY);
                offsetY += lineHeight;
            }
        }
    }

    /** Обработка клика по магазину */
    public void handleClick(Player player, Shop shop, int mouseX, int mouseY, HexTile tile) {

        if (boxX == null || boxY == null) return; // рендер ещё не был вызван

        for (int i = 0; i < buildings.length; i++) {
            if (mouseX >= boxX[i] && mouseX <= boxX[i] + boxWidth[i] &&
                    mouseY >= boxY[i] && mouseY <= boxY[i] + boxHeight[i]) {

                boolean success = shop.buyBuilding(player, buildings[i]);
                if (success) {
                    System.out.println("Постройка куплена: " + buildings[i]);
                } else {
                    System.out.println("Недостаточно ресурсов для: " + buildings[i]);
                }
            }
        }
    }
}
