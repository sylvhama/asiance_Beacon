package asiance.anniversary;

public class Fact {
	public int id;
	public String factTitle;
	public int image;
	public String factDetail;
	public String answer;
	public String factQuizAnswer1;
	public String factQuizAnswer2;
	public boolean rightAnswer;
	
	public Fact () {}
	
	public Fact(int id, String factTitle, int image, String factDetail, String answer, String factQuizAnswer1, String factQuizAnswer2, boolean rightAnswer) {
		this.id = id;
		this.factTitle = factTitle;
		this.image = image;
		this.factDetail = factDetail;
		this.answer = answer;
		this.factQuizAnswer1 = factQuizAnswer1;
		this.factQuizAnswer2 = factQuizAnswer2;
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
	 * @return the factAnswer
	 */
	public String getAnswer() {
		return answer;
	}



	/**
	 * @return the factQuizAnswer
	 */
	public String getFactQuizAnswer1() {
		return factQuizAnswer1;
	}



	/**
	 * @return the factQuizAnswer
	 */
	public String getFactQuizAnswer2() {
		return factQuizAnswer2;
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
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @param factQuizAnswer the factQuizAnswer to set
	 */
	public void setFactQuizAnswer1(String factQuizAnswer1) {
		this.factQuizAnswer1 = factQuizAnswer1;
	}

	/**
	 * @param factQuizAnswer the factQuizAnswer to set
	 */
	public void setFactQuizAnswer2(String factQuizAnswer2) {
		this.factQuizAnswer2 = factQuizAnswer2;
	}

	/**
	 * @param rightAnswer the rightAnswer to set
	 */
	public void setRightAnswer(boolean rightAnswer) {
		this.rightAnswer = rightAnswer;
	}
	
	
}
