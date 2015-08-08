package Game;

import java.awt.*;


public class Weapon {	
	private String name;
	private Point pos;
	private boolean isMurderWeapon;
	private Image image;
	private Image card;
	protected static final String CARD_LOC = "assets/cards/weapon/";
	protected static final String TKN_LOC = "assets/weapon/";
	
	/**
	 * Creates a weapon with the given parameters
	 * @param Name Name of the weapon
	 * @param pos Location on the board
	 * @param tokenPath The image path of the token
	 * @param imageloc The image path of the card
	 */
	public Weapon(String name,Point pos, String tokenPath, String imageloc){
		this.name = name;
		this.setPos(pos);
		this.image = ui.GUI.loadimage(tokenPath);
		this.card = ui.GUI.loadimage(imageloc);
	}
	
	public String getName(){
		return name;
	}
	
	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}
	
	boolean isMurderWeapon() {
		return isMurderWeapon;
	}

	void setMurderWeapon(boolean isMurderWeapon) {
		this.isMurderWeapon = isMurderWeapon;
	}
	
	public Image getImage() {
		return image;
	}

	public void redraw(Graphics g){
		
	}

	public Image getCard() {
		return card;
	}

	public String toString(){
		return this.name;
	}



	

}
