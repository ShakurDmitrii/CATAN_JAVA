package org.example.objects;

import org.example.map.HexTile;
import java.util.HashMap;
import java.util.Map;

public class Shop {

    private final Map<String, Map<String, Integer>> buildingCosts;

    public Shop() {
        buildingCosts = new HashMap<>();

        // стоимость дороги
        buildingCosts.put("road", Map.of(
                "wood", 1,
                "brick", 1
        ));

        // стоимость деревни
        buildingCosts.put("village", Map.of(
                "wood", 1,
                "brick", 1,
                "sheep", 1,
                "wheat", 1
        ));

        // стоимость города
        buildingCosts.put("city", Map.of(
                "wheat", 2,
                "ore", 3
        ));
    }

    /** Проверка возможности покупки */
    public boolean canBuy(Player player, String building) {
        Map<String, Integer> cost = buildingCosts.get(building);
        if (cost == null) return false;

        for (String type : cost.keySet()) {
            if (player.getResources().getOrDefault(type, 0) < cost.get(type)) {
                return false;
            }
        }
        return true;
    }

    /** Покупка и списание ресурсов */
    public boolean buy(Player player, String building, HexTile tile) {
        Map<String, Integer> cost = buildingCosts.get(building);
        if (cost == null) return false;

        if (!canBuy(player, building)) {
            System.out.println(player.getName() + " не хватает ресурсов для " + building);
            return false;
        }

        // списываем ресурсы
        for (Map.Entry<String, Integer> entry : cost.entrySet()) {
            player.spendResources(Map.of(entry.getKey(), entry.getValue()));
        }

        // строим соответствующую постройку
        boolean success = false;
        switch (building) {
            case "road":
                success = player.buildRoad(tile);
                break;
            case "village":
                success = player.buildVillage(tile);
                break;
            case "city":
                success = player.buildCity(tile);
                break;
        }

        if (success) {
            System.out.println(player.getName() + " построил " + building + "!");
        }

        return success;
    }
}
