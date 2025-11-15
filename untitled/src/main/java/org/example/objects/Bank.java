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

        // Пример: для покупки 1 brick нужно 1 wood и 1 sheep
        Map<String, Integer> brickCost = new HashMap<>();
        brickCost.put("wood", 1);
        brickCost.put("sheep", 1);
        costs.put("brick", brickCost);

        Map<String, Integer> wheatCost = new HashMap<>();
        wheatCost.put("break", 1);
        wheatCost.put("sheep", 1);
        costs.put("wheat", wheatCost);

        Map<String, Integer> oreCost = new HashMap<>();
        oreCost.put("wood", 2);
        oreCost.put("sheep", 2);
        costs.put("ore", oreCost);

        Map<String, Integer> sheepCost = new HashMap<>();
        sheepCost.put("break", 2);
        sheepCost.put("ore", 2);
        costs.put("sheep", sheepCost);
    }

    public Map<String, Integer> getResources() {
        return resources;
    }

    public Map<String, Integer> getCost(String resource) {
        return costs.get(resource);
    }

    public boolean sell(String resource) {
        // уменьшить количество ресурса в банке
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
        if (!canBuy(player.getResources(), resource)) return false;

        // списываем ресурсы у игрока
        Map<String, Integer> cost = getCost(resource);
        for (String res : cost.keySet()) {
            player.buyResources(res, cost.get(res));
        }

        // даём игроку купленный ресурс
        player.addResource(resource, 1);

        // уменьшаем ресурс в банке
        sell(resource);

        return true;

    }
}