package Game;

import java.awt.Point;

public class Conservatory extends Room{	
	public Conservatory(){
			super();
			this.card = ui.GUI.loadimage(CARD_LOC+"conservatory.png");
			this.TopLeftX = 18;
			this.TopLeftY = 1;
			this.width = 6;
			this.height = 5;
			this.removalPoints = new Point[]{ new Point(18,5), new Point(23,5)};
			this.entrance = new Point[]{new Point(19,5)};
			this.exit = new Point[]{ new Point(18,5)};
			initialiseCoordinates();
	}
	
	
	public String toString(){
		return "Conservatory";
		
	}

}
