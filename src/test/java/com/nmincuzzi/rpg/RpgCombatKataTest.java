package com.nmincuzzi.rpg;

import org.junit.jupiter.api.Test;

import static com.nmincuzzi.rpg.Character.deadCharacter;
import static com.nmincuzzi.rpg.Character.defaultCharacter;
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

        character.hitTo(enemy, 100);

        assertEquals(enemy.getHealth(), new Health(900));
    }

    @Test
    public void when_damage_received_exceeds_current_health() {
        Character character = defaultCharacter();
        Character enemy = defaultCharacter();

        character.hitTo(enemy, 2000);

        assertEquals(enemy.getHealth(), new Health(0));
        assertFalse(enemy.isAlive());
    }

    @Test
    public void character_can_heal_character_if_he_is_not_dead() {
        Character character = defaultCharacter();
        Character friend = deadCharacter();

        boolean result = character.healTo(friend, 2000);

        assertFalse(result);
    }

    @Test
    public void character_cannot_deal_damage_to_itself() {
        Character character = defaultCharacter();

        character.hitTo(character, 100);

        assertEquals(character.getHealth(), new Health(1000));
    }

    @Test
    public void character_can_only_heal_itself_if_he_is_alive() {
        Character character = defaultCharacter();
        Character character2 = defaultCharacter();
        character.hitTo(character2, 200);

        boolean result = character.healTo(character2, 200);

        assertFalse(result);
        assertEquals(new Health(800), character2.getHealth());
    }

    @Test
    public void if_the_target_is_5_or_more_Levels_above_the_attacker() {
        Character character = defaultCharacter();
        Character enemy = defaultCharacter();
        enemy.increaseLevel(5);

        character.hitTo(enemy, 100);

        assertEquals(enemy.getHealth(), new Health(950));
    }

    @Test
    public void if_the_target_is_5_or_more_Levels_below_the_attacker() {
        Character character = defaultCharacter();
        Character enemy = defaultCharacter();
        character.increaseLevel(5);

        character.hitTo(enemy, 100);

        assertEquals(enemy.getHealth(), new Health(850));
    }
}
