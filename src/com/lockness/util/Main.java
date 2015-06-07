package com.lockness.util;

import java.io.File;

import com.lockness.kitchen.Cookbook;
import com.lockness.kitchen.Recipe;

/**
 * @author Justin Carruthers
 * @author Ryan Tomlinson
 */
public class Main {

	static Cookbook cookbook; 
	
	private Main() {
		// Private so no possible initialization. 
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		initializeProgram();
		System.out.println(cookbook.toString());
		System.out.println("test");

	}

	
	public static void initializeProgram(){
		File recipeFolder = new File("Recipe");
		File[] listOfRecipes = recipeFolder.listFiles();
		cookbook = new Cookbook();
		
		for(int i = 0; i < listOfRecipes.length; i++) {
			Recipe recipe = InputParser.parseRCP(listOfRecipes[i].getName());
			cookbook.addRecipe(recipe);
		}
	}
	
}
