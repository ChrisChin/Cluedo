package Game;

import java.awt.Point;

public class Lounge extends Room{
	public Lounge() {
		super();
		this.card = ui.GUI.loadimage(CARD_LOC+"lounge.png");
		this.TopLeftX = 0;
		this.TopLeftY = 19;
		this.width = 7;
		this.height = 6;
		this.removalPoints = new Point[]{ new Point(6,24)};
		this.entrance = new Point[]{new Point(6,18)};
		this.exit = new Point[]{ new Point(6,17)};
		initialiseCoordinates();
	}

	public String toString(){
		return "Lounge";		
	}
	
}
