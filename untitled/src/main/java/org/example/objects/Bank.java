package org.example.objects;

import java.util.HashMap;
import java.util.Map;

public class Bank {

    private Map<String, Integer> resources; // сколько ресурсов есть в банке
    private Map<String, Map<String, Integer>> costs; // стоимость ресурса в виде других ресурсов

    public Bank() {
        resources = new HashMap<>();
        resources.put("wood", 10);
        resources.put("brick", 10);
        resources.put("wheat", 10);
        resources.put("sheep", 10);
        resources.put("ore", 10);

        costs = new HashMap<>();

        // Пример: для покупки 1 wood нужно отдать 1 brick и 1 wheat
        Map<String, Integer> woodCost = new HashMap<>();
        woodCost.put("brick", 1);
        woodCost.put("wheat", 1);
        costs.put("wood", woodCost);

        // Для покупки 1 brick нужно 1 wood и 1 sheep
        Map<String, Integer> brickCost = new HashMap<>();
        brickCost.put("wood", 1);
        brickCost.put("sheep", 1);
        costs.put("brick", brickCost);

        // Для покупки 1 wheat нужно 1 brick и 1 sheep
        Map<String, Integer> wheatCost = new HashMap<>();
        wheatCost.put("brick", 1);
        wheatCost.put("sheep", 1);
        costs.put("wheat", wheatCost);

        // Для покупки 1 ore нужно 2 wood и 2 sheep
        Map<String, Integer> oreCost = new HashMap<>();
        oreCost.put("wood", 2);
        oreCost.put("sheep", 2);
        costs.put("ore", oreCost);

        // Для покупки 1 sheep нужно 2 brick и 2 ore
        Map<String, Integer> sheepCost = new HashMap<>();
        sheepCost.put("brick", 2);
        sheepCost.put("ore", 2);
        costs.put("sheep", sheepCost);
    }

    public String getOre() { return String.valueOf(resources.get("ore")); }
    public String getBrick() { return String.valueOf(resources.get("brick")); }
    public String getWheat() { return String.valueOf(resources.get("wheat")); }
    public String getSheep() { return String.valueOf(resources.get("sheep")); }
    public String getWood() { return String.valueOf(resources.get("wood")); }

    public Map<String, Integer> getResources() { return resources; }

    public Map<String, Integer> getCost(String resource) { return costs.get(resource); }

    public boolean sell(String resource) {
        if (resources.containsKey(resource) && resources.get(resource) > 0) {
            resources.put(resource, resources.get(resource) - 1);
            return true;
        }
        return false;
    }

    public boolean canBuy(Map<String, Integer> playerResources, String resource) {
        Map<String, Integer> cost = getCost(resource);
        if (cost == null) return false;
        for (String res : cost.keySet()) {
            if (playerResources.getOrDefault(res, 0) < cost.get(res)) return false;
        }
        return true;
    }

    public boolean buy(Player player, String resource) {
        Map<String, Integer> cost = getCost(resource);
        if (cost == null) return false;

        // Проверяем, есть ли у игрока нужные ресурсы
        if (!player.spendResources(cost)) {
            return false; // недостаточно ресурсов
        }

        // Даём игроку купленный ресурс
        player.addResource(resource, 1);

        // Уменьшаем ресурс в банке
        sell(resource);

        return true;
    }

}
