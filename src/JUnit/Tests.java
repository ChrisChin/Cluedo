package JUnit;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import Game.*;
import Game.Character;
import static org.junit.Assert.*;

public class Tests {
	
	/**
	 * Checks whether when you add a character to a room, The character.room is that room.
	 */
	@Test
	public void testRoomCharacters() {
		List<Character> characters = new ArrayList<Character>();
		Character c = new Character("Miss Scarlet", Color.red, new Point(7,24), "assets/cards/character/MissScarlet.jpg");
		characters.add(c);
		Room r = new DiningRoom();		
		r.add(c);
		assertTrue(characters.get(0).room.equals(r));	
	}
	
	/**
	 * tests that the getPlayers method gets the correct player list
	 */
	@Test
	public void testgetPlayers() {
		List<Character> characters = new ArrayList<Character>();
		characters.add(new Character("Miss Scarlet", Color.red, new Point(7,24), "assets/cards/character/MissScarlet.jpg"));
		characters.add(new Character("Colonel Mustard", Color.yellow, new Point(0,17),"assets/cards/character/ColonelMustard.jpg"));
		characters.add(new Character("Mrs. White", Color.white, new Point(9,0), "assets/cards/character/MrsWhite.jpg"));
		characters.add(new Character("The Reverend Green", Color.green, new Point(14,0),"assets/cards/character/MrGreen.jpg"));
		characters.add(new Character("Mrs. Peacock", Color.blue, new Point(23,6), "assets/cards/character/MrsPeacock.jpg"));
		characters.add(new Character("Professor Plum", Color.pink, new Point(23,19),"assets/cards/character/ProfessorPlum.jpg"));
		Player p = new Player("Chris");
		characters.get(1).setPlayer(p);		
		Board b = new Board(characters);
		assertTrue(b.getPlayers().contains(p));
		assertTrue(b.getPlayers().size() ==1);
		
	}		

}
