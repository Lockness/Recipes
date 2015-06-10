package com.lockness.kitchen;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

import com.lockness.util.InputParser;


public class Test {


	public static void main(String[] args) {
		String filename = makeRCP();
		System.out.println(filename);
	}

	public static int safeInt(Scanner input) {
		String userInput = input.nextLine();
		while (true) {
			if(tryParseInt(userInput)) {
				return Integer.parseInt(userInput);
			} else {
				System.out.println("Error. Not an int.");
				userInput = input.nextLine();
			}
		}
	}

	static boolean tryParseInt(String value) {  
		try {  
			Integer.parseInt(value);  
			return true;  
		} catch(NumberFormatException nfe) {  
			return false;  
		}  
	}

	public static String makeRCP() {
		//Objects and variables needed
		Scanner input = new Scanner(System.in);
		String name = "", description = "", ingredientName = "", ingredientUnit = "", instructions = "", currentLine;
		int servingSize = -1, time[] = new int[3], numOfIngredients = 0;
		float ingredientQuantity = 0.00f;

		//Get information
		System.out.println("Enter the Recipe name");
		name = input.nextLine();
		String filename = name.replace(' ', '_') + ".rcp";
		System.out.println("Enter Recipe description");
		description = input.nextLine();
		System.out.println("Enter serving size");
		servingSize = safeInt(input);
		System.out.println("Enter prep time");
		time[0] = safeInt(input);
		System.out.println("Enter cook time");
		time[1] = safeInt(input);
		time[2] = time[0] + time[1];
		System.out.println("Enter number of ingredients");
		numOfIngredients = safeInt(input);
		Ingredient[] listOfIngredients = new Ingredient[numOfIngredients];
		for (int i = 0; i < numOfIngredients; i++) {
			System.out.println("Enter name of ingredient");
			ingredientName = input.nextLine();
			System.out.println("Enter how many " + ingredientName + " are needed");
				ingredientQuantity = safeInt(input);
			System.out.println("Enter the unit");
			ingredientUnit = input.nextLine();
			listOfIngredients[i] = new Ingredient(ingredientName, ingredientQuantity, ingredientUnit);
		}
		System.out.println("Enter the instructions");
		instructions = input.nextLine();
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("Recipe/" + filename), "UTF-8"))) {
			writer.write("0 " + name + '\n');
			writer.write("1 " + description + '\n');
			writer.write("2 " + servingSize + '\n');
			writer.write("3 " + time[0] + " " + time[1] + " " + time[2] + "\n");
			for (int i = 0; i < numOfIngredients; i++) {
				writer.write("4 " + listOfIngredients[i].getName() + '\n');
				writer.write("5 " + listOfIngredients[i].getQuantity() + '\n');
				writer.write("6 " + listOfIngredients[i].getUnit() + '\n');
			}
			writer.write("7 " + instructions);
		} catch (Exception e) {
			System.err.println("Could not write to " + filename);
		}

		input.close();
		return filename;
	}
}

