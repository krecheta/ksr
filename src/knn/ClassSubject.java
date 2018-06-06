package knn;

import java.util.HashMap;

/**
 * Represents classification subject.
 *
 */
public class ClassSubject {
	private HashMap <Integer, Double> vector;
	private String label;
	private String predictedLabel;
	
	public ClassSubject(String label, HashMap <Integer, Double> vector) {
		this.label = label;
		this.vector = vector;
	}
	
	public HashMap<Integer, Double> getVector() {
		return vector;
	}
	public void setVector(HashMap<Integer, Double> vector) {
		this.vector = vector;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getPredictedLabel() {
		return predictedLabel;
	}
	public void setPredictedLabel(String predictedLabel) {
		this.predictedLabel = predictedLabel;
	}
	
	@Override
	public String toString() {
		return label;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((predictedLabel == null) ? 0 : predictedLabel.hashCode());
		result = prime * result + ((vector == null) ? 0 : vector.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassSubject other = (ClassSubject) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (predictedLabel == null) {
			if (other.predictedLabel != null)
				return false;
		} else if (!predictedLabel.equals(other.predictedLabel))
			return false;
		if (vector == null) {
			if (other.vector != null)
				return false;
		} else if (!vector.equals(other.vector))
			return false;
		return true;
	}
	
	
}
