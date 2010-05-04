/*	BTQ1000.java

	This program is free software; you can redistribute it and/or modify
	it under the terms of the GNU General Public License version 2.x,
	as published by	the Free Software Foundation;

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/*	PMTK Packets
 
	Info Set 1:
	$PMTK182,2,3*
	$PMTK182,2,4*
	$PMTK182,2,5*

	Info Set 2:
	$PMTK182,2,7*
	$PMTK182,2,8*
	$PMTK182,2,10*
	$PMTK182,7,001F0000,2*

	Erase:
	$PMTK182,6,1*

	Download:
	$PMTK182,7,00000000,00010000*
 */

import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class BTQ1000 extends NMEA
{
	public enum ConnectionMode
	{
		USB, BLUETOOTH;
	}
	
	public enum LogFormat
	{
		XML, KML, CSV, BIN, NMEA;
	}

	public enum RecordMethod
	{
		OVERWRITE	( 1 ),
		STOP		( 2 );
		
		private final int value;
		RecordMethod( int value ) { this.value = value; }
		public int value() { return this.value; }
	}
	
	public enum LogSettings
	{
	//	UNDEFINED		(  1 ),		// UNDEFINED
		LOG_FORMAT		(  2 ),		// Log format
		TIME_INTERVAL	(  3 ),		// Time interval to log (in 0.1 s)
		DIST_INTERVAL	(  4 ),		// Distance interval to log (in 0.1m)
		SPEED_INTERVAL	(  5 ),		// Speed interval to log (in 0.1km/h)
		RECORD_METHOD	(  6 ),		// Recording method (overwrite/stop)
		LOG_STATUS		(  7 ),		// Request log status
		MEMORY_USED		(  8 ),		// Memory Used (BUG?:Returned value does seem to be always correct)
		FLASH			(  9 ),		// Flash, needs cmd
		NUM_POINTS		( 10 );		// Number of logging points
	//	UNDEFINED		( 11 );		// UNDEFINED
	
		private final int value;
		LogSettings( int value ) { this.value = value; }
		public int value() { return this.value; }
	}
	
	public enum LogCommands
	{
		SET				( 1 ),		//
		QUERY			( 2 ),		
		DEVICE_RESPONSE	( 3 ),
		ON				( 4 ),
		OFF				( 5 ),
		ERASE			( 6 ),
		REQUEST			( 7 ),
		RESPONSE		( 8 ),
		INIT			( 9 );
		
		private final int value;
		LogCommands( int value ) { this.value = value; }
		public int value() { return this.value; }
	}
	
	public enum Verb
	{
		GPGLL		(  0 ),	// Geographic Position - Latitude longitude
		GPRMC		(  1 ),	// Recommended Min. specic GNSS sentence
		GPVTG		(  2 ),	// Course Over Ground and Ground Speed
		GPGGA		(  3 ),	// GPS Fix Data
		GPGSA		(  4 ),	// GNSS DOPS and Active Satellites
		GPGSV		(  5 ),	// GNSS Satellites in View
		GPGRS		(  6 ),	// GNSS Range Residuals
		GPGST		(  7 ),	// GNSS Pseudorange Error Statistics
		PMTKALM		( 13 ),	// GPS almanac information
		PMTKEPH		( 14 ),	// GPS ephemeris information
		PMTKDGP		( 15 ),	// GPS differential correction information
		PMTKDBG		( 16 ),	// MTK debug information
		GPZDA		( 17 ),	// Time & Date
		PMTKCHN		( 18 );	// GPS channel status
		
		private int val;
		
		Verb( int value ) { this.val = value; }
//		public 		
	}

	public enum PMTKNoun
	{
		PMTK_TEST					( "000" ),
		PMTK_ACK					( "001" ),
		PMTK_SYS_MSG				( "010" ),
		PMTK_CMD_HOT_START			( "101" ),
		PMTK_CMD_WARM_START			( "102" ),
		PMTK_CMD_COLD_START			( "103" ),
		PMTK_CMD_FULL_COLD_START	( "104" ),
		PMTK_LOG_CMD				( "182" ),
		PMTK_SET_FIX_INTERVAL		( "220" ),
		PMTK_SET_NMEA_BAUDRATE		( "251" ),
		PMTK_API_SET_FIX_CTL		( "300" ),
		PMTK_API_SET_DGPS_MODE		( "301" ),
		PMTK_API_SET_SBAS_ENABLED	( "313" ),
		PMTK_API_SET_NMEA_OUTPUT	( "314" ),
		PMTK_API_SET_SBAS_TEST		( "319" ),
		PMTK_API_SET_PWR_SAV_MODE	( "320" ),
		PMTK_API_SET_DATUM			( "330" ),
		PMTK_API_SET_DATUM_ADVANCE	( "331" ),
		PMTK_API_SET_USER_OPTION	( "390" ),
		PMTK_API_Q_FIX_CTL			( "400" ),
		PMTK_API_Q_DGPS_MODE		( "401" ),
		PMTK_API_Q_SBAS_ENABLED		( "413" ),
		PMTK_API_Q_NMEA_OUTPUT		( "414" ),
		PMTK_API_Q_SBAS_TEST		( "419" ),
		PMTK_API_Q_PWR_SAV_MOD		( "420" ),
		PMTK_API_Q_DATUM			( "430" ),
		PMTK_API_Q_DATUM_ADVANCE	( "431" ),
		PMTK_API_GET_USER_OPTION	( "490" ),
		PMTK_DT_FIX_CTL				( "500" ),
		PMTK_DT_DGPS_MODE			( "501" ),
		PMTK_DT_SBAS_ENABLED		( "513" ),
		PMTK_DT_NMEA_OUTPUT			( "514" ),
		PMTK_DT_SBAS_TEST			( "519" ),
		PMTK_DT_PWR_SAV_MODE		( "520" ),
		PMTK_DT_DATUM				( "530" ),
		PMTK_DT_FLASH_USER_OPTION	( "590" ),
		PMTK_Q_DGPS_INFO			( "602" ),
		PMTK_Q_VERSION				( "604" ),
		PMTK_Q_RELEASE				( "605" ),
		PMTK_RTCM_BAUD_RATE			( "702" ),
		PMTK_DT_VERSION				( "704" ),
		PMTK_DT_RELEASE				( "705" );
		
		private String noun;
		
		PMTKNoun( String value ) { this.noun = value; }
	}
	
	// Constants
	private final static String bluetoothPort = "/dev/cu.iBT-GPS";
	private final static String usbPort = "/dev/cu.SLAB_USBtoUART";
	
	// Variables
	private byte[] logData;
	private boolean[] blockStatus;
	private int bytesComplete;
	private LinkedList< NMEASentence > dataRequests;
	private File logFile;
	private LogFormat logFormat;
	
	public BTQ1000()
	{
		super();
		initArrays();
	}
	
	public BTQ1000( ConnectionMode deviceConnection )
	{
		super();
		initArrays();
		connect( deviceConnection );
	}
	
	protected void initArrays()
	{
		logData = new byte[ 0x00200000 ];
		blockStatus = new boolean[ 0x00200000 ];
		bytesComplete = 0;

		dataRequests = new LinkedList();

		Arrays.fill( logData, 0x00000000, 0x00200000, (byte)0x00 );
		Arrays.fill( blockStatus, 0x00000000, 0x00200000, false );
	}
	
	public void connect( ConnectionMode deviceConnection )
	{
		switch( deviceConnection )
		{
			case USB:
				super.connect( usbPort, 115200 );
				break;
			case BLUETOOTH:
				super.connect( bluetoothPort );
				break;
		}
	}
	
	protected void populateVerbs()
	{
		super.populateVerbs();
		verbs.put( new PMTK() );
	}
	
	public void beginDownloadLogData( File file, LogFormat format )
	{
		final int interval = 0x00010000;
		
		initArrays();
		
		logFile = file;
		logFormat = format;

		dataRequests.add( new NMEASentence( "PMTK182", new String[] { "7", formatHex( 0x00000000 ), formatHex( 0x00200000 ) } ) );
		
//		for( int offset = 0x00000000; offset < 0x00200000; offset += interval )
//		{ 
//			dataRequests.add( new NMEASentence( "PMTK182", new String[] { "7", formatHex( offset ), formatHex( interval ) } ) );
//		}

		sendSentence( dataRequests.poll() );
	}
	
	public void eraseDevice()
	{
		sendSentence( new NMEASentence( "PMTK182", new String[] { "6", "1" } ) );
	}
	
	public int getDownloadProgress()
	{
		return bytesComplete;
	}
	
	protected String formatHex( int hex )
	{
		return String.format( "%1$08X", hex );
	}
	
	public void verbEvent( String verb, Object[] args )
	{
//		System.out.println( String.format( "Verb: %s, Argc: %d, Argv[ 0 ] type: %s", verb, args.length,
//										   ( args.length >= 1 && args[ 0 ] != null) ? args[ 0 ].getClass().getName() : "NULL" ) );
		if( verb.equals( "PMTK" ) && args.length >= 1 && args[ 0 ] instanceof PMTK.DataPacket )
		{
			PMTK.DataPacket data = ( PMTK.DataPacket ) args[ 0 ];
			System.arraycopy( data.getData(), 0, logData, data.getOffset(), data.getLength() );
			Arrays.fill( blockStatus, data.getOffset(), data.getOffset() + data.getLength(), true );
			bytesComplete += data.getLength();
			
//			if( bytesComplete % 0x00010000 == 0 && dataRequests.peek() != null )
//			{
//				sendSentence( dataRequests.poll() );
//			}

			if( bytesComplete == 0x00200000 )
			{
				try
				{
//					convertLog( logData, LogFormat.BIN, LogFormat.KML );
					FileOutputStream fos = new FileOutputStream( logFile );
					fos.write( logData );
					fos.flush();
					fos.close();
				}
				catch( IOException ioex )
				{
					ioex.printStackTrace();
				}
			}
		}
		else
		{
			super.verbEvent( verb, args );
		}
	}
	
	public boolean isComplete()
	{
		boolean retval = blockStatus[ 0 ];
		for( int i = 1; i < blockStatus.length; ++i )
		{
			retval &= blockStatus[ i ];
			if( retval == false ) { break; }
		}
		return retval;
	}
	
}
