package com.nmincuzzi.rpg;

import org.junit.jupiter.api.Test;

import static com.nmincuzzi.rpg.Character.deadCharacter;
import static com.nmincuzzi.rpg.Character.defaultCharacter;
import static org.junit.jupiter.api.Assertions.*;

public class RpgCombatKataTest {

    @Test
    public void default_character_parameters() {
        Character character = defaultCharacter();

        assertEquals(character.getHealth(), 1000);
        assertEquals(character.getLevel(), 1);
        assertTrue(character.isAlive());
    }

    @Test
    public void characters_can_deal_damage_to_characters() {
        Character character = defaultCharacter();
        Character enemy = defaultCharacter();

        character.hitTo(enemy, 100);

        assertEquals(enemy.getHealth(), 900);
    }

    @Test
    public void when_damage_received_exceeds_current_health() {
        Character character = defaultCharacter();
        Character enemy = defaultCharacter();

        character.hitTo(enemy, 2000);

        assertEquals(enemy.getHealth(), 0);
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
    public void character_can_heal_character_if_he_is_alive() {
        Character character = defaultCharacter();
        Character friend = defaultCharacter();

        boolean result = character.healTo(friend, 200);

        assertTrue(result);
        assertEquals(friend.getHealth(), 1000);
    }
}
