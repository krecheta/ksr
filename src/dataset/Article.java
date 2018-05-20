package dataset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents single article.
 *
 */
public class Article {
	
	private String label;
	private String text;
	private List<String> words;
	private HashMap<Integer, Double> vector;
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String place) {
		this.label = place;
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

	public HashMap<Integer, Double> getVector() {
		return vector;
	}

	public void setVector(HashMap<Integer, Double> vector) {
		this.vector = vector;
	}
	
}
