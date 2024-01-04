
/**
 * creates text field which has a dotted border and is used by Recipe and MealSection templates
 */
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.Border;

public class TxtField {

	private final JTextField txtField;

	public TxtField(int tfLength) {

		txtField = new JTextField("");
		txtField.setFont(new Font("Arial", Font.PLAIN, 15));
		txtField.setSize(tfLength, 40);
		txtField.setForeground(Color.decode("#9B9B9B"));
		txtField.setLocation(50, 190 + RecipeTemplate.ingSpacing);
		Border border = BorderFactory.createDashedBorder(Color.decode("#9B9B9B"), 2, 1, 3, true);
		txtField.setBorder(border);

	}

	public JTextField getTF() {
		return txtField;
	}
}
