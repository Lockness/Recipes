package com.lockness.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;
import java.util.TreeMap;

import com.lockness.kitchen.Ingredient;
import com.lockness.kitchen.Recipe;

/**
 * @author Justin Carruthers
 * @author Ryan Tomlinson
 */
public class InputParser {

	/**
	 * Private constructor to stop possible initialization. 
	 */
	private InputParser() { }

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
		TreeMap<String, Ingredient> ingredients = new TreeMap<String, Ingredient>();
		File file = new File("Recipe/" + filename);
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
					nextSpace = currentLine.indexOf(' ');
					time[1] = Integer.parseInt(currentLine.substring(0, nextSpace));
					currentLine = currentLine.substring(nextSpace + 1);
					time[2] = Integer.parseInt(currentLine);
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
				default:
					System.err.println("ERROR: Error parsing " + filename + " Line not labeled correctly.!");
					break;
				}
			}

			Recipe newRecipe = new Recipe(name, description, servingSize, time[0], time[1], time[2], instruction);
			newRecipe.replaceIngredientSet(ingredients);

			input.close();
			return newRecipe;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
