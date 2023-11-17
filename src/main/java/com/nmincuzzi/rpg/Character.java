package com.nmincuzzi.rpg;

import java.util.UUID;

public class Character {
    private final String id;
    private Health health;
    private Level level;
    private boolean alive;

    private Character(Health health, Level level, boolean alive) {
        this.id = UUID.randomUUID().toString();
        this.health = health;
        this.level = level;
        this.alive = alive;
    }

    public static Character defaultCharacter() {
        return new Character(new Health(1000), new Level(1), true);
    }

    public static Character deadCharacter() {
        return new Character(new Health(0), new Level(1), false);
    }

    public String getId() {
        return id;
    }

    public Health getHealth() {
        return health;
    }

    public int getLevel() {
        return level.value();
    }

    public boolean isAlive() {
        return alive;
    }

    public void hitTo(Character enemy, int damage) {
        if (isItSelf(enemy)) {
            return;
        }
        if (level.isTargetLevelFiveOrMoreAboveTheAttacker(enemy.getLevel())) {
            damage = damage / 2;
        }
        if (level.isTargetLevelFiveOrMoreBelowTheAttacker(enemy.getLevel())) {
            damage += damage / 2;
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
        int newLevelValue = this.level.value() + value;
        this.level = new Level(newLevelValue);
    }

    private void decreaseHealth(int damage) {
        if (health.isLethalDamage(damage)) {
            health = new Health(0);
            alive = false;
            return;
        }

        int newHealthValue = health.value() - damage;
        this.health = new Health(newHealthValue);
    }

    private void increaseHealth(int power) {
        this.health = new Health(health.healthPostHealing(power));
    }

    private boolean isItSelf(Character character) {
        return id.equals(character.getId());
    }

}
