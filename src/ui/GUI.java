package ui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Game.Board;

/**
 * Contains and manages all components related to a GUI
 * which a user will interact with. Interaction is based
 * on a MouseListener and an ActionListener.
 *
 */
public class GUI extends JFrame{
	private Canvas canvas;
	private Board board;
	private int numPlayers = 1;
	private JLabel dice1;
	private JLabel dice2;
	private JLabel playerCard;
	public static final int tileSize = 30;
	public static final String ACCUSE = "ACCUSE";
	public static final String ANNOUNCE = "ANNOUNCE";
	public static final String ROLLDICE = "ROLLDICE";
	public static final String DONE = "DONE";
	public static final String VIEW_HAND = "VIEW_HAND"; 
	
	/**
	 * Creates a new GUI object and its contained components.
	 * This constructor requires that the board implements
	 * MouseListener and ActionListener.
	 * @param board The board which users will be interacting with.
	 */
	public GUI(Board board){
		super("Cluedo");
		this.board = board;
		//setUndecorated(true); If we remove the frame we need an event handler for movement.
		//BorderLayout b = new BorderLayout(0, 0);
		setLayout(new BorderLayout());
		add(setupMenu(), BorderLayout.NORTH);
		canvas = new Canvas(board);
		add(canvas, BorderLayout.CENTER);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				exitDialogue();
			}
		});
		JPanel eastpanel = new JPanel(new BorderLayout());
		eastpanel.add(playerPanel(), BorderLayout.EAST);
		eastpanel.add(buttonPanel(), BorderLayout.WEST);
		add(eastpanel, BorderLayout.EAST);
		
		pack();
		
		
		
		
		//Leave until last.
		setVisible(true);
		setResizable(false);
	}
	/**
	 * Generates a panel based on the current player using the board.
	 * This includes images such as the player's card and their dice roll.
	 * @return
	 */
	private JPanel playerPanel(){
		JPanel playerPanel = new JPanel(new BorderLayout());
		playerCard = new JLabel(new ImageIcon(board.getCurrentPlayer().getCard()));//TODO THIS NEEDS TWO IMPLEMENTED METHODS 
		playerPanel.add(playerCard, BorderLayout.NORTH);
		
		JPanel dicePanel = new JPanel(new BorderLayout());
		dice1 = new JLabel(getDice(board.diceRoll[0]));
		dice1.setBorder(new EmptyBorder(10, 10, 10, 10));
		dice2 = new JLabel(getDice(board.diceRoll[1]));
		dice2.setBorder(new EmptyBorder(10, 10, 10, 10));
		dicePanel.add(dice1, BorderLayout.WEST);
		dicePanel.add(dice2, BorderLayout.EAST);
		
		playerPanel.add(dicePanel, BorderLayout.SOUTH);
		return playerPanel;
	}
	
	private ImageIcon getDice(int diceNumber){
		if(diceNumber == 1 )return new ImageIcon(loadimage("assets/dice/1.png"));
		if(diceNumber == 2 )return new ImageIcon(loadimage("assets/dice/2.png"));
		if(diceNumber == 3 )return new ImageIcon(loadimage("assets/dice/3.png"));
		if(diceNumber == 4 )return new ImageIcon(loadimage("assets/dice/4.png"));
		if(diceNumber == 5 )return new ImageIcon(loadimage("assets/dice/5.png"));
		if(diceNumber == 6 )return new ImageIcon(loadimage("assets/dice/6.png"));
		else return null; // should never happen
	}
	
	
	/**
	 * Generates a panel with user interactive buttons.
	 * These buttons all have a ActionCommand string which
	 * is public static final and can be used by the board
	 * to check the command being used. 
	 * @return
	 */
	private JPanel buttonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JButton accBtn = new JButton("Accuse");
		accBtn.setActionCommand(ACCUSE);
		accBtn.addActionListener(board);
		accBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton anounBtn = new JButton("Anounce");//Not doing anything currently
		anounBtn.setActionCommand(ANNOUNCE);
		anounBtn.addActionListener(board);
		anounBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton viewBtn = new JButton("View Hand");
		viewBtn.setActionCommand(VIEW_HAND);
		viewBtn.addActionListener(board);
		viewBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton rollBtn = new JButton("Roll Dice");
		rollBtn.setActionCommand(ROLLDICE);
		rollBtn.addActionListener(board);
		rollBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton doneBtn = new JButton("Done");
		doneBtn.setActionCommand(DONE);
		doneBtn.addActionListener(board);
		doneBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panel.add(accBtn);
		panel.add(anounBtn);
		panel.add(rollBtn);
		panel.add(viewBtn);
		panel.add(doneBtn);
		//panel.setBorder(BorderFactory.createTitledBorder("Options"));
		return panel;
	}

	/**
	 * Generates the menu bar.
	 * @return JMenuBar for the Frame 
	 */
	private JMenuBar setupMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				exitDialogue();
			}});
		fileMenu.add(exit);
		
		return menuBar;
	}

	/**
	 * Method which is called on all attempts to close the program.
	 * It check to make sure that the user really wants to quit,
	 * if the user selects yes the program will terminate with a 
	 * status code of 0 
	 */
	private void exitDialogue(){
		int choice = JOptionPane.showConfirmDialog(null, "Are you sure?",
				"Confirm exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(choice==JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
	
	/**
	 * Updates the GUI and all components within it with the latest
	 * information from the board. 
	 */
	public void redraw(){
		playerCard.setIcon(new ImageIcon(board.getCurrentPlayer().getCard()));
		dice1.setIcon(getDice(board.diceRoll[0]));
		dice2.setIcon(getDice(board.diceRoll[1]));
		
		canvas.repaint();
	}

	public int getNumPlayers(){
		return numPlayers;
	}

	/*
	public static Image loadimage(String loc) {
		try {
			Image img = ImageIO.read(new File(loc));
			return img;
		} catch (Exception e) {
			throw new RuntimeException("Unable to load image: " + loc);
		}
	}*/
	
	
	public static Image loadimage(String filename) {
		// using the URL means the image loads when stored
		// in a jar or expanded into individual files.
		java.net.URL imageURL = GUI.class.getResource("/"+filename);
		try {
			Image img = ImageIO.read(imageURL);
			return img;
		} catch (IOException e) {
			throw new RuntimeException("Unable to load image: " + filename);
		}
	}


}
