package com.lockness.kitchen;

import java.util.TreeMap;

public class Cookbook {

	/**
	 * An alphabetically-ordered map or recipes.
	 */
	TreeMap<String, Recipe> recipeList;

	/**
	 * No-argument constructor.
	 */
	public Cookbook(){
		this.recipeList = null;
	}
	
	/**
	 * Adds a recipe to the cookbook.
	 * @param name
	 * 	The name of the recipe
	 * @param recipe
	 * 	The complete recipe itself
	 */
	public void addRecipe(String name, String description, int servingSize, int prep, int cook, int ready){
                Recipe newRecipe = new Recipe(name, description, servingSize, prep, cook, ready); 
		this.recipeList.put(name, newRecipe);
	}
}
