package dataset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Similarity {
	
	List<String> keyWords;
	
	public void createBagOfWords(List<Article> trainingSet) {
		
		List<String> allWords = new ArrayList<>();
		trainingSet.forEach( x -> allWords.addAll(x.getWords()) );
		
		keyWords = allWords.stream()
						   .distinct()
						   .collect(Collectors.toList());
		
//		System.out.println(keyWords.size());

		
//		Map<String, Long> counts = allWords.stream()
//									  	   .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
//		
//		Map<String, Long> sortedMap = 
//			     counts.entrySet().stream()
//							      .sorted(Entry.comparingByValue())
//							      .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
//							                              (e1, e2) -> e1, LinkedHashMap::new));
//				
//		sortedMap.forEach((k,v) -> System.out.println(k + " " + v));
		
	}

	public void binaryFunction(List<Article> articles) {
		HashMap<Integer, Double> vector;
		
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
	
	public void tfidf(List<Article> articles) {
		HashMap<Integer, Double> vector;
		
	}
}
