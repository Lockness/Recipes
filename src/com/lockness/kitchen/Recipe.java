package com.lockness.kitchen;
import java.util.Map;

public class Recipe {
	
	
	String name;
	String description;
	Map<String, String> ingredients;
	int servingSize; 
	int time[];
	
	/**
	 * Constructors
	 */
	
	public Recipe() {
		this(null, null, -1, -1, -1, -1);
	}
	
	public Recipe(String name) {
		this(name, null, -1, -1, -1, -1);
	}
	
	public Recipe(String name, String description) {
		this(name, description, -1, -1, -1, -1);
	}
	
	public Recipe(String name, String description, int ready) {
		this(name, description, -1, -1, -1, ready);
	}
	
	public Recipe(String name, String description, int servingSize, int ready) {
		this(name, description, servingSize, -1, -1, ready);
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
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getServingSize() {
		return servingSize;
	}
	
	public void setServingSize(int servingSize) {
		this.servingSize = servingSize;
	}
	
	public int getPrepTime() {
		return time[0];
	}
	
	public void setPrepTime(int prep) {
		this.time[0] = prep;
	}
	
	public int getCookTime() {
		return time[1];
	}
	
	public void setCookTime(int cook) {
		this.time[1] = cook;
	}
	
	public int getReadyTime() {
		return time[2];
	}
	
	public void setReadyTime(int ready) {
		this.time[2] = ready;
	}
	
	public void addIngredient(String ingredient, String quantity) {
		this.ingredients.put(ingredient, quantity);
	}

	public boolean containsIngredient(String ingredient) {
		return this.ingredients.containsKey(ingredient);
	}
	
}
