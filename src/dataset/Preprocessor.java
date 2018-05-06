package dataset;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class responsible for data processing.
 *
 */
public class Preprocessor {
	
	List<String> stopWords;
	
	public Preprocessor() {
		stopWords = new ArrayList<>();
		readStopWords();
	}
	
	/**
	 * Processes specified data and creates 
	 * list of words for each article.
	 * Processing rules:
	 * 1. Set all characters to lower case
	 * 2. Remove all non-alphabetic character
	 * 3. Remove word "reuter" (It's in all articles at the end)
	 * 4. Remove all single characters
	 * 5. Remove extra spaces
	 * 6. Remove all words from the stop words list
	 * @param articles list of articles
	 */
	public void processData(List<Article> articles) {		
		for(Article art : articles) {
			String text = art.getText()
					.toLowerCase()
					.replaceAll("[^a-z]", " ")
					.replaceAll("reuter", " ")
					.replaceAll("(^|\\s+)[a-z](\\s+|$)", " ")
					.trim().replaceAll(" +", " ");
			
			List<String> words = new ArrayList<String>(Arrays.asList(text.split("\\s+")));
			words.removeAll(stopWords);

			art.setWords(words);
		}
	}
	
	/**
	 * Reads file with english stop words to the list of strings.
	 */
	private void readStopWords() {
		
		try(BufferedReader r = new BufferedReader(new FileReader("stopwords.txt"))){
			r.lines().forEach(stopWords::add);
			
		} catch (IOException e) {
			System.err.println("B³¹d wczytywania stop listy s³ów");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
