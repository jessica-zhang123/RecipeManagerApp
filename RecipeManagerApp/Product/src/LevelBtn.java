
/**
 * Individual difficulty button on DifficultyRate bar
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class LevelBtn extends JButton implements ActionListener, MouseListener {
	// numeric ID of 0, 1, 2 for each button
	private int lvlID;
	// lvlTotal used to assign a number of 0, 1, 2 to difficulty rate buttons in
	// order to ID them
	public static int lvlTotal = 0;
	// set none selected at first
	public static int selectedBtn = -1;

	public LevelBtn(String text) {
		
		lvlID = lvlTotal;
		lvlTotal++;
		
		setText(text);
		setFont(new Font("Arial", Font.PLAIN, 15));
		setBackground(Color.WHITE);
		setForeground(Color.decode("#55A630"));
		setFocusable(false);
		setBorderPainted(false);
		addActionListener(this);
		addMouseListener(this);
		
	}

	@Override
	/**
	 * Detects a mouseclick on the level button and changes the colour of the selected button 
	 * resets the colour of non selected buttons
	 */
	public void mouseClicked(MouseEvent e) {
		// ensures colour persists only on selected button
		selectedBtn = lvlID;
		setBackground(Color.decode("#97EDA5"));
		for (int i = 0; i < 3; i++) {
			if (i != lvlID) {
				DifficultyRate.btnList.get(i).setBackground(Color.white);
			}
		}
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// changes colour on hover
		setBackground(Color.decode("#97EDA5"));

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// deselects colour on exit
		if (selectedBtn == lvlID) {

		} else {
			setBackground(Color.WHITE);
		}
		repaint();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
