package Game;

import java.awt.*;
import java.util.List;
import java.util.*;

import ui.GUI;

public abstract class Room {
	protected int TopLeftX = 0;
	protected int TopLeftY = 0;
	protected int width = 0;
	protected int height = 0;
	protected Point[] removalPoints;
	protected ArrayList<Point> coordinates;
	protected Point[] entrance;
	protected Point[] exit;
	protected List<Character> roomCharacters;
	protected List<Weapon> roomWeapons;
	protected boolean isMurderRoom;
	protected Image card;
	protected static final String CARD_LOC = "assets/cards/room/";
		
	
	public Room(){		
		this.coordinates = new ArrayList<Point>();
		this.entrance = new Point[10];
		this.exit = new Point[10];
		this.removalPoints = new Point[10];
		this.roomCharacters = new ArrayList<Character>();
		this.roomWeapons = new ArrayList<Weapon>();

	}
	
	public void initialiseCoordinates(){
		for(int x=TopLeftX; x<width + TopLeftX; x++){ // for all the x values of the width
			for(int y=TopLeftY; y<height + TopLeftY; y++){// for all the y values of the height
				coordinates.add(new Point(x,y));
			}			
		}
		for(int i =0; i< removalPoints.length; i++){ // remove the squares which aren't apart of the room,
			coordinates.remove(removalPoints[i]);
		}
	}
	
	public ArrayList<Point> getCoordinates(){
		return coordinates;
	}
	
	public Point[] getEntrance(){
		return entrance;
	}
	
	public Character getCharacter(Character target){
		for(Character c : roomCharacters){
			if(c.equals(target)) return target;			
		}
		return null;
	}
	
	public Weapon getWeapon(Weapon target){
		for(Weapon w : roomWeapons){
			if(w.equals(target)) return target;			
		}
		return null;
	}
	
	public void add(Character c){
		roomCharacters.add(c);
		c.room = this;
		organisePositions();
	}
	
	public void add(Weapon w){
		roomWeapons.add(w);
		organisePositions();
	}
	
	public void remove(Character c){
		roomCharacters.remove(c);
		c.room =null;
		organisePositions();
	}
	
	public void remove(Weapon w){
		roomWeapons.remove(w);
		organisePositions();
	}
	
	/**
	 * Organises the position of the characters and weapons whenever there is an addition or removal
	 */
	private void organisePositions(){
		int i = 0;
		for(Character c:roomCharacters){
			c.setPos(coordinates.get(i++)); // set the position to the next available set of coordinates
		}
		for(Weapon w:roomWeapons){
			w.setPos(coordinates.get(i++)); // set the position to the next available set of coordinates
		}		
	}
	
	public boolean isMurderRoom(){
		return isMurderRoom;
	}
	
	public void setMurderRoom(boolean isMurderRoom){
		this.isMurderRoom = isMurderRoom;
	}
	
	public Image getCard() {
		return card;
	}

	public boolean contains(Point p) {
		return coordinates.contains(p);
	}
}
