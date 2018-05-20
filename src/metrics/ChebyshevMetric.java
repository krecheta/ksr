package metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChebyshevMetric implements Metric {

	@Override
	public double calculateDistance(HashMap<Integer, Double> x, HashMap<Integer, Double> y) {
		List<Double> distances = new ArrayList<>();
		int key;
		double value;
		
		for(Map.Entry<Integer, Double> entry : x.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			
			if(y.keySet().contains(key)) {
				distances.add(value - y.get(key));
				y.remove(key);
			} else {
				distances.add(value);
			}
		}
		
		for(Map.Entry<Integer, Double> entry : y.entrySet()) {
			if(!x.containsKey(entry.getKey())) {
				distances.add(entry.getValue());
			}
		}
		
		return Math.abs(Collections.max(distances));
	}

}
