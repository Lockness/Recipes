package com.lockness.program;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import com.lockness.kitchen.Cookbook;
import com.lockness.kitchen.Ingredient;
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
		String ingredientName = "", ingredientUnit = "";
		float ingredientQuantity = 0.00f;

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
			} else if (userInput.equalsIgnoreCase("edit")) {
				this.edit(scanner);
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

	public void edit(Scanner scanner) {
		boolean tOF = true;

		System.out.println("---EDITING---");
		System.out.println("Enter name of recipe to edit.");

		while (true) {
			String userInput = scanner.nextLine();
			if (this.cookbook.hasRecipe(userInput)) {
				Recipe recipe = this.cookbook.seeRecipe(userInput);
				System.out.println("What to edit?");
				System.out.println("Name, Instructions, Ingredients, Cook Time, Prep Time or Back?");
				while (tOF) {
					userInput = scanner.nextLine();
					if (userInput.equalsIgnoreCase("ingredients") || userInput.equalsIgnoreCase("ing")) {
						System.out.println("Enter number of ingredients");
						int numOfIngredients = InputParser.safeInt(scanner);
						Ingredient[] listOfIngredients = new Ingredient[numOfIngredients];
						for (int i = 0; i < numOfIngredients; i++) {
							System.out.println("Enter name of ingredient");
							String ingredientName = scanner.nextLine();
							System.out.println("Enter how many " + ingredientName + " are needed");
							float ingredientQuantity = InputParser.safeInt(scanner);
							System.out.println("Enter the unit");
							String ingredientUnit = scanner.nextLine();
							listOfIngredients[i] = new Ingredient(ingredientName, ingredientQuantity, ingredientUnit);
						}
						recipe.replaceIngredientSet(listOfIngredients);
						tOF = false;
					} else if (userInput.equalsIgnoreCase("name") || userInput.equalsIgnoreCase("n")) {
						System.out.println("Enter the name");
						userInput= scanner.nextLine();
						if (userInput.length() != 0 && !userInput.equalsIgnoreCase(recipe.getName())) {
							String oldName = recipe.getName();
							recipe.rename(userInput);
							this.cookbook.editName(oldName, userInput);
							tOF = false;
						}
					} else if (userInput.equalsIgnoreCase("Prep Time") || userInput.equalsIgnoreCase("pt")) {
						System.out.println("Enter the Prep Time");
						int prepTime = InputParser.safeInt(scanner);
						if (prepTime != -1) {
							recipe.updateCookAndPrepTime(prepTime, true);
							tOF = false;
						}
					} else if (userInput.equalsIgnoreCase("Cook Time") || userInput.equalsIgnoreCase("ct")) {
						System.out.println("Enter the Cook Time");
						int cookTime = InputParser.safeInt(scanner);
						if (cookTime != -1) {
							recipe.updateCookAndPrepTime(cookTime, false);
							tOF = false;
						}
					} else if (userInput.equalsIgnoreCase("Instructions") || userInput.equalsIgnoreCase("ins")) {
						System.out.println("Enter the instructions");
						String instructions = scanner.nextLine();
						recipe.replaceInstructions(instructions);
						tOF = false;
					} else if (userInput.equals("") || userInput.equalsIgnoreCase("back")) {
						return;
					} else {
						System.out.println("Not a valid command");
					}
				}

				return;
			} else if (userInput.length() == 0) {
				return;
			} else {
				System.out.println("Not a recipe. Try again or hit enter to back out.");
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
		System.out.println("  |Edit    |  Edit a Recipe |");
		System.out.println("  ---------------------------");
		System.out.println("  |Help    |  Get Help      |");
		System.out.println("  ---------------------------");
	}

}
