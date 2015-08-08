package Game;

import java.awt.Point;

public class Ballroom extends Room{	
	public Ballroom(){
			super();
			this.card = ui.GUI.loadimage(CARD_LOC+"ballroom.png");
			this.TopLeftX = 8;
			this.TopLeftY = 0;
			this.width = 8;
			this.height = 8;
			this.removalPoints = new Point[]{ 
					new Point(8,0), new Point(9,0),new Point(8,1), new Point(9,1),
					new Point(14,0), new Point(15,0),new Point(14,1), new Point(15,1)};
			this.entrance = new Point[]{new Point(8,5),new Point(9,7), new Point(14,7),new Point(15,5)};
			this.exit = new Point[]{ new Point(7,5),new Point(9,8), new Point(14,8),new Point(16,5)};
			initialiseCoordinates();
	}
	
	public String toString(){
		return "Ballroom";
		
	}

}
