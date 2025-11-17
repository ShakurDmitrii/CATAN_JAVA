package org.example.render;

import org.example.objects.Player;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.Map;

public class PlayerInfoRender {
    public void render(Player selectedPlayer, Graphics g, int width, int height, int menuWidth) {
        if (selectedPlayer != null) {
            int infoWidth = width / 5;
            int infoHeight = height / 2;
            int infoX = width - infoWidth - menuWidth - 40;
            int infoY = 50;

            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(infoX, infoY, infoWidth, infoHeight);

            g.setColor(Color.white);
            g.drawRect(infoX, infoY, infoWidth, infoHeight);

            int closeSize = infoWidth / 10;
            int closeX = infoX + infoWidth - closeSize - 5;
            int closeY = infoY + 5;

            // крестик
            g.setColor(Color.red);
            g.fillRect(closeX, closeY, closeSize, closeSize);
            g.setColor(Color.white);
            g.drawLine(closeX, closeY, closeX + closeSize, closeY + closeSize);
            g.drawLine(closeX + closeSize, closeY, closeX, closeY + closeSize);

            g.setColor(Color.white);
            g.drawString("Player: " + selectedPlayer.getName(), infoX + 10, infoY + 10);
            g.drawString("Village: " + selectedPlayer.getVillagesCount(), infoX + 10, infoY + 40);
            g.drawString("Cities: " + selectedPlayer.getCitiesCount(), infoX + 10, infoY + 70);
            g.drawString("Score: " + selectedPlayer.getScore(), infoX + 10, infoY + 100);
            g.drawString("Resources:", infoX + 10, infoY + 130);

            int lineHeightRes = 30;
            int line = 0;
            for (Map.Entry<String, Integer> entry : selectedPlayer.getResources().entrySet()) {
                String resLine = entry.getKey() + ": " + entry.getValue();
                g.drawString(resLine, infoX + 10, infoY + 160 + line * lineHeightRes);
                line++;
            }


        }
    }
}
