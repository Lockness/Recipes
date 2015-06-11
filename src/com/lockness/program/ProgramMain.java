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
			this.cookbook.addRecipe(recipe);
		}

	}

	void welcome() {
		System.out.println("//--------------------------\\\\");
		System.out.println("|| Welcome to CollegeCooks! ||");
		System.out.println("\\\\--------------------------//\n");
	}

	void userInterface() {
		Scanner scanner = new Scanner(System.in);
		boolean loop = true;

		while (loop) {
			System.out.print("| ");
			String userInput = scanner.nextLine();
			if (userInput.equalsIgnoreCase("Search")) {
				System.out.println("Enter a keyword");
				String keyword = scanner.nextLine();
				List<Recipe> foundRecipes = this.cookbook.searchRecipe(keyword);

				System.out.println("0 - Back");
				for (int i = 0; i < foundRecipes.size(); i++) {
					System.out.println((i + 1) + " - " + foundRecipes.get(i).getName());
				}
				int userSelection = 0;
				while (true) {
					try {
						userSelection = scanner.nextInt();
						scanner.nextLine();
						break;
					} catch (Exception e) {
						System.out.println("Please enter an integer.");
					}
				}
				if (userSelection == 0) {
					//insert possible boolean
				} else {
					while(true) {
						if (userSelection > 0 && userSelection <= foundRecipes.size()) {
							Recipe userRecipe = foundRecipes.get(userSelection - 1);
							System.out.println(userRecipe.toString());
							break;
						} else {
							System.out.println("Not a valid selection. Try again.");
							userSelection = scanner.nextInt(); //Find safer way to get int from user.
							scanner.nextLine();
						}
					}
				}
			} else if (userInput.equalsIgnoreCase("list") || userInput.equalsIgnoreCase("ls")) {
				List<String> recipeNamesList = this.cookbook.listRecipes();
				System.out.println("Enter a name to print it or press enter to return to the Main Menu.\n");
				String userRecipeChoiceFromList = scanner.nextLine();
				if (recipeNamesList.contains(userRecipeChoiceFromList)) {
					Recipe recipeFromName = this.cookbook.seeRecipe(userRecipeChoiceFromList);
					System.out.println(recipeFromName.toString());
				}
			} else if (userInput.equalsIgnoreCase("add")) {
				String recipeName = InputParser.makeRCP(scanner);
				this.cookbook.addRecipe(InputParser.parseRCP(recipeName));
				System.out.println("Added " + recipeName + '\n');
			} else if (userInput.equalsIgnoreCase("help") || userInput.equalsIgnoreCase("h")) {
				System.out.println("Options: ");
				System.out.println("   COMMAND       ACTION");
				System.out.println("  --------------------------");
				System.out.println("  |Search  |  Find a Recipe|");
				System.out.println("  --------------------------");
				System.out.println("  |List    |  List Recipes |");
				System.out.println("  --------------------------");
				System.out.println("  |Help    |  Get Help     |");
				System.out.println("  --------------------------");
			} else if (userInput.equalsIgnoreCase("exit")) {
				loop = false;
			} else {
				System.out.println("Please enter command. \"help\" for assistance");
			}
		}

		scanner.close();
	}

}
