public class NMEASatelliteMapException extends Exception {

	private Exception baseException;

	public NMEASatelliteMapException( Exception base ) {
		this.baseException = base;
	}

}
