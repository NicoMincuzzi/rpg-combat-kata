package com.nmincuzzi.rpg;

public class Level {
    private int value;

    public Level(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isTargetLevelFiveOrMoreAboveTheAttacker(int targetLevel) {
        return value + 5 <= targetLevel;
    }

    public boolean isTargetLevelFiveOrMoreBelowTheAttacker(int targetLevel) {
        return value - targetLevel >= 5;
    }
}
