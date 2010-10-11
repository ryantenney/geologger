import java.util.*;

public class GPRMC extends NMEAVerb {

	public static class DataPacket {

		Calendar utc;
		boolean fix;
		int latDeg, longDeg;
		double latMin, longMin;

		public DataPacket() {
			utc = Calendar.getInstance( TimeZone.getTimeZone( "UTC" ) );
			fix = false;
			latDeg = 0;
			latMin = 0.0;
			longDeg = 0;
			longMin = 0.0;
		}

		public void setCoordinates( int degreesLat, double minutesLat, String NS, int degreesLong, double minutesLong, String EW ) {
			latDeg  = NS.toUpperCase().charAt( 0 ) == 'S' ? -Math.abs( degreesLat  ) : Math.abs( degreesLat  );
			latMin  = NS.toUpperCase().charAt( 0 ) == 'S' ? -Math.abs( minutesLat  ) : Math.abs( minutesLat  );
			longDeg = EW.toUpperCase().charAt( 0 ) == 'W' ? -Math.abs( degreesLong ) : Math.abs( degreesLong );
			longMin = EW.toUpperCase().charAt( 0 ) == 'W' ? -Math.abs( minutesLong ) : Math.abs( minutesLong );
		}

		public void setCoordinates( double latitude, double longitude ) {
			latDeg  = ( int ) Math.floor( latitude );
			latMin  = ( latitude - latDeg ) * 60.0;
			longDeg = ( int ) Math.floor( longitude );
			longMin = ( longitude - longDeg ) * 60.0;
		}

		public boolean getFix() {
			return fix;
		}

		public void setFix( boolean value ) {
			fix = value;
		}

		public Calendar getTime() {
			return utc;
		}

		public void setTime( String value ) {
			try {
				utc.set( Calendar.HOUR_OF_DAY, Integer.parseInt( value.substring( 0, 2 ) ) );
				utc.set( Calendar.MINUTE, Integer.parseInt( value.substring( 2, 4 ) ) );
				utc.set( Calendar.SECOND, Integer.parseInt( value.substring( 4, 6 ) ) );
				utc.set( Calendar.MILLISECOND, ( int )( Double.parseDouble( value.substring( 6 ) ) * 1000 ) );
			} catch( Exception ex ) {
				System.out.println( "Error setting time" );
			}
		}

		public String toString() {
			return String.format( "%d\u00B0 %f' %c %d\u00B0 %f' %c",
								  Math.abs( latDeg ),  Math.abs( latMin ),  latDeg  < 0 ? 'S' : 'N',
								  Math.abs( longDeg ), Math.abs( longMin ), longDeg < 0 ? 'W' : 'E' );
		}
	}

	public GPRMC() {
		super( "GPRMC" );
	}

	protected void abstractParser( NMEASentence sentence ) {
		String[] args = sentence.getArgs();

		DataPacket fixData = new DataPacket();

		fixData.setTime( args[ 0 ] );
		fixData.setFix( args[ 1 ].equalsIgnoreCase( "A" ) );
		fixData.setCoordinates( Integer.parseInt(   args[ 2 ].substring( 0, 2 ) ),
								Double.parseDouble( args[ 2 ].substring( 2    ) ), args[ 3 ],
								Integer.parseInt(   args[ 4 ].substring( 0, 3 ) ),
								Double.parseDouble( args[ 4 ].substring( 3    ) ), args[ 5 ] );
		raiseListener( fixData );
	}

}
