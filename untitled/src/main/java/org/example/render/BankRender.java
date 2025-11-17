package org.example.render;


import org.example.objects.Bank;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class BankRender {

    private final int spacing = 80;

    public void render(Graphics g, Bank bank, int menuX, int menuY) {

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
        g.drawString("wood", startX + spacing * 2, startY);
        g.drawString("" + bank.getWood(), startX + spacing * 2, startY + 20);

// ---- Brick ----
        g.setColor(Color.orange);
        g.drawString("brick", startX + spacing * 3, startY);
        g.drawString("" + bank.getBrick(), startX + spacing * 3, startY + 20);

// ---- Sheep ----
        g.setColor(Color.white);
        g.drawString("sheep", startX + spacing * 4, startY);
        g.drawString("" + bank.getSheep(), startX + spacing * 4, startY + 20);
    }

    private void drawResource(Graphics g, String name, int value, Color color,
                              int startX, int startY, int index) {

        g.setColor(color);
        int x = startX + spacing * index;

        g.drawString(name, x, startY);
        g.drawString(String.valueOf(value), x, startY + 20);
    }
}
