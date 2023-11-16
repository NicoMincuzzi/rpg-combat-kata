package com.nmincuzzi.rpg;

public class Character {
    private int health;
    private final int level;
    private boolean alive;

    private Character(int health, int level, boolean alive) {
        this.health = health;
        this.level = level;
        this.alive = alive;
    }

    public static Character defaultCharacter() {
        return new Character(1000, 1, true);
    }

    public static Character deadCharacter() {
        return new Character(0, 1, false);
    }

    public int getHealth() {
        return health;
    }

    public int getLevel() {
        return level;
    }

    public boolean isAlive() {
        return alive;
    }

    public void hitTo(Character enemy, int damage) {
        enemy.decreaseHealth(damage);
    }

    public boolean healTo(Character friend, int power) {
        if (!friend.isAlive()) {
            return false;
        }
        friend.increaseHealth(power);
        return true;
    }

    private void decreaseHealth(int damage) {
        if (damage >= health) {
            health = 0;
            alive = false;
            return;
        }

        this.health -= damage;
    }

    private void increaseHealth(int power) {
        this.health += power;

        if (this.health > 1000) {
            this.health = 1000;
        }
    }
}
