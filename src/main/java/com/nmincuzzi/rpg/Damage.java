package com.nmincuzzi.rpg;

public record Damage(int value) {

    public int calculateBasedOnChractersLevels(Level characterLevel, Level enemyLevel) {
        int damageValue = value;
        if (characterLevel.isTargetLevelFiveOrMoreAboveTheAttacker(enemyLevel.value())) {
            damageValue = decreaseFiftyPercent(damageValue);
        }
        if (characterLevel.isTargetLevelFiveOrMoreBelowTheAttacker(enemyLevel.value())) {
            damageValue = increaseFiftyPercent(damageValue);
        }
        return damageValue;
    }

    private int decreaseFiftyPercent(int damageValue) {
        return damageValue / 2;
    }

    private int increaseFiftyPercent(int damageValue) {
        return damageValue + damageValue / 2;
    }
}
