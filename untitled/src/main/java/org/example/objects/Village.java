package org.example.objects;

public class Village {

    private Player player;

    private float vx;
    private float vy;

    public Village(Player player, float vx, float vy) {
        this.player = player;
        this.vx = vx;
        this.vy = vy;
    }

    public Player getOwner() {
        return player;
    }



    public float getVx() {
        return vx;
    }



    public float getVy() {
        return vy;
    }

}
