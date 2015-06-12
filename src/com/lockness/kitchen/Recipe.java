package com.lockness.kitchen;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author Justin Carruthers
 * @author Ryan Tomlinson
 */
public class Recipe {

	/**
	 * Name of the recipe.
	 */
	String name;

	/**
	 * Brief description of the recipe.
	 */
	String description;

	/**
	 * A list of Ingredient Objects that contain the ingredient and it's quantity
	 */
	TreeMap<String, Ingredient> ingredients;

	/**
	 * The serving size that the given recipe creates.
	 */
	int servingSize;

	/**
	 * An array at which time[0] = preparation time, time[1] = cooking time, time[2] = total time before ready
	 */
	int time[];

	/**
	 * The detailed instructions on how to make the given recipe.
	 */
	String instructions;

	boolean favorite;

	/**
	 * Constructors
	 */

	Recipe(String name) {
		this(name, null, -1, -1, -1, null, false);
	}

	public Recipe(String name, String description, int servingSize, int prep, int cook, String instructions) {
		this(name, description, servingSize, prep, cook, instructions, false);

	}

	public Recipe(String name, String description, int servingSize, int prep, int cook, String instructions, boolean favorite) {
		this.name = name;
		this.description = description;
		this.servingSize = servingSize;
		this.time = new int[3];
		this.time[0] = prep;
		this.time[1] = cook;
		this.time[2] = this.time[0] + this.time[1];
		this.ingredients = new TreeMap<String, Ingredient>();
		this.instructions = instructions;
		this.favorite = favorite;
	}

	/**
	 *  Getters and Setters
	 */

	public String getName() {
		return this.name;
	}

	void rename (String name) {
		this.name = name;
	}

	String getDescription() {
		return this.description;
	}

	void replaceDescription(String description) {
		this.description = description;
	}

	int getServingSize() {
		return this.servingSize;
	}

	void updateServingSize(int servingSize) {
		this.servingSize = servingSize;
	}

	int getPrepTime() {
		return this.time[0];
	}

	void updatePrepTime(int prep) {
		this.time[0] = prep;
	}

	int getCookTime() {
		return this.time[1];
	}

	void updateCookTime(int cook) {
		this.time[1] = cook;
	}

	int getReadyTime() {
		return this.time[2];
	}

	void updateReadyTime(int ready) {
		this.time[2] = ready;
	}

	String getInstructions() {
		return this.instructions;
	}

	void replaceInstructions(String instructions) {
		this.instructions = instructions;
	}

	void addIngredient(String name, float quantity, String unit) {
		Ingredient ingredient = new Ingredient(name, quantity, unit);
		this.ingredients.put(name, ingredient);
	}

	public void replaceIngredientSet(TreeMap<String, Ingredient> ingredientList){
		this.ingredients = ingredientList;
	}

	boolean containsIngredient(String name) {
		return this.ingredients.containsKey(name);
	}

	public boolean isFavorite() {
		return this.favorite;
	}

	public void setFavorite(boolean favorite) {
		if (favorite != this.favorite) {
			this.favorite = favorite;
			String filename = this.name.replace(' ', '_') + ".rcp";
			File file = new File("Recipe/" + filename);
			Scanner inFile;
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("TempRecipe"), "UTF-8"))) {
				inFile = new Scanner(file);
				writer.write(inFile.nextLine() + '\n');
				writer.write(inFile.nextLine() + '\n');
				writer.write(inFile.nextLine() + '\n');
				writer.write(inFile.nextLine() + '\n');
				for (int i = 0; i < this.numberOfUniqueIngredients(); i++) {
					writer.write(inFile.nextLine() + '\n');
					writer.write(inFile.nextLine() + '\n');
					writer.write(inFile.nextLine() + '\n');
				}
				writer.write(inFile.nextLine() + '\n');
				if (favorite) {
					writer.write("8 t");
				} else {
					writer.write("8 f");
				}
				File tmpFile = new File ("TempRecipe");
				tmpFile.renameTo(file);

				inFile.close();
				writer.close();
			} catch (Exception e) {
				System.err.println("Could not write to " + filename);
			}

		}

	}

	/**
	 * Instance Methods
	 */

	int numberOfUniqueIngredients() {
		return this.ingredients.size();
	}

	void editIngredientQuantity(String name, float newQuantity) {
		Ingredient ingredient = this.ingredients.remove(name);
		ingredient.setQuantity(newQuantity);
		this.ingredients.put(name, ingredient);
	}

	@Override
	public String toString() {

		String underline = "";
		for(int i = 0; i < this.name.length(); i++) {
			underline += '-';
		}

		String returnMe = this.name + '\n' + underline + '\n' + this.description + '\n';
		returnMe = returnMe + "Serves " + this.servingSize + " people." + '\n' + "Ready in " + this.time[2] + '\n' + this.favorite;
		String ingredAsString = "";
		for (Map.Entry<String, Ingredient> ingred : this.ingredients.entrySet()) {
			ingredAsString = ingredAsString + ingred.getValue().toString() + '\n';
		}
		returnMe = returnMe + ingredAsString + this.instructions;
		return returnMe;
	}

}
