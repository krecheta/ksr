package dataset;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible for reading data from files.
 *
 */
public class Parser {
	
	/**
	 * List of all files with data
	 */
	List<String> files = new ArrayList<>();
	
	/**
	 * Class constructor specifying directory with data.
	 * Fills files list with files with ".sgm" extension.
	 * @param directory directory with data files
	 */
	public Parser(String directory) {
		File dir = new File(directory);
		for(File f : dir.listFiles()) {
			if(f.getName().endsWith(".sgm")) {
				files.add(f.getAbsolutePath());
			}
		}
	}
	
	/**
	 * Returns list of all Articles from files in specified directory.
	 * @return list of articles
	 * @throws IOException
	 */
	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();
		
		for(String fileName : files) {
			String fileContent = readFile(fileName);
			articles.addAll(parseFile(fileContent));
		}
		return articles;
	}
	
	/**
	 * Reads the specified file to string.
	 * @param fileName path to file with data
	 * @return string with content of file
	 * @throws IOException
	 */
	private String readFile(String fileName) {
		StringBuilder sb = new StringBuilder();
		
		try(BufferedReader r = new BufferedReader(new FileReader(fileName))){
			r.lines().forEach( x -> sb.append(x).append(" ") );
			//r.lines().map(x -> sb.append(x).append(" "));
		} catch (FileNotFoundException e) {
			System.err.println("Nie znaleziono pliku: " + fileName);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("B³¹d wczytywania danych z pliku");
			e.printStackTrace();
			System.exit(1);
		}
		
		return sb.toString();
	}
	
	/**
	 * Parses specified string into list of articles using regular expressions.
	 * @param fileContent string with content of file
	 * @return list of articles from specified string
	 */
	private List<Article> parseFile(String fileContent){
		List<Article> articles = new ArrayList<>();
		Pattern pattern = Pattern.compile(".*?<PLACES>(.*?)</PLACES>.*?<BODY>(.*?)</BODY>");
		Matcher matcher = pattern.matcher(fileContent);
		while(matcher.find()) {
			String places = parsePlaces(matcher.group(1));
			if(places != null) {
				Article article = new Article();
				article.setPlace(places);
				article.setText(matcher.group(2));
				articles.add(article);
			}
		}
		return articles;
	}
	
	/**
	 * Returns null if specified string isn't allowed place
	 * (west-germany, usa, france, uk, canada or japan).
	 * Otherwise, returns this place without <D> marks. 
	 * @param places string with parsed places
	 * @return
	 */
	private String parsePlaces(String places) {
		List<String> possiblePlaces = Arrays.asList(
				"west-germany", "usa", "france", "uk", "canada", "japan");
		places = places.replaceAll("<D>|</D>","");
		
		if(possiblePlaces.contains(places)) {
			return places;
		}
		return null;
	}
}
