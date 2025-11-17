package org.example.render;

import org.example.objects.Player;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.util.List;

public class MenuBarRender {
    public void render(int width, int height, List<Player> players, Graphics g, int currentPlayerIndex) {
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

    }
}