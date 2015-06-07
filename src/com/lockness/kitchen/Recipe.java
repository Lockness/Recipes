package com.lockness.kitchen;
import java.util.Map;

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
	 * A map where the keys are the ingredients and the values are the quantities.
	 */
	Map<String, String> ingredients;

	/**
	 * The serving size that the given recipe creates.
	 */
	int servingSize; 

	/**
	 * An array at which time[0] = preparation time, time[1] = cooking time, time[2] = total time before ready
	 */
	int time[];

	/**
	 * Constructors
	 */

	Recipe(String name) {
		this(name, null, -1, -1, -1, -1);
	}

	Recipe(String name, String description, int servingSize, int prep, int cook, int ready) {
		this.name = name;
		this.description = description;
		this.servingSize = servingSize;
		this.time[0] = prep;
		this.time[1] = cook;
		this.time[2] = ready;
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

	void addIngredient(String ingredient, String quantity) {
		this.ingredients.put(ingredient, quantity);
	}

	boolean containsIngredient(String ingredient) {
		return this.ingredients.containsKey(ingredient);
	}

	/**
	 * Instance Methods
	 */

	int numberOfUniqueIngredients() {
		return this.ingredients.size();
	}
	
	void editIngredientQuantity(String name, String newQuantity) {
		this.ingredients.replace(name, newQuantity);
	}

}
