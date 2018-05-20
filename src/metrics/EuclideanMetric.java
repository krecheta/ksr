package metrics;

import java.util.HashMap;
import java.util.Map;

public class EuclideanMetric implements Metric {

	@Override
	public double calculateDistance(HashMap<Integer, Double> x, HashMap<Integer, Double> y) {
		double distance = 0;
		int xKey;
		double yValue;
		
		for(Map.Entry<Integer, Double> entry : x.entrySet()) {
			xKey = entry.getKey();
			yValue = entry.getValue();
			
			if(y.keySet().contains(xKey)) {
				distance += Math.pow(yValue - y.get(xKey), 2);
			} else {
				distance += Math.pow(yValue, 2);
			}
		}
		
		for(Map.Entry<Integer, Double> entry : y.entrySet()) {
			if(!x.containsKey(entry.getKey())) {
				distance += Math.pow(entry.getValue(), 2);
			}			
		}
		
		return Math.pow(distance, (1.0 / 2.0));
	}

}
