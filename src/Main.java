import java.io.File;

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
<<<<<<< HEAD

			/* BFS */
			g.calculerCheminLePlusCourt("Macaulay Culkin", "Guillaume Canet", "output.xml");
			
			/* Dijkstra */
			g.calculerCheminCoutMinimum("Macaulay Culkin", "Guillaume Canet","output2.xml");
=======
			try {
				g.calculerCheminLePlusCourt("Macaulay Culkin", "Guillaume Canet", "output.xml");
			}catch(ActorNotFoundException ex) {
				System.out.println(ex.getMessage());
			}
			// g.calculerCheminCoutMinimum("Macaulay Culkin", "Guillaume Canet",
			// "output2.xml");

			/*
			 * System.out.println("\tMovies"); for(Entry<String, Movie> entry:
			 * g.getMovies().entrySet()) { System.out.println(entry.getValue()); }
			 */
>>>>>>> 52ad0dab6fbaf0e7a3a56294a675d555d46e1302

			/* Bonus : Dijkstra + BFS */
			g.calculerCheminLePlusCourtAvecCoutMinimum("Macaulay Culkin", "Guillaume Canet", "output.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
