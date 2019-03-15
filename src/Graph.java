import java.io.File;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Queue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Graph {

	private HashMap<String, HashSet<Movie>> moviesOfActor;
	private HashMap<String, HashSet<Actor>> actorsOfMovie;
	private HashMap<String, String> actorsName;
	private HashMap<String, Actor> actorsID;
	private HashMap<String, Movie> movies;

	Document document;

	public Graph() {
		this.moviesOfActor = new HashMap<>();
		this.actorsOfMovie = new HashMap<>();
		this.actorsName = new HashMap<>();
		this.actorsID = new HashMap<>();
		this.movies = new HashMap<>();
	}

	public HashMap<String, HashSet<Movie>> getMoviesOfActor() {
		return moviesOfActor;
	}

	public HashMap<String, HashSet<Actor>> getActorsOfMovie() {
		return actorsOfMovie;
	}

	public HashMap<String, Actor> getActorsID() {
		return actorsID;
	}

	public HashMap<String, String> getActorsName() {
		return actorsName;
	}

	public HashMap<String, Movie> getMovies() {
		return movies;
	}

	public void addMovie(Movie movie) {
		movies.put(movie.getTitle(), movie);

		for (String actorID : movie.getActors()) {
			actorsID.get(actorID).addMovie(movie);
		}
	}

	public void addActor(Actor actor) {
		moviesOfActor.put(actor.getId(), new HashSet<>());
		actorsName.put(actor.getName(), actor.getId());
		actorsID.put(actor.getId(), actor);
	}

	/*
	 * BFS : Breadth First Search
	 */
	public void calculerCheminLePlusCourt(String acteurA, String acteurB, String output) {
		// TODO Auto-generated method stub

		HashSet<String> visited = new HashSet<>();
		HashSet<Movie> visitedMovies = new HashSet<>();
		ArrayDeque<Actor> queue = new ArrayDeque<>();

		HashMap<Actor, Actor> pathActors = new HashMap<>();
		HashMap<Actor, Movie> pathMovies = new HashMap<>();

		queue.add(this.actorsID.get(this.actorsName.get(acteurA)));
		visited.add(this.actorsName.get(acteurA));

		boolean found = false;

		while (!queue.isEmpty() || found == false) {

			Actor current = queue.removeFirst();

			for (Movie movie : current.getMovies()) {

				if (!visitedMovies.contains(movie)) {

					if (movie.getActors().contains(this.actorsName.get(acteurB))) {
						// pathActors.put(this.actorsID.get(this.actorsName.get(acteurB)), current);
						// pathMovies.put(this.actorsID.get(this.actorsName.get(acteurB)), movie);
						found = true;
					}
					for (String actorID : movie.getActors()) {
						if (!visited.contains(actorID)) {
							visited.add(actorID);
							queue.addLast(this.actorsID.get(actorID));
							pathActors.put(this.actorsID.get(actorID), current);
							pathMovies.put(this.actorsID.get(actorID), movie);
						}
					}
					visitedMovies.add(movie);
				}
			}
		}

		Path path = formaterHistorique(acteurA, acteurB, pathActors, pathMovies);
		boolean isOk = ecrireFichierXML(path, output);
		if (isOk) {
			System.out.println("OK");
		} else {
			System.out.println("KO");
		}

	}

	/*
	 * Dijkstra : Shortest paths between nodes in a graph
	 */
	public void calculerCheminCoutMinimum(String acteurA, String acteurB, String output) {
		// TODO Auto-generated method stub

	}

	public Path formaterHistorique(String acteurA, String acteurB, HashMap<Actor, Actor> pathActors,
			HashMap<Actor, Movie> pathMovies) {

		int cost = 0, nbMovies = 0;
		Queue<Actor> actors = new ArrayDeque<Actor>();
		Queue<Movie> movies = new ArrayDeque<Movie>();
		Actor tmpActor = actorsID.get(actorsName.get(acteurB));
		Actor parent = tmpActor;

		while (!parent.getName().equalsIgnoreCase(acteurA)) {
			for (Entry<Actor, Actor> entry : pathActors.entrySet()) {
				if (entry.getKey().getName().equalsIgnoreCase(parent.getName())) {
					actors.add(parent);
					movies.add(pathMovies.get(parent));
					parent = entry.getValue();
				}
			}
		}
		actors.add(actorsID.get(actorsName.get(acteurA)));
		nbMovies = movies.size();

		// TODO cost
		Path path = new Path(cost, nbMovies, actors, movies);
		return path;
	}

	public boolean ecrireFichierXML(Path path, String output) {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder;
			documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			Element root = document.createElement("path");
			Attr attrCost = document.createAttribute("cost");
			Attr attrNbMovies = document.createAttribute("nbMovies");
			attrCost.setValue(path.getCost() + "");
			attrNbMovies.setValue(path.getNbMovies() + "");
			root.setAttributeNode(attrCost);
			root.setAttributeNode(attrNbMovies);
			document.appendChild(root);

			Actor actor = path.getActors().poll();
			Movie movie = path.getMovies().poll();
			while (actor != null) {
				Element actorElement = document.createElement("actor");
				actorElement.appendChild(document.createTextNode(actor.getName()));
				root.appendChild(actorElement);
				if (movie != null) {
					Element movieElement = document.createElement("movie");
					Attr nameAttr = document.createAttribute("name");
					Attr yearAttr = document.createAttribute("year");
					nameAttr.setValue(movie.getTitle());
					yearAttr.setValue(movie.getYear());
					movieElement.setAttributeNode(nameAttr);
					movieElement.setAttributeNode(yearAttr);
					root.appendChild(movieElement);
					movie = path.getMovies().poll();
				}
				actor = path.getActors().poll();
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(output));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(domSource, streamResult);

		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
