import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/* Bonus : 1. Implémentez un parseur DOM pour créer le graphe */
public class DOMParser {

	Document doc;
	Graph graph;
	
	public DOMParser() {
		this.graph = new Graph();
	}
	
	public void chargerLeFichier(File file) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(file);
	}
	
	public void lireLeFichier() {
		NodeList actors = doc.getElementsByTagName("actor");
		NodeList movies = doc.getElementsByTagName("movie");
		int actorsLength = actors.getLength();
		int moviesLength = movies.getLength();
		
		for(int i = 0; i < actorsLength; i++) {
			Node noeud = actors.item(i);
			Element actorElement = (Element) noeud;
			Actor actor = new Actor(actorElement.getAttribute("id"), actorElement.getAttribute("name"));
			graph.addActor(actor);
		}
		
		for(int i = 0; i < moviesLength; i++) {
			Node noeud = movies.item(i);
			Element movieElement = (Element) noeud;
			Movie movie = new Movie(movieElement.getAttribute("year"), movieElement.getAttribute("actors"), movieElement.getTextContent());
			graph.addMovie(movie);
		}
	}
	
	public Graph getGraph() {
		return graph;
	}
	
	
}
