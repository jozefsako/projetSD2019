import java.util.Stack;

public class Path {
	private int cost;
	private int nbMovies;
	private Stack<Actor> actors;
	private Stack<Movie> movies;

	public Path(int cost, int nbMovies, Stack<Actor> actors2, Stack<Movie> movies2) {
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

	public Stack<Actor> getActors() {
		return actors;
	}

	public Stack<Movie> getMovies() {
		return movies;
	}
}
