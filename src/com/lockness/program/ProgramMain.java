package com.lockness.program;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import com.lockness.kitchen.Cookbook;
import com.lockness.kitchen.Recipe;
import com.lockness.util.InputParser;

/**
 * @author Justin Carruthers
 * @author Ryan Tomlinson
 */
public class ProgramMain {

	Cookbook cookbook;

	public void programStart() {
		this.initializeProgram();
		this.welcome();
		this.userInterface();
	}
	
	void initializeProgram(){
		this.cookbook = new Cookbook();

		File recipeFolder = new File("Recipe");
		File[] listOfRecipes = recipeFolder.listFiles();

		for(int i = 0; i < listOfRecipes.length; i++) {
			Recipe recipe = InputParser.parseRCP(listOfRecipes[i].getName());
			cookbook.addRecipe(recipe);
		}

	}
	
	void welcome() {
		System.out.println("//--------------------------\\");
		System.out.println("|| Welcome to CollegeCooks! ||");
		System.out.println("\\--------------------------//");
		System.out.println("\n\nWhat would you like to do? (\"help\" for assistance)");
	}

	void userInterface() {
		Scanner scanner = new Scanner(System.in);
		boolean loop = true;

		while (loop) {
			String userInput = scanner.nextLine();
			if (userInput.equalsIgnoreCase("Search")) {
				System.out.println("Enter a keyword");
				String keyword = scanner.nextLine();
				List<Recipe> foundRecipes = cookbook.searchRecipe(keyword);

				System.out.println("0 - Back");
				for (int i = 0; i < foundRecipes.size(); i++) {
					System.out.println((i + 1) + " - " + foundRecipes.get(i).getName());
				}
				int userSelection = scanner.nextInt();
				scanner.nextLine();
				if (userSelection == 0) {
					//insert possible boolean
				} else {
					if (userSelection > 0 && userSelection <= foundRecipes.size()) {
					Recipe userRecipe = foundRecipes.get(userSelection - 1);
					System.out.println(userRecipe.toString());
					} else {
						System.out.println("Not a valid selection.");
					}
				}
			} else if (userInput.equalsIgnoreCase("help") || userInput.equalsIgnoreCase("h")) {
				System.out.println("Options: ");
				System.out.println("   Search\tFind a Recipe");
				System.out.println("   help\tGet Help");
				System.out.println("-------------------\n");
			} else if (userInput.equalsIgnoreCase("exit")) {
				loop = false;
			} else {
				System.out.println("Please enter command. \"help\" for assistance");
			}
		}
	}

}
