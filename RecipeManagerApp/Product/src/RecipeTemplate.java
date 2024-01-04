
/**
 * template for the recipe fill out form
 * includes template for existing recipe and corresponding method for populating fields
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class RecipeTemplate extends JFrame implements ActionListener, FocusListener {

	public static boolean editingRecip;
	private JFrame frame;
	public static JPanel container;
	private JLabel nameLab;
	private final JTextField name;
	private final JTextField dum;
	public static int ingSpacing;
	public static JButton create;

	public static JButton deleteRecip;
	public static ListForm ingForm;
	public static ListForm instructForm;
	public static ListForm subForm;
	public static ListForm noteForm;
	public static ArrayList<ListForm> allForms;
	public static String recipName = "";
	public static int ingY;
	public static int insY;
	public static int noteY;
	public static int yCreate;
	private OtherOptions opts;
	public static int spacingY;
	private String nameStr;
	private String msName;
	public static String colName;
	private String raw;
	public static String newColName;
	private int type;

	// template for new recipe, type = 1
	public RecipeTemplate(String msName) {

		editingRecip = false;
		type = 1;
		allForms = new ArrayList<>();

		this.msName = msName;
		spacingY = 0;
		frame = new JFrame();
		frame.setBounds(300, 90, 900, 650);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		container = new JPanel();
		container.setBackground(Color.white);
		frame.setTitle("New Recipe");

		JScrollPane jsp = new JScrollPane(container);

		container.setPreferredSize(new Dimension(0, 650));
		container.setLayout(null);

		deleteRecip = new JButton("Delete Recipe");
		deleteRecip.setFont(new Font("Arial", Font.PLAIN, 15));
		deleteRecip.setSize(180, 40);
		deleteRecip.setLocation(30, 50);
		deleteRecip.setForeground(Color.DARK_GRAY);
		deleteRecip.setBackground(Color.decode("#55A630"));
		deleteRecip.addActionListener(this);
		container.add(deleteRecip);

		nameLab = new JLabel("New " + msName + " Recipe");
		nameLab.setFont(new Font("Arial", Font.BOLD, 20));
		nameLab.setSize(300, 50);
		nameLab.setLocation(370, spacingY);
		nameLab.setForeground(Color.decode("#9B9B9B"));
		container.add(nameLab);

		try {
			BufferedImage bar = ImageIO.read(new File("systemImages/greenRec.png"));
			Image imgBar = bar.getScaledInstance(900, 50, Image.SCALE_DEFAULT);
			JLabel barLabel = new JLabel(new ImageIcon(imgBar));
			barLabel.setSize(900, 50);
			barLabel.setLocation(-5, spacingY);
			container.add(barLabel);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// create dummy text field to grab mouse focus so that default text is displayed
		// as the title
		dum = new JTextField();
		dum.grabFocus();
		dum.setFont(new Font("Arial", Font.PLAIN, 1));
		dum.setSize(1, 1);
		dum.setLocation(0, spacingY);
		container.add(dum);

		spacingY += 80;
		name = new JTextField("Recipe Name");
		name.setHorizontalAlignment(JTextField.CENTER);
		name.setFont(new Font("Arial", Font.PLAIN, 30));
		name.setForeground(Color.decode("#9B9B9B"));
		name.setSize(300, 50);
		name.setLocation(290, spacingY);
		container.add(name);

		name.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {

				// if focus is gained and text field is empty or has default text, clear it
				if (name.getText().equals("") || name.getText().equals("Recipe Name")) {
					name.setText("");
				}
			}

			public void focusLost(FocusEvent e) {

				// if focus is lost and field is empty, set text to default text
				if (name.getText().equals("")) {
					name.setText("Recipe Name");
				}

				// else set the text to whatever was entered
				recipName = name.getText();
			}

		});

		spacingY += 50;
		opts = new OtherOptions();
		opts.setSize(900, 310);
		opts.setBackground(Color.white);
		opts.setLocation(0, spacingY);
		container.add(opts);

		spacingY += 330;
		ingY = spacingY;
		ingForm = new ListForm("Ingrediants", 1);
		ingForm.setSize(400, 150);
		ingForm.setLocation(20, ingY);
		container.add(ingForm);

		subForm = new ListForm("Substitutions", 1);
		subForm.setSize(400, 150);
		subForm.setLocation(450, ingY);
		container.add(subForm);

		spacingY += 160;
		insY = spacingY;
		instructForm = new ListForm("Instructions", 1);
		instructForm.setSize(900, 150);
		instructForm.setLocation(20, insY);
		container.add(instructForm);

		spacingY += 160;
		noteY = spacingY;
		noteForm = new ListForm("Notes", 1);
		noteForm.setSize(900, 150);
		noteForm.setLocation(20, noteY);
		container.add(noteForm);

		spacingY += 160;
		yCreate = spacingY;
		create = new JButton("Create");
		create.setFont(new Font("Arial", Font.PLAIN, 15));
		create.setSize(100, 40);
		create.setLocation(405, yCreate);
		create.setForeground(Color.white);
		create.setBackground(Color.decode("#55A630"));
		create.addActionListener(this);
		container.add(create);

		allForms.add(ingForm);
		allForms.add(subForm);
		allForms.add(instructForm);
		allForms.add(noteForm);

		frame.getContentPane().add(jsp);
		frame.setVisible(true);

	}

	public RecipeTemplate(String nameStr, String raw) {

		editingRecip = true;
		type = 2;
		allForms = new ArrayList<>();

		this.nameStr = nameStr;

		// raw is a concatenation of the meal section name and recipe name
		// it is the exact title of a recipe column in the db
		this.raw = raw;

		spacingY = 0;
		frame = new JFrame();
		frame.setBounds(300, 90, 900, 650);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		container = new JPanel();
		container.setBackground(Color.white);
		frame.setTitle("Editing Recipe");

		JScrollPane jsp = new JScrollPane(container);

		container.setPreferredSize(new Dimension(0, 650));
		container.setLayout(null);

		deleteRecip = new JButton("Delete Recipe");
		deleteRecip.setFont(new Font("Arial", Font.PLAIN, 15));
		deleteRecip.setSize(180, 40);
		deleteRecip.setLocation(30, 50);
		deleteRecip.setForeground(Color.DARK_GRAY);
		deleteRecip.setBackground(Color.decode("#55A630"));
		deleteRecip.addActionListener(this);
		container.add(deleteRecip);

		nameLab = new JLabel("Editing " + nameStr + " Recipe");
		nameLab.setFont(new Font("Arial", Font.BOLD, 20));
		nameLab.setSize(300, 50);
		nameLab.setLocation(370, spacingY);
		nameLab.setForeground(Color.decode("#9B9B9B"));
		container.add(nameLab);

		try {
			BufferedImage bar = ImageIO.read(new File("systemImages/greenRec.png"));
			Image imgBar = bar.getScaledInstance(900, 50, Image.SCALE_DEFAULT);
			JLabel barLabel = new JLabel(new ImageIcon(imgBar));
			barLabel.setSize(900, 50);
			barLabel.setLocation(-5, spacingY);
			container.add(barLabel);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		dum = new JTextField();
		dum.grabFocus();
		dum.setFont(new Font("Arial", Font.PLAIN, 1));
		dum.setSize(1, 1);
		dum.setLocation(0, spacingY);
		container.add(dum);

		spacingY += 80;
		name = new JTextField(nameStr);
		name.setHorizontalAlignment(JTextField.CENTER);
		name.setFont(new Font("Arial", Font.PLAIN, 30));
		name.setForeground(Color.decode("#9B9B9B"));
		name.setSize(300, 50);
		name.setLocation(290, spacingY);
		container.add(name);

		name.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (name.getText().equals("") || name.getText().equals(nameStr)) {
					name.setText("");
				}
			}

			public void focusLost(FocusEvent e) {
				if (name.getText().equals("")) {
					name.setText(nameStr);
				}
				recipName = name.getText();
			}

		});

		spacingY += 50;
		opts = new OtherOptions();
		opts.setSize(900, 310);
		opts.setBackground(Color.white);
		opts.setLocation(0, spacingY);
		container.add(opts);

		spacingY += 330;
		ingY = spacingY;
		ingForm = new ListForm("Ingrediants", 1);
		ingForm.setSize(400, 150);
		ingForm.setLocation(20, ingY);
		container.add(ingForm);

		subForm = new ListForm("Substitutions", 1);
		subForm.setSize(400, 150);
		subForm.setLocation(450, ingY);
		container.add(subForm);

		spacingY += 160;
		insY = spacingY;
		instructForm = new ListForm("Instructions", 1);
		instructForm.setSize(900, 150);
		instructForm.setLocation(20, insY);
		container.add(instructForm);

		spacingY += 160;
		noteY = spacingY;
		noteForm = new ListForm("Notes", 1);
		noteForm.setSize(900, 150);
		noteForm.setLocation(20, noteY);
		container.add(noteForm);

		spacingY += 160;
		yCreate = spacingY;
		create = new JButton("Save Changes");
		create.setFont(new Font("Arial", Font.PLAIN, 15));
		create.setSize(160, 40);
		create.setLocation(405, yCreate);
		create.setForeground(Color.white);
		create.setBackground(Color.decode("#55A630"));
		create.addActionListener(this);
		container.add(create);

		allForms.add(ingForm);
		allForms.add(subForm);
		allForms.add(instructForm);
		allForms.add(noteForm);

		frame.getContentPane().add(jsp);
		frame.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == create) {
			boolean numCheck = false;

			// check if only numbers are entered into the prep and cook time fields
			try {
				int n = 0;

				// if one of these returns an error, then catch it by showing a JOptionPane
				// dialog message
				n = Integer.parseInt(((TimePanel) OtherOptions.optArr.get(3)).getHours());
				n = Integer.parseInt(((TimePanel) OtherOptions.optArr.get(3)).getMins());
				n = Integer.parseInt(((TimePanel) OtherOptions.optArr.get(4)).getHours());
				n = Integer.parseInt(((TimePanel) OtherOptions.optArr.get(4)).getMins());

				// if all assignments to n are integer and no errors are caused, then all the
				// values are integers
				numCheck = true;
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Enter numbers for Cook and Prep time.");
			}

			boolean validRecipName = true;

			// remove leading and trailing white space
			recipName = recipName.trim();
			// since the recipe name will be in the column name, it must exclude certain
			// characters
			for (int i = 0; i < recipName.length(); i++) {
				if (!Character.isLetterOrDigit(recipName.charAt(i)) && recipName.charAt(i) != ' '
						|| recipName.contains("  ")) {
					JOptionPane.showMessageDialog(null,
							"Recipe name is not in correct format. It must only contain letters or digts, single spaces, and no other special characters.");
					validRecipName = false;
					break;
				}
			}

			// if all numeric required values are correct and the name is valid, then
			// proceed by sending data to db
			if (numCheck && validRecipName) {
				try {

					String url = "jdbc:sqlite:db.db";
					try {
						Class.forName("org.sqlite.JDBC");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Connection con = DriverManager.getConnection(url);
					Statement stmt = con.createStatement();
					// __ distinguishes meal section name from recipe name

					if (type == 1) {
						colName = msName.replaceAll(" ", "_") + "__" + RecipeTemplate.recipName.replaceAll(" ", "_");
						String query = "ALTER TABLE '" + CreateLoginForm.currUser + "' ADD '" + colName + "' text NULL";
						stmt.execute(query);

						ingForm.addInfoToDB();
						subForm.addInfoToDB();
						instructForm.addInfoToDB();
						noteForm.addInfoToDB();
						ListForm.addOptsInfo();

						// when a new recipe is created after one has already been created in the same
						// session,
						// static counter totalRecip is to be reset and the arrayList is cleared so at
						// to preserve the spacing
						// and reformatting system
						ListForm.totalRecip = 0;

						ClearBtn.ks.deleteKS();

						ClearBtn.ks = new RecipesUnderMS(msName);
						frame.dispose();

					} else if (type == 2) {
						// when updating a recipe, you just delete the old one and add the new data
						// under the same column name

						String query = "ALTER TABLE '" + CreateLoginForm.currUser + "' DROP COLUMN '" + raw + "'";
						stmt.execute(query);

						String msName = "";
						for (int i = 0; i < raw.length(); i++) {
							if (!(raw.charAt(i) == '_' && raw.charAt(i + 1) == '_')) {
								msName += raw.charAt(i);
							} else {
								break;
							}
						}
						newColName = msName + "__" + name.getText().replaceAll(" ", "_");
						query = "ALTER TABLE '" + CreateLoginForm.currUser + "' ADD '" + newColName + "' text NULL";

						stmt.execute(query);

						// add the rest of the info to the column
						ingForm.addInfoToDB();
						subForm.addInfoToDB();
						instructForm.addInfoToDB();
						noteForm.addInfoToDB();
						ListForm.addOptsInfo();

						editingRecip = false;

						ListForm.totalRecip = 0;

						ClearBtn.ks.deleteKS();
						ClearBtn.ks = new RecipesUnderMS(msName);
						
						frame.dispose();
					}

					con.close();
				} catch (SQLException e1) {

					// if recipe with same name already exists under that meal section, show error
					JOptionPane.showMessageDialog(null, "Recipe already defined.");
					e1.printStackTrace();
				}
			}
		}

		// if deleting recipe
		if (e.getSource() == deleteRecip) {
			int result = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to delete this recipe? " + "All data will be lost forever.");

			if (result == JOptionPane.YES_OPTION) {

				// if deleting a recipe that is currently being written,
				if (type == 1) {
					ListForm.totalRecip = 0;
					RecipesUnderMS ks = new RecipesUnderMS(msName);
					frame.dispose();
				}

				// deleting a recipe that had been written previously and has now been loaded
				else {
					String url = "jdbc:sqlite:db.db";
					try {
						Class.forName("org.sqlite.JDBC");
					} catch (ClassNotFoundException E) {
						E.printStackTrace();
					}

					try {
						Connection con = DriverManager.getConnection(url);
						Statement stmt = con.createStatement();

						// drop the column with that meal section and recipe name which are concatenated
						// in a single string called raw
						String query = "ALTER TABLE '" + CreateLoginForm.currUser + "' DROP COLUMN '" + raw + "'";
						stmt.execute(query);

					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					// getting the meal section name from raw
					String s = "";
					int i = 0;
					while (raw.charAt(i) != '_') {
						s += raw.charAt(i);
						i++;
					}
					// ListForm.totalRecip = 0 resets the page so that formatting of elements is
					// normal
					ListForm.totalRecip = 0;
					ClearBtn.ks.deleteKS();
					ClearBtn.ks = new RecipesUnderMS(s);
					frame.dispose();

				}
			}

		}

	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 *  method for populating a recipe template with existing data from db
	 */
	public void populateRecipe() {

		try {
			String url = "jdbc:sqlite:db.db";
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			Connection con = DriverManager.getConnection(url);

			// using raw select that recipe column from db
			String query = "SELECT " + raw + " FROM '" + CreateLoginForm.currUser + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			ArrayList<String> ingList = new ArrayList<>();
			ArrayList<String> subList = new ArrayList<>();
			ArrayList<String> insList = new ArrayList<>();
			ArrayList<String> notesList = new ArrayList<>();
			ArrayList<String> otherOpts = new ArrayList<>();

			// start retrieving values and adding to appropriate arraylists
			while (rs.next() && rs.getString(1) == null) {
			}

			String s = rs.getString(1);

			if (s.contains("Ingrediants:")) {

				// call to a method which adds the elements from a comma seperated string to an
				// arraylist
				addValueToArr(ingList, s);
			}

			while (rs.next() && rs.getString(1) != null) {
				s = rs.getString(1);

				if (s.contains("Substitutions:")) {
					addValueToArr(subList, s);
				}

				else if (s.contains("Instructions:")) {
					addValueToArr(insList, s);
				}

				else if (s.contains("Notes:")) {
					addValueToArr(notesList, s);
				}

				else {
					addValueToArr(otherOpts, s);
				}

			}
			con.close();

			// populating elements from arraylist into the list form text field
			for (String str : ingList) {
				ingForm.populateRecipeListForm(str);
			}

			for (String str : subList) {
				subForm.populateRecipeListForm(str);
			}
			for (String str : insList) {
				instructForm.populateRecipeListForm(str);
			}
			for (String str : notesList) {
				noteForm.populateRecipeListForm(str);
			}

			opts.populateRecipeListForm(otherOpts.get(0), otherOpts.get(1), otherOpts.get(2), otherOpts.get(3),
					otherOpts.get(4), otherOpts.get(5), otherOpts.get(6), otherOpts.get(7));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * method which takes an arraylist and comma seperated string as input and adds
	 * each element from the string to the arraylist
	 * @param arr array which will store the seperate sections of the strings
	 * @param s the string which will be seperated
	 */
	public void addValueToArr(ArrayList<String> arr, String s) {
		boolean passedColon = false;
		String value = "";

		// must skip over : in the string to access values
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ':' && !passedColon) {
				passedColon = true;
			} else if (passedColon) {

				// start adding values seperated by comma
				if (s.charAt(i) != ',') {
					value += s.charAt(i);
				} else {
					if (!value.equals("empty")) {
						arr.add(value);
					}
					value = "";
				}
			}
		}
	}
}
