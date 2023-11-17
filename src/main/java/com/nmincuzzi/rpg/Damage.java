package com.nmincuzzi.rpg;

public record Damage(int value) {

    public int calculateBasedOn(Level characterLevel, Level enemyLevel) {
        int damageValue = value;
        if (characterLevel.isTargetLevelFiveOrMoreAboveTheAttacker(enemyLevel.value())) {
            damageValue = damageValue / 2;
        }
        if (characterLevel.isTargetLevelFiveOrMoreBelowTheAttacker(enemyLevel.value())) {
            damageValue += damageValue / 2;
        }
        return damageValue;
    }
}
