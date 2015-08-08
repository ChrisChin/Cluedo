package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import Game.Character;
import Game.Player;


public class PlayerSetupDialog extends JDialog{
	private int numPlayers = 2;
	private ArrayList<Character> characters;
	private ArrayList<JComboBox<Character>> comboBoxes;
	private ArrayList<JTextField> names;
	private ActionListener submit;
	
	/**
	 * Constructs a JDialog which queries the user for:
	 * <ul><li>Number of players</li>
	 * <li>Player names</li>
	 * <li>Chosen character</li></ul>
	 * Needs to be passed an ActionListener where the actionPerformed
	 * method will be called when the form is submitted.
	 * @param submit ActionListener to be performed on dialog submission
	 */
	public PlayerSetupDialog(final ActionListener submit){
		this.submit = submit;
		setUndecorated(true);
		inputNumPlayers();
		

	}
	
	private void inputNumPlayers(){
		ButtonGroup bg = new ButtonGroup();
		final JDialog playerOptions = new JDialog(this, "Select human players");
		Border border = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		JPanel buttonsPnl = new JPanel();
		buttonsPnl.setBorder(border);
		buttonsPnl.setLayout(new BoxLayout(buttonsPnl, BoxLayout.Y_AXIS));
		for(int i=2; i<=6; i++){
			final int x = i;//for anon class
			JRadioButton rb = new JRadioButton(i + " Players");
			rb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					numPlayers = x;
				}
			});
			if(i==2)rb.setSelected(true);//Default 2 to selected
			buttonsPnl.add(rb);
			bg.add(rb);
		}
		JButton okBtn = new JButton("OK");
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				initialise();
				playerOptions.dispose();
			}
		});
		buttonsPnl.add(okBtn);
		playerOptions.add(buttonsPnl);
		playerOptions.pack();
		playerOptions.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		playerOptions.setLocationRelativeTo(null);
		playerOptions.setVisible(true);
	}
	
    private void initialise(){
		ImageIcon bg = null;
		bg = new ImageIcon(GUI.loadimage("assets/splash.png"));
		JLabel contentPanel = new JLabel(bg) {
            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                return size;
            }
        };
        
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(4, 20, 4, 20);
        //gbc.weightx = 1.0;
        //gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        names = new ArrayList<JTextField>();
        gbc.gridy=0;
        gbc.gridx=0;
        for (int i = 1; i <= numPlayers; i++) {
        	JTextField tf = new JTextField("Player " + i, 15);
        	names.add(tf);
            contentPanel.add(tf, gbc);
        	gbc.gridy++;
        }
       
        comboBoxes = new ArrayList<JComboBox<Character>>();
        
		characters = new ArrayList<Character>();
		characters.add(new Character("Miss Scarlet", Color.red, new Point(7,24), "assets/cards/character/MissScarlet.jpg"));
		characters.add(new Character("Colonel Mustard", Color.yellow, new Point(0,17),"assets/cards/character/ColonelMustard.jpg"));
		characters.add(new Character("Mrs. White", Color.white, new Point(9,0), "assets/cards/character/MrsWhite.jpg"));
		characters.add(new Character("The Reverend Green", Color.green, new Point(14,0),"assets/cards/character/MrGreen.jpg"));
		characters.add(new Character("Mrs. Peacock", Color.blue, new Point(23,6), "assets/cards/character/MrsPeacock.jpg"));
		characters.add(new Character("Professor Plum", Color.pink, new Point(23,19),"assets/cards/character/ProfessorPlum.jpg"));
		
        gbc.gridy=0;
        gbc.gridx++;
        for(int i=0; i<numPlayers; i++){
        	comboBoxes.add(new JComboBox<Character>(characters.toArray(new Character[characters.size()])));
        	JComboBox<Character> box = comboBoxes.get(comboBoxes.size()-1);
        	box.setSelectedIndex(i);
        	contentPanel.add(box, gbc);
        	gbc.gridy++;
        }
        //gbc.gridwidth = GridBagConstraints.REMAINDER;
        //gbc.gridx=0;
        gbc.gridy++;
		JButton OK = new JButton("Start game");
		
		
		OK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkSetInfo()){
					submit.actionPerformed(null);
				}
			}
		});
		
		contentPanel.add(OK, gbc);
		add(contentPanel);
		pack();
		setLocationRelativeTo(null);//Centres on screen
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
    }
	
	private boolean checkSetInfo(){
		ArrayList<Character> chosenChars = new ArrayList<Character>();
		ArrayList<String> chosenNames = new ArrayList<String>();
		for(int i=0; i<numPlayers; i++){
			if(chosenChars.contains(comboBoxes.get(i).getSelectedItem())){
				JOptionPane.showMessageDialog(this, "Cannot have multiple players with the same characters",
						"Conflict", JOptionPane.OK_OPTION);
				return false;
			}if(chosenNames.contains(names.get(i).getText())){
				JOptionPane.showMessageDialog(this, "Cannot have multiple players with the same name",
						"Conflict", JOptionPane.OK_OPTION);
				return false;
			}
			chosenChars.add((Character) comboBoxes.get(i).getSelectedItem());
			chosenNames.add(names.get(i).getText());
		}//made it through. verified data, can set.
		for(int i=0; i<numPlayers; i++){
			Character c = (Character) comboBoxes.get(i).getSelectedItem();//safe cast
			Player p = new Player(names.get(i).getText());
			c.setPlayer(p);
		}
		return true;
	}
	
	public ArrayList<Character> getCharacters(){
		return characters;
	}

}
