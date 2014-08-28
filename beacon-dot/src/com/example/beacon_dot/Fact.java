package com.example.beacon_dot;

public class Fact {
	public int id;
	public String factTitle;
	public int image;
	public String factDetail;
	public String factQuiz;
	public String factQuizAnswer;
	public boolean rightAnswer;
	
	public Fact () {}
	
	public Fact(int id, String factTitle, int image, String factDetail, String factQuiz, String factQuizAnswer, boolean rightAnswer) {
		this.id = id;
		this.factTitle = factTitle;
		this.image = image;
		this.factDetail = factDetail;
		this.factQuiz = factQuiz;
		this.factQuizAnswer = factQuizAnswer;
		this.rightAnswer = rightAnswer;
	}
	
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}



	/**
	 * @return the factTitle
	 */
	public String getFactTitle() {
		return factTitle;
	}



	/**
	 * @return the image
	 */
	public int getImage() {
		return image;
	}



	/**
	 * @return the factDetail
	 */
	public String getFactDetail() {
		return factDetail;
	}



	/**
	 * @return the factQuiz
	 */
	public String getFactQuiz() {
		return factQuiz;
	}



	/**
	 * @return the factQuizAnswer
	 */
	public String getFactQuizAnswer() {
		return factQuizAnswer;
	}



	/**
	 * @return the rightAnswer
	 */
	public boolean isRightAnswer() {
		return rightAnswer;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return factTitle;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param factTitle the factTitle to set
	 */
	public void setFactTitle(String factTitle) {
		this.factTitle = factTitle;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(int image) {
		this.image = image;
	}

	/**
	 * @param factDetail the factDetail to set
	 */
	public void setFactDetail(String factDetail) {
		this.factDetail = factDetail;
	}

	/**
	 * @param factQuiz the factQuiz to set
	 */
	public void setFactQuiz(String factQuiz) {
		this.factQuiz = factQuiz;
	}

	/**
	 * @param factQuizAnswer the factQuizAnswer to set
	 */
	public void setFactQuizAnswer(String factQuizAnswer) {
		this.factQuizAnswer = factQuizAnswer;
	}

	/**
	 * @param rightAnswer the rightAnswer to set
	 */
	public void setRightAnswer(boolean rightAnswer) {
		this.rightAnswer = rightAnswer;
	}
	
	
}
