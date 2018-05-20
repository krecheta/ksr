package knn;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import metrics.Metric;

public class Classifier {
	private final Metric metric;
	private final int kParam;
	private final List<ClassSubject> trainingSet; 
	private final List<ClassSubject> testSet;
	
	public Classifier(Metric metric, int kParam,
			List<ClassSubject> trainingSet, List<ClassSubject> testSet) {
		this.metric = metric;
		this.kParam = kParam;
		this.trainingSet = trainingSet;
		this.testSet = testSet;
		
		
	}
	
	public void classify() {
		
		Map<ClassSubject, Double> distances;
		List<String> nearestLabels;
		Map<String, Integer> labelsCount;
		String predictedLabel = null;
		
		for(ClassSubject testObject : testSet) {			
			distances = new HashMap<>();

			//calculate distances to objects in training set
			for(ClassSubject trainingObject : trainingSet) {
				distances.put(trainingObject, 
						metric.calculateDistance(testObject.getVector(), trainingObject.getVector()));
			}
			
			//sort distances and get k nearest labels
			nearestLabels = distances.entrySet().stream()
												.sorted(Map.Entry.comparingByValue())
												.map(x -> x.getKey().getLabel())
												.limit(kParam)
												.collect(Collectors.toCollection(LinkedList::new));
			
			//count occurrence of each label
			labelsCount = new HashMap<>();
			for(String label : nearestLabels) {
				Integer counter = labelsCount.get(label);
				if(counter == null) {
					labelsCount.put(label, 1);
				} else {
					labelsCount.put(label, ++counter);
				}
			}
			
			//get most popular label
			Entry<String, Integer> mostOccLabel = Collections.max(labelsCount.entrySet(), Map.Entry.comparingByValue());
			long counter = labelsCount.values().stream()
										  .filter( x -> x.equals(mostOccLabel.getValue()))
										  .count();
			
			//if counter > 1 - there are two or more nearest labels -> set the nearest label
			if(counter > 1) {				
				List<String> labelsToRemove = labelsCount.entrySet().stream()
																	.filter(x -> !x.getValue().equals(mostOccLabel.getValue()))
																	.map(x -> x.getKey())
																	.collect(Collectors.toList());
				nearestLabels.removeAll(labelsToRemove);
				predictedLabel = nearestLabels.get(0);
				
			} else {
				predictedLabel = mostOccLabel.getKey();
			}
			
			testObject.setPredictedLabel(predictedLabel);
		}
	}
	public void printResults() {

		HashMap<String, Integer> correctlyClassified = new HashMap<>();
		Integer counter;
		String label;
		long total = 0;

		for(ClassSubject testObject : testSet) {
			label = testObject.getLabel();

			if(label.equals(testObject.getPredictedLabel())) {
				counter = correctlyClassified.get(label);
				if (counter == null) {
					correctlyClassified.put(label, 1);
				} else {
					correctlyClassified.put(label, ++counter);
				} 
			}
		}

		counter = 0;
		for(Entry<String, Integer> entry : correctlyClassified.entrySet()) {

			total = testSet.stream()
						   .filter(x -> x.getLabel().equals(entry.getKey()))
						   .count();
			
			counter += entry.getValue();
			System.out.print("\n" + entry.getKey());
			System.out.print(": " + entry.getValue() + "/" + total + " (");
			System.out.printf("%.2f", (entry.getValue()/(double)total)*100);
			System.out.print(" %)");
		}

		System.out.print("\nTOTAL:: " + counter + "/" + testSet.size() + " (");
		System.out.printf("%.2f", (counter/(double)testSet.size()) * 100);
		System.out.print(" %)");
	}
}