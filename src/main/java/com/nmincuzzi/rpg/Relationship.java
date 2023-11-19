package com.nmincuzzi.rpg;

import java.util.Set;

public class Relationship {
    private final Factions factions;

    public Relationship(Factions factions) {
        this.factions = factions;
    }

    public boolean areAllied(Character character, Character target) {
        Set<Faction> targetFactions = factions.retrieveFactionsBy(target);

        return factions.retrieveFactionsBy(character).stream().anyMatch(targetFactions::contains);
    }

}
