package Game;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private String name;
	private List<Card> cards;

	public Player(String name){
		this.name = name;
		cards = new ArrayList<Card>();		
	}
	
	
	public String getName() {
		return name;
	}


	public List<Card> getCards() {
		return cards;
	}


	void setCards(List<Card> cards) {
		this.cards = cards;
	}


	public void addCard(Card card) {
		cards.add(card);		
	}

}
