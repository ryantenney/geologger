import java.util.*;
import java.util.concurrent.*;

public class NMEAReceiveQueue extends NMEASentenceQueue implements DeviceListener {

	protected DeviceConnection dev;
	protected NMEA nmea;

	public NMEAReceiveQueue( DeviceConnection device, NMEA nmeaObj ) {
		super( 512 );
		super.setName( "NMEAReceiveQueue" );
		dev = device;
		nmea = nmeaObj;
		nmea.addDeviceListener( this );
		//this.start();
	}

	public void deviceDataEvent( DeviceConnection obj, DeviceListener.Direction dir, int len, String data ) {
		try {
			if( dir == DeviceListener.Direction.RECEIVE ) {
				NMEASentence sentence = new NMEASentence( data );
				super.offer( sentence, 1, TimeUnit.SECONDS );
			}
		} catch( NMEASentenceQueueOfferFailedException nsqofex ) {
			// Offer failed handler
		} catch( InterruptedException iex ) {
			// Interrupted handler
		}
	}

	public void deviceStateEvent( DeviceConnection obj, DeviceState state ) {

	}

	public void run() {
		try {
			while( nmea.isConnected() ) {
				nmea.interpretSentence( super.take() );
			}
		} catch( InterruptedException iex ) {
			// interrupted
			System.out.println( "interrupted" );
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
	}

}
