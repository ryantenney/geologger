import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class GPSClock extends JFrame implements DeviceListener, NMEAVerbListener {

	private NMEA gps;

	private JLabel timeDisplay;

	public GPSClock( NMEA gpsConn ) {
		super( "GPS Clock" );
		//this.setDefaultCloseOperation( JFrame. );
		this.getContentPane().setLayout( null );

		gps = gpsConn;
		gps.addDeviceListener( this );
		gps.addVerbListener( this );

		timeDisplay = new JLabel();
		timeDisplay.setBounds( 10, 10, 380, 130 );
		timeDisplay.setFont( new Font( "Monaco", Font.BOLD, 36 ) );
		this.getContentPane().add( timeDisplay );

		setSize( 400, 150 );
		setResizable( false );
		setVisible( true );
	}

	public void deviceDataEvent( DeviceConnection obj, DeviceListener.Direction dir, int len, String data ) {
		//if( "GPZDA".equalsIgnoreCase( NMEASentence.getVerb( data ) ) && dir == DeviceListener.Direction.RECEIVE ) {}
	}

	public void deviceStateEvent( DeviceConnection obj, DeviceState state ) {
		if( !( state == DeviceState.ASYNC || state == DeviceState.OPEN ) ) {
			timeDisplay.setText( "< no signal >" );
			timeDisplay.revalidate();
		}
	}

	public void verbEvent( String verb, Object[] args ) {
		if( verb.equals( "GPZDA" ) && args.length >= 1 && args[ 0 ] instanceof Calendar ) {
			Calendar utc = (Calendar)args[ 0 ];
			utc.setTimeZone( TimeZone.getDefault() );
			timeDisplay.setText( new SimpleDateFormat( "HH:mm:ss z" ).format( utc.getTime() ) );
			timeDisplay.revalidate();
		}
	}

}
