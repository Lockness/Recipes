package com.lockness.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;
import java.util.TreeMap;

import com.lockness.kitchen.Ingredient;
import com.lockness.kitchen.Recipe;

/**
 * @author Justin Carruthers
 */
public class InputParser {

	/**
	 * Private constructor to stop possible initialization.
	 */
	private InputParser() {}

	/**
	 * Turns a '.rcp' file into a Recipe, and returns the it. This only
	 * needs the filename of the .rcp file stored in the Recipe/ folder.
	 *
	 * @param filename
	 * 		Name of file in Recipe/ folder to be parsed
	 * @return
	 * 		The corresponding Recipe object from the file
	 *
	 */
	public static Recipe parseRCP(String filename) {
		String name = "", description = "", ingredientName = "", ingredientUnit = "", instruction = "", currentLine;
		int servingSize = -1, time[] = new int[3];
		float ingredientQuantity = 0.00f;
		boolean isFavorite = false;
		TreeMap<String, Ingredient> ingredients = new TreeMap<String, Ingredient>();
		File file = new File(Recipe.folder + filename);
		Scanner input;
		try {
			input = new Scanner(file);

			while (input.hasNext()) {
				currentLine = input.nextLine();
				switch (currentLine.charAt(0)) {
				case '0':
					name = currentLine.substring(2);
					break;
				case '1':
					description = currentLine.substring(2);
					break;
				case '2':
					currentLine = currentLine.substring(2);
					servingSize = Integer.parseInt(currentLine);
					break;
				case '3':
					currentLine = currentLine.substring(2);
					int nextSpace = currentLine.indexOf(' ');
					time[0] = Integer.parseInt(currentLine.substring(0, nextSpace));
					currentLine = currentLine.substring(nextSpace + 1);
					time[1] = Integer.parseInt(currentLine);
					time[2] = time[0] + time[1];
					break;
				case '4':
					ingredientName = currentLine.substring(2);
					break;
				case '5':
					currentLine = currentLine.substring(2);
					ingredientQuantity = Float.parseFloat(currentLine);
					break;
				case '6':
					ingredientUnit = currentLine.substring(2);
					Ingredient newIngredient = new Ingredient(ingredientName, ingredientQuantity, ingredientUnit);
					ingredients.put(ingredientName, newIngredient);
					break;
				case '7':
					instruction = currentLine.substring(2);
					break;
				case '8' :
					currentLine = currentLine.substring(2);
					if (currentLine.charAt(0) == 't') {
						isFavorite = true;
					} else {
						isFavorite = false;
					}
					break;
				default:
					System.err.println("ERROR: Error parsing " + filename + " Line not labeled correctly.!");
					break;
				}
			}

			Recipe newRecipe = new Recipe(name, description, servingSize, time[0], time[1], instruction, isFavorite);
			newRecipe.replaceIngredientSet(ingredients);

			input.close();
			return newRecipe;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String makeRCP(Scanner input) {
		//Objects and variables needed
		String name = "", description = "", ingredientName = "", ingredientUnit = "", instructions = "", filename = "";
		int servingSize = -1, time[] = new int[3], numOfIngredients = 0;
		float ingredientQuantity = 0.00f;
		boolean loop = true;

		//Get information
		System.out.println("Enter the Recipe name");
		while (loop) {
			name = input.nextLine();
			filename = name.replace(' ', '_') + ".rcp";

			//Check if file exists
			File file = new File(Recipe.folder + filename);
			if (file.exists()) {
				System.out.println("Sorry, that name is already taken. Please choose another.");
			} else {
				loop = false;
			}
		}
		System.out.println("Enter Recipe description");
		description = input.nextLine();
		System.out.println("Enter serving size");
		servingSize = safeInt(input);
		System.out.println("Enter prep time");
		time[0] = safeInt(input);
		System.out.println("Enter cook time");
		time[1] = safeInt(input);
		time[2] = time[0] + time[1];
		System.out.println("Enter number of ingredients");
		numOfIngredients = safeInt(input);
		Ingredient[] listOfIngredients = new Ingredient[numOfIngredients];
		for (int i = 0; i < numOfIngredients; i++) {
			System.out.println("Enter name of ingredient");
			ingredientName = input.nextLine();
			System.out.println("Enter how many " + ingredientName + " are needed");
			ingredientQuantity = safeInt(input);
			System.out.println("Enter the unit");
			ingredientUnit = input.nextLine();
			listOfIngredients[i] = new Ingredient(ingredientName, ingredientQuantity, ingredientUnit);
		}
		System.out.println("Enter the instructions");
		instructions = input.nextLine();

		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(Recipe.folder + filename), "UTF-8"))) {
			writer.write("0 " + name + '\n');
			writer.write("1 " + description + '\n');
			writer.write("2 " + servingSize + '\n');
			writer.write("3 " + time[0] + " " + time[1] + "\n");
			for (int i = 0; i < numOfIngredients; i++) {
				writer.write("4 " + listOfIngredients[i].getName() + '\n');
				writer.write("5 " + listOfIngredients[i].getQuantity() + '\n');
				writer.write("6 " + listOfIngredients[i].getUnit() + '\n');
			}
			writer.write("7 " + instructions + '\n');
			writer.write("8 f");
			writer.close();
		} catch (Exception e) {
			System.err.println("Could not write to " + filename);
		}

		return filename;
	}


	public static int safeInt(Scanner input) {
		String userInput = input.nextLine();
		while (true) {
			if(tryParseInt(userInput)) {
				return Integer.parseInt(userInput);
			} else {
				System.out.println("Error. Not an int.");
				userInput = input.nextLine();
			}
		}
	}

	private static boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch(NumberFormatException nfe) {
			return false;
		}
	}

}
