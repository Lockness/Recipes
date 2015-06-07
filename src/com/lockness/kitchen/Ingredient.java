package com.lockness.kitchen;

public class Ingredient {

	String name;
	int quantity;
	String unit;
	
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
	
	
}
