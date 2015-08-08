package Game;

import java.awt.Point;

public class Hall extends Room {
	
	public Hall() {
		super();
		this.card = ui.GUI.loadimage(CARD_LOC+"hall.png");
		this.TopLeftX = 9;
		this.TopLeftY = 18;
		this.width = 6;
		this.height = 7;
		this.removalPoints = new Point[]{ };
		this.entrance = new Point[]{new Point(11,18),new Point(12,18),new Point(14,20)};
		this.exit = new Point[]{ new Point(11,17),new Point(12,17),new Point(15,20)};
		initialiseCoordinates();
	}

	public String toString(){
		return "Hall";		
	}
}
