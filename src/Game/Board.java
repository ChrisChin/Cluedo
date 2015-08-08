package Game;
import Game.Card;
import Game.Character;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import ui.AccuseDialog;
import ui.GUI;
import ui.HandDialog;
import ui.RefuteDialog;


public class Board implements MouseListener, ActionListener{	
	private List<Character> characters;//ArrayList of Characters
	private List<Weapon> weapons;//ArrayList of Weapons
	private GUI gui;//GUI
	private List<Room> rooms;//Rooms
	public int[][] board;//2D array for board
	public int[] diceRoll; // Dice array with 2 ints to represent each di
	private Character accusedMurderer; 
	private Weapon accusedWeapon;
	private Character currentCharacter;
	private int turn;
	private boolean canAnnounce = false;
	private boolean canRollDice = false;
	private boolean finishedMove = false; // flag to make sure the player moves before ending their turn
	public List <Point> validCoordinates; // points on the board excluding rooms and other character positions
	public Set<Point> validMoves; // points the user can move with given dice roll
	public List<Room> validRooms; //rooms the user can move with given dice roll
	public List<Point> characterPositions; // Positions of the characters
	private List<Point> roomExits; // list of room exits
	private static final int width = 24;
	private static final int height = 25;
	public List<Point> removalPoints;	
	private AccuseDialog accuseDialog;
	private AccuseDialog suggestionDialog;
	private Room accusedRoom;

	public Board(List<Character> characters){				
		board = new int[width][height];
		this.characters = characters;
		initiliseRemovalPoints();
		createNewWeapons();
		createNewRooms();
		initiliseMurder();	// Set up the murder room/character/weapon	
		createCards();		
		accusedMurderer = characters.get(0);
		accusedWeapon = weapons.get(0);		
		diceRoll = new int[]{1,1};		
		gui = new GUI(this);		
		printInfo();
		playTurn();
	}
	
	/**
	 * Initilises the arrayList of Points that the characters cannot stand on.
	 */
	public void initiliseRemovalPoints(){		
		Point[] removal = new Point[]{ 
				new Point(6,0),new Point(6,1),new Point(7,0),new Point(8,0),new Point(15,0),new Point(16,0),
				new Point(17,0),new Point(18,0),new Point(19,0),new Point(20,0),new Point(21,0),new Point(22,0),
				new Point(23,0),new Point(17,1),new Point(23,5),new Point(23,7),new Point(23,13),new Point(23,14),
				new Point(23,18),new Point(23,20),new Point(6,24),new Point(8,24),new Point(15,24),new Point(17,24),
				new Point(0,6),new Point(0,8),new Point(0,16),new Point(0,18),					
			};	
		removalPoints = new ArrayList<Point>(Arrays.asList(removal));
		for(int x = 10; x< 15; x++){ // put in the middle rectangle
			for(int y = 10; y <17; y++){
				removalPoints.add(new Point(x,y));
			}
		}
	}
	
	
	public void initiliseMurder(){
		int i = (int) (Math.random() * characters.size());		
		characters.get(i).setMurderer(true);		
		i = (int) (Math.random() * weapons.size());
		weapons.get(i).setMurderWeapon(true);
		i = (int) (Math.random() * rooms.size());
		rooms.get(i).setMurderRoom(true);		
	}
	
	/**
	 * Debugging method to tell the user the winning combination 
	 */
	public void printInfo(){
		for(Character c : characters){
			String s = c.toString();
			if(c.isMurderer()) s = s + " is Murderer";	
			System.out.println(s);
		}
		for(Weapon w : weapons){
			String s = w.toString();
			if(w.isMurderWeapon()) s = s + " is Murderer weapon";
			System.out.println(s);
		}
		for(Room r : rooms){
			String s = r.toString();
			if(r.isMurderRoom()) s = s + " is Murderer Room";	
			System.out.println(s);
		}
	}
	
	
	public void createCards(){
		List<Card> cards = new ArrayList<Card>();
		for(Character c:characters){
			if(!c.isMurderer())
				cards.add(new Card(c.getName(), c.getCard())); // create new cards for every character name			
		}
		for(Weapon w:weapons){
			if(!w.isMurderWeapon())
				cards.add(new Card(w.getName(), w.getCard())); // create new cards for every weapon name			
		}
		for(Room r:rooms){
			if(!r.isMurderRoom)
				cards.add(new Card(r.toString(), r.getCard())); // create new cards for every room name
		}
		Collections.shuffle(cards); // shuffles the card collection
		List<Player> players = getPlayers();
		int i= 0;
		while(true){ //adds the cards to the players
			for(Player p : players){
				if(i>= cards.size())return;
				p.addCard(cards.get(i++));
			}
		}		
	}
	
	public void createNewRooms(){
		rooms = new ArrayList<Room>();		
		rooms.add(new Kitchen());
		rooms.add(new DiningRoom());
		rooms.add(new Lounge());
		rooms.add(new Hall());
		rooms.add(new Study());
		rooms.add(new Library());
		rooms.add(new BilliardRoom());
		rooms.add(new Conservatory());
		rooms.add(new Ballroom());			
		int i =0;
		for(Weapon w : weapons){
			rooms.get(i++).add(w); // put the weapons in the rooms
		}		
		roomExits = new ArrayList<Point>(); // initilise roomExits
		for(Room r : rooms){
			for(int j = 0; j<r.exit.length; j++){ 
				roomExits.add(r.exit[j]);
			}			
		}		
	}
	
	public void createNewWeapons(){	
		weapons = new ArrayList<Weapon>();
		weapons.add(new Weapon("Candlestick", new Point(5,5), Weapon.TKN_LOC+"candlestick.png", Weapon.CARD_LOC+"candlestick.png"));
		weapons.add(new Weapon("Dagger", new Point(0,12), Weapon.TKN_LOC+"dagger.png", Weapon.CARD_LOC+"dagger.png"));
		weapons.add(new Weapon("LeadPipe", new Point(0,20), Weapon.TKN_LOC+"leadpipe.png", Weapon.CARD_LOC+"leadpipe.png"));
		weapons.add(new Weapon("Revolver", new Point(23,0), Weapon.TKN_LOC+"revolver.png", Weapon.CARD_LOC+"revolver.png"));
		weapons.add(new Weapon("Rope", new Point(23,10), Weapon.TKN_LOC+"rope.png", Weapon.CARD_LOC+"rope.png"));
		weapons.add(new Weapon("Spanner", new Point(23,23), Weapon.TKN_LOC+"spanner.png", Weapon.CARD_LOC+"spanner.png"));
	}	
	
	/**
	 * Main method of the class which plays the game
	 * and executes one turn 
	 */
	public void playTurn(){			
		turn = getNextPlayer(turn);
		canRollDice = true; // let the user roll the dice	
		finishedMove = false; // Set to false to start and set to true after they have moved
	}
	
	/**
	 * Finds the next player and puts it in currentCharacter
	 * @param turn
	 * @return the index turn
	 */
	public int getNextPlayer(int turn){
		boolean hasPlayer = false;
		while(!hasPlayer){//Finds the next valid player and puts the character into currentCharacter
			if(turn == characters.size()) turn = 0; // if turn has reached the end then reset to start - 0
			currentCharacter = characters.get(turn); // current character
			if(currentCharacter.getPlayer() == null) turn++;// if is not player then go to next character
			else hasPlayer = true; // found a valid player for the currentCharacter
			
		}
		return turn;		
	}
	
	/**
	 * Returns a list of the current Players
	 * @return list of players
	 */
	public List<Player> getPlayers(){
		List<Player>playerList = new ArrayList<Player>();
		for(Character c : characters){
			Player p = c.getPlayer();
			if( p != null) playerList.add(p);
		}
		return playerList;
	}

	/**
	 * Recursively fills the validRooms and validMoves
	 * @param startingPoint The point the character is standing on
	 * @param movesLeft The amount of moves the character has left
	 */
	public void findMove(Point startingPoint, int movesLeft){
		if(movesLeft >0 && roomExits.contains(startingPoint)) validRooms.add(findValidRoom(startingPoint));
		if(movesLeft >=0){ // if still have moves left
			validMoves.add(startingPoint); // add this point to the list	
			Point up = new Point(startingPoint.x,startingPoint.y - 1);
			Point down = new Point(startingPoint.x,startingPoint.y + 1);
			Point left = new Point(startingPoint.x -1,startingPoint.y);
			Point right = new Point(startingPoint.x + 1,startingPoint.y);
			movesLeft--;
			if(!characterPositions.contains(up) && validCoordinates.contains(up)) findMove(up,movesLeft);
			if(!characterPositions.contains(down) && validCoordinates.contains(down)) findMove(down,movesLeft);
			if(!characterPositions.contains(left) && validCoordinates.contains(left)) findMove(left,movesLeft);
			if(!characterPositions.contains(right) && validCoordinates.contains(right)) findMove(right,movesLeft);	
		}
	}
	
	/**
	 * Finds the Room which contains the point p
	 * @param p Point p
	 * @return
	 */
	public Room findValidRoom(Point p){
		for(Room r : rooms){
			for(int i =0; i<r.exit.length; i++){
				if(r.exit[i].equals(p)) return r;
			}
		}
		System.out.println("Error");
		return null;
	}
	/**
	 * Fills the validCoordinates arraylist which contains all the possible points that characters can stand on
	 * excluding the rooms
	 */
	public void validCoordinates(){
		validCoordinates = new ArrayList<Point>();
		for(int x = 0; x<width; x++ ){ // add every point to validCoordinates
			for(int y = 0; y<height; y++){
				validCoordinates.add(new Point(x,y));
			}
		}
		for(Room r:rooms){ // Remove all room points
			for(Point p : r.coordinates){
				validCoordinates.remove(p);
			}
		}
		validCoordinates.removeAll(removalPoints); // remove all removal points
	}
	
	/**
	 * Rolls the dice between 1-6	
	 * @return
	 */
	public int[] rollDice(){
		int[] i = new int[2];
		i[0] = 1 + (int)(Math.random() * 6);
		i[1] = 1 + (int)(Math.random() * 6);	
		canRollDice = false; //Only allow the user to roll the dice once
		return i;
	}
	
	public void announcement(){
		Character suggC = suggestionDialog.getAccused();
		Weapon suggW = suggestionDialog.getAccusedWeapon();
		Room suggR = currentCharacter.room;
		
		for(Room r : rooms){ // removes the murderer and accusedweapon from the rooms
			if(r.roomCharacters.contains(suggC)) r.remove(suggC);
			if(r.roomWeapons.contains(suggW)) r.remove(suggW);
		}
		suggR.add(suggW);// place weapon into room
		suggR.add(suggC);// place character into room if it isn't already in a room
		gui.redraw();
		List<Character> otherChars = new ArrayList<Character>(characters); // list containing every character except this
		otherChars.remove(currentCharacter);
		List<Player> playerList = null;
		if(turn==getPlayers().size()-1){
			playerList=getPlayers().subList(0, getPlayers().size()-1);
		}else if(turn==0){
			playerList=getPlayers().subList(1, getPlayers().size());
		}else{
			playerList=getPlayers().subList(turn+1, getPlayers().size());
			playerList.addAll(getPlayers().subList(0, turn));
		}//above is logic to go from player next to currentPlayer
		
		for(Player p : playerList){//for every character other than its self
			RefuteDialog refuteD = new RefuteDialog(this, p, suggC, suggW, suggR);			
			if(refuteD.getRefutedCard()!=null){
				return;
			}
		}
		
		//show dialog no one had either character or weapon card
	}
	
	public void accusation(){
		accusedMurderer = accuseDialog.getAccused();
		accusedWeapon = accuseDialog.getAccusedWeapon();
		accusedRoom = accuseDialog.getAccusedRoom();
		if( accusedMurderer.isMurderer() && accusedWeapon.isMurderWeapon() && accusedRoom.isMurderRoom()){ // accusation is correct
	
			int opt = JOptionPane.showConfirmDialog(gui, "Congratulations " + currentCharacter.getPlayer().getName()
					+ " you have correctly accused the murderer\n\nNew Game?", "Winner!", JOptionPane.YES_NO_OPTION);
			if(opt==JOptionPane.NO_OPTION){
				System.exit(0);
			}else{
				gui.dispose();
				new Game();
			}
		}
		else{ // accusation was wrong so eliminate player
			JOptionPane.showMessageDialog(gui, currentCharacter.getPlayer().getName()
					+ " has incorrectly accused!\nThey are now eliminated.");
			currentCharacter.setPlayer(null); // sets the character player to null
			getNextPlayer(turn);
			gui.redraw();
		}		
	}
	
	public void redraw(){
		gui.redraw();
	}
	
	/**
	 * Gets an ArrayList of all characters in the game
	 * @return ArrayList of Character
	 */
	public List<Character> getCharacters() {
		return characters;
	}

	/**
	 * Gets an ArrayList of all weapons in the game
	 * @return ArrayList of Weapon
	 */
	public List<Weapon> getWeapons() {
		return weapons;
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		
		Point pressed = arg0.getPoint();
		int xPressed = pressed.x;
		int yPressed = pressed.y;
		System.out.println("X Position" + pressed.x);
		System.out.println("Y Position" + pressed.y);
		boolean validMove = false;
		if(validMoves != null){			
			for(Point p : validMoves){
				if(		p.x*GUI.tileSize <xPressed && // left of x tile
						xPressed <p.x*GUI.tileSize + GUI.tileSize &&	// right of x tile
						p.y*GUI.tileSize <yPressed && //top of the y tile
						yPressed <p.y*GUI.tileSize + GUI.tileSize	//bottom of the y tile
						){
					validMove = true;
					currentCharacter.setPos(p); //clicked on a valid position so move character to it
					for(Room r : rooms){ // checks if clicked on a room. then put character in that room
						if(r.contains(p)){
							r.add(currentCharacter);
							break;
						}
						
					}
					System.out.println("Current Room " + currentCharacter.room);
					System.out.println(currentCharacter.toString());
					validMoves = null; // reset validMoves
					validRooms = null; // reset validRooms
					gui.redraw();				
					if(currentCharacter.room !=null){// if in a room	
//						canAccuse = true; // Allow the user to accuse
						canAnnounce = true; // Allow the user to announce										
					}
					finishedMove = true;
					return;	
				}
				
			}
			//Did not click on a valid tile
			if(!validMove){
				JOptionPane.showMessageDialog(gui,
						"Please select a valid tile", "Invalid location", JOptionPane.INFORMATION_MESSAGE);			
			}
		}		
	}

	public Character getCurrentPlayer(){
		return characters.get(turn);
	}
	
	/**
	 * Returns the oppositeRoom if the room has a secret passage
	 * @param oppositeRoom
	 * @return Room
	 */
	public Room oppositeRoom(Room oppositeRoom){		
		for(Room r : rooms){
			if(oppositeRoom instanceof Lounge && r instanceof Conservatory)return r;
			if(oppositeRoom instanceof Conservatory && r instanceof Lounge)return r;
			if(oppositeRoom instanceof Kitchen && r instanceof Study)return r;
			if(oppositeRoom instanceof Study && r instanceof Kitchen)return r;
		}					
		return null;		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand(); 
		if(cmd.equals(GUI.ACCUSE)){	
			//if(canAccuse){	
				System.out.println("ACCUSE called");
				accuseDialog = new AccuseDialog(getCurrentPlayer(),characters,rooms,this,true);
				accusation();
//				canAccuse = false;
			//}
				/*
			else{
				JOptionPane.showMessageDialog(gui,
						"You can only accuse once and if you're in a room",
						"Invalid accusation", JOptionPane.INFORMATION_MESSAGE);	
			}*/		
		}
		else if(cmd.equals(GUI.ANNOUNCE)){			
			if(canAnnounce){	
				System.out.println("ANNOUNCE called");
				suggestionDialog = new AccuseDialog(getCurrentPlayer(),characters,rooms,this,false);
				announcement();
				canAnnounce = false;
			}
			else{
				JOptionPane.showMessageDialog(gui,
						"You can only announce once and if you're in a room",
						"Invalid announce", JOptionPane.INFORMATION_MESSAGE);
			}	
		}
		else if(cmd.equals(GUI.ROLLDICE)){
			if(canRollDice){
				System.out.println("Rolling Dice");
				diceRoll = rollDice();//roll dice
				characterPositions = new ArrayList<Point>();
				for(Character c: characters){
					characterPositions.add(c.getPos());
				}
				validRooms = new ArrayList<Room>();
				validMoves = new HashSet<Point>();
				validCoordinates();
				if(currentCharacter.room != null){ // if the character is in a room
					
					for(Point entrance: currentCharacter.room.entrance){
						findMove(entrance,diceRoll[0] + diceRoll[1]);
					}
					if(oppositeRoom(currentCharacter.room) != null){
						validRooms.add(oppositeRoom(currentCharacter.room));
					}
					currentCharacter.room.remove(currentCharacter); // remove the character out of the room
				}
				else{ // character is not in a room
					findMove(currentCharacter.getPos(),diceRoll[0] + diceRoll[1]); // list of valid moves 
				}
				for(Room r: validRooms){ // fills valid moves with valid rooms
					validMoves.addAll(r.getCoordinates());
				}
				gui.redraw(); //redraws for dice
			}
			else{
				System.out.println("Can only roll the dice once");
			}
		}
		else if(cmd.equals(GUI.VIEW_HAND)){
			new HandDialog(getCurrentPlayer().getPlayer());
		}
		else if(cmd.equals(GUI.DONE) && finishedMove){			
			turn++;
			playTurn();
			gui.redraw();
		}

	}	
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	
}
