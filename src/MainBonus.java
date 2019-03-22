import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/* Bonus : 1. Implementez un parseur DOM pour creer le graphe */
public class MainBonus {

	public static void main(String args[]) {
		File inputFile = new File("movies.xml");
		DOMParserFactory domParserFactory = DOMParserFactory.newInstance();
		DOMParser domParser = domParserFactory.newParser();
		try {
			domParser.chargerLeFichier(inputFile);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}

		domParser.lireLeFichier();
		Graph g = domParser.getGraph();
		try {
			/* BFS */
			g.calculerCheminLePlusCourt("Macaulay Culkin", "Guillaume Canet", "outputMainBonus.xml");

			/* Dijkstra */
			g.calculerCheminCoutMinimum("Macaulay Culkin", "Guillaume Canet", "outputMainBonus2.xml");
		} catch (ActorNotFoundException ex) {
			System.out.println(ex.getMessage());
		}
	}

}
