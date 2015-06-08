package com.lockness.kitchen;
import java.util.Map;
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

	/**
	 * Constructors
	 */

	Recipe(String name) {
		this(name, null, -1, -1, -1, -1, null);
	}

	public Recipe(String name, String description, int servingSize, int prep, int cook, int ready, String instructions) {
		this.name = name;
		this.description = description;
		this.servingSize = servingSize;
		this.time = new int[3];
		this.time[0] = prep;
		this.time[1] = cook;
		this.time[2] = ready;
		this.ingredients = new TreeMap<String, Ingredient>();
		this.instructions = instructions;
	}

	/**
	 *  Getters and Setters
	 */

	String getName() {
		return name;
	}

	void rename (String name) {
		this.name = name;
	}

	String getDescription() {
		return description;
	}

	void replaceDescription(String description) {
		this.description = description;
	}

	int getServingSize() {
		return servingSize;
	}

	void updateServingSize(int servingSize) {
		this.servingSize = servingSize;
	}

	int getPrepTime() {
		return time[0];
	}

	void updatePrepTime(int prep) {
		this.time[0] = prep;
	}

	int getCookTime() {
		return time[1];
	}

	void updateCookTime(int cook) {
		this.time[1] = cook;
	}

	int getReadyTime() {
		return time[2];
	}

	void updateReadyTime(int ready) {
		this.time[2] = ready;
	}

	String getInstructions() {
		return instructions;
	}

	void replaceInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	void addIngredient(String name, int quantity, String unit) {
		Ingredient ingredient = new Ingredient(name, quantity, unit);
		this.ingredients.put(name, ingredient);
	}

	public void addIngredientSet(TreeMap<String, Ingredient> ingredientList){
		this.ingredients = ingredientList;
	}
	
	boolean containsIngredient(String name) {
		return this.ingredients.containsKey(name);
	}

	/**
	 * Instance Methods
	 */

	int numberOfUniqueIngredients() {
		return this.ingredients.size();
	}
	
	void editIngredientQuantity(String name, int newQuantity) {
		Ingredient ingredient = this.ingredients.remove(name);
		ingredient.setQuantity(newQuantity);
		this.ingredients.put(name, ingredient);
	}
	
	@Override
	public String toString() {
		
		String underline = "";
		for(int i = 0; i < name.length(); i++) {
			underline += '-';
		}
		
		String returnMe = name + '\n' + underline + '\n' + description + '\n';
		returnMe = returnMe + "Serves " + servingSize + " people." + '\n' + "Ready in " + time[2] + '\n';
		String ingredAsString = "";
		for (Map.Entry<String, Ingredient> ingred : this.ingredients.entrySet()) {
			ingredAsString = ingredAsString + ingred.getValue().toString() + '\n';
		}
		returnMe = returnMe + ingredAsString + instructions;
		return returnMe;
	}

}
