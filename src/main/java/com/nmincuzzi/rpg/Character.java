package com.nmincuzzi.rpg;

import static java.util.UUID.randomUUID;

public class Character {
    private final String id;
    private final int attackMaxRange;
    private Health health;
    private final Level level;
    private boolean alive;

    public Character(Health health, Level level, boolean alive, int attackMaxRange) {
        this.id = randomUUID().toString();
        this.health = health;
        this.level = level;
        this.alive = alive;
        this.attackMaxRange = attackMaxRange;
    }

    public static Character defaultCharacter() {
        return new Character(new Health(1000), new Level(1), true, 0);
    }

    public static Character melee() {
        return new Character(new Health(1000), new Level(1), true, 2);
    }

    public static Character ranged() {
        return new Character(new Health(1000), new Level(1), true, 20);
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

    public int getAttackMaxRange() {
        return attackMaxRange;
    }

    public void attackTo(Character enemy, Damage damage) {
        if (isItSelf(enemy) || attackMaxRange < enemy.attackMaxRange) {
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
