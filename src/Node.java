import java.util.HashMap;

public class Node {

	private int cost;
	private Actor actor;
	
	HashMap<Actor, Actor> pathActors;
	HashMap<Actor, Movie> pathMovies;
	
	public Node(Actor actor) {
		this.actor = actor;
		this.cost = Integer.MAX_VALUE;
	}
	
	public Node(Actor actor, int cost) {
		this(actor);
		this.cost = cost;
	}
	
	
}
