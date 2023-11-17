package com.nmincuzzi.rpg;

import org.junit.jupiter.api.Test;

import static com.nmincuzzi.rpg.Character.*;
import static com.nmincuzzi.rpg.Character.ranged;
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
        Character deadCharacter = new Character(new Health(0), new Level(1), false, 0);

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
        Character enemy = new Character(new Health(1000), new Level(6), true, 0);

        character.attackTo(enemy, new Damage(100));

        assertEquals(enemy.getHealth(), new Health(950));
    }

    @Test
    public void if_the_target_is_5_or_more_Levels_below_the_attacker() {
        Character character = new Character(new Health(1000), new Level(6), true, 0);
        Character enemy = defaultCharacter();

        character.attackTo(enemy, new Damage(100));

        assertEquals(enemy.getHealth(), new Health(850));
    }

    @Test
    public void characters_have_an_attack_max_range() {
        Character character = new Character(new Health(1000), new Level(6), true, 20);

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
}
