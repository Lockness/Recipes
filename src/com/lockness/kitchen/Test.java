package com.lockness.kitchen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;


public class Test {


	public static void main(String[] args) {

		parseXML();
	}

	public static void parseXML() {

		String name = "", description = "", currentLine;
		int servingSize = -1, time[] = new int[3];
		TreeMap<String, Ingredient> ingredients = new TreeMap<String, Ingredient>();

		File file = new File("Recipe/test.ing");
		Scanner input;
		try {
			input = new Scanner(file);

		while (input.hasNext()) {
			currentLine = input.nextLine();
			switch (currentLine.charAt(0)) {
			case '0':
				name = currentLine.substring(2);
				break;
			case '1':
				description = currentLine.substring(2);
				break;
			case '2':
				currentLine = currentLine.substring(2);
				servingSize = Integer.parseInt(currentLine);
				break;
			case '3':
				currentLine = currentLine.substring(2);
				int nextSpace = currentLine.indexOf(' ');
				time[0] = Integer.parseInt(currentLine.substring(0, nextSpace));
				currentLine = currentLine.substring(nextSpace + 1);
				nextSpace = currentLine.indexOf(' ');
				time[1] = Integer.parseInt(currentLine.substring(0, nextSpace));
				currentLine = currentLine.substring(nextSpace + 1);
				time[2] = Integer.parseInt(currentLine);
				break;
			case '4':
				//System.out.println("CASE 4");
				break;
			default:
				System.err.println("ERROR: Error parsing .ing file. Line not labeled correctly.!");
			}
		}

		System.out.println("NAME: " + name);
		System.out.println("DESCRIP: " + description);
		System.out.println("SERVING: " + servingSize);
		System.out.println("TIME: " + time[0] + " " + time[1] + " " + time[2]);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

