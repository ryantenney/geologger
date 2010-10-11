import java.util.*;
import java.util.concurrent.*;

public class SerialDeviceConnection implements DeviceConnection {

	// native events to be accessed via JNI
	protected final native void open( String port );
	protected final native void puts( String data );
	protected final native String gets();
	protected final native void startGetsAsync();
	protected final native void endGetsAsync();
	protected final native void setBaud( int baudrate );
	protected final native int getBaud();
	protected final native void close();
	protected final native int getState();

	protected DeviceState state;

	protected Thread async;
	protected final Set< DeviceListener > callbacks;

	public SerialDeviceConnection() {
		callbacks = Collections.synchronizedSet( new HashSet< DeviceListener >() );
		state = DeviceState.CLOSED;
	}

	public void connect( String port ) {
		if( state.compareTo( DeviceState.CLOSED ) <= 0 ) {
			open( port );
		}
	}

	public void connect( String port, int baudrate ) {
		if( state.compareTo( DeviceState.CLOSED ) <= 0 ) {
			open( port );
			setBaud( baudrate );
		}
	}

	public void disconnect() {
		System.out.println( state.compareTo( DeviceState.OPEN ) );
		if( state.compareTo( DeviceState.OPEN ) >= 0 ) {
			close();
			async = null;
		}
	}

	public void beginReceiveAsync() {
		if( async == null && state == DeviceState.OPEN ) {
			async = new Thread( new Runnable() {
				public void run() {
					startGetsAsync();
				}
			}, "SerialDeviceConnection::ReceiveAsync" );
			async.start();
		}
	}

	public void endReceiveAsync() {
		if( async != null && state == DeviceState.ASYNC ) {
			endGetsAsync();
			// wait for the thread to finish, mebbe?
			async = null;
		}
	}

	public void addDeviceListener( DeviceListener listener ) {
		if( listener != null ) {
			callbacks.add( listener );
		}
	}

	public void removeDeviceListener( DeviceListener listener ) {
		callbacks.remove( listener );
	}

	protected void raiseDataEvent( int len, String data ) {
		// raised through JNI by startGetsAsync in DeviceConnection.jnilib
		deviceDataEvent( DeviceListener.Direction.RECEIVE, len, data );
	}

	protected void deviceDataEvent( final DeviceListener.Direction dir, final int len, final String data ) {
		final DeviceConnection finalThis = this;

		// anonymous class
		new Thread( new Runnable() {
			public synchronized void run() {
				for( DeviceListener listener : callbacks ) {
					if( listener != null ) {
						listener.deviceDataEvent( finalThis, dir, len, data );
					} else {
						callbacks.remove( listener );
					}
				}
			}
		}, "SerialDeviceConnection::deviceDataEvent" ).start();

	}

	protected void deviceStateEvent( final int state ) {
		this.state = DeviceState.getState( state );

		final DeviceConnection finalThis = this;
		final DeviceState finalState = this.state;

		// anonymous class
		new Thread( new Runnable() {
			public synchronized void run() {
				for( DeviceListener listener : callbacks ) {
					if( listener != null ) {
						listener.deviceStateEvent( finalThis, finalState );
					} else {
						callbacks.remove( listener );
					}
				}
			}
		}, "SerialDeviceConnection::deviceStateEvent" ).start();
	}

	public void send( String data ) {
		puts( data );
		deviceDataEvent( DeviceListener.Direction.TRANSMIT, data.length(), data );
	}

	public DeviceState getDeviceState() {
		return state;
	}

	public int getBaudRate() {
		return getBaud();
	}

	public void setBaudRate( int value ) {
		setBaud( value );
	}

	static {
		System.loadLibrary( "SerialDeviceConnection" );
	}

}
