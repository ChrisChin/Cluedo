package Game;

import java.awt.Image;

public class Card {
	
	private String name;
	private Image cardImg;

	public Card(String name, Image cardImage) {
		this.setName(name);
		this.cardImg = cardImage;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	public Image getCardImg() {
		return cardImg;
	}
	
	public String toString(){
		return name;
	}

}
