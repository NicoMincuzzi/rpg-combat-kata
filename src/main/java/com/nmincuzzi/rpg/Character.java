package com.nmincuzzi.rpg;

import java.util.UUID;

public class Character {
    private final String id;
    private int health;
    private int level;
    private boolean alive;

    private Character(int health, int level, boolean alive) {
        this.id = UUID.randomUUID().toString();
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

    public String getId() {
        return id;
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
        if (isItSelf(enemy)) {
            return;
        }
        if(level + 5 <= enemy.getLevel()){
            damage = damage/2;
        }if(level - enemy.getLevel() >= 5){
            damage += damage/2;
        }

        enemy.decreaseHealth(damage);
    }

    public boolean healTo(Character friend, int power) {
        if (!friend.isAlive() || !isItSelf(friend)) {
            return false;
        }
        friend.increaseHealth(power);
        return true;
    }

    public void increaseLevel(int value) {
        this.level += value;
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

    private boolean isItSelf(Character character) {
        return id.equals(character.getId());
    }

}
