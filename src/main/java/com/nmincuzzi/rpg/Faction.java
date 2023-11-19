package com.nmincuzzi.rpg;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Faction {
    private final String name;
    private final Set<Character> members;

    public Faction(String name) {
        this.name = name;
        this.members = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public boolean hasMember(Character character) {
        return members.contains(character);
    }

    public void addMember(Character character) {
        members.add(character);
    }

    public void removeMember(Character character) {
        members.remove(character);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faction faction = (Faction) o;
        return Objects.equals(name, faction.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
