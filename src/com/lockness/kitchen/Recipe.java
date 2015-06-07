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
	
	public Recipe(String name) {
		this(name, null, -1, -1, -1, -1);
	}
	
	public Recipe(String name, String description, int servingSize, int prep, int cook, int ready) {
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
	
	public String getName() {
		return name;
	}
	
	public void rename (String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void replaceDescription(String description) {
		this.description = description;
	}
	
	public int getServingSize() {
		return servingSize;
	}
	
	public void updateServingSize(int servingSize) {
		this.servingSize = servingSize;
	}
	
	public int getPrepTime() {
		return time[0];
	}
	
	public void updatePrepTime(int prep) {
		this.time[0] = prep;
	}
	
	public int getCookTime() {
		return time[1];
	}
	
	public void updateCookTime(int cook) {
		this.time[1] = cook;
	}
	
	public int getReadyTime() {
		return time[2];
	}
	
	public void updateReadyTime(int ready) {
		this.time[2] = ready;
	}
	
	public void addIngredient(String ingredient, String quantity) {
		this.ingredients.put(ingredient, quantity);
	}

	public boolean containsIngredient(String ingredient) {
		return this.ingredients.containsKey(ingredient);
	}
	
}
