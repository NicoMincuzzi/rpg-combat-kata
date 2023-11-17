package com.nmincuzzi.rpg;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.nmincuzzi.rpg.Character.*;
import static org.junit.jupiter.api.Assertions.*;

public class RpgCombatKataTest {

    @Test
    public void default_character_parameters() {
        Character character = defaultCharacter();

        assertEquals(character.getHealth(), new Health(1000));
        assertEquals(character.getLevel(), 1);
        assertTrue(character.isAlive());
    }

    @Test
    public void characters_can_deal_damage_to_characters() {
        Character character = defaultCharacter();
        Character enemy = defaultCharacter();

        character.attackTo(enemy, new Damage(100));

        assertEquals(enemy.getHealth(), new Health(900));
    }

    @Test
    public void when_damage_received_exceeds_current_health() {
        Character character = defaultCharacter();
        Character enemy = defaultCharacter();

        character.attackTo(enemy, new Damage(2000));

        assertEquals(enemy.getHealth(), new Health(0));
        assertFalse(enemy.isAlive());
    }

    @Test
    public void character_can_heal_character_if_he_is_not_dead() {
        Character character = defaultCharacter();
        Character deadCharacter = new Character(new Health(0), new Level(1), false, 0, Set.of());

        boolean result = character.healTo(deadCharacter, 2000);

        assertFalse(result);
    }

    @Test
    public void character_cannot_deal_damage_to_itself() {
        Character character = defaultCharacter();

        character.attackTo(character, new Damage(100));

        assertEquals(character.getHealth(), new Health(1000));
    }

    @Test
    public void character_can_only_heal_itself_if_he_is_alive() {
        Character character = defaultCharacter();
        Character character2 = defaultCharacter();
        character.attackTo(character2, new Damage(200));

        boolean result = character.healTo(character2, 200);

        assertFalse(result);
        assertEquals(new Health(800), character2.getHealth());
    }

    @Test
    public void if_the_target_is_5_or_more_Levels_above_the_attacker() {
        Character character = defaultCharacter();
        Character enemy = new Character(new Health(1000), new Level(6), true, 0, Set.of());

        character.attackTo(enemy, new Damage(100));

        assertEquals(enemy.getHealth(), new Health(950));
    }

    @Test
    public void if_the_target_is_5_or_more_Levels_below_the_attacker() {
        Character character = new Character(new Health(1000), new Level(6), true, 0, Set.of());
        Character enemy = defaultCharacter();

        character.attackTo(enemy, new Damage(100));

        assertEquals(enemy.getHealth(), new Health(850));
    }

    @Test
    public void characters_have_an_attack_max_range() {
        Character character = new Character(new Health(1000), new Level(6), true, 20, Set.of());

        assertEquals(character.getAttackMaxRange(), 20);
    }

    @Test
    public void melee_fighters_have_range_of_2_meters() {
        Character melee = melee();

        assertEquals(melee.getAttackMaxRange(), 2);
    }

    @Test
    public void ranged_fighters_have_range_of_20_meters() {
        Character ranged = ranged();

        assertEquals(ranged.getAttackMaxRange(), 20);
    }

    @Test
    public void characters_must_be_in_range_to_deal_damage_to_a_target() {
        Character character = melee();
        Character enemy = ranged();

        character.attackTo(enemy, new Damage(100));

        assertEquals(enemy.getHealth(), new Health(1000));
    }

    @Test
    public void characters_may_belong_to_one_or_more_factions_newly_created_characters_belong_to_no_faction() {
        Character character = defaultCharacter();

        assertFalse(character.hasFactions());
    }

    @Test
    public void character_may_join_one_or_more_factions() {
        Character character = defaultCharacter();

        character.joinToFactions(Set.of(new Faction("faction_one"), new Faction("faction_two")));

        assertTrue(character.hasFactions());
    }

    @Test
    public void character_may_leave_one_or_more_factions() {
        Character character = defaultCharacter();
        character.joinToFactions(Set.of(new Faction("faction_one"), new Faction("faction_two")));

        character.leaveFactions(Set.of(new Faction("faction_one")));

        assertTrue(character.hasFactions());
    }

    @Test
    public void character_may_leave_one_or_more_factions_when_no_factions_exists() {
        Character character = defaultCharacter();

        character.leaveFactions(Set.of(new Faction("faction_one")));

        assertFalse(character.hasFactions());
    }

    @Test
    public void players_belonging_to_the_same_faction_are_considered_allies() {
        Character character = defaultCharacter();
        character.joinToFactions(Set.of(new Faction("faction_one"), new Faction("faction_two")));
        Character ally = defaultCharacter();
        ally.joinToFactions(Set.of(new Faction("faction_one")));

        boolean result = character.isAlliedWith(ally);

        assertTrue(result);
    }

    @Test
    public void players_belonging_to_the_different_factions_are_considered_no_allies() {
        Character characterOne = defaultCharacter();
        characterOne.joinToFactions(Set.of(new Faction("faction_two")));
        Character characterTwo = defaultCharacter();
        characterTwo.joinToFactions(Set.of(new Faction("faction_one")));

        boolean result = characterOne.isAlliedWith(characterTwo);

        assertFalse(result);
    }

    @Test
    public void allies_cannot_deal_damage_to_one_another() {
        Character character = defaultCharacter();
        character.joinToFactions(Set.of(new Faction("faction_one"), new Faction("faction_two")));
        Character ally = defaultCharacter();
        ally.joinToFactions(Set.of(new Faction("faction_one")));

        character.attackTo(ally, new Damage(100));

        assertEquals(ally.getHealth(), new Health(1000));
    }

    @Test
    public void allies_can_heal_one_another() {
        Character character = defaultCharacter();
        character.joinToFactions(Set.of(new Faction("faction_one"), new Faction("faction_two")));
        Character ally = new Character(new Health(700), new Level(1), true, 0, Set.of(new Faction("faction_one")));

        character.healTo(ally, 100);

        assertEquals(ally.getHealth(), new Health(800));
    }

    @Test
    public void character_can_do_deal_damage_to_non_character() {
        Character character = defaultCharacter();
        NonCharacter tree = new NonCharacter(new Health(2000));

        character.attackTo(tree, new Damage(100));

        assertEquals(new Health(1900), tree.getHealth());
    }

    @Test
    public void when_reduced_to_0_health_non_character_are_destroyed() {
        Character character = defaultCharacter();
        NonCharacter tree = new NonCharacter(new Health(300));

        character.attackTo(tree, new Damage(500));

        assertTrue(tree.isDestroyed());
    }
}
