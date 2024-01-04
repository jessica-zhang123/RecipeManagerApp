
/**
 * Create the difficult rate bar on the recipe template
 * has easy, medium and hard buttons and changes colour on hover and on click
 */
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.*;

public class DifficultyRate extends JPanel {
	private JLabel diffLab;
	private LevelBtn easy;
	private LevelBtn med;
	private LevelBtn hard;
	public static ArrayList<LevelBtn> btnList;

	public DifficultyRate() {

		btnList = new ArrayList<>();
		// reset static variables that track selected options
		LevelBtn.lvlTotal = 0;
		LevelBtn.selectedBtn = -1;
		setSize(400, 40);
		setBackground(Color.white);
		diffLab = new JLabel("Level: ");
		diffLab.setFont(new Font("Arial", Font.BOLD, 20));
		diffLab.setForeground(Color.decode("#9B9B9B"));
		add(diffLab);

		easy = new LevelBtn("Easy");
		med = new LevelBtn("Medium");
		hard = new LevelBtn("Hard");

		add(easy);
		add(med);
		add(hard);

		btnList.add(easy);
		btnList.add(med);
		btnList.add(hard);
	}

	public int getSelectedDiff() {
		return LevelBtn.selectedBtn;
	}

	/**
	 * method for updating the colour of the buttons so only the selected is
	 */
	// coloured in
	public void repaintSelectedDiff() {
		for (int i = 0; i < 3; i++) {
			if (i != getSelectedDiff()) {
				DifficultyRate.btnList.get(i).setBackground(Color.white);
			} else {
				DifficultyRate.btnList.get(i).setBackground(Color.decode("#97EDA5"));

			}
		}
	}

}
