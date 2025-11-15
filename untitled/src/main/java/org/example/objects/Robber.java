package org.example.objects;

import org.example.map.HexTile;

import java.util.Map;

public class Robber {

    private Player owner;
    private HexTile currentTile;

    public Robber(HexTile startTile){
        this.currentTile = startTile;
        this.owner = null;
    }
    public Player getOwner() {
        return owner;
    }

    public HexTile getCurrentTile() {
        return currentTile;
    }

    public void moveTo(HexTile newTile) {
        if (currentTile != null) {
            currentTile.setRobber(false); // убрать с текущего тайла
        }
        currentTile = newTile;
        currentTile.setRobber(true); // пометить новый тайл
    }

    public void stealResource(Player target) {
        if (target == null) return;      // если нет игрока на тайле
        if (owner == null) return;       // если нет владельца хода

        Map<String, Integer> res = target.getResources();
        for (String r : res.keySet()) {
            if (res.get(r) > 0) {
                target.removeResource(r, 1);
                owner.addResource(r, 1);
                break; // украли один ресурс
            }
        }
    }


}
