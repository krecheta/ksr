package knn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sun.javafx.font.Metrics;

import dataset.Article;
import dataset.Parser;
import dataset.Preprocessor;
import dataset.Similarity;
import metrics.ChebyshevMetric;
import metrics.EuclideanMetric;
import metrics.ManhattanMetric;
import metrics.Metric;

public class Main {

	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		
		System.out.println("---- Reading data ----");
		Parser p = new Parser("D:\\workspace\\ksr-zad1\\reuters_data");
		List<Article> art = p.getArticles();
		
		System.out.println("---- Data pre-processing ----");
		Preprocessor pr = new Preprocessor();
		pr.processData(art);
		
		
		List<Article> artTrainingSet = new ArrayList<>();
		List<Article> artTestSet = new ArrayList<>();
		
		int index = (int) (art.size()*0.6);
		int i;
		for(i = 0; i < index; i++) {
			artTrainingSet.add(art.get(i));
		}
		
		for(;i < art.size(); i++) {
			artTestSet.add(art.get(i));
		}
		
		Similarity s = new Similarity();
		s.createBagOfWords(artTrainingSet);
		s.binaryFunction(art);
		
//		podzia³ na zbiory
		List<ClassSubject> trainingSet = new ArrayList<>();
		List<ClassSubject> testSet = new ArrayList<>();
		
		artTrainingSet.forEach( x -> trainingSet.add(new ClassSubject(x.getPlace(), x.getVector())));
		artTestSet.forEach( x -> testSet.add(new ClassSubject(x.getPlace(), x.getVector())));
		
		System.out.println("---- Classifying ----");
		Metric metric = new EuclideanMetric();
		Classifier knn = new Classifier(metric, 3, trainingSet, testSet);
		knn.classify();
		System.out.println("---- Results: ----");
		knn.printResults();
		
		long stopTime = System.currentTimeMillis();
		System.out.println("\nCZAS: " + (stopTime - startTime));
	}
}
