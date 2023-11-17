package com.nmincuzzi.rpg;

import static java.lang.Math.min;

public record Health(int value) {

    public static final int HEALTH_MAX_THRESHOLD = 1000;

    public int healthPostDamage(Damage damage) {
        if (isLethalDamage(damage)) {
            return 0;
        }

        return value - damage.value();
    }

    public int healthPostHealing(int power) {
        int newHealthValue = value + power;

        return min(newHealthValue, HEALTH_MAX_THRESHOLD);
    }

    public boolean isUnderTheAliveThreshold() {
        return value <= 0;
    }

    private boolean isLethalDamage(Damage damage) {
        return damage.value() >= value;
    }
}
