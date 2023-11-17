package com.nmincuzzi.rpg;

import java.util.HashSet;
import java.util.Set;

import static java.util.UUID.randomUUID;

public class Character {
    private final String id;
    private final int attackMaxRange;
    private final Set<Faction> factions;
    private Health health;
    private final Level level;
    private boolean alive;

    public Character(Health health, Level level, boolean alive, int attackMaxRange, Set<Faction> factions) {
        this.id = randomUUID().toString();
        this.health = health;
        this.level = level;
        this.alive = alive;
        this.attackMaxRange = attackMaxRange;
        this.factions = factions;
    }

    public static Character defaultCharacter() {
        return new Character(new Health(1000), new Level(1), true, 0, new HashSet<>());
    }

    public static Character melee() {
        return new Character(new Health(1000), new Level(1), true, 2, new HashSet<>());
    }

    public static Character ranged() {
        return new Character(new Health(1000), new Level(1), true, 20, new HashSet<>());
    }

    public String getId() {
        return id;
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

    public Health getHealth() {
        return health;
    }

    public void attackTo(Character enemy, Damage damage) {
        if (!isEnableToAttach(enemy)) {
            return;
        }

        int newDamageValue = damage.calculateBasedOnChractersLevels(level, enemy.level);
        enemy.evaluateHealth(new Damage(newDamageValue));
    }

    public void attackTo(NonCharacter enemy, Damage damage) {
        enemy.evaluateHealth(damage);
    }

    public boolean healTo(Character character, int power) {
        if (!isEnableToHeal(character)) {
            return false;
        }
        character.increaseHealth(power);
        return true;
    }

    public boolean hasFactions() {
        return !factions.isEmpty();
    }

    public void joinToFactions(Set<Faction> factions) {
        this.factions.addAll(factions);
    }

    public void leaveFactions(Set<Faction> factionOne) {
        factions.removeAll(factionOne);
    }

    public boolean isAlliedWith(Character character) {
        return factions.stream().anyMatch(character.factions::contains);
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

    private boolean isEnableToAttach(Character enemy) {
        return !isItSelf(enemy) && attackMaxRange >= enemy.attackMaxRange && !isAlliedWith(enemy);
    }

    private boolean isEnableToHeal(Character character) {
        return character.isAlive() && (isItSelf(character) || isAlliedWith(character));
    }
}
