package Game;

import java.awt.Point;

public class Kitchen extends Room {

	public Kitchen() {
		super();
		this.card = ui.GUI.loadimage(CARD_LOC+"kitchen.png");
		this.TopLeftX = 0;
		this.TopLeftY = 0;
		this.width = 6;
		this.height = 7;
		this.removalPoints = new Point[]{ new Point(0,6)};
		this.entrance = new Point[]{ new Point(4,6),};
		this.exit = new Point[]{new Point(4,7)};
		initialiseCoordinates();
	}
	
	public String toString(){
		return "Kitchen";
		
	}
	
}
