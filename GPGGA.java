public class GPGGA extends NMEAVerb {

	public GPGGA() {
		super( "GPGGA" );
	}

	protected void abstractParser( NMEASentence sentence ) {}

}
