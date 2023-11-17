package com.nmincuzzi.rpg;

import java.util.UUID;

public class Character {
    private final String id;
    private Health health;
    private final Level level;
    private boolean alive;

    public Character(Health health, Level level, boolean alive) {
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

    public void hitTo(Character enemy, Damage damage) {
        if (isItSelf(enemy)) {
            return;
        }

        int newDamageValue = damage.calculateBasedOn(level, enemy.level);

        enemy.evaluateHealth(new Damage(newDamageValue));
    }

    public boolean healTo(Character friend, int power) {
        if (!friend.isAlive() || !isItSelf(friend)) {
            return false;
        }
        friend.increaseHealth(power);
        return true;
    }

    private void evaluateHealth(Damage damage) {
        this.health = new Health(health.healthPostDamage(damage));

        if (health.isUnderTheAliveThreshold()) {
            alive = false;
        }
    }

    private void increaseHealth(int power) {
        this.health = new Health(health.healthPostHealing(power));
    }

    private boolean isItSelf(Character character) {
        return id.equals(character.getId());
    }

}
