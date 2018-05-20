package knn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.sun.javafx.font.Metrics;

import dataset.Article;
import dataset.ReutersParser;
import dataset.Preprocessor;
import dataset.DataReader;
import dataset.Similarity;
import metrics.ChebyshevMetric;
import metrics.EuclideanMetric;
import metrics.ManhattanMetric;
import metrics.Metric;

public class Main {

	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		int kParam = 5;
		char metricParam = 'E';
		//String dataPath = "D:\\workspace\\ksr-zad1\\reuters_data";
		String dataPath = "data_banknote.txt";
		char classificationParam = ' ';
		String labelParam = "PLACES";
		String similarityParam = "binary";
		
		List<ClassSubject> trainingSet = new ArrayList<>();
		List<ClassSubject> testSet = new ArrayList<>();

		if(classificationParam == 't') {
			ReutersParser p = new ReutersParser(dataPath, labelParam);
			List<Article> articles = p.getArticles();
			
			System.out.println("---- Data pre-processing ----");
			Preprocessor pr = new Preprocessor();
			pr.processData(articles);
			
			Similarity s = new Similarity();
			
			if(similarityParam.equals("binary")) {
				s.binaryFunction(articles);
			} else if(similarityParam.equals("tf-idf")){
				s.TFxIDF(articles);
			}
			
			int index = (int) (articles.size()*0.6);
			int i;
			for(i = 0; i < index; i++) {
				trainingSet.add(new ClassSubject(articles.get(i).getLabel(), articles.get(i).getVector()));
			}
			
			for(; i < articles.size(); i++) {
				testSet.add(new ClassSubject(articles.get(i).getLabel(), articles.get(i).getVector())); 
			}
		} else {
			DataReader dr = new DataReader(dataPath);
			List<ClassSubject> data = dr.getData();
			Map<String, List<ClassSubject>> sets = new HashMap<>();
			
			for(ClassSubject d : data) {
				if(sets.get(d.getLabel()) == null) {
					sets.put(d.getLabel(), new ArrayList<>());
				}
				sets.get(d.getLabel()).add(d);
			}
			
			for(Entry<String, List<ClassSubject>> entry : sets.entrySet()) {
				int index = (int) (entry.getValue().size() * 0.6);
				int i = 0;
				for(; i < index; i++) {
					trainingSet.add(entry.getValue().get(i));
				}
				for(; i < entry.getValue().size(); i++) {
					testSet.add(entry.getValue().get(i));
				}
			}
		}
		
		Metric metric = null;
		if(metricParam == 'E') {
			metric = new EuclideanMetric();
		} else if(metricParam == 'M') {
			metric = new ManhattanMetric();
		} else if(metricParam == 'C') {
			metric = new ChebyshevMetric();
		}
		
		Classifier knn = new Classifier(metric, kParam, trainingSet, testSet);
		System.out.println("---- Classifying ----");
		knn.classify();
		System.out.println("---- Results: ----");
		knn.printResults();
		
		long stopTime = System.currentTimeMillis();
		System.out.println("\nCZAS: " + (stopTime - startTime));
	}
}
