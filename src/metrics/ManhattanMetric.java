package metrics;

import java.util.HashMap;
import java.util.Map;

public class ManhattanMetric implements Metric {

	@Override
	public double calculateDistance(HashMap<Integer, Double> x, HashMap<Integer, Double> y) {
		double distance = 0;
		int key;
		double value;
		
		for(Map.Entry<Integer, Double> entry : x.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			
			if(y.keySet().contains(key)) {
				distance += Math.abs(value - y.get(key));;
			} else {
				distance += Math.abs(value);
			}
		}
		
		for(Map.Entry<Integer, Double> entry : y.entrySet()) {
			if(!x.containsKey(entry.getKey())) {
				distance += Math.abs(entry.getValue());
			}		
		}
		
		return distance;
	}

}
