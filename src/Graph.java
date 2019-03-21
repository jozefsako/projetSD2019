import java.io.File;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;

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
	public void calculerCheminLePlusCourt(String acteurA, String acteurB, String output) throws ActorNotFoundException {

		HashSet<String> visited = new HashSet<>();
		HashSet<Movie> visitedMovies = new HashSet<>();
		ArrayDeque<Actor> queue = new ArrayDeque<>();

		HashMap<Actor, Actor> pathActors = new HashMap<>();
		HashMap<Actor, Movie> pathMovies = new HashMap<>();

		queue.add(this.actorsID.get(this.actorsName.get(acteurA)));
		visited.add(this.actorsName.get(acteurA));

		boolean found = false;
		while(!queue.isEmpty() && !found) {

			Actor current = queue.removeFirst();

			for (Movie movie : current.getMovies()) {

				if (!visitedMovies.contains(movie)) {

					if(movie.getActors().contains(this.actorsName.get(acteurB))) {
						found = true;
					}

					for (String actorID : movie.getActors()) {
						if(!visited.contains(actorID)) {
							pathActors.put(this.actorsID.get(actorID), current);
							pathMovies.put(this.actorsID.get(actorID), movie);
							queue.addLast(this.actorsID.get(actorID));
							visited.add(actorID);
						}
					}
					visitedMovies.add(movie);
				}
			}
		}
		
		if(!found) {
			throw new ActorNotFoundException("Pas de lien trouvé entre " + acteurA + " et " + acteurB + " non trouvé");
		}

		Path path = formaterHistorique(acteurA, acteurB, pathActors, pathMovies);

		if (ecrireFichierXML(path, output)) {
			System.out.println("EcrireFichierXML (" + output + ") : OK");
		} else {
			System.out.println("EcrireFichierXML (" + output + ") : KO");
		}

	}

	/*
	 * Dijkstra : Shortest paths between nodes in a graph
	 */
	public void calculerCheminCoutMinimum(String acteurA, String acteurB, String output) {
		// TODO Auto-generated method stub

		HashMap<Actor, Actor> pathActors = new HashMap<>();
		HashMap<Actor, Movie> pathMovies = new HashMap<>();

		HashSet<Movie> visitedMovies = new HashSet<>();
		HashSet<String> visited = new HashSet<>();
		ArrayDeque<Actor> queue = new ArrayDeque<>();

		SortedSet<Actor> eTmp = new TreeSet<>(comparator());
		HashMap<String, Integer> eDef = new HashMap<>();

		Actor sommet = this.actorsID.get(this.actorsName.get(acteurA));
		sommet.setCost(0);

		queue.add(sommet);
		visited.add(sommet.getId());
		eDef.put(sommet.getId(), 0);

		boolean found = false;
		while(!queue.isEmpty() && !found) {

			Actor current = queue.removeFirst();
			visited.add(current.getId());

			if(eDef.containsKey(this.getActorsName().get(acteurB))) {
				found = true;
			}

			for(Movie movie : current.getMovies()) {
				if(!visitedMovies.contains(movie)) {

					for(String actorID : movie.getActors()) {

						if(!eDef.containsKey(actorID)) {
							Actor actor = this.actorsID.get(actorID);
							int movieCost = movie.getActors().size();
							int old_cost = actor.getCost();

							if(eTmp.contains(actor)) {
								if(old_cost > (current.getCost() + movieCost)) {
									eTmp.remove(actor);
									actor.setCost(current.getCost() + movieCost);
									eTmp.add(actor);
									pathActors.put(actor, current);
									pathMovies.put(actor, movie);
								}
							}else {
								actor.setCost(current.getCost() + movieCost);
								eTmp.add(actor);
								pathActors.put(actor, current);
								pathMovies.put(actor, movie);
							}
						}
					}
					visitedMovies.add(movie);
				}
			}
			Actor min = eTmp.first();
			eTmp.remove(min);
			eDef.put(min.getId(), min.getCost());
			queue.addLast(min);
		}

		Path path = formaterHistorique(acteurA, acteurB, pathActors, pathMovies);

		if (ecrireFichierXML(path, output)) {
			System.out.println("EcrireFichierXML (" + output + ") : OK");
		} else {
			System.out.println("EcrireFichierXML (" + output + ") : KO");
		}

	}

	/*
	 *  Bonus : 2 calculer un chemin en minimisant 
	 *  d'abord le combre de films et ensuite le cout 
	 */
	public void calculerCheminLePlusCourtAvecCoutMinimum(String acteurA, String acteurB, String output) {
		// TODO Auto-generated method stub
		
		HashSet<String> visited = new HashSet<>();
		HashSet<Movie> visitedMovies = new HashSet<>();
		ArrayDeque<Actor> queue = new ArrayDeque<>();

		HashMap<Actor, Actor> pathActors = new HashMap<>();
		HashMap<Actor, Movie> pathMovies = new HashMap<>();
		HashSet<Path> shortestPath = new HashSet<>();
		
		queue.add(this.actorsID.get(this.actorsName.get(acteurA)));
		visited.add(this.actorsName.get(acteurA));

		boolean found = false;
		
		while(!queue.isEmpty()) {

			Actor current = queue.removeFirst();

			for (Movie movie : current.getMovies()) {

				if (!visitedMovies.contains(movie)) {

					if(movie.getActors().contains(this.actorsName.get(acteurB))) {
						shortestPath.add(formaterHistorique(acteurA, acteurB, pathActors, pathMovies));
						found = true;
					}

					for (String actorID : movie.getActors()) {
						if(!visited.contains(actorID)) {
							pathActors.put(this.actorsID.get(actorID), current);
							pathMovies.put(this.actorsID.get(actorID), movie);
							queue.addLast(this.actorsID.get(actorID));
							visited.add(actorID);
						}
					}
					visitedMovies.add(movie);
				}
			}
		}
		
		Path path = formaterHistorique(acteurA, acteurB, pathActors, pathMovies);
		int ShortestPath = path.getNbMovies();
		
		
		if (ecrireFichierXML(path, output)) {
			System.out.println("EcrireFichierXML (" + output + ") : OK");
		} else {
			System.out.println("EcrireFichierXML (" + output + ") : KO");
		}
	}
	
	private Comparator<Actor> comparator() {
		return new Comparator<Actor>() {
			@Override
			public int compare(Actor a1, Actor a2) {
				// TODO Auto-generated method stub
				if(a1.getCost() == a2.getCost()) {
					return (int) a1.getId().compareTo(a2.getId());
				}else {
					return a1.getCost() - a2.getCost();
				}
			}
		};
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
