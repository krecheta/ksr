package dataset;

import java.util.List;

/**
 * Represents single article.
 *
 */
public class Article {
	
	private String place;
	private String text;
	private List<String> words;
	
	public String getPlace() {
		return place;
	}
	
	public void setPlace(String place) {
		this.place = place;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}
	
}
