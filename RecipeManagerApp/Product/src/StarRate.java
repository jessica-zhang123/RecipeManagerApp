
/**
 * Creates the panel that houses 5 StarBtn 's
 * and method to repaint star bar
 */
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StarRate extends JPanel {

	// starArr stores all the stars
	public static ArrayList<StarBtn> starArr;
	private JLabel tasteLab;

	public StarRate() {

		StarBtn.count = 0;
		StarBtn.selectedStar = -1;
		tasteLab = new JLabel("Taste Rating: ");
		tasteLab.setFont(new Font("Arial", Font.BOLD, 20));
		tasteLab.setForeground(Color.decode("#9B9B9B"));
		add(tasteLab);

		starArr = new ArrayList<>();

		setSize(400, 50);
		setLocation(100, 100);
		setBackground(Color.decode("#F7FFF4"));
		setLayout(new FlowLayout());

		// generate 5 StarBtns and add to starArr
		for (int i = 0; i < 5; i++) {
			StarBtn star = new StarBtn();
			starArr.add(star);
			add(star);
		}
	}

	/**
	 * returns the chosen star level
	 * @return the id number of the clicked star
	 */
	public int getSelectedStar() {
		return StarBtn.selectedStar;
	}
	
	/**
	 * updates the star bar to display the correct orientation of selected and
	 * deselected stars. Called from OtherOptions class
	 */
	public void repaintStarBar() {
		ImageIcon btn = new ImageIcon("systemImages/star.png");
		for (int i = 0; i <= getSelectedStar(); i++) {
			if (i < StarRate.starArr.size())
				StarRate.starArr.get(i).setIcon(btn);
		}
		btn = new ImageIcon("systemImages/emptyStar.png");
		for (int i = getSelectedStar() + 1; i < 5; i++) {
			StarRate.starArr.get(i).setIcon(btn);
		}
	}
}
