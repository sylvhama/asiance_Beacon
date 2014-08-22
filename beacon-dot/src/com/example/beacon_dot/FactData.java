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
		addItem(new Fact(0, "Fact_adobe", R.drawable.fact_adobe, 
				"As a cross-cultural company, we work in English, French, Korean, Japanese, and Chinese. Our clients can therefore rest assured that their projects will not be affected by the language and culture barriers all too often associated with doing business in Asia. Asiance will see your project through from inception to completion.", 
				"Fact quiz 1", "asiance"));
		addItem(new Fact(1, "Fact_airplain", R.drawable.fact_airplain, 
				"As a cross-cultural company, we work in English, French, Korean, Japanese, and Chinese. Our clients can therefore rest assured that their projects will not be affected by the language and culture barriers all too often associated with doing business in Asia. Asiance will see your project through from inception to completion.", 
				"Fact quiz 2", "asiance"));
		addItem(new Fact(2, "Fact_award", R.drawable.fact_award, 
				"As a cross-cultural company, we work in English, French, Korean, Japanese, and Chinese. Our clients can therefore rest assured that their projects will not be affected by the language and culture barriers all too often associated with doing business in Asia. Asiance will see your project through from inception to completion.", 
				"Fact quiz 3", "asiance"));
		addItem(new Fact(3, "Fact_awards", R.drawable.fact_awards, 
				"As a cross-cultural company, we work in English, French, Korean, Japanese, and Chinese. Our clients can therefore rest assured that their projects will not be affected by the language and culture barriers all too often associated with doing business in Asia. Asiance will see your project through from inception to completion.", 
				"Fact quiz 4", "asiance"));
		addItem(new Fact(4, "Fact_super", R.drawable.fact_super, 
				"As a cross-cultural company, we work in English, French, Korean, Japanese, and Chinese. Our clients can therefore rest assured that their projects will not be affected by the language and culture barriers all too often associated with doing business in Asia. Asiance will see your project through from inception to completion.", 
				"Fact quiz 5", "asiance"));
		addItem(new Fact(5, "Fact_summer", R.drawable.fact_summer, 
				"As a cross-cultural company, we work in English, French, Korean, Japanese, and Chinese. Our clients can therefore rest assured that their projects will not be affected by the language and culture barriers all too often associated with doing business in Asia. Asiance will see your project through from inception to completion.", 
				"Fact quiz 6", "asiance"));
//		addItem(new Fact(6, "Fact_winter", R.drawable.fact_winter, "Fact winter detail", ""));
	}

	private void addItem(Fact item) {
		Facts.add(item);
	}
	
}
