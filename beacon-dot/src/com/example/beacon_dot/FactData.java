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
	// fact object : String factTitle, int image, String factDetail, String factQuiz, String factQuizAnswer
	public FactData() {
		addItem(new Fact(0, "Babyfoot", R.drawable.fact_adobe, 
				"What was the very first project of Asiance for a main Brand since 2004?", 
				"Veolia", "Veolia", "Dior", false));
		addItem(new Fact(1, "Photo Wall", R.drawable.fact_airplain, 
				"What means Asiance since 2004?", 
				"Asia + Alliance", "Asia + Maintenance", "Asia + Alliance", false));
		addItem(new Fact(2, "Awards", R.drawable.fact_award, 
				"What was the biggest project ever developed by Asiance financially speaking since 2004?", 
				"Amore Pacific", "Amore Pacific", "Lacoste", false));
		/*addItem(new Fact(3, "Fact_awards", R.drawable.fact_awards, 
				"As a cross-cultural company, we work in English, French, Korean, Japanese, and Chinese. Our clients can therefore rest assured that their projects will not be affected by the language and culture barriers all too often associated with doing business in Asia. Asiance will see your project through from inception to completion.", 
				"Fact quiz 4", "asiance", false));
		addItem(new Fact(4, "Fact_super", R.drawable.fact_super, 
				"As a cross-cultural company, we work in English, French, Korean, Japanese, and Chinese. Our clients can therefore rest assured that their projects will not be affected by the language and culture barriers all too often associated with doing business in Asia. Asiance will see your project through from inception to completion.", 
				"Fact quiz 5", "asiance", false));
		addItem(new Fact(5, "Fact_summer", R.drawable.fact_summer, 
				"As a cross-cultural company, we work in English, French, Korean, Japanese, and Chinese. Our clients can therefore rest assured that their projects will not be affected by the language and culture barriers all too often associated with doing business in Asia. Asiance will see your project through from inception to completion.", 
				"Fact quiz 6", "asiance", false));*/
//		addItem(new Fact(6, "Fact_winter", R.drawable.fact_winter, "Fact winter detail", ""));
	}

	private void addItem(Fact item) {
		Facts.add(item);
	}
	
}
