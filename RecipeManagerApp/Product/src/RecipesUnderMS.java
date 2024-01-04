
/**
 * Creates the window where all recipes under a meal section are stored
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class RecipesUnderMS extends JFrame implements ActionListener {

	private JPanel c;
	public static JFrame frame;
	private JLabel sectName;
	private JScrollPane jsp;
	private JButton makeRecipe;
	private JButton backToKitchen;
	private JButton seeMealSection;
	private JButton sort;
	private RecipeDataRetrieval ms;
	public static String msName;
	public static boolean sortSelected;

	public RecipesUnderMS(String sectText) {
		
		msName = sectText;
		frame = new JFrame();
		frame.setBounds(300, 90, 900, 650);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Kitchen Space");

		// making instance of meal section by supplying meal section name
		ms = new RecipeDataRetrieval(sectText);
		c = new JPanel();
		c.setBackground(Color.white);

		jsp = new JScrollPane(c);
		c.setPreferredSize(new Dimension(0, 300 + ms.yDim));
		c.setLayout(new FlowLayout(FlowLayout.RIGHT));
		c.setBackground(Color.white);

		backToKitchen = new JButton("Back to kitchen");
		backToKitchen.setFont(new Font("Arial", Font.PLAIN, 30));
		backToKitchen.setSize(230, 60);
		backToKitchen.setBackground(Color.WHITE);
		backToKitchen.setForeground(Color.decode("#55A630"));
		c.add(backToKitchen);

		seeMealSection = new JButton("See Meal Section");
		seeMealSection.setFont(new Font("Arial", Font.PLAIN, 30));
		seeMealSection.setSize(230, 60);
		seeMealSection.setBackground(Color.WHITE);
		seeMealSection.setForeground(Color.decode("#55A630"));
		seeMealSection.setFocusable(false);

		c.add(seeMealSection);
		
		sort = new JButton("Sort alphabetically");
		sort.setFont(new Font("Arial", Font.PLAIN, 30));
		sort.setSize(230, 60);
		sort.setBackground(Color.WHITE);
	    sort.setForeground(Color.decode("#55A630"));
		sort.setFocusable(false);

		c.add(sort);

		sectName = new JLabel(sectText);
		sectName.setFont(new Font("Arial", Font.BOLD, 80));
		sectName.setForeground(Color.white);
		sectName.setBackground(Color.decode("#BAF2BB"));
		sectName.setPreferredSize(new Dimension(sectText.length() * 60, 130));
		sectName.setHorizontalAlignment(SwingConstants.LEFT);
		sectName.setOpaque(true);
		c.add(sectName);

		c.add(ms);

		makeRecipe = new JButton("Make a recipe");
		makeRecipe.setFont(new Font("Arial", Font.PLAIN, 30));
		makeRecipe.setSize(230, 60);
		makeRecipe.setBackground(Color.WHITE);
		makeRecipe.setForeground(Color.decode("#55A630"));
		c.add(makeRecipe);

		makeRecipe.addActionListener(this);
		seeMealSection.addActionListener(this);
		backToKitchen.addActionListener(this);
		sort.addActionListener(this);

		frame.getContentPane().add(jsp);
		frame.setVisible(true);

	}

	public void deleteKS() {
		frame.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		// if a recipe is made, dispose of the current frame and create frame of recipe
		// template
		if (e.getSource() == makeRecipe) {
			RecipeTemplate recip = new RecipeTemplate(msName);
			frame.dispose();
		}
		
		if (e.getSource() == sort) {
			sortSelected = true;
			frame.dispose();
			ClearBtn.ks= new RecipesUnderMS(msName);

		}

		// If viewing meal section, dispose of current frame and create Meal Section
		// template populated with corresponding data
		else if (e.getSource() == seeMealSection) {
			frame.dispose();
			MSTemplate ms = new MSTemplate(msName);
			ms.populateMS();
			ms.repaint();

		}
		// Going back to KitchenSpace, dispose of current frame and reload Main Kitchen
		// screen
		else if (e.getSource() == backToKitchen) {
			MealSectionsUnderAccount ms = new MealSectionsUnderAccount();
			frame.dispose();
		}
	}

}