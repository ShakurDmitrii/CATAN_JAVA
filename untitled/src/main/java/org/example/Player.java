package org.example;

import org.example.map.Cities;
import org.example.map.HexTile;
import org.example.map.HexTile.*;
import org.example.map.Road;
import org.example.map.Village;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private String name;
    private  int id;
    private int score;
    private int money;
    private Map<String, Integer> resources; // ресурсы: дерево, руда, зерно, овцы, глина
    private List<HexTile> ownedTiles; // какие тайлы контролирует игрок
    private int owendRoads;
    private List<Village> village;
    private List <Cities> cities;
    private int numVillages;
    private int numCities;
    private List<Road> roads;

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
        this.score = 0;
        this.money = 0;
        this.resources = new HashMap<>();
        this.ownedTiles = new ArrayList<>();
        this.owendRoads = 0;
        this.roads = new ArrayList<>();
        this.village = new ArrayList<>();
        this.cities = new ArrayList<>();
        this.numVillages = 0;
        this.numCities = 0;

        resources.put("wood", 0);
        resources.put("brick", 0);
        resources.put("sheep", 0);
        resources.put("wheat", 0);
        resources.put("ore", 0);
    }
    public int getPlayerId(){
        return id;
    }
    public void AddResource(String type, int amount){
        resources.put(type, resources.getOrDefault(type, 0) + amount);
    }

    public void AddOwnedTile(float x, float y, String type, int number, Player player){
        HexTile tile = new HexTile(x, y, type, number, player); // создаём тайл
        ownedTiles.add(tile); // добавляем в список
    }
    public void AddRoad(Player player){
        int wood = resources.getOrDefault("wood", 0);
        int brick = resources.getOrDefault("brick", 0);

        // проверяем, хватает ли ресурсов
        if (wood >= 1 && brick >= 1) {
            resources.put("wood", wood - 1);
            resources.put("brick", brick - 1);

            owendRoads++;
            System.out.println(player.getName() + " построил дорогу!");
        } else {
            System.out.println("Недостаточно ресурсов для постройки дороги!");
        }
    }

    private String getName() {
        return name;
    }

    public void BuildingRoad(Player player, HexTile tile){
        if (tile.GetHexPlayer(id) == player.getPlayerId()){

            Road road = new Road(player);
            roads.add(road);
            score += 2; // город даёт 2 победных очка
            owendRoads--;
        }


    }

    public void AddVillage(Player player){
        int wood = resources.getOrDefault("wood", 0);
        int brick = resources.getOrDefault("brick", 0);
        int sheep = resources.getOrDefault("sheep", 0);
        int wheat = resources.getOrDefault("wheat", 0);
        if (wood >= 1 && brick >= 1) {
            resources.put("wood", wood - 1);
            resources.put("brick", brick - 1);
            resources.put("sheep", sheep - 1);
            resources.put("wheat", wheat - 1);

            owendRoads++;
            System.out.println(player.getName() + " построил дорогу!");
        } else {
            System.out.println("Недостаточно ресурсов для постройки дороги!");
        }
        numVillages++;

    }
    public void AddCities(Player player){
        int wheat = resources.getOrDefault("wheat", 0);
        int ore = resources.getOrDefault("ore", 0);
        if(wheat >= 1 && ore >= 3){
            resources.put("wheat", wheat - 1);
            resources.put("ore", ore - 3);
            numCities++;
            System.out.println(player.getName() + " построил дорогу!");
        } else {
            System.out.println("Недостаточно ресурсов для постройки дороги!");
        }

    }
    public void BuildingCities(Player player, HexTile tile){
        if (tile.GetHexPlayer(id) == player.getPlayerId())
            if (numCities > 0 && money >= 2) {
                money -= 2;
                numCities--;
                Cities city = new Cities(player);
                cities.add(city);

                score += 2; // город даёт 2 победных очка
            }
    }
    public void BuildVillage(Player player, HexTile tile){
        if (tile.GetHexPlayer(id) == player.getPlayerId())
            if (numVillages > 0 && money >= 5) {
                money -= 5;
                numVillages--;
                Village village1 = new Village(player);
                village.add(village1);
                score += 1;
            }
    }
}
