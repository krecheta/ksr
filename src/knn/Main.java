package knn;

import java.io.IOException;
import java.util.List;

import dataset.Article;
import dataset.Parser;

public class Main {

	public static void main(String[] args) {
		Parser p = new Parser("D:\\workspace\\ksr-zad1\\reuters_data");
		List<Article> art = null;
		
		try {
			art = p.getArticles();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("WSZYSTKICH: " + art.size());
		System.out.print("USA: ");
		System.out.println(art.stream().filter(x -> x.getPlace().equals("usa")).count());
		System.out.print("WEST-GERMANY: ");
		System.out.println(art.stream().filter(x -> x.getPlace().equals("west-germany")).count());
		System.out.print("UK: ");
		System.out.println(art.stream().filter(x -> x.getPlace().equals("uk")).count());
		System.out.print("FRANCE: ");
		System.out.println(art.stream().filter(x -> x.getPlace().equals("france")).count());
		System.out.print("CANADA: ");
		System.out.println(art.stream().filter(x -> x.getPlace().equals("canada")).count());
		System.out.print("JAPAN: ");
		System.out.println(art.stream().filter(x -> x.getPlace().equals("japan")).count());
		
//		for(int i=0; i<50; i++) {
//			System.out.println("PLACE: " + art.get(i).getPlace());
//			System.out.println(art.get(i).getText());
//		}
		
//		for(Article a:art) {
//			if(a.getPlace().equals("canada")) {
//				System.out.println("PLACE: " + a.getPlace());
//				System.out.println(a.getText());
//			}
//		}
	}

}
