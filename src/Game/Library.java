package Game;

import java.awt.Point;

public class Library extends Room{	
	public Library(){
			super();
			this.card = ui.GUI.loadimage(CARD_LOC+"library.png");
			this.TopLeftX = 17;
			this.TopLeftY = 14;
			this.width = 7;
			this.height = 5;
			this.removalPoints = new Point[]{ new Point(17,14), new Point(17,18), new Point(23,14), new Point(23,18)};
			this.entrance = new Point[]{new Point(20,14),new Point(17,16)};
			this.exit = new Point[]{ new Point(20,13),new Point(16,16)};
			initialiseCoordinates();
	}

	public String toString(){
		return "Library";
		
	}
	
}
