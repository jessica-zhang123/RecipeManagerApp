
/**
 * creates panel which houses star rating bar, difficulty level bar, prep and cook time, and yield
 * includes method for populating Recipe template with existing option panel selections
 */
import java.util.ArrayList;
import javax.swing.*;

public class OtherOptions extends JPanel {

	private StarRate rateBar;
	private DifficultyRate difBar;
	private JPanel level;
	private TimePanel prep;
	private TimePanel cook;
	private TimePanel yield;
	public static ArrayList optArr;

	private Dragger imgField;
	private JPanel total;
	int startYcord;

	public OtherOptions() {

		optArr = new ArrayList<>();
		setLayout(null);
		startYcord = 20;
		rateBar = new StarRate();
		rateBar.setLocation(20, startYcord);
		add(rateBar);

		optArr.add(rateBar);

		imgField = new Dragger();
		imgField.setSize(230, 230);
		imgField.setLocation(530, startYcord + 30);
		add(imgField);

		optArr.add(imgField);

		startYcord += 60;
		difBar = new DifficultyRate();
		difBar.setLocation(-15, startYcord);
		add(difBar);

		optArr.add(difBar);

		prep = new TimePanel("Prep Time: ");
		prep.setSize(400, 60);
		startYcord += 55;
		prep.setLocation(10, startYcord);
		cook = new TimePanel("Cook Time: ");

		startYcord += 60;

		cook.setSize(400, 60);
		cook.setLocation(10, startYcord);

		startYcord += 60;
		yield = new TimePanel("Yield: ", "yieldTF");
		yield.setSize(400, 60);
		yield.setLocation(-100, startYcord);

		add(prep);
		add(cook);
		add(yield);

		optArr.add(prep);
		optArr.add(cook);
		optArr.add(yield);

	}

	/**
	 * method for populating a recipe template with existing recipe option selections
	 * @param starRate the string for the star rating panel
	 * @param imgPath path of image
	 * @param difRate rating of difficulty
	 * @param pTimeHours hours time prep
	 * @param pTimeMins minutes time prep
	 * @param cTimeHours hours time cook
	 * @param cTimeMins minutes time cook
	 * @param yield quantity to be produced from recipe
	 */
	public void populateRecipeListForm(String starRate, String imgPath, String difRate, String pTimeHours,
			String pTimeMins, String cTimeHours, String cTimeMins, String yield) {
		StarBtn.selectedStar = Integer.parseInt(starRate);
		rateBar.repaintStarBar();

		imgField.loadImg(imgPath);

		LevelBtn.selectedBtn = Integer.parseInt(difRate);
		difBar.repaintSelectedDiff();

		prep.setTime(pTimeHours, pTimeMins);
		cook.setTime(cTimeHours, cTimeMins);
		this.yield.setYield(yield);
	}

	public String getImgPath() {
		return imgField.getImagePath();
	}
}
