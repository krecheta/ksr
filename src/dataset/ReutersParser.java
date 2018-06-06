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
 * Class responsible for reading Reuters dataset from files.
 *
 */
public class ReutersParser {
	
	/**
	 * List of all files with data
	 */
	private List<String> files = new ArrayList<>();
	
	private final String LABEL;
	
	/**
	 * Class constructor specifying directory with data.
	 * Fills files list with files with ".sgm" extension.
	 * @param directory directory with data files
	 */
	public ReutersParser(String directory, String label) {
		File dir = new File(directory);
		for(File f : dir.listFiles()) {
			if(f.getName().endsWith(".sgm")) {
				files.add(f.getAbsolutePath());
			}
		}
		this.LABEL = label;
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
		Pattern pattern = Pattern.compile(".*?<TOPICS>(.*?)</TOPICS>.*?<PLACES>(.*?)</PLACES>.*?<BODY>(.*?)</BODY>");
		Matcher matcher = pattern.matcher(fileContent);
		while(matcher.find()) {
			String lbl = null;
			if(LABEL.equals("PLACES")) {
				lbl = parsePlaces(matcher.group(2));
			} else if(LABEL.equals("TOPICS")) {
				lbl = parseTopics(matcher.group(1));
			}
			if(lbl != null) {
				Article article = new Article();
				article.setLabel(lbl);
				article.setText(matcher.group(3));
				articles.add(article);
			}
		}
		return articles;
	}
	
	/**
	 * Returns null if specified string isn't allowed place
	 * (west-germany, usa, france, uk, canada or japan).
	 * Otherwise, returns this place without <D> marks. 
	 * @param places string with places
	 * @return
	 */
	private String parsePlaces(String places) {
		List<String> possiblePlaces = Arrays.asList(
				"west-germany", "usa", "france", "uk", "canada", "japan");
		
		String place = places.replaceAll("<D>|</D>","");
		if(possiblePlaces.contains(place)) {
			return place;
		}
		return null;
	}
	/**
	 * Returns null if specified string isn't allowed topic
	 * (earn, crude, money-fx, sugar or money-supply).
	 * Otherwise returns this topic wothout <D> marks.
	 * @param topics string with topics
	 * @return
	 */
	private String parseTopics(String topics) {

		List<String> possibleTopics = Arrays.asList(
				"earn", "crude", "money-fx", "sugar", "money-supply");
		
		String topic = topics.replaceAll("<D>|</D>","");
		if(possibleTopics.contains(topic)) {
			return topic;
		}
		
		return null;
	}
}
