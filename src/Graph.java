import java.util.HashMap;
import java.util.HashSet;

public class Graph {

	private HashMap<String, HashSet<Movie>> moviesOfActor;
	private HashMap<String, HashSet<Actor>> actorsOfMovie;
	private HashMap<String, Actor> actors;
	private HashMap<String, Movie> movies;

	public Graph() {
		this.moviesOfActor = new HashMap<>();
		this.actorsOfMovie = new HashMap<>();
		this.actors = new HashMap<>();
		this.movies = new HashMap<>();
	}

	public HashMap<String, HashSet<Movie>> getMoviesOfActor() {
		return moviesOfActor;
	}

	public HashMap<String, HashSet<Actor>> getActorsOfMovie() {
		return actorsOfMovie;
	}

	public HashMap<String, Actor> getActors() {
		return actors;
	}

	public HashMap<String, Movie> getMovies() {
		return movies;
	}

	public void addMovie(Movie movie) {
		movies.put(movie.getTitle(), movie);

		for (String actorID : movie.getActors()) {
			actors.get(actorID).addMovie(movie);
		}
	}

	public void addActor(Actor actor) {
		moviesOfActor.put(actor.getId(), new HashSet<>());
		this.actors.put(actor.getId(), actor);
	}

	/*
	 * BFS : Breadth First Search 
	 */
	public void calculerCheminLePlusCourt(String acteurA, String acteurB, String output) {
		// TODO Auto-generated method stub

	}

	/*
	 * Dijkstra : Shortest paths between nodes in a graph
	 */
	public void calculerCheminCoutMinimum(String acteurA, String acteurB, String output) {
		// TODO Auto-generated method stub

	}

}
