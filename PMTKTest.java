import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class PMTKTest extends JFrame implements DeviceListener, ActionListener {

	private NMEA gps;

	private JButton sendSentence;
	private JTextArea sentenceInput;
	private JTextArea responseDisplay;
	private JScrollPane responseScroll;

	public PMTKTest( NMEA gpsConn ) {
		super( "PMTK Test" );
//		this.setDefaultCloseOperation( JFrame. );
		this.getContentPane().setLayout( null );

		gps = gpsConn;
		gps.addDeviceListener( this );

		sendSentence = new JButton( "Send" );
		sendSentence.addActionListener( this );
		sendSentence.setBounds( 370, 10, 120, 29 );
		this.getContentPane().add( sendSentence );

		sentenceInput = new JTextArea();
		sentenceInput.setBounds( 10, 15, 360, 20 );
		sentenceInput.setFont( new Font( "Courier New", Font.PLAIN, 12 ) );
		this.getContentPane().add( sentenceInput );

		responseDisplay = new JTextArea();
		responseDisplay.setEditable( false );
		responseDisplay.setFont( new Font( "Courier New", Font.PLAIN, 12 ) );

		responseScroll = new JScrollPane( responseDisplay );
		responseScroll.setBounds( 10, 40, 480, 300 );
		this.getContentPane().add( responseScroll );

		setSize( 500, 400 );
		setResizable( false );
		setVisible( true );
	}

	public void deviceDataEvent( DeviceConnection obj, DeviceListener.Direction dir, int len, String data ) {
		if( "PMTK".equalsIgnoreCase( NMEASentence.getVerb( data ) ) ) {
			responseDisplay.insert( data, 0 );
			if( dir == DeviceListener.Direction.TRANSMIT ) {
				responseDisplay.insert( "<< ", 0 );
			} else if( dir == DeviceListener.Direction.RECEIVE ) {
				responseDisplay.insert( ">> ", 0 );
			}
		}
	}

	// defined by interface, but not implemented
	public void deviceStateEvent( DeviceConnection obj, DeviceState state ) {}

	public void actionPerformed( ActionEvent e ) {
		if( e.getSource() == sendSentence ) {
			String str = sentenceInput.getText();

			sentenceInput.setText( "" );

/*			String verb = NMEASentence.getVerb( str );
			String pmtkType = NMEASentence.getPMTKType( str );
			String[] args = NMEASentence.getArgs( str );
*/
			NMEASentence sentence = new NMEASentence( str ); // verb, pmtkType, args );

			gps.sendSentence( sentence );

		}
	}

}
