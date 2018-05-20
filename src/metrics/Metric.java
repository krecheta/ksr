package metrics;

import java.util.HashMap;

public interface Metric {
	public double calculateDistance(HashMap<Integer, Double> x, HashMap<Integer, Double> y);
}
