package Game;

import java.awt.Point;

	public class Study extends Room{		
		public Study(){
			super();
			this.card = ui.GUI.loadimage(CARD_LOC+"study.png");
			this.TopLeftX = 17;
			this.TopLeftY = 21;
			this.width = 7;
			this.height = 4;
			this.removalPoints = new Point[]{ new Point(17,24)};
			this.entrance = new Point[]{new Point(17,21)};
			this.exit = new Point[]{ new Point(17,20)};
			initialiseCoordinates();
	}
		
	public String toString(){
			return "Study";
			
	}
	
}
