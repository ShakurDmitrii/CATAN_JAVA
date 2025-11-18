package org.example.render;

import org.example.objects.Bank;
import org.example.objects.Player;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.Map;

public class BankRender {

    private final int spacing = 80;          // расстояние между ресурсами
    private final int lineHeight = 20;       // расстояние между названием и количеством
    private final int costLineHeight = 18;   // расстояние между строками стоимости
    private final int verticalOffset = 170;  // общий сдвиг вниз
    private final int boxPadding = 6;        // отступ рамки от контента

    // Храним координаты рамок для клика
    private int[] boxX = new int[5];
    private int[] boxY = new int[5];
    private int[] boxWidth = new int[5];
    private int[] boxHeight = new int[5];
    private String[] resources = {"ore", "wheat", "wood", "brick", "sheep"};

    public void render(Graphics g, Bank bank, int startX, int startY) {
        startY += verticalOffset;
        startX += 10;

        g.drawString("Bank", startX, startY - 50);

        for (int i = 0; i < resources.length; i++) {
            String res = resources[i];
            int x = startX + spacing * i;
            int y = startY;
            int height = lineHeight * 2 + drawCostHeight(bank.getCost(res));

            // Сохраняем координаты рамки
            boxX[i] = x - boxPadding - 3;
            boxY[i] = y - boxPadding;
            boxWidth[i] = spacing + boxPadding * 2 - 11;
            boxHeight[i] = height + boxPadding * 2;

            // Рисуем рамку
            drawBox(g, x, y, spacing, height);

            // Цвет текста
            Color color;
            switch (res) {
                case "ore": color = Color.lightGray; break;
                case "wheat": color = Color.yellow; break;
                case "wood": color = Color.green; break;
                case "brick": color = Color.orange; break;
                default: color = Color.white; break;
            }
            g.setColor(color);

            // Рендер названия и количества
            g.drawString(res, x, y);
            String amount = "";
            switch (res) {
                case "ore": amount = bank.getOre(); break;
                case "wheat": amount = bank.getWheat(); break;
                case "wood": amount = bank.getWood(); break;
                case "brick": amount = bank.getBrick(); break;
                case "sheep": amount = bank.getSheep(); break;
            }
            g.drawString(amount, x, y + lineHeight);

            // Стоимость
            drawCost(g, bank.getCost(res), x, y + lineHeight * 2);
        }
    }



    private void drawCost(Graphics g, Map<String, Integer> cost, int x, int startY) {
        if (cost == null) return;
        g.setColor(Color.white);
        int offsetY = 0;
        for (Map.Entry<String, Integer> entry : cost.entrySet()) {
            g.drawString(entry.getKey() + ": " + entry.getValue(), x, startY + offsetY);
            offsetY += costLineHeight;
        }
    }

    private int drawCostHeight(Map<String, Integer> cost) {
        if (cost == null) return 0;
        return cost.size() * costLineHeight;
    }

    private void drawBox(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.gray);
        g.drawRect(
                x - boxPadding - 3,
                y - boxPadding,
                width + boxPadding * 2 - 11,
                height + boxPadding * 2
        );
    }
    public void handleClick(Player player, Bank bank, int mouseX, int mouseY) {
        for (int i = 0; i < resources.length; i++) {
            if (mouseX >= boxX[i] && mouseX <= boxX[i] + boxWidth[i] &&
                    mouseY >= boxY[i] && mouseY <= boxY[i] + boxHeight[i]) {
                boolean success = bank.buy(player, resources[i]);
                if (success) {
                    System.out.println("Куплен ресурс: " + resources[i]);
                } else {
                    System.out.println("Не хватает ресурсов для покупки: " + resources[i]);
                }
            }
        }
    }

}
