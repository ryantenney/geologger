public class GPGSV extends NMEAVerb {

	private int satelliteCount = 0;
	private NMEASatellite[] sats = new NMEASatellite[ 0 ];

	public GPGSV() {
		super( "GPGSV" );
	}

	protected void abstractParser( NMEASentence sentence ) {
		try {
			String[] args = sentence.getArgs();

			int msgCount = Integer.parseInt( args[ 0 ] );
			int msgNbr = Integer.parseInt( args[ 1 ] );
			int satCount = Integer.parseInt( args[ 2 ] );
			int index = ( ( msgNbr - 1 ) * 4 ) - 1;
			int satsToRead = ( msgNbr == msgCount ) ? satCount - ( ( msgNbr - 1 ) * 4 ) : 4;

			//if( msgNbr == 1 && msgCount == 1 && satCount == 1 ) {
				//System.out.println( sentence );
			//}

			if( msgNbr == 1 ) {
				synchronized( updating ) {
					updating = true;
					satelliteCount = satCount;
					sats = new NMEASatellite[ satCount ];
				}
			}

			for( int i = 0; i < satsToRead; ++i ) {
				int j = ( i * 4 ) + 3;
				NMEASatellite sv = new NMEASatellite();
				sv.setPRN( ( j < args.length ) && ( args[ j ].length() != 0 ) ? args[ j++ ] : "" );
				sv.setElevation( ( j < args.length ) && ( args[ j ].length() != 0 ) ? Integer.parseInt( args[ j++ ] ) : 0 );
				sv.setAzimuth( ( j < args.length ) && ( args[ j ].length() != 0 ) ? Integer.parseInt( args[ j++ ] ) : 0 );
				sv.setSNR( ( j < args.length ) && ( args[ j ].length() != 0 ) ? Integer.parseInt( args[ j++ ] ) : 0 );
				sats[ ++index ] = sv;
			}

			if( msgNbr == msgCount ) {
				synchronized( updating ) {
					updating = false;
				}
				raiseListener( sats );
			}
		} catch( ArrayIndexOutOfBoundsException aioobex ) {
			aioobex.printStackTrace();
			System.out.println( "Source sentence: " + sentence );
		}
	}

/*	public NMEASatellite[] getSatellites()
	{
		// ensures the updating flag is not in use by other processes
		synchronized( updating ) {
			// if this class is updating, it is waiting on other sentences to be received
			if( updating == true ) {
				int iter = 0;
				// loop until no longer updating, max of 50 loops
				while( updating == true && iter < 50 ) {
					try {
						// one iteration has elapsed, and sleep for 10 ms
						++iter;
						Thread.sleep( 10 );
					} catch( InterruptedException iex ) {
						// if interrupted
					}
				}
			}

			// after having ensured that
			if( updating == false ) {
				return sats;
			} else {
				return null;
			}
		}
	}*/

}
