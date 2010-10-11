import java.util.*;
import java.util.concurrent.*;

public class NMEASendQueue extends NMEASentenceQueue {

	private DeviceConnection device;
	private NMEA nmea;

	public NMEASendQueue( DeviceConnection dev, NMEA nmeaObj ) {
		super( 512 );
		super.setName( "NMEASendQueue" );
		device = dev;
		nmea = nmeaObj;
		//this.start();
	}

	public void sendSentence( NMEASentence data ) {
		super.offer( data );
	}

	public void run() {
		try {
			while( nmea.isConnected() ) {
				device.send( super.take().getSentence() );
			}
		} catch( InterruptedException iex ) {
			// interrupted
			System.out.println( "interrupted" );
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
	}

}
