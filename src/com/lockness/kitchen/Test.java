package com.lockness.kitchen;

import java.util.Scanner;

import com.lockness.util.InputParser;


public class Test {


	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Input Number");
		int x = safeInt(input);
		System.out.println(x);
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
}

