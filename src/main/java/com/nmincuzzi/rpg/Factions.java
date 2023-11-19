package com.nmincuzzi.rpg;

import java.util.List;
import java.util.Set;

public class Factions {
    private final Set<Faction> factionSet;

    public Factions(Set<Faction> factionSet) {
        this.factionSet = factionSet;
    }

    public void joinTo(List<String> factionNames, Character character) {
        List<Faction> factions = retrieveFactionsByName(factionNames);
        factions.forEach(it -> it.addMember(character));
    }

    public void leave(List<String> factionNames, Character character) {
        List<Faction> factions = retrieveFactionsByName(factionNames);
        factions.forEach(it -> it.removeMember(character));
    }

    private List<Faction> retrieveFactionsByName(List<String> factionNames) {
        return factionSet.stream()
                .filter(it -> factionNames.contains(it.getName()))
                .toList();
    }

}
