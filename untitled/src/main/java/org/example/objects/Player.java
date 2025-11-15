package org.example.objects;

import org.example.map.HexTile;

import java.util.HashMap;
import java.util.Map;

public class Player {

    private String name;
    private int id;
    private int score;

    private Map<String, Integer> resources;

    private int roadsPlaced;
    private int villagesPlaced;
    private int citiesPlaced;

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
        this.score = 0;

        resources = new HashMap<>();
        resources.put("wood", 1);
        resources.put("brick", 1);
        resources.put("wheat", 1);
        resources.put("sheep", 1);
        resources.put("ore", 1);

        roadsPlaced = 0;
        villagesPlaced = 0;
        citiesPlaced = 0;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    // ---------------------------
    // РЕСУРСЫ
    // ---------------------------
    public void addResource(String type, int amount) {
        resources.put(type, resources.get(type) + amount);
    }
    public void removeResource(String type, int amount) {
        resources.put(type, resources.get(type) - amount);
    }
    public void buyResource(String type, int amount) {

    }

    private boolean hasResources(Map<String,Integer> cost) {
        for (String type : cost.keySet()) {
            if (resources.get(type) < cost.get(type)) return false;
        }
        return true;
    }

    public void spendResources(Map<String, Integer> cost) {
        for (String type : cost.keySet()) {
            resources.put(type, resources.get(type) - cost.get(type));
        }
    }
    public void buyResources(String type, int amount) {
        resources.put(type, resources.getOrDefault(type, 0) + amount);
    }

    // ---------------------------
    // ПОСТРОЙКА ДОРОГИ
    // ---------------------------
    public boolean buildRoad(HexTile tile) {

        Map<String,Integer> cost = Map.of(
                "wood", 1,
                "brick", 1
        );

        if (!hasResources(cost)) {
            System.out.println(name + ": недостаточно ресурсов для дороги!");
            return false;
        }

        spendResources(cost);
        roadsPlaced++;

        System.out.println(name + " построил дорогу!");
        return true;
    }

    // ---------------------------
    // ПОСТРОЙКА ДЕРЕВНИ
    // ---------------------------
    public boolean buildVillage(HexTile tile) {

        Map<String,Integer> cost = Map.of(
                "wood", 1,
                "brick", 1,
                "sheep", 1,
                "wheat", 1
        );

        if (!hasResources(cost)) {
            System.out.println(name + ": недостаточно ресурсов для деревни!");
            return false;
        }

        spendResources(cost);
        villagesPlaced++;
        score += 1;

        System.out.println(name + " построил деревню!");
        return true;
    }

    // ---------------------------
    // ПОСТРОЙКА ГОРОДА
    // ---------------------------
    public boolean buildCity(HexTile tile) {

        Map<String,Integer> cost = Map.of(
                "wheat", 2,
                "ore", 3
        );

        if (!hasResources(cost)) {
            System.out.println(name + ": недостаточно ресурсов для города!");
            return false;
        }

        spendResources(cost);
        citiesPlaced++;
        score += 2;

        System.out.println(name + " построил город!");
        return true;
    }

    public String getVillagesCount() {
        return String.valueOf(villagesPlaced);
    }
    public String getCitiesCount() {
        return String.valueOf(citiesPlaced);
    }
    public int getScore() { return score; }

    public Map<String, Integer> getResources() { return resources; }
}
