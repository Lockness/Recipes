package com.lockness.kitchen;

/**
 * @author Justin Carruthers
 * @author Ryan Tomlinson
 */
public class Ingredient {

	private String name;
	private int quantity;
	private String unit;
	
	public Ingredient(String name, int quantity, String unit) {
		this.name = name;
		this.quantity = quantity;
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	public void renameIngredient(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void changeUnit(String unit) {
		this.unit = unit;
	}
	
	@Override
	public String toString(){
		String returnMe = this.name + " " + this.quantity + " " + this.unit;
		return returnMe;
	}
	
}
