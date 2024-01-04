/**
 * create the jpanel which houses all the recipes made 
 * fetches info from the db, loads onto a panel
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.swing.*;

public class RecipeDataRetrieval extends JPanel {

	ArrayList<RecipeThumbnail> recipArr;
	public static int count = 0;
	public int yDim;

	// input name of meal section
	public RecipeDataRetrieval(String name) {

		// character __ is used in database so we must replace them with spaces
		name = name.replaceAll(" ", "_");
		// add name to query so that only recipes of that meal section are called

		yDim = 0;
		count = 0;
		setBackground(Color.white);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		recipArr = new ArrayList<>();

		ArrayList<String> list = new ArrayList<>();

		try {
			String url = "jdbc:sqlite:db.db";
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			Connection con = DriverManager.getConnection(url);

			// pragma_table_info selects only column names, meaning it just goes along the
			// top row of column names
			// which consists of MealSection column, followed by column names in the form of
			// [MealSectionName]__[RecipeName]
			String query = "select c.name from pragma_table_info('" + CreateLoginForm.currUser + "') c";

			// statement for previous query
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// statement for executing query which collects recipe info
			Statement stmt2 = con.createStatement();
			ResultSet rs2;

			while (rs.next()) {
				// skip meal section column and only choose columns part of the requested meal
				// section
				if (!rs.getString(1).equals("MealSection") && rs.getString(1).startsWith(name)) {
					// Keep track of number of recipes for spacing
					count++;

					ArrayList<String> tempList = new ArrayList<>();
					String recipName = rs.getString(1).replace(' ', '_');
					list.add(recipName);

					// use that column name and select all the rows directly below it
					String queryInfo = "SELECT " + recipName + " FROM '" + CreateLoginForm.currUser + "'";
					rs2 = stmt2.executeQuery(queryInfo);

					// skip null values
					while (rs2.next() && rs2.getString(1) == null) {
					}
					// start adding the values
					while (rs2.next() && rs2.getString(1) != null) {
						list.add(rs2.getString(1));
					}

					// store the elements of the recipe in tempList
					for (int i = 0; i < list.size(); i++) {
						if (i == 0 || i == 5 || i == 6 || i == 7 || i == 8 | i == 9) {
							tempList.add(list.get(i));

						}
					}

					// assign values from tempList
					String recipTitle = "";
					String recipPath = "";
					String recipRate = "";
					try {
						recipTitle = tempList.get(0);
						recipPath = tempList.get(1);
						recipRate = "Unrated";

						// retrieving numeric rating from the string
						String num = "" + tempList.get(2).charAt(11);
						if (Integer.parseInt(num) == 0) {
							recipRate = "Easy";
						} else if (Integer.parseInt(num) == 1) {
							recipRate = "Medium";
						} else {
							recipRate = "Hard";
						}
					} catch (Exception e) {

					}

					// adding up prep and cook time
					// 2d arrays, 1 column dedicated to prep time, the other cook time
					int[] hourList = new int[2];
					int[] minList = new int[2];

					boolean afterCol = false;

					int count = 0;

					try {
						for (int m = 3; m <= 4; m++) {
							// skip until after the :
							for (int i = 0; i < tempList.get(m).length(); i++) {
								if (tempList.get(m).charAt(i) == ':') {
									afterCol = true;
								} else if (afterCol) {
									int k = i;
									String s = "";

									// in the db, times are stored as 4,3 for example, where the first digit in this
									// case 4 is hours and the digit after the, is the minute
									while (tempList.get(m).charAt(k) != ',') {
										// concatenating the hour string
										s += tempList.get(m).charAt(k);
										k++;
									}

									// adding the integer value to the list
									hourList[count] = Integer.parseInt(s);
									s = "";

									// skip over ,
									k++;

									// concatenating the minute string
									while (tempList.get(m).charAt(k) != ',') {
										s += tempList.get(m).charAt(k);
										k++;
									}

									// adding integer value to list
									minList[count] = Integer.parseInt(s);
									break;
								}
							}
							count++;
							afterCol = false;
						}
						int totalMin = 0;
						int totalHour = 0;

						// summing up times
						for (int i = 0; i < 2; i++) {
							totalMin += minList[i];
							totalHour += hourList[i];
						}

						// formating times to appropriate values
						if (totalMin > 59) {
							totalHour += totalMin / 60;
							totalMin %= 60;
						}

						String recipTime;

						recipTime = totalHour + " h " + totalMin + " m";

						String recipYield = tempList.get(5).substring(6);
						String temp = "";

						// retrieving yield value
						for (int i = 0; i < recipYield.length(); i++) {
							if (recipYield.charAt(i) != ',') {
								temp += recipYield.charAt(i);
							} else {
								break;
							}
						}
						recipYield = temp;
						try {

							// retrieving recipe name from db
							temp = "";

							boolean afterMSName = false;
							for (int i = 0; i < recipTitle.length(); i++) {
								if (i < recipTitle.length() - 1 && recipTitle.charAt(i) == '_'
										&& recipTitle.charAt(i + 1) == '_' && !afterMSName) {
									afterMSName = true;
									i++;
								}

								// only start concatenating recipe name if passed meal section name
								else if (afterMSName) {
									temp += recipTitle.charAt(i);
								}

							}
							temp = temp.replaceAll("_", " ");

							RecipeThumbnail recip = new RecipeThumbnail(temp, recipPath, recipTime, recipRate, recipYield,
									recipTitle);
							recipArr.add(recip);

						} catch (Exception e) {
							e.printStackTrace();
						}

						list.clear();
						rs2.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}

			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// formatting the recipe thumbnails

		if (count >= 3) {

			// if there are an odd amount of panels, divide total by 3 and multiply by 200
			// pixels for the height
			if (count % 3 == 0) {
				yDim = (count / 3) * 200;
			} else {

				// divide by 3 and add extra panel to house remaining thumbnails
				yDim = (count / 3 + 1) * 200;
			}
		}

		// less than 3, just use 200 pixels for height of container
		else {
			yDim = 200;
		}

		setPreferredSize(new Dimension(900, yDim));
		
		RecipeThumbnail[] arr = new RecipeThumbnail[recipArr.size()];
		// sort alphabetically
		
		for (int i = 0; i<recipArr.size(); i++) {
			arr[i] = recipArr.get(i);
		}
		
		if (RecipesUnderMS.sortSelected) {
			for (int i = 1; i<recipArr.size(); i++) {
				RecipeThumbnail var = arr[i];
				int j = i-1;
				while (j>=0 && var.getName().compareToIgnoreCase(arr[j].getName())<0) {
					arr[j+1] = arr[j];
					j--;
					
				}
				arr[j+1] = var;
			
			}
			RecipesUnderMS.sortSelected = false;
		}
		
		// add each thumbnail to container
		for (int i = 0; i<recipArr.size(); i++) {
			add(arr[i]);
		}
	}
}