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
	public void addRecipe(String name, Recipe recipe){
		this.recipeList.put(name, recipe);
	}
}
