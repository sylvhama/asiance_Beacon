package com.example.beacon_dot;

public class Fact {
	public String factTitle;
	public int image;
	public String factDetail;
	
	public Fact(String factTitle, int image, String factDetail) {
		this.factTitle = factTitle;
		this.image = image;
		this.factDetail = factDetail;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return factTitle;
	}
	
	
}
