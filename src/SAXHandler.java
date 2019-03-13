import org.xml.sax.* ;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {

	String id, name, title, actorsId, year;
	
	boolean bActor;
	boolean bMovie;
	
	Movie tmpMovie;
	Actor tmpActor;
	String[] tmpActorsId;
	
	Graph graph = new Graph();


	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		/*
		 * Set Element : Actor or Movie 
		 */
		if (qName.equalsIgnoreCase("actor")) {
			id = attributes.getValue("id");
			name = attributes.getValue("name");
			tmpActor = new Actor(id, name);
			bActor = true;
		}
		else if (qName.equalsIgnoreCase("movie")) {
			year = attributes.getValue("year");
			actorsId = attributes.getValue("actors");
			tmpMovie = new Movie(year, actorsId);
			bMovie = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		/*
		 * The element is totaly constructed
		 * we add it to the right collection
		 */
		if (qName.equalsIgnoreCase("movie")) {			
			graph.addMovie(tmpMovie);
			
		} else if(qName.equalsIgnoreCase("actor")) {
			graph.addActor(tmpActor);
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		
		/*
		 * Set the last field for each Element 
		 */
		String cdata = new String(ch, start, length);
		
		if (bActor) {
			bActor = false;
		} 
		else if(bMovie) {
			tmpMovie.setTitle(cdata);
			bMovie = false;
		}
	}

	public Graph getGraph() {
		return graph;
	}

}