package com.lockness.kitchen;

import java.util.List;
import java.util.Map;
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
	 * Reports the specific recipe associated with {@code name} in {@code this}.
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
	
	/**
	 * Searches for all recipes that contain {@code name}.
	 * 
	 * @param name
	 * 				the name to be searched for in the cookbook
	 * @return a list of recipes containing {@code name}
	 * @ensures 
	 * 		if Cookbook contains any recipes with part of the searched {@code name},
	 * 		then it will return a list of all the recipes which match the above criteria
	 * 
	 * 		if Cookbook does not contain a recipe who's name contains {@code name},
	 * 		then it will return a list with an empty recipe who's title is "No matches found."
	 * 
	 */
	public List<Recipe> searchRecipe(String name){
		// List of search results to be returned
		List<Recipe> searchResults = null;
		// Iterates through recipeList entries one by one
		for (Map.Entry<String, Recipe> recipe : this.recipeList.entrySet()) {
			// If the key contains or is equal to the "name" being searched
			// Add it to the list of search results
			if (recipe.getKey().contains(name) | recipe.getKey().equals(name)) {
				searchResults.add(recipe.getValue());
			}
		}
		
		//TODO - Idk if the if statement below is necessary, because we could just say
		// in main that if searchResults returns empty then display a message to the 
		// user to say that there were no matches.
		
		
		// In case of no matches, this adds a recipe with title "No matches found."
		// so that it can be displayed to the user
		if (searchResults.isEmpty()) {
			Recipe nameNotFound = null;
			nameNotFound.name = "No matches found.";
			searchResults.add(nameNotFound);
		}
		// Returns list of recipes containing the search name
		return searchResults;
	}
}
