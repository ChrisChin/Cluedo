package ui;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import Game.Card;
import Game.Player;

/**
 * Dialog which can display a character's hand for convenience.
 *
 */
public class HandDialog extends JDialog{
	private Player player;

	public HandDialog(Player player){
		super();
		setTitle("Hand for " + player.getName());
		this.player = player;
		setLayout(new BorderLayout());
		add(cardsPanel(), BorderLayout.NORTH);
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//dispose();
				setVisible(false);
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
	
	private JScrollPane cardsPanel(){
		JPanel panel = new JPanel(new FlowLayout());//perhaps boxlayout along x
		JScrollPane pane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		for(Card c : player.getCards()){
			JLabel l = new JLabel(new ImageIcon(c.getCardImg()));
			l.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			l.setBorder(new EmptyBorder(10, 10, 10, 10));
			panel.add(l);
		}
		return pane;
	}

}
