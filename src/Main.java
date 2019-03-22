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

			try {
				/* BFS */
				g.calculerCheminLePlusCourt("Macaulay Culkin", "Guillaume Canet", "output.xml");

				/* Dijkstra */
				g.calculerCheminCoutMinimum("Macaulay Culkin", "Guillaume Canet", "output2.xml");

			} catch (ActorNotFoundException ex) {
				System.out.println(ex.getMessage());
			}

			/* Bonus : Dijkstra + BFS */
			g.calculerCheminLePlusCourtAvecCoutMinimum("Macaulay Culkin", "Guillaume Canet", "output3.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
