package metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChebyshevMetric implements Metric {

	@Override
	public double calculateDistance(HashMap<Integer, Double> x, HashMap<Integer, Double> y) {
		//List<Double> distances = new ArrayList<>();
		double val = 0, tmp = 0;
		int key;
		double value;
		
		for(Map.Entry<Integer, Double> entry : x.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			
			if(y.keySet().contains(key)) {
				tmp = Math.abs(value - y.get(key));
			} else {
				if(!x.containsKey(entry.getKey())) {
					tmp = Math.abs(value);
				}
			}
			if(tmp > val) {
				val = tmp;
			}
		}
		
		for(Map.Entry<Integer, Double> entry : y.entrySet()) {
			if(!x.containsKey(entry.getKey())) {
				tmp = Math.abs(entry.getValue());
			}
			if(tmp > val) {
				val = tmp;
			}
		}
		
		return val;
	}

}
