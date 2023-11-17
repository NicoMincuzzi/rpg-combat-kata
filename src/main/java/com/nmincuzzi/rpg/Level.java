package com.nmincuzzi.rpg;

public record Level(int value) {

    public boolean isTargetLevelFiveOrMoreAboveTheAttacker(int targetLevel) {
        return value + 5 <= targetLevel;
    }

    public boolean isTargetLevelFiveOrMoreBelowTheAttacker(int targetLevel) {
        return targetLevel + 5 <= value;
    }
}
