package com.nmincuzzi.rpg;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class Factions {
    private final Set<Faction> factionSet;

    public Factions(Set<Faction> factionSet) {
        this.factionSet = factionSet;
    }

    public void joinTo(List<String> factionNames, Character character) {
        Set<Faction> factions = retrieveFactionsByName(factionNames);
        factions.forEach(it -> it.addMember(character));
    }

    public void leave(List<String> factionNames, Character character) {
        Set<Faction> factions = retrieveFactionsByName(factionNames);
        factions.forEach(it -> it.removeMember(character));
    }

    public Set<Faction> retrieveFactionsBy(Character character) {
        return factionSet.stream()
                .filter(it -> it.hasMember(character))
                .collect(toSet());
    }

    private Set<Faction> retrieveFactionsByName(List<String> factionNames) {
        return factionSet.stream()
                .filter(it -> factionNames.contains(it.getName()))
                .collect(toSet());
    }

}
