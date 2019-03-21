
public class DOMParserFactory {

	public static DOMParserFactory newInstance() {
		return new DOMParserFactory();
	}

	public DOMParser newParser() {
		return new DOMParser();
	}
}
