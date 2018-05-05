package knn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DistanceCalculator {
	public static double euclideanMetric(List<Double> x, List<Double> y) {
		double distance = 0;
		
		for(int i = 0; i < x.size(); i++) {
			distance += Math.pow(x.get(i) - y.get(i), 2);
		}
		
		return Math.sqrt(distance);
	}
	
	public static double manhattanMetric(List<Double> x, List<Double> y) {
		double distance = 0;
		
		for(int i = 0; i < x.size(); i++) {
			distance += Math.abs(x.get(i) - y.get(i));
		}
		
		return distance;
	}
	
	public static double chebyshevMetric(List<Double> x, List<Double> y) {
		List<Double> distances = new ArrayList<>();
		
		for(int i = 0; i < x.size(); i++) {
			distances.add(x.get(i) - y.get(i));
		}
		
		return Collections.max(distances);
	}
}
