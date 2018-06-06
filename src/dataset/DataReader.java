package dataset;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import knn.ClassSubject;

/**
 * Class responsible for reading banknote 
 * autenthication dataset from specified file.
 *
 */
public class DataReader {
	/**
	 * Path to file with data.
	 */
	private final String PATH;
	
	/**
	 * Class constructor specifying path to file with data.
	 * @param path
	 */
	public DataReader(String path) {
		this.PATH = path;
	}
	
	/**
	 * Reads data from file into list of ClassSubject.
	 * @return list of ClassSubject
	 */
	public List<ClassSubject> getData(){
		List<ClassSubject> list = new ArrayList<>();
		
		try(BufferedReader r = new BufferedReader(new FileReader(PATH))){
			while(r.ready()) {
				String[] arr = r.readLine().split(",");

				String label = arr[arr.length-1];
				HashMap<Integer, Double> vector = new HashMap<>();
				
				for(int i = 0; i < arr.length-1; i++) {
					vector.put(i, Double.parseDouble(arr[i]));
				}
				
				list.add(new ClassSubject(label, vector));
			}
		} catch (FileNotFoundException e) {
			System.err.println("Nie znaleziono pliku: " + PATH);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("B³¹d wczytywania danych z pliku");
			e.printStackTrace();
			System.exit(1);
		}
		
		return list;
	}
}
