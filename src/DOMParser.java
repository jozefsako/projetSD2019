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
		for(int i = 0; i < movies.getLength(); i++) {
			Node noeud = movies.item(i);
			System.out.println(noeud.getNodeName());
			if(noeud.getNodeName().equalsIgnoreCase("actor")){
				Element actorElement = (Element) noeud;
				Actor actor = new Actor(actorElement.getAttribute("id"), actorElement.getAttribute("name"));
				graph.addActor(actor);
			}else if(noeud.getNodeName().equalsIgnoreCase("movie")) {
				Element movieElement = (Element) noeud;
				Movie movie = new Movie(movieElement.getAttribute("year"), movieElement.getAttribute("actors"), movieElement.getTextContent());
				graph.addMovie(movie);
			}
		}
	}
	
	public Graph getGraph() {
		return graph;
	}
	
	
}
