package dataset;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class responsible for data pre-processing.
 *
 */
public class Preprocessor {
	
	/**
	 * List of english stop words.
	 */
	private List<String> stopWords;
	
	public Preprocessor(String stopWordsPath) {
		stopWords = new ArrayList<>();
		readStopWords(stopWordsPath);
	}
	
	/**
	 * Processes specified data and creates 
	 * list of words for each article.
	 * Processing rules:
	 * 1. Set all characters to lower case
	 * 2. Remove all non-alphabetic characters
	 * 3. Remove all words with single character
	 * 4. Remove extra spaces
	 * 5. Remove all words from the stop words list
	 * 6. Words stemming
	 * @param articles list of articles
	 */
	public void processData(List<Article> articles) {		
		for(Article art : articles) {
			
			String text = art.getText()
							 .toLowerCase()
						   	 .replaceAll("[^a-z]", " ")
							 .replaceAll("(^|\\s+)[a-z](\\s+|$)", " ")
							 .trim()
							 .replaceAll(" +", " ");
			
			List<String> words = new ArrayList<String>(Arrays.asList(text.split("\\s+")));
			words.removeAll(stopWords);
			
			PorterStemmer stemmer;
			List<String> stemmWords = new ArrayList<>();
			String stemmWord;
			
			for(String w : words) {
				stemmer = new PorterStemmer();
				stemmer.add(w.toCharArray(), w.length());
				stemmer.stem();
				stemmWord = stemmer.toString();
				if(stemmWord.length() > 1 ) {
					stemmWords.add(stemmWord);
				}
			}
			
			stemmWords.removeAll(stopWords);
			
			art.setWords(stemmWords);
		}
	}
	
	/**
	 * Reads file with english stop words to the list of strings.
	 */
	private void readStopWords(String stopWordsPath) {
		
		try(BufferedReader r = new BufferedReader(new FileReader(stopWordsPath))){
			r.lines().forEach(stopWords::add);
		} catch (IOException e) {
			System.err.println("B³¹d wczytywania stop listy s³ów");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
