/**
 * Format the new meal section template
 * includes the template for a new meal section and for editing an existing one
 * includes method for populating an existing meal section
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MSTemplate extends JFrame implements ActionListener, FocusListener {

	private JFrame frame;
	public static JPanel container;
	private JLabel nameLab;
	private final JTextField name;
	public static int spacingY;
	private final JTextField dum;
	public static String msName = "";

	private JScrollPane jsp;

	// spacing variables
	public static int descY;
	public static int primFY;
	public static int culnGY;

	public static ListForm descForm;
	public static ListForm primFForm;
	public static ListForm culnGForm;

	private Dragger imgField;
	public static JButton create;
	public static JButton deleteMS;

	public static int yCreate;

	public static boolean editingMS;

	private String editableMSName;

	// arrays for storing text field values
	private ArrayList<String> descList;
	private ArrayList<String> primFList;
	private ArrayList<String> culnGoalsList;
	private String imgPath;
	
	public static RecipesUnderMS ks;

	public static ArrayList<ListForm> allForms;

	public MSTemplate() {

		// upon creation of a meal section form, dispose of the previous screen since it
		// will need to be updated anyway to display the new information
		MealSectionsUnderAccount.frame.dispose();
		imgPath = "";

		allForms = new ArrayList<ListForm>();
		editingMS = false;
		descList = new ArrayList<>();
		primFList = new ArrayList<>();
		culnGoalsList = new ArrayList<>();

		spacingY = 0;
		descY = 0;
		primFY = 0;
		culnGY = 0;
		yCreate = 0;

		frame = new JFrame();
		frame.setBounds(300, 90, 900, 900);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		container = new JPanel();
		container.setBackground(Color.white);
		frame.setTitle("New Meal Section");

		jsp = new JScrollPane(container);

		container.setPreferredSize(new Dimension(0, 1150));
		container.setLayout(null);

		nameLab = new JLabel("New Meal Section");
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

		// creates a dummy textfield that is not visible to the user so that the mouse
		// focus is initally set on this dummy
		// allows for mouse focus to be sent elsewhere so that correct default text is
		// displayed on the title bar
		dum = new JTextField();
		dum.grabFocus();
		dum.setFont(new Font("Arial", Font.PLAIN, 1));
		dum.setSize(1, 1);
		dum.setLocation(0, spacingY);
		container.add(dum);

		spacingY += 80;
		name = new JTextField("Meal Section Name");
		name.setHorizontalAlignment(JTextField.CENTER);
		name.setFont(new Font("Arial", Font.PLAIN, 30));
		name.setForeground(Color.decode("#9B9B9B"));
		name.setSize(300, 50);
		name.setLocation(290, spacingY);
		container.add(name);

		name.addFocusListener(new FocusListener() {
			// when focus is gained, and the field is either empty or set to default text,
			// clear the field
			public void focusGained(FocusEvent e) {
				if (name.getText().equals("") || name.getText().equals("Meal Section Name")) {
					name.setText("");
				}
			}

			public void focusLost(FocusEvent e) {

				// when focus is lost and there is no text, set to default text
				if (name.getText().equals("")) {
					name.setText("Meal Section Name");
				}

				// if there is text, set the meal section name to be that text after the mouse
				// leaves the field
				msName = name.getText();
			}

		});

		spacingY += 70;

		imgField = new Dragger(620, 280);
		imgField.setSize(620, 280);
		imgField.setLocation(120, spacingY + 30);

		container.add(imgField);

		spacingY += 330;
		descY = spacingY;
		descForm = new ListForm("Description", 2);
		descForm.setSize(400, 150);
		descForm.setLocation(20, descY);
		container.add(descForm);

		spacingY += 160;
		primFY = spacingY;
		primFForm = new ListForm("Primary Flavours", 2);
		primFForm.setSize(900, 150);
		primFForm.setLocation(20, primFY);
		container.add(primFForm);

		spacingY += 160;
		culnGY = spacingY;
		culnGForm = new ListForm("Culinary Goals", 2);
		culnGForm.setSize(900, 150);
		culnGForm.setLocation(20, culnGY);
		container.add(culnGForm);

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

		allForms.add(descForm);
		allForms.add(primFForm);
		allForms.add(culnGForm);

		frame.getContentPane().add(jsp);
		frame.setVisible(true);

	}

	// class for an existing meal section

	public MSTemplate(String editableMSName) {

		editingMS = true;
		allForms = new ArrayList<ListForm>();

		this.editableMSName = editableMSName;
		imgPath = "";

		descList = new ArrayList<>();
		primFList = new ArrayList<>();
		culnGoalsList = new ArrayList<>();

		spacingY = 0;
		descY = 0;
		primFY = 0;
		culnGY = 0;
		yCreate = 0;

		frame = new JFrame();
		frame.setBounds(300, 90, 900, 900);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		container = new JPanel();
		container.setBackground(Color.white);
		frame.setTitle("Editing " + editableMSName + " Section");

		jsp = new JScrollPane(container);

		container.setPreferredSize(new Dimension(0, 1150));
		container.setLayout(null);

		nameLab = new JLabel("Editing " + editableMSName + " Section");
		nameLab.setFont(new Font("Arial", Font.BOLD, 20));
		nameLab.setSize(300, 50);
		nameLab.setLocation(370, spacingY);
		nameLab.setForeground(Color.decode("#9B9B9B"));
		container.add(nameLab);

		try {

			// add green bar to top of template
			BufferedImage bar = ImageIO.read(new File("systemImages/greenRec.png"));
			Image imgBar = bar.getScaledInstance(900, 50, Image.SCALE_DEFAULT);
			JLabel barLabel = new JLabel(new ImageIcon(imgBar));
			barLabel.setSize(900, 50);
			barLabel.setLocation(-5, spacingY);
			container.add(barLabel);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// dummy textfield to first grab mouse focus
		dum = new JTextField();
		dum.grabFocus();
		dum.setFont(new Font("Arial", Font.PLAIN, 1));
		dum.setSize(1, 1);
		dum.setLocation(0, spacingY);
		container.add(dum);

		spacingY += 80;
		name = new JTextField(editableMSName);
		name.setEditable(false);
		name.setHorizontalAlignment(JTextField.CENTER);
		name.setFont(new Font("Arial", Font.PLAIN, 30));
		name.setForeground(Color.decode("#9B9B9B"));
		name.setSize(300, 50);
		name.setLocation(290, spacingY);
		container.add(name);

		spacingY += 70;

		imgField = new Dragger(620, 280);
		imgField.setSize(620, 280);
		imgField.setLocation(120, spacingY + 30);

		container.add(imgField);

		spacingY += 330;
		descY = spacingY;
		descForm = new ListForm("Description", 2);
		descForm.setSize(400, 150);
		descForm.setLocation(20, descY);
		container.add(descForm);

		spacingY += 160;
		primFY = spacingY;
		primFForm = new ListForm("Primary Flavours", 2);
		primFForm.setSize(900, 150);
		primFForm.setLocation(20, primFY);
		container.add(primFForm);

		spacingY += 160;
		culnGY = spacingY;
		culnGForm = new ListForm("Culinary Goals", 2);
		culnGForm.setSize(900, 150);
		culnGForm.setLocation(20, culnGY);
		container.add(culnGForm);

		deleteMS = new JButton("Delete meal section");
		deleteMS.setFont(new Font("Arial", Font.PLAIN, 15));
		deleteMS.setSize(180, 40);
		deleteMS.setLocation(30, 50);
		deleteMS.setForeground(Color.DARK_GRAY);
		deleteMS.setBackground(Color.decode("#55A630"));
		deleteMS.addActionListener(this);
		container.add(deleteMS);

		spacingY += 160;
		yCreate = spacingY;
		create = new JButton("Save Changes");
		create.setFont(new Font("Arial", Font.PLAIN, 15));
		create.setSize(180, 40);
		create.setLocation(405, yCreate);
		create.setForeground(Color.white);
		create.setBackground(Color.decode("#55A630"));
		create.addActionListener(this);
		container.add(create);

		allForms.add(descForm);
		allForms.add(primFForm);
		allForms.add(culnGForm);

		frame.getContentPane().add(jsp);
		frame.setVisible(true);

	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// when create button is pressed,
		if (e.getSource() == create) {

			// first check if name is valid,
			boolean validMS = true;

			// trim white space
			msName = msName.trim();
			// since the meal section name will be in the column name, it must exclude
			// certain characters
			for (int i = 0; i < msName.length(); i++) {
				if (!Character.isLetterOrDigit(msName.charAt(i)) && msName.charAt(i) != ' ' || msName.contains("  ")) {
					JOptionPane.showMessageDialog(null,
							"Meal Section name is not in correct format. "
							+ "It must only contain letters or digts, single spaces, and no other special characters.");
					validMS = false;
					break;
				}
			}

			// check is MS name already exists only if new meal section is being made since
			// the name cannot be changed after creation

			if (!editingMS) {
				try {
					String url = "jdbc:sqlite:db.db";
					try {
						Class.forName("org.sqlite.JDBC");
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					Connection con = DriverManager.getConnection(url);
					String query = "SELECT MealSection FROM '" + CreateLoginForm.currUser + "'";
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					while (rs.next()) {
						if (rs.getString(1) != null) {
							if (rs.getString(1).contains(msName)) {
								JOptionPane.showMessageDialog(null,
										"Meal Section name already exists. Either delete the old one or give this one a unique name.");
								validMS = false;
								break;
							}

						}

					}

					rs.close();
					con.close();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			// if the name is valid and its a new meal section, add the info to the db
			if (validMS && !editingMS) {
				msName.replaceAll(" ", "_");
				imgField.addImgPathToDB(msName, CreateLoginForm.currUser, "MealSection");
				descForm.addInfoToDB();
				primFForm.addInfoToDB();
				culnGForm.addInfoToDB();

				// clear the listform arraylists upon creation to maintain spacing and
				// organization for the sections using listform afterwards
				ListForm.totalMS = 0;
				allForms.clear();

				// create a new main kitchen screen
				MealSectionsUnderAccount ks = new MealSectionsUnderAccount();

				// dispose of this screen
				frame.dispose();

			}

			// if editing the meal section
			else if (editingMS) {

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

					// delete those rows with the meal section name under MealSection column
					String query = "DELETE FROM '" + CreateLoginForm.currUser + "' WHERE MealSection LIKE '%"
							+ editableMSName + "%'";
					stmt.execute(query);

					String path = imgField.getImagePath().replaceAll((char) 92 + "" + (char) 92, (char) 47 + "");

					// add the updated info in its place
					String newMSPath = name.getText() + "|" + "imgPath:" + path;
					String newDescrip = name.getText() + "|" + "Description:"
							+ descForm.formatMSList().replaceAll("Description:", "");
					String newPrimF = name.getText() + "|" + "Primary Flavours:"
							+ primFForm.formatMSList().replaceAll("Primary Flavours:", "");
					String newCulnG = name.getText() + "|" + "Culinary Goals:"
							+ culnGForm.formatMSList().replaceAll("Culinary Goals:", "");

					query = "INSERT INTO '" + CreateLoginForm.currUser + "' (MealSection) VALUES ('" + newMSPath + "')";
					stmt.execute(query);

					query = "INSERT INTO '" + CreateLoginForm.currUser + "' (MealSection) VALUES ('" + newDescrip
							+ "')";
					stmt.execute(query);

					query = "INSERT INTO '" + CreateLoginForm.currUser + "' (MealSection) VALUES ('" + newPrimF + "')";
					stmt.execute(query);

					query = "INSERT INTO '" + CreateLoginForm.currUser + "' (MealSection) VALUES ('" + newCulnG + "')";
					stmt.execute(query);

					this.msName = name.getText();

					con.close();

					// reset variables and clear arraylists
					editingMS = false;
					ListForm.totalMS = 0;
					allForms.clear();
					
					ClearBtn.ks.deleteKS();
					MealSectionsUnderAccount.frame.dispose();
					// go back to the screen which stores the recipes
					ks = new RecipesUnderMS(msName);

					frame.dispose();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		// if deleting mealsection,
		else if (e.getSource() == deleteMS) {

			// display caution message
			int result = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to delete this section? " + "All recipes will be lost forever.");

			if (result == JOptionPane.YES_OPTION) {

				String url = "jdbc:sqlite:db.db";
				try {
					Class.forName("org.sqlite.JDBC");
				} catch (ClassNotFoundException E) {
					// TODO Auto-generated catch block
					E.printStackTrace();
				}

				try {
					Connection con = DriverManager.getConnection(url);
					Statement stmt = con.createStatement();

					// go to database and delete all rows under MealSection with same name
					String query = "DELETE FROM '" + CreateLoginForm.currUser + "' WHERE MealSection LIKE '%"
							+ editableMSName + "%'";
					stmt.execute(query);

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				// delete the frame and load the frame with all meal sections
				ListForm.totalMS = 0;
				frame.dispose();
				MealSectionsUnderAccount ms = new MealSectionsUnderAccount();

			}

		}

	}

	/**
	 * code for populating a meal section template with data from an existing meal section
	 */
	public void populateMS() {
		editingMS = true;
		try {
			String url = "jdbc:sqlite:db.db";
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Connection con = DriverManager.getConnection(url);
			String query = "SELECT MealSection FROM '" + CreateLoginForm.currUser + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// find rows under MealSection with the target name
			while (rs.next() && rs.getString(1) == null || !rs.getString(1).contains(editableMSName)) {

			}
			String s = rs.getString(1);
			if (s.contains("imgPath:")) {
				imgPath = s.substring(s.indexOf("imgPath:") + 8);
			}

			// break out of loops once the value is found
			while (rs.next() && rs.getString(1) != null) {

				// storing data found under the same name
				s = rs.getString(1);
				if (s.contains(editableMSName)) {

					if (s.contains("imgPath:")) {
						imgPath = s.substring(s.indexOf("imgPath:") + 8);
					}

					if (s.contains("Description:")) {

						// call to method which adds the comma seperated values retrieved from db into
						// an array
						addValueToArr(descList, s, descForm.header);

					} else if (s.contains("Primary Flavours:")) {
						addValueToArr(primFList, s, primFForm.header);

					} else if (s.contains("Culinary Goals:")) {
						addValueToArr(culnGoalsList, s, culnGForm.header);
						break;
					}

				}
			}

			// for every element collected in the arraylist, display that in the MS template
			// using the ListForm method: populateMSListForm()
			for (String str : descList) {
				descForm.populateMSListForm(str);
			}

			for (String str : primFList) {
				primFForm.populateMSListForm(str);
			}

			for (String str : culnGoalsList) {
				culnGForm.populateMSListForm(str);
			}

			imgField.loadImg(imgPath);

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * takes in arraylist which stores individual values, the comma seperated string with all the values, and name of meal section
	 * @param arr arraylist in which to insert the values 
	 * @param s meal section and recipe name concatenated string
	 * @param header title of section
	 */
	public void addValueToArr(ArrayList<String> arr, String s, String header) {

		// remove the MS name and | from the string
		s = s.replaceFirst(editableMSName + "|" + header, "");

		// example of a string stored in db:
		// MSName|Culinary Goals:fry, boil

		// msut also remove the :
		boolean passedColon = false;
		String value = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ':' && !passedColon) {
				passedColon = true;
			}

			// once the colon has been passed, begin retrieving individual values seperated
			// by commas
			else if (passedColon) {
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
