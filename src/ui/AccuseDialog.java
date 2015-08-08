package ui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Game.Board;
import Game.Character;
import Game.Room;
import Game.Weapon;

/**
 * Accusation helper dialog box, this box displays all information
 * which will allow a player to make an accusation. Stores selected
 * info and fires an ActionEvent when the user is finished.
 *
 */
public class AccuseDialog extends JDialog{
	private Character currentCharacter;
	private List<Character> accusable;
	private List<Room> rooms;
	private Board board;
	private JComboBox<Character> characterBox;
	private JComboBox<Weapon> weaponBox;
	private JComboBox<Room> roomBox;
	
	private Character accused;
	private Weapon accusedWeapon;
	private Room accusedRoom; 
	private boolean accusation;
	


	/**
	 * Creates a dialog which requires the user to input accusation details
	 * setting accusation to true will result in the dialog gaining a room
	 * Combobox allowing the user to accuse for any room.
	 * @param currChar The character being accused
	 * @param accusable The list of characters which are to be appear as accusable
	 * @param rooms The list of Rooms where the murder could occur
	 * @param board The game board
	 * @param accusation whether the dialog is a suggestion or accusation box
	 */
	public AccuseDialog(Character currChar, List<Character> accusable, List<Room> rooms, Board board, boolean accusation){
		super();
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		
		this.currentCharacter = currChar;
		this.accusable = accusable;
		this.rooms = rooms;
		this.board = board;
		this.accusation = accusation;
		
		setLayout(new BorderLayout());
		add(selectPanel(), BorderLayout.NORTH);
		add(new JLabel("To murder a player!"), BorderLayout.CENTER);
		
		JButton OK = null;
		if(accusation){
			OK = new JButton("Accuse!");
		}else{
			OK = new JButton("Suggest!");
		}
		OK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		OK.addActionListener(board);
		add(OK,BorderLayout.SOUTH);
		
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	/**
	 * Dynamically creates the panel with possible input information
	 * for an accusation 
	 * @return JPanel with comboboxes
	 */
	private JPanel selectPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(new JLabel("Accuse "));
		characterBox = new JComboBox<Character>(accusable.toArray(new Character[accusable.size()]));
		characterBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				accused = (Character) characterBox.getSelectedItem();//safe casts
			}
		});
		panel.add(characterBox);
		panel.add(new JLabel(" of using a "));
		List<Weapon> wpns = board.getWeapons();
		weaponBox = new JComboBox<Weapon>(wpns.toArray(new Weapon[wpns.size()]));
		weaponBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accusedWeapon = (Weapon) weaponBox.getSelectedItem();
			}
		});
		panel.add(weaponBox);
		
		if(accusation){
			panel.add(new JLabel(" in the "));
			roomBox = new JComboBox<Room>(rooms.toArray(new Room[rooms.size()]));
			roomBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					accusedRoom = (Room) roomBox.getSelectedItem();
				}
			});
			panel.add(roomBox);
		}
		accusedWeapon = (Weapon) weaponBox.getSelectedItem();
		accused = (Character) characterBox.getSelectedItem();//safe casts
		if(accusation){
			accusedRoom = (Room) roomBox.getSelectedItem();
		}

		
		return panel;
	}

	/**
	 * Will return null until user fires ActionEvent
	 * @return Character being accused
	 */
	public Character getAccused() {
		return accused;
	}
	
	/**
	 * Will return null until user fires ActionEvent
	 * @return Weapon being accused
	 */
	public Weapon getAccusedWeapon() {
		return accusedWeapon;
	}
	
	/**
	 * Will return null until user fires ActionEvent
	 * @return Weapon being accused
	 */
	public Room getAccusedRoom() {
		return accusedRoom;
	}

	
}
