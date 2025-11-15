package org.example.objects;

public class Cities {

    private Player owner;

    // точка — вершина гекса
    private float vx;
    private float vy;

    public Cities(Player owner, float vx, float vy) {
        this.owner = owner;
        this.vx = vx;
        this.vy = vy;
    }

    public Player getOwner() {
        return owner;
    }

    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }


}
