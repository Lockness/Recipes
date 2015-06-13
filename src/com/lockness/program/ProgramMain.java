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

	Cookbook cookbook, favorites;

	public void programStart() {
		this.initializeProgram();
		this.welcome();
		this.userInterface();
	}

	void initializeProgram(){
		this.cookbook = new Cookbook();
		this.favorites = new Cookbook();

		File recipeFolder = new File(Recipe.folder);
		File[] listOfRecipes = recipeFolder.listFiles();

		for(int i = 0; i < listOfRecipes.length; i++) {
			Recipe recipe = InputParser.parseRCP(listOfRecipes[i].getName());
			this.cookbook.addRecipe(recipe);
			if (recipe.isFavorite()) {
				this.favorites.addRecipe(recipe);
			}
		}

	}

	void welcome() {
		System.out.println("//------------------------------\\");
		System.out.println("|| Welcome to Midterm Munchies! ||");
		System.out.println("\\------------------------------//\n");
	}

	void userInterface() {
		Scanner scanner = new Scanner(System.in);
		boolean loop = true;

		while (loop) {
			System.out.print("| ");
			String userInput = scanner.nextLine();
			if (userInput.equalsIgnoreCase("Search")) {
				this.search(scanner);
			} else if (userInput.equalsIgnoreCase("Fav") || userInput.equalsIgnoreCase("Favorites")) {
				this.favorites(scanner);
			}else if (userInput.equalsIgnoreCase("list") || userInput.equalsIgnoreCase("ls")) {
				this.list(scanner);
			} else if (userInput.equalsIgnoreCase("add")) {
				this.add(scanner);
			} else if (userInput.equalsIgnoreCase("help") || userInput.equalsIgnoreCase("h")) {
				this.options();
			} else if (userInput.equalsIgnoreCase("exit") || userInput.equalsIgnoreCase("quit")) {
				loop = false;
			} else {
				System.out.println("Please enter command. \"help\" for assistance");
			}
		}

		scanner.close();
	}

	/**
	 * Searches the recipes in the cookbook given a keyword.
	 *
	 * @param scanner
	 */
	public void search(Scanner scanner) {
		System.out.println("Enter a keyword");
		String keyword = scanner.nextLine();
		List<Recipe> foundRecipes = this.cookbook.searchRecipe(keyword);

		System.out.println("0 - Back");
		for (int i = 0; i < foundRecipes.size(); i++) {
			System.out.println((i + 1) + " - " + foundRecipes.get(i).getName());
		}
		while(true) {
			int userSelection = InputParser.safeInt(scanner);
			if (userSelection > 0 && userSelection <= foundRecipes.size()) {
				Recipe userRecipe = foundRecipes.get(userSelection - 1);
				System.out.println(userRecipe.toString());
				return;
			} else if (userSelection == 0) {
				return;
			} else {
				System.out.println("Not a valid selection. Try again.");
			}
		}
	}

	/**
	 * Lists the recipes already in the cookbook.
	 *
	 * @param scanner
	 */
	public void list(Scanner scanner) {
		List<String> recipeNamesList = this.cookbook.listRecipes();
		System.out.println("Enter a name to print it or press enter to return to the Main Menu.\n");
		String userRecipeChoiceFromList = scanner.nextLine();
		if (recipeNamesList.contains(userRecipeChoiceFromList)) {
			Recipe recipeFromName = this.cookbook.seeRecipe(userRecipeChoiceFromList);
			System.out.println(recipeFromName.toString());
		}
	}

	/**
	 * Allows user to input new Recipe.
	 *
	 * @param scanner
	 */
	public void add(Scanner scanner) {
		String recipeName = InputParser.makeRCP(scanner);
		this.cookbook.addRecipe(InputParser.parseRCP(recipeName));
		System.out.println("Added " + recipeName + '\n');
	}

	/**
	 * Enters the Favorites' Menu. This allows you to list, add, and remove your favorite recipes.
	 *
	 * @param scanner
	 */
	public void favorites(Scanner scanner) {
		System.out.println("---FAVORITES---");

		while (true) {
			System.out.println("List, Add, Remove or Back?");
			String userInput = scanner.nextLine();
			if (userInput.equalsIgnoreCase("List") || userInput.equalsIgnoreCase("ls")) {
				this.favorites.listRecipes();
			} else if (userInput.equalsIgnoreCase("add")) {
				boolean tOF = true;
				System.out.println("Which Recipe?");
				while (tOF) {
					userInput = scanner.nextLine();
					if (this.cookbook.hasRecipe(userInput)) {
						Recipe recipe = this.cookbook.seeRecipe(userInput);
						recipe.setFavorite(true);
						this.favorites.addRecipe(recipe);
						System.out.println("Added " + userInput + " to favorites");
						tOF = false;
					} else if (userInput.equals("")) {
						tOF = false;
					} else {
						System.out.println("Not a recipe. Try again or hit enter to back out.");
					}
				}
			} else if (userInput.equalsIgnoreCase("remove") || userInput.equalsIgnoreCase("rm")) {
				boolean tOF = true;
				System.out.println("Which Recipe?");
				while (tOF) {
					userInput = scanner.nextLine();
					if (this.cookbook.hasRecipe(userInput)) {
						Recipe recipe = this.cookbook.seeRecipe(userInput);
						recipe.setFavorite(false);
						this.favorites.removeRecipe(userInput);
						System.out.println("Removed " + userInput + " from favorites");
						tOF = false;
					} else if (userInput.equals("")) {
						tOF = false;
					}else {
						System.out.println("Not a recipe. Try again or hit enter to back out.");
					}
				}
			} else if (userInput.equals("") || userInput.equalsIgnoreCase("back")) {
				return;
			} else {
				System.out.println("Not a valid command");
			}
		}
	}

	/**
	 * Lists the current commands of the User Interface.
	 */
	public void options() {
		System.out.println("Options: ");
		System.out.println("   COMMAND       ACTION");
		System.out.println("  ---------------------------");
		System.out.println("  |Search  |  Find a Recipe |");
		System.out.println("  ---------------------------");
		System.out.println("  |List    |  List Recipes  |");
		System.out.println("  ---------------------------");
		System.out.println("  |Add     |  Add a Recipe  |");
		System.out.println("  ---------------------------");
		System.out.println("  |Fav     |  Open Favorites|");
		System.out.println("  ---------------------------");
		System.out.println("  |Help    |  Get Help      |");
		System.out.println("  ---------------------------");
	}

}
