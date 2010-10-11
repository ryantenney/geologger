public class GPGSA extends NMEAVerb {

	public GPGSA() {
		super( "GPGSA" );
	}

	protected void abstractParser( NMEASentence sentence ) {}

}
