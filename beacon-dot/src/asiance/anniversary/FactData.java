package asiance.anniversary;

import java.util.ArrayList;
import java.util.List;

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
		addItem(new Fact(0, "Foosball", R.drawable.babyfoot, 
				"How many people worked at Asiance since 2004?", 
				"150", "100", "150", false));
		addItem(new Fact(1, "Photo Wall", R.drawable.photowall, 
				"What means Asiance since 2004?", 
				"Asia + Alliance", "Asia + Maintenance", "Asia + Alliance", false));
		addItem(new Fact(2, "Awards", R.drawable.awards, 
				"How many Award did Asiance got since 2004?", 
				"14", "14", "10", false));
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
