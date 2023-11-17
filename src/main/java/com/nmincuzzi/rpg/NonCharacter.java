package com.nmincuzzi.rpg;

public class NonCharacter {
    private Health health;
    private boolean destroyed;

    public NonCharacter(Health health) {
        this.health = health;
        this.destroyed = false;
    }

    public Health getHealth() {
        return health;
    }

    public void evaluateHealth(Damage damage) {
        this.health = new Health(health.healthPostDamage(damage));

        if (health.isUnderTheAliveThreshold()) {
            destroyed = true;
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
