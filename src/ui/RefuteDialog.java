package ui;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import Game.Board;
import Game.Card;
import Game.Player;
import Game.Room;
import Game.Weapon;
import Game.Character;

public class RefuteDialog extends JDialog{
	boolean refutable = false;
	private Player player;
	private Card refutedWith;
	/**
	 * Dialog which will search the player's hand for the given parameters
	 * if 1 or more are found then the user must make a choice with the
	 * buttons before clicking refute.
	 * @param board The active game board.
	 * @param playerRefuting The player which is attempting to refute.
	 * @param c The Character being accused.
	 * @param w The Weapon assumed to be used.
	 * @param r The Room the murder was assumed to be in.
	 */
	public RefuteDialog(Board board, Player playerRefuting, Character c, Weapon w, Room r){
		super();
		this.player = playerRefuting;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setModalityType(Dialog.DEFAULT_MODALITY_TYPE);//pause execution until closed
		
		setTitle("Refute options for " + playerRefuting.getName());
		setLayout(new BorderLayout());
		add(cardsPanel(c, w, r), BorderLayout.NORTH);
		JButton close = null;
		if(!refutable){
			close = new JButton("Skip");
		}else{
			close = new JButton("Refute!");
		}
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(refutedWith!=null||!refutable){//player must select a card to hide dialog or have no cards to refute with
					dispose();//Does this work?
				}
			}
		});
		add(close, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public Dimension getPreferredSize() {
		Dimension curr = super.getPreferredSize();
		return new Dimension(700, curr.height);
	}
	
	/**
	 * Helper method makes simple scrollable panel full of possible responses.
	 * @param c Character accused
	 * @param w Weapon accused
	 * @param r Room accused
	 * @return scroll panel of buttons
	 */
	private JScrollPane cardsPanel(Character c, Weapon w, Room r){
		JPanel panel = new JPanel(new FlowLayout());//perhaps boxlayout along x
		JScrollPane pane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		for(Card crd : player.getCards()){
			if(crd.toString().equals(c.getName())||
					crd.toString().equals(w.getName())||
					crd.toString().equals(r.toString())){
				refutable = true;
				JButton l = new JButton(new ImageIcon(crd.getCardImg()));
				final Card refW = crd;
				l.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						refutedWith = refW;
					}
				});
				l.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
				l.setBorder(new EmptyBorder(10, 10, 10, 10));
				panel.add(l);
			}
		}
		return pane;
	}
	
	public Card getRefutedCard(){
		return refutedWith;
	}
}
