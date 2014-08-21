package com.example.beacon_dot;

import java.util.ArrayList;
import java.util.List;
import com.example.beacon_dot.R;

public class FactData {
	
	private List<Fact> Facts = new ArrayList();

	/**
	 * @return the facts
	 */
	public List<Fact> getFacts() {
		return Facts;
	}
	// fact object : String factTitle, int image, String factDetail
	public FactData() {
		addItem(new Fact("Fact_adobe", R.drawable.fact_adobe, "Fact adobe detail"));
		addItem(new Fact("Fact_airplain", R.drawable.fact_airplain, "Fact airplain detail"));
		addItem(new Fact("Fact_award", R.drawable.fact_award, "Fact award detail"));
		addItem(new Fact("Fact_awards", R.drawable.fact_awards, "Fact awards detail"));
		addItem(new Fact("Fact_logo", R.drawable.fact_logo, "Fact logo detail"));
		addItem(new Fact("Fact_summer", R.drawable.fact_summer, "Fact summer detail"));
		addItem(new Fact("Fact_winter", R.drawable.fact_winter, "Fact winter detail"));
	}

	private void addItem(Fact item) {
		Facts.add(item);
	}
	
}
