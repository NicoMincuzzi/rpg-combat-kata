package com.nmincuzzi.rpg;

import static java.lang.Math.*;

public record Health(int value) {

    public static final int HEALTH_MAX_THRESHOLD = 1000;

    public boolean isLethalDamage(int damage) {
        return damage >= value;
    }

    public int healthPostHealing(int power) {
        int newHealthValue = value + power;

        return min(newHealthValue, HEALTH_MAX_THRESHOLD);
    }
}
