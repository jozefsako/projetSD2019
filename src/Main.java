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

			/* BFS */
			g.calculerCheminLePlusCourt("Macaulay Culkin", "Guillaume Canet", "output.xml");
			
			/* Dijkstra */
			g.calculerCheminCoutMinimum("Macaulay Culkin", "Guillaume Canet","output2.xml");

			/* Bonus : Dijkstra + BFS */
			g.calculerCheminLePlusCourtAvecCoutMinimum("Macaulay Culkin", "Guillaume Canet", "output.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
