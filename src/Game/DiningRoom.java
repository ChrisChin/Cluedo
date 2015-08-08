package Game;

import java.awt.Point;

public class DiningRoom extends Room{

	public DiningRoom() {
		super();
		this.card = ui.GUI.loadimage(CARD_LOC+"diningroom.png");
		this.TopLeftX = 0;
		this.TopLeftY = 9;
		this.width = 8;
		this.height = 7;
		this.removalPoints = new Point[]{ new Point(9,5),new Point(9,6),new Point(9,7)};
		this.entrance = new Point[]{ new Point(7,12), new Point(6,15)};
		this.exit = new Point[]{ new Point(8,12),  new Point(6,16)};
		initialiseCoordinates();
	}

	public String toString(){
		return "DiningRoom";
		
	}
	
}
