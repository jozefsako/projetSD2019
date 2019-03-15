import java.util.Queue;

public class Path {
	private int cost;
	private int nbMovies;
	private Queue<Actor> actors;
	private Queue<Movie> movies;

	public Path(int cost, int nbMovies, Queue<Actor> actors2, Queue<Movie> movies2) {
		this.cost = cost;
		this.nbMovies = nbMovies;
		this.actors = actors2;
		this.movies = movies2;
	}

	public int getCost() {
		return cost;
	}

	public int getNbMovies() {
		return nbMovies;
	}

	public Queue<Actor> getActors() {
		return actors;
	}

	public Queue<Movie> getMovies() {
		return movies;
	}
}
