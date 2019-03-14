import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.TreeSet;

import apple.laf.JRSUIUtils.Tree;

public class Graph {

	private HashMap<String, HashSet<Movie>> moviesOfActor;
	private HashMap<String, HashSet<Actor>> actorsOfMovie;
	private HashMap<String, String>actorsName;
	private HashMap<String, Actor> actorsID;
	private HashMap<String, Movie> movies;

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

	public HashMap<String, String> getActorsName(){
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

		while(!queue.isEmpty() || found==false) {

			Actor current = queue.removeFirst();
		
			for (Movie movie : current.getMovies()) {

				if(!visitedMovies.contains(movie)) {

					if(movie.getActors().contains(this.actorsName.get(acteurB))) {
						//pathActors.put(this.actorsID.get(this.actorsName.get(acteurB)), current);
						//pathMovies.put(this.actorsID.get(this.actorsName.get(acteurB)), movie);
						found = true;
					}
					for (String actorID : movie.getActors()) {
						if(!visited.contains(actorID)) {
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
		Actor actor = pathActors.get(this.actorsID.get(this.actorsName.get(acteurB)));
		
		HashSet<Movie> solutionM = new HashSet<>();
		HashSet<Actor> solutionA = new HashSet<>();
		
		solutionA.add(this.actorsID.get(this.actorsName.get(acteurA)));
		solutionA.add(this.actorsID.get(this.actorsName.get(acteurB)));
		
		while(pathActors.get(actor)!=null) {
			System.out.println("<Actor>" + actor.getName() + "</Actor>");
			System.out.println("<Movie>" + pathMovies.get(actor).getTitle() + "</Movie>");
			solutionM.add(pathMovies.get(actor));
			solutionA.add(actor);
			actor = pathActors.get(actor);
		}
		
		/*
		 * Analyse Solution
		 */
		System.out.println("\n\nSOL Movies");
		for (Movie movie : solutionM) {
			System.out.println(movie);
		}
		
		System.out.println("\n\nSOL actors");
		for (Actor a : solutionA) {
			for (Movie movie : solutionM) {
				if(movie.getActors().contains(a.getId())) {
					System.out.println("[ " + a.getName() + " - " + a.getId() + " ] joue dans => " + movie.getTitle());
				}
			}
		}
		
	}

	/*
	 * Dijkstra : Shortest paths between nodes in a graph
	 */
	public void calculerCheminCoutMinimum(String acteurA, String acteurB, String output) {
		// TODO Auto-generated method stub

	}

}
