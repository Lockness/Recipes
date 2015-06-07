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

	//---------------------------------------//
	//---------Methods for Cookbook----------//
	//---------------------------------------//

	/**
	 * Adds the {@code recipe} with the title {@code name}.
	 * 
	 * @param name
	 * 				the name of the recipe to be added
	 * @param recipe
	 * 				the recipe to be added
	 * @updates this
	 * @requires name is not in DOMAIN(this)
	 * @ensures this = #this union {(name, recipe)}
	 */
	public void addRecipe(String name, String description, int servingSize, int prep, int cook, int ready){
		Recipe newRecipe = new Recipe(name, description, servingSize, prep, cook, ready); 
		this.recipeList.put(name, newRecipe);
	}

	/**
	 * Removes the recipe who's title is {@code  name} and returns it.
	 * 
	 * @param name
	 * 	The name of the recipe to be returned
	 * @return the recipe to be removed
	 * @requires name is in DOMAIN({@code this}
	 * @ensures
	 * remove.name = name and
	 * remove is in #this and
	 * this = #this \ {remove}
	 */
	public Recipe removeRecipe(String name){
		return this.recipeList.remove(name);	
	}

	/**
	 * Reports the recipe associated with {@code name} in {@code this}.
	 *
	 * @param name
	 * 				the name whose associated recipe is to be reported
	 * @return the recipe associated with {@code name}
	 * @requires {@code name } is in DOMAIN(this)
	 * 
	 */
	public Recipe seeRecipe(String name){
		return this.recipeList.get(name);
	}
}
