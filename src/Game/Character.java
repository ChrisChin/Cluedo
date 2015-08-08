package Game;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;





public class Character {


	private String name;
	private Color color;
	private Point pos;
	private boolean isMurderer;
	private Player player;
	private Image card;
	public Room room = null;
	
	public Character(String name,Color color,Point pos, String imageloc){
		this.name = name;
		this.color = color;
		this.setPos(pos);
		this.card = ui.GUI.loadimage(imageloc);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + (isMurderer ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Character other = (Character) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (isMurderer != other.isMurderer)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		return true;
	}
	
	public String getName(){
		return name;
	}
	
	public Color getColor(){
		return color;
	}
	
	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}
	
	boolean isMurderer() {
		return isMurderer;
	}

	void setMurderer(boolean isMurderer) {
		this.isMurderer = isMurderer;
	}
	
	public Image getCard(){
		return card;
	}
	
	
	public void redraw(Graphics g){
		
	}
	
	public String toString(){
		String s = getName();
		//if(getPlayer() != null) s = s + " Player Name: " + getPlayer().getName();
		return s;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	

	
}
