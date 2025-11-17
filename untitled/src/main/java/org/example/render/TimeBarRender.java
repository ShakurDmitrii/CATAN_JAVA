package org.example.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class TimeBarRender {
    public void render(int width, int height, Graphics g, int turnTimer, int turnTime) {
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
    }
}
