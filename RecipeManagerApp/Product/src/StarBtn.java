
/**
 * custom JButton for individual star button on star rate bar
 */
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class StarBtn extends JButton implements MouseListener {

	private ImageIcon btn;
	public static int count = 0;
	private int starID;

	// set default to no star selected
	public static int selectedStar = -1;

	public StarBtn() {

		starID = count;
		count++;
		setBackground(null);
		setBorder(null);
		setBorderPainted(false);
		setFocusPainted(false);

		// image for greyed out (unselected star)
		btn = new ImageIcon("systemImages/emptyStar.png");
		setIcon(btn);

		addMouseListener(this);

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		btn = new ImageIcon("systemImages/star.png");
		selectedStar = starID;
		for (int i = 0; i <= starID; i++) {
			// colour all stars up to the selected star as yellow
			StarRate.starArr.get(i).setIcon(btn);
		}
		btn = new ImageIcon("systemImages/emptyStar.png");
		for (int i = starID + 1; i < 5; i++) {
			// colour all stars following the selected stars grey
			StarRate.starArr.get(i).setIcon(btn);
		}
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
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
