import java.util.*;

public class GPZDA extends NMEAVerb {

	public GPZDA() {
		super( "GPZDA" );
	}

	protected void abstractParser( NMEASentence sentence ) {
		String[] args = sentence.getArgs();

		Calendar utc = new GregorianCalendar();

		utc.clear();

		utc.set( Calendar.DAY_OF_MONTH, Integer.parseInt( args[ 1 ] ) );
		utc.set( Calendar.MONTH, Integer.parseInt( args[ 2 ] ) - 1 );
		utc.set( Calendar.YEAR, Integer.parseInt( args[ 3 ] ) );
		utc.set( Calendar.HOUR_OF_DAY, Integer.parseInt( args[ 0 ].substring( 0, 2 ) ) );
		utc.set( Calendar.MINUTE, Integer.parseInt( args[ 0 ].substring( 2, 4 ) ) );
		utc.set( Calendar.SECOND, Integer.parseInt( args[ 0 ].substring( 4, 6 ) ) );
		utc.set( Calendar.MILLISECOND, ( int )( Double.parseDouble( args[ 0 ].substring( 6 ) ) * 1000 ) );

		if( args.length >= 5 && args[ 4 ] != null && args[ 5 ] != null ) {
			utc.set( Calendar.ZONE_OFFSET, ( ( Integer.parseInt( args[ 4 ] ) * 60 ) + Integer.parseInt( args[ 5 ] ) ) * ( 60 * 1000 ) );
		} else {
			utc.set( Calendar.ZONE_OFFSET, 0 );
		}

		//System.out.println( "////" );
		//System.out.println( System.currentTimeMillis() - sentence.getTimeOffered() );
		//System.out.println( utc.toString() );
		//System.out.println( "" );

		raiseListener( utc );
	}

}
