package dataset;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Vectorizer {
	
	/**
	 * Calculates vectors for articles using binary weighting.
	 *
	 */
	public void binaryWeighting(List<Article> articles, boolean cutVectors) {
		HashMap<Integer, Double> vector;
		List<String> trainingWords = new ArrayList<>();
		
		for(int i = 0; i < articles.size()*0.6; i++) {
			trainingWords.addAll(articles.get(i).getWords());
		}
		
		List<String> keyWords = null;
		
		if(cutVectors) {
			keyWords = cutVectors(trainingWords, 0.1);
		} else {
			keyWords = trainingWords.stream()
									.distinct()
									.collect(Collectors.toList());
		}
		
		for(Article art : articles) {
			vector = new HashMap<Integer, Double>();
			
			for(int i = 0; i < keyWords.size(); i++) {
				if(art.getWords().contains( keyWords.get(i) )) {
					vector.put(i, 1.0);
				}
			}
			art.setVector(vector);
		}

	}
	
	/**
	 * Calculates vectors for articles using TF-IDF.
	 *
	 */
	public void TFxIDF(List<Article> articles, boolean cutVectors) {
		
		HashMap<Integer, Double> vector;
		List<String> allWords = new ArrayList<>();
		List<String> allWordsDistinct;
		double tf, idf, tfidf;
		
		articles.forEach( x -> allWords.addAll(x.getWords()));
		
		if(cutVectors) {
			allWordsDistinct = cutVectors(allWords, 0.1);
		} else {
			allWordsDistinct = allWords.stream()
					   				   .distinct()
					   				   .collect(Collectors.toList());
		}
		
		//count occurrences of each word
		Map<String, Long> allWordsCounts = 
				allWords.stream()
						.collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		for(Article art : articles) {
			vector = new HashMap<>();
			
			//count occurrences of each word in article
			Map<String, Long> artWordCounts = 
					art.getWords().stream()
								  .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
			
			for(String word : art.getWords()) {
				//calculate tf
				tf = artWordCounts.get(word) / (double) art.getWords().size();
				
				//calculate idf
				idf = Math.log(articles.size() / (double) allWordsCounts.get(word));
				
				tfidf = tf*idf;
				if(tfidf != 0) {
					int index = allWordsDistinct.indexOf(word);
					if(index != -1) {
						vector.put(index, tf*idf);
					}
				}	
			}
			art.setVector(vector);
		}
	}
	
	/**
	 * Limits list of word to the most popular words.
	 * @param words
	 * @param percent 
	 * @return
	 */
	private List<String> cutVectors(List<String> words, double percent) {
		//calculate occurences of each word and sort it descending
		Map<String, Long> wordsCounts = 
				words.stream()
					 .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
					 .entrySet().stream()
								.sorted(Map.Entry.<String, Long>comparingByValue().reversed())
								.collect(Collectors.toMap(Map.Entry::getKey, 
									Map.Entry::getValue,(e1, e2) -> e1, LinkedHashMap::new));
		
		return wordsCounts.keySet().stream()
								   .limit((long) (percent * wordsCounts.size()))
								   .collect(Collectors.toList());
	}
}
