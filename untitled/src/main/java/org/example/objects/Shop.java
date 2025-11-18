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
    /** Публичный геттер для стоимости здания */
    public Map<String, Integer> getBuildingCost(String building) {
        return buildingCosts.get(building);
    }
    /** Покупка и списание ресурсов */

    public boolean buyBuilding(Player player, String building) {
        Map<String, Map<String, Integer>> buildingCosts = Map.of(
                "road", Map.of("wood", 1, "brick", 1),
                "village", Map.of("wood", 1, "brick", 1, "sheep", 1, "wheat", 1),
                "city", Map.of("wheat", 2, "ore", 3)
        );

        Map<String, Integer> cost = buildingCosts.get(building);
        if (cost == null) {
            System.out.println("Неизвестная постройка: " + building);
            return false;
        }

        // Проверяем ресурсы
        for (Map.Entry<String, Integer> entry : cost.entrySet()) {
            if (player.getResource(entry.getKey()) < entry.getValue()) {
                System.out.println(player.getName() + " недостаточно ресурсов для " + building + "!");
                return false;
            }
        }

        // Списываем ресурсы
        player.spendResources(cost);

        // Добавляем постройку игроку
        switch (building) {
            case "road":
                player.addRoad();      // просто увеличиваем счетчик или добавляем в список
                break;
            case "village":
                player.addVillage();
                break;
            case "city":
                player.addCity();
                break;
        }

        System.out.println(player.getName() + " купил " + building + "!");
        return true;
    }



}
