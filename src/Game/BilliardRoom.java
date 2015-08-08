package Game;

import java.awt.Point;

public class BilliardRoom extends Room{	
	public BilliardRoom(){
			super();
			this.card = ui.GUI.loadimage(CARD_LOC+"billiardroom.png");
			this.TopLeftX = 18;
			this.TopLeftY = 8;
			this.width = 6;
			this.height = 5;
			this.removalPoints = new Point[]{ };
			this.entrance = new Point[]{new Point(18,9),new Point(22,12)};
			this.exit = new Point[]{ new Point(17,9),new Point(22,13)};
			initialiseCoordinates();
	}

	
	public String toString(){
		return "BilliardRoom";
		
	}
}
