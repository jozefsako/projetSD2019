import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie {

	private String title;
	private String year;
	private List<String> actors;

	public Movie(String year, String actors) {
		this.year = year;
		this.actors = new ArrayList<String>(Arrays.asList(actors.split(" ")));
		this.title = "";
	}

	public String getTitle() {
		return title;
	}

	public String getYear() {
		return year;
	}

	public List<String> getActors() {
		return actors;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actors == null) ? 0 : actors.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (actors == null) {
			if (other.actors != null)
				return false;
		} else if (!actors.equals(other.actors))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Movie [title=" + title + ", year=" + year + ", actors=" + actors + "]";
	}
	
}
