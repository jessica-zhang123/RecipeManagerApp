/*
 * Class which creates the clear overlays on meal section and recipe buttons
 * includes specific actions for on click in both buttons
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ClearBtn extends JButton implements ActionListener {
	/*
	 * type differentiates between the clear overlay button used to open a certain
	 * meal section(on first window after login) and the other overlay used to open
	 * a recipe (on top of recipe thumbnail panels)
	 */

	private int type;
	// name refers to either Meal Section name or recipe name
	private String name;

	// if a new KitchenSpace is to be created in the case of a meal section click
	public static RecipesUnderMS ks;

	// rawName is the specific reference in the database to a recipe and is needed
	// for a recipe click
	private String rawName;

	public ClearBtn(String name, String rawName, int n) {
		this.name = name;
		this.type = n;
		this.rawName = rawName;

		setBorder(null);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setOpaque(false);
		addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// if meal section is clicked
		if (e.getSource() == this && type == 1) {
			// name is meal section name
			ks = new RecipesUnderMS(name);
			// dispose of the previous frame so as to allow for only the updated Main
			// kitchen to display which will be created at a later point
			MealSectionsUnderAccount.frame.dispose();

		}
		
		// if a recipe is clicked 
		if (e.getSource() == this && type == 2) {
			ks.dispose();
			MSTemplate.ks.frame.dispose();
			MealSectionsUnderAccount.frame.dispose();
			RecipeTemplate selectedRecipe = new RecipeTemplate(name, rawName);
			selectedRecipe.populateRecipe();
			selectedRecipe.repaint();
		}

	}

}
