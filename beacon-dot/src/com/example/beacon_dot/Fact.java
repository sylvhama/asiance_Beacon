package com.example.beacon_dot;

public class Fact {
	public int id;
	public String factTitle;
	public int image;
	public String factDetail;
	public String factQuiz;
	public String factQuizAnswer;
	
	public Fact(int id, String factTitle, int image, String factDetail, String factQuiz, String factQuizAnswer) {
		this.id = id;
		this.factTitle = factTitle;
		this.image = image;
		this.factDetail = factDetail;
		this.factQuiz = factQuiz;
		this.factQuizAnswer = factQuizAnswer;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return factTitle;
	}
	
	
}
