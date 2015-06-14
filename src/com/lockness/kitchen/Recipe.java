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

	public static String folder = "Recipe/";

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

	public void rename (String name) {
		String filename = this.name.replace(' ', '_') + ".rcp";
		File file = new File(folder + filename);
		Scanner inFile;
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("TempRecipe"), "UTF-8"))) {
			this.name = name;
			inFile = new Scanner(file);
			writer.write("0 " + name + '\n');
			inFile.nextLine();
			writer.write(inFile.nextLine() + '\n');
			writer.write(inFile.nextLine() + '\n');
			writer.write(inFile.nextLine() + '\n');
			for (int i = 0; i < this.numberOfUniqueIngredients(); i++) {
				writer.write(inFile.nextLine() + '\n');
				writer.write(inFile.nextLine() + '\n');
				writer.write(inFile.nextLine() + '\n');
			}
			writer.write(inFile.nextLine() + '\n');
			writer.write(inFile.nextLine() + '\n');
			File tmpFile = new File ("TempRecipe");
			file.delete();
			file = new File(folder + name.replace(' ', '_') + ".rcp");
			tmpFile.renameTo(file);

			inFile.close();
			writer.close();
		} catch (Exception e) {
			System.err.println("Could not write to " + filename);
		}
	}

	public String getDescription() {
		return this.description;
	}

	public void replaceDescription(String description) {
		this.description = description;
	}

	public int getServingSize() {
		return this.servingSize;
	}

	public void updateServingSize(int servingSize) {
		this.servingSize = servingSize;
	}

	public int getPrepTime() {
		return this.time[0];
	}

	public int getCookTime() {
		return this.time[1];
	}

	public void updateCookAndPrepTime(int time, boolean prep) {
		if (prep) {
			this.time[0] = time;
		} else {
			this.time[1] = time;
		}
		this.time[2] = this.time[0] + this.time[1];
		String filename = this.name.replace(' ', '_') + ".rcp";
		File file = new File(folder + filename);
		Scanner inFile;
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("TempRecipe"), "UTF-8"))) {
			inFile = new Scanner(file);
			writer.write(inFile.nextLine() + '\n');
			writer.write(inFile.nextLine() + '\n');
			writer.write(inFile.nextLine() + '\n');
			writer.write("3 " + this.time[0] + " " + this.time[1] + '\n');
			inFile.nextLine();
			for (int i = 0; i < this.numberOfUniqueIngredients(); i++) {
				writer.write(inFile.nextLine() + '\n');
				writer.write(inFile.nextLine() + '\n');
				writer.write(inFile.nextLine() + '\n');
			}
			writer.write(inFile.nextLine() + '\n');
			writer.write(inFile.nextLine() + '\n');
			File tmpFile = new File ("TempRecipe");
			tmpFile.renameTo(file);

			inFile.close();
			writer.close();
		} catch (Exception e) {
			System.err.println("Could not write to " + filename);
		}
	}

	public int getReadyTime() {
		return this.time[2];
	}

	public String getInstructions() {
		return this.instructions;
	}

	public void replaceInstructions(String instructions) {
		if (instructions.length() != 0) {
			this.instructions = instructions;
			String filename = this.name.replace(' ', '_') + ".rcp";
			File file = new File(folder + filename);
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
				writer.write("7 " + instructions + '\n');
				inFile.nextLine();
				writer.write(inFile.nextLine() + '\n');
				File tmpFile = new File ("TempRecipe");
				tmpFile.renameTo(file);

				inFile.close();
				writer.close();
			} catch (Exception e) {
				System.err.println("Could not write to " + filename);
			}
		}
	}

	public void addIngredient(String name, float quantity, String unit) {
		Ingredient ingredient = new Ingredient(name, quantity, unit);
		this.ingredients.put(name, ingredient);
	}

	public void replaceIngredientSet(Ingredient[] listOfIngredients){
		int numberOfOldIng = this.numberOfUniqueIngredients();
		if (this.ingredients.size() != 0) {
			String filename = this.name.replace(' ', '_') + ".rcp";
			File file = new File(folder + filename);
			Scanner inFile;
			TreeMap<String, Ingredient> ingredients = new TreeMap<String, Ingredient>();
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("TempRecipe"), "UTF-8"))) {
				inFile = new Scanner(file);
				writer.write(inFile.nextLine() + '\n');
				writer.write(inFile.nextLine() + '\n');
				writer.write(inFile.nextLine() + '\n');
				writer.write(inFile.nextLine() + '\n');
				for (int i = 0; i < numberOfOldIng; i++) {
					inFile.nextLine();
					inFile.nextLine();
					inFile.nextLine();
				}
				for (int i = 0; i < listOfIngredients.length; i++) {
					writer.write("4 " + listOfIngredients[i].getName() + '\n');
					writer.write("5 " + listOfIngredients[i].getQuantity() + '\n');
					writer.write("6 " + listOfIngredients[i].getUnit() + '\n');
					ingredients.put(listOfIngredients[i].getName(), listOfIngredients[i]);
				}
				writer.write(inFile.nextLine() + '\n');
				writer.write(inFile.nextLine() + '\n');
				File tmpFile = new File ("TempRecipe");
				tmpFile.renameTo(file);
				this.ingredients = ingredients;

				inFile.close();
				writer.close();
			} catch (Exception e) {
				System.err.println("Could not write to " + filename);
			}
		}
	}

	public void replaceIngredientSet(TreeMap<String, Ingredient> ingredients){
		this.ingredients = ingredients;
	}

	public boolean containsIngredient(String name) {
		return this.ingredients.containsKey(name);
	}

	public boolean isFavorite() {
		return this.favorite;
	}

	public void setFavorite(boolean favorite) {
		if (favorite != this.favorite) {
			this.favorite = favorite;
			String filename = this.name.replace(' ', '_') + ".rcp";
			File file = new File(folder + filename);
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

	public int numberOfUniqueIngredients() {
		return this.ingredients.size();
	}

	public void editIngredientQuantity(String name, float newQuantity) {
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
		returnMe = returnMe + "Serves " + this.servingSize + " people." + '\n' + "Ready in " + this.time[2] + '\n';
		String ingredAsString = "";
		for (Map.Entry<String, Ingredient> ingred : this.ingredients.entrySet()) {
			ingredAsString = ingredAsString + ingred.getValue().toString() + '\n';
		}
		returnMe = returnMe + ingredAsString + this.instructions;
		return returnMe;
	}

}
