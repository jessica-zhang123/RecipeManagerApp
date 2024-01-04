
/**
 * Template for the extendable lists built jframes with a green button present on Meal section and Recipe form
 * Handles spacing, storage of textfield values
 * Responsible for sending text info to the db 
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.*;

class ListForm extends JPanel implements ActionListener {

	private ArrayList<JTextField> elmntList;
	private JButton elmntAdd;
	private JLabel label;
	private int spacing;
	public String header;
	private static int totalSpacingRecip;
	private static int totalSpacingMS;
	private static int scrollCountRecip;
	private static int scrollCountMS;

	private int formID;

	// counting listforms created
	public static int totalRecip = 0;
	public static int totalMS = 0;

	// keeping track of the type (either Recipe or Meal Section)
	private static int track = 0;

	public ListForm(String header, int n) {

		totalSpacingRecip = 0;
		totalSpacingMS = 0;
		scrollCountRecip = 0;
		scrollCountMS = 0;

		// when n = 1 indicates Recipe
		// when n = 2 indicates Meal Section
		track = n;
		if (n == 1) {
			formID = totalRecip;
			totalRecip++;
		}

		else if (n == 2) {
			formID = totalMS;
			totalMS++;
		}

		this.header = header;
		setLayout(null);
		setBackground(Color.WHITE);
		spacing = 0;
		elmntList = new ArrayList<>();
		label = new JLabel(header);
		label.setFont(new Font("Arial", Font.BOLD, 25));
		label.setSize(300, 50);
		label.setLocation(10, 0);
		label.setForeground(Color.decode("#9B9B9B"));
		add(label);

		elmntAdd = new JButton();
		ImageIcon btn = new ImageIcon("systemImages/btn.png");
		elmntAdd.setIcon(btn);
		elmntAdd.setSize(70, 80);
		elmntAdd.setBackground(null);
		elmntAdd.setBorder(null);
		elmntAdd.addActionListener(this);
		elmntAdd.setLocation(60, 60);
		elmntAdd.setBorderPainted(false);
		elmntAdd.setFocusPainted(false);
		add(elmntAdd);

	}

	public int getSpacing() {
		return spacing;
	}

	public void actionPerformed(ActionEvent e) {

		// for ListForms on a Recipe template
		if (e.getSource() == elmntAdd && track == 1) {
			int tfLength = 770;
			int tfWidth = 900;

			// Make ingrediant and substitution text fields smaller
			if (formID == 0 || formID == 1) {
				tfLength = 300;
				tfWidth = 400;
			}
			// subForm spacing not taken into consideration as it is never the case that
			// substitutions outnumber actual ingrediants

			// size and position the text field
			TxtField field = new TxtField(tfLength);
			field.getTF().setLocation(50, 50 + spacing);
			elmntList.add(field.getTF());
			add(field.getTF());

			spacing += 60;

			// increase height of the panel with each addition of a textfied element
			setSize(tfWidth, 150 + spacing);

			// increase spacing of the panel
			totalSpacingRecip += 60;

			// move the create button down
			int yBtn = RecipeTemplate.yCreate + totalSpacingRecip;

			// increase scroll size of JScrollPane
			scrollCountRecip = 1000 + totalSpacingRecip;

			// change dimension of recipe template to accomodate for increasing number of
			// textfields
			RecipeTemplate.container.setPreferredSize(new Dimension(0, scrollCountRecip));

			// move create button down
			RecipeTemplate.create.setLocation(405, yBtn);

			// move green add button lower
			elmntAdd.setLocation(60, 80 + spacing);

			// if the first list form is extended, move the 2 below it down
			if (formID == 0) {
				int insY = RecipeTemplate.insY + RecipeTemplate.ingForm.getSpacing();
				RecipeTemplate.allForms.get(2).setLocation(20, insY);

				int noteY = RecipeTemplate.noteY + RecipeTemplate.instructForm.getSpacing() + RecipeTemplate.ingForm.getSpacing();
				RecipeTemplate.allForms.get(3).setLocation(20, noteY);
			}

			// if the second list form is extended, only move the last down
			else if (formID == 2) {
				int noteY = RecipeTemplate.noteY + RecipeTemplate.instructForm.getSpacing() + RecipeTemplate.ingForm.getSpacing();
				RecipeTemplate.allForms.get(3).setLocation(20, noteY);
			}

		}

		// For listForms on Meal Section template
		else if (e.getSource() == elmntAdd && track == 2) {
			int tfLength = 770;
			int tfWidth = 900;

			TxtField field = new TxtField(tfLength);
			field.getTF().setLocation(50, 50 + spacing);
			elmntList.add(field.getTF());
			add(field.getTF());

			spacing += 60;

			// with the addition of another textfield, increase vertical spacing
			setSize(tfWidth, 150 + spacing);

			// increase overall spacing
			totalSpacingMS += 60;

			// move create button down
			int yBtn = MSTemplate.yCreate + totalSpacingMS;
			scrollCountMS = 1150 + totalSpacingMS;
			MSTemplate.container.setPreferredSize(new Dimension(0, scrollCountMS));
			MSTemplate.create.setLocation(405, yBtn);
			elmntAdd.setLocation(60, 80 + spacing);

			// if first list form is extended, move both of the following sections down
			if (formID == 0) {
				int primFY = MSTemplate.primFY + MSTemplate.descForm.getSpacing();
				MSTemplate.allForms.get(1).setLocation(20, primFY);

				int culnGY = MSTemplate.culnGY + MSTemplate.primFForm.getSpacing()
						+ MSTemplate.descForm.getSpacing();
				MSTemplate.allForms.get(2).setLocation(20, culnGY);
			}

			// if second list form is extended, only move the one directly under it down
			else if (formID == 1) {
				int culnGY = MSTemplate.culnGY + MSTemplate.primFForm.getSpacing()
						+ MSTemplate.descForm.getSpacing();
				MSTemplate.allForms.get(2).setLocation(20, culnGY);
			}
		}
	}
	
	/**
	 * method for populating a recipe list so that previous recipes are loaded from
	 * the db and displayed
	 * @param addStr string that will be put into the listform text field 
	 */
	public void populateRecipeListForm(String addStr) {
		int tfLength = 770;
		int tfWidth = 900;
		if (formID == 0 || formID == 1) {
			tfLength = 300;
			tfWidth = 400;
		}

		TxtField field = new TxtField(tfLength);

		// adding the provided text read from db
		field.getTF().setText(addStr);
		field.getTF().setLocation(50, 50 + spacing);
		elmntList.add(field.getTF());
		add(field.getTF());

		spacing += 60;

		setSize(tfWidth, 150 + spacing);

		totalSpacingRecip += 60;
		int yBtn = RecipeTemplate.yCreate + totalSpacingRecip;
		scrollCountRecip = 1000 + totalSpacingRecip;
		RecipeTemplate.container.setPreferredSize(new Dimension(0, scrollCountRecip));
		RecipeTemplate.create.setLocation(405, yBtn);
		elmntAdd.setLocation(60, 80 + spacing);

		if (formID == 0) {
			int insY = RecipeTemplate.insY + RecipeTemplate.ingForm.getSpacing();
			RecipeTemplate.allForms.get(2).setLocation(20, insY);

			int noteY = RecipeTemplate.noteY + RecipeTemplate.instructForm.getSpacing() + RecipeTemplate.ingForm.getSpacing();
			RecipeTemplate.allForms.get(3).setLocation(20, noteY);
		} else if (formID == 2) {
			int noteY = RecipeTemplate.noteY + RecipeTemplate.instructForm.getSpacing() + RecipeTemplate.ingForm.getSpacing();
			RecipeTemplate.allForms.get(3).setLocation(20, noteY);
		}

	}

	/**
	 * method for populating meal section list forms
	 * @param addStr is string to be added to the listform
	 */
	public void populateMSListForm(String addStr) {
		int tfLength = 770;
		int tfWidth = 900;

		TxtField field = new TxtField(tfLength);

		// add inputted text
		field.getTF().setText(addStr);
		field.getTF().setLocation(50, 50 + spacing);
		elmntList.add(field.getTF());
		add(field.getTF());

		spacing += 60;

		setSize(tfWidth, 150 + spacing);

		totalSpacingMS += 60;
		int yBtn = MSTemplate.yCreate + totalSpacingMS;
		scrollCountMS = 1150 + totalSpacingMS;
		MSTemplate.container.setPreferredSize(new Dimension(0, scrollCountMS));
		MSTemplate.create.setLocation(405, yBtn);
		elmntAdd.setLocation(60, 80 + spacing);

		if (formID == 0) {
			int primFY = MSTemplate.primFY + MSTemplate.descForm.getSpacing();
			MSTemplate.allForms.get(1).setLocation(20, primFY);

			int culnGY = MSTemplate.culnGY + MSTemplate.primFForm.getSpacing()
					+ MSTemplate.descForm.getSpacing();
			MSTemplate.allForms.get(2).setLocation(20, culnGY);
		} else if (formID == 1) {
			int culnGY = MSTemplate.culnGY + MSTemplate.primFForm.getSpacing()
					+ MSTemplate.descForm.getSpacing();
			MSTemplate.allForms.get(2).setLocation(20, culnGY);
		}

	}
	
	/**
	 * method for formatting data and adding to the db
	 * I want data to enter the db as such:
	 * [MealSectionName]: ing1,ing2,ing3
	 */
	public void addInfoToDB() {
		String s = header + ":";
		if (elmntList.size() > 0) {
			for (JTextField t : elmntList) {
				if (t.getText() != "") {
					s += t.getText() + ",";
				}

			}
		} else {
			s += "empty";
		}
		elmntList.clear();

		try {
			String url = "jdbc:sqlite:db.db";
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Connection con = DriverManager.getConnection(url);
			Statement stmt = con.createStatement();
			stmt.execute(formatQuery(s));
			con.close();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Recipe already defined");
			e1.printStackTrace();
		}
	}
	
	/**
	 * follow same string format for meal section items
	 * but instead of adding directly to db, return the value
	 */
	public String formatMSList() {
		String s = header + ":";
		if (elmntList.size() > 0) {
			for (JTextField t : elmntList) {
				if (t.getText() != "") {
					s += t.getText() + ",";
				}

			}
		}

		else {
			s += "empty";
		}
		elmntList.clear();
		return s;
	}
	
	/**
	 * method for adding data from the block of star rating, image, difficulty
	 * rating, times, and yield
	 */
	public static void addOptsInfo() {
		String starRate = "Star Rating:" + ((StarRate) OtherOptions.optArr.get(0)).getSelectedStar() + ",";

		String imgPath = "imgPath:" + ((Dragger) OtherOptions.optArr.get(1)).getImagePath() + ",";

		// replace \\ with /
		imgPath = imgPath.replaceAll((char) 92 + "" + (char) 92, (char) 47 + "");

		String difRate = "dif Rating:" + ((DifficultyRate) OtherOptions.optArr.get(2)).getSelectedDiff() + ",";

		String prepTime = "prep Time:" + ((TimePanel) OtherOptions.optArr.get(3)).getHours() + ","
				+ ((TimePanel) OtherOptions.optArr.get(3)).getMins() + ",";

		String cookTime = "cook Time:" + ((TimePanel) OtherOptions.optArr.get(4)).getHours() + ","
				+ ((TimePanel) OtherOptions.optArr.get(4)).getMins() + ",";

		// make so that yield and all other textfields cannot add commas (unnecessary
		// and messes up retrieval from db)

		String yield = "yield:" + ((TimePanel) OtherOptions.optArr.get(5)).getYield() + ",";

		// add data to db
		try {
			String url = "jdbc:sqlite:db.db";
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Connection con = DriverManager.getConnection(url);
			Statement stmt = con.createStatement();

			stmt.execute(formatQuery(starRate));
			stmt.execute(formatQuery(imgPath));
			stmt.execute(formatQuery(difRate));
			stmt.execute(formatQuery(prepTime));
			stmt.execute(formatQuery(cookTime));
			stmt.execute(formatQuery(yield));

			con.close();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Could not connect to database, something went wrong");
			e1.printStackTrace();
		}

	}
	
	/**
	 * method for preparing the query that sends info to db
	 * @param s text to be sent to database
	 * @return formatted query
	 */
	public static String formatQuery(String s) {

		// for empty values
		if (s.charAt(s.length() - 1) == ':' || s.charAt(s.length() - 1) == ',') {
			s.substring(0, s.length() - 2);
			s += "empty";
		}
		// making updates to a recipe, just insert the data into the same recipe
		if (RecipeTemplate.editingRecip) {
			return "INSERT INTO '" + CreateLoginForm.currUser + "' ('" + RecipeTemplate.newColName + "') VALUES ('" + s + "')";

		}

		// if making a new recipe,
		if (track == 1) {
			return "INSERT INTO '" + CreateLoginForm.currUser + "' ('" + RecipeTemplate.colName + "') VALUES ('" + s + "')";
		}
		// if making a new meal section
		String temp = MSTemplate.msName + "|";
		temp += s;
		s = temp;
		return "INSERT INTO '" + CreateLoginForm.currUser + "' (MealSection) VALUES ('" + s + "')";

	}

}
