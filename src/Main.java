import java.io.File;
import java.util.Map.Entry;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main {
	public static void main(String[] args) {
		try {
			File inputFile = new File("movies.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			SAXHandler userhandler = new SAXHandler();
			saxParser.parse(inputFile, userhandler);
			Graph g = userhandler.getGraph();
			
			//g.calculerCheminLePlusCourt("Macaulay Culkin", "Guillaume Canet", "output.xml");
			//g.calculerCheminCoutMinimum("Macaulay Culkin", "Guillaume Canet", "output2.xml");
			
			
			System.out.println("\tActors : total => " + g.getActors().size());
			for (Entry<String, Actor> entry: g.getActors().entrySet()) {
				System.out.println(entry.getValue().getId() + " - " + entry.getValue().getName());
			}
			
//			System.out.println("\tMovies");
//			for(Entry<String, Movie> entry: g.getMovies().entrySet()) {
//				System.out.println(entry.getValue());
//			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
