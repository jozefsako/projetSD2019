import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class Graph {

	private HashMap<String, HashSet<Movie>> moviesOfActor;
	private HashMap<String, HashSet<Actor>> actorsOfMovie;
	private HashMap<String, String>actorsName;
	private HashMap<String, Actor> actorsID;
	private HashMap<String, Movie> movies;
	public static final int MAX_VALUE = Integer.MAX_VALUE;
	
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
		while(!queue.isEmpty() && !found) {

			Actor current = queue.removeFirst();

			for (Movie movie : current.getMovies()) {

				if(!visitedMovies.contains(movie)) {

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
	}

	/*
	 * Dijkstra : Shortest paths between nodes in a graph
	 */
	public void calculerCheminCoutMinimum(String acteurA, String acteurB, String output) {
		// TODO Auto-generated method stub
		
		HashSet<Actor> visited = new HashSet<>();
		SortedSet<Actor> sorted = new TreeSet<>(new Comparator<Actor>() {
			
			@Override
			public int compare(Actor o1, Actor o2) {
				// TODO Auto-generated method stub
				return 0;
			}
		});
	}

}
