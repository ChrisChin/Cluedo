package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Game.Board;
import Game.Character;
import Game.Room;
import Game.Weapon;

public class Canvas extends JPanel{
	Image boardImage;
	Board board;
	
	protected Canvas(Board board){
		this.boardImage = GUI.loadimage("assets/board.png");
		this.board = board;
		addMouseListener(board);
	}

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	public void paint(Graphics g){
		g.drawImage(boardImage, 0, 0, null);
		int size = GUI.tileSize;//for tidiness
		for(Character c: board.getCharacters()){
			g.setColor(c.getColor());
			g.fillOval(c.getPos().x*size, c.getPos().y*size, size, size);
			//System.out.println("Drawing character " + c.getName() + " at " + c.getPos().x + ", "+ c.getPos().y);
		}
		for(Weapon w : board.getWeapons()){
			g.drawImage(w.getImage(), w.getPos().x*size, w.getPos().y*size, size, size, Color.BLACK, null);
		}
				
		g.setColor(Color.BLUE);
		if(board.validMoves!=null){
			for(Point p : board.validMoves){
				g.drawRect(p.x*size, p.y*size, size, size);
				g.drawRect(p.x*size+1, p.y*size+1, size-2, size-2);//2px line
			}
		}
		
		/*
		//For debugging
		g.setColor(Color.PINK);
		for(int i = 0; i<board.removalPoints.size(); i++){			
			Point p = board.removalPoints.get(i);
			g.drawRect(p.x*size, p.y*size, size, size);
			g.drawRect(p.x*size+1, p.y*size+1, size-2, size-2);//2px line
		}
		*/
		
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(boardImage.getWidth(null), boardImage.getHeight(null));
	}


}
