package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ui.PlayerSetupDialog;


public class Game{
	private  PlayerSetupDialog playerSetup;
	private  Board board;
	
	public Game(){
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				board = new Board(playerSetup.getCharacters());
				playerSetup.dispose();
			}
		};
		playerSetup = new PlayerSetupDialog(al);
	}

	public static void main(String[] args) {
		new Game();
	}

	
	
}
