/*	NMEA.java

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

import java.math.*;
import java.util.*;
import java.util.concurrent.*;

public class NMEA implements DeviceListener, NMEAVerbListener
{
	protected int bytesReceived, bytesSent;
	protected String port;

	protected DeviceState state;
	
	protected NMEAVerbCollection verbs;
	protected DeviceConnection device;
	
	protected NMEAReceiveQueue in;
	protected NMEASendQueue out;
	
	public NMEA()
	{
		init();
	}
	
	public NMEA( String devicePort )
	{
		init();
		connect( devicePort );
	}
	
	public NMEA( String devicePort, int baudRate )
	{
		init();
		connect( devicePort, baudRate );
	}
	
	protected void init()
	{
		verbs = new NMEAVerbCollection();
		device = new NMEADeviceConnection();

		in = new NMEAReceiveQueue( device, this );
		out = new NMEASendQueue( device, this );
		
		device.addDeviceListener( this );
		verbs.addVerbListener( this );
		
		this.state = DeviceState.CLOSED;
		
		populateVerbs();
	}
	
	protected void populateVerbs()
	{
		verbs.put( new GPGGA() );
		verbs.put( new GPGLL() );
		verbs.put( new GPGSA() );
		verbs.put( new GPGSV() );
		verbs.put( new GPRMC() );
		verbs.put( new GPZDA() );
	}
	
	public void connect( final String devicePort )
	{
		// anonymous class
		new Thread( new Runnable() {
			public synchronized void run()
			{
				device.connect( devicePort );
				device.beginReceiveAsync();
			}
		}, "NMEA::connect(1)" ).start();
	}
	
	public void connect( final String devicePort, final int baudRate )
	{
		// anonymous class
		new Thread( new Runnable() {
			public synchronized void run()
			{
				device.connect( devicePort, baudRate );
				device.beginReceiveAsync();
			}
		}, "NMEA::connect(2)" ).start();
	}
	
	public void disconnect()
	{
		// disconnect method
		endAsync();
		//device.endReceiveAsync();
		device.disconnect();
	}
	
	public boolean isConnected()
	{
		return ( device.getDeviceState() == DeviceState.OPEN
				 || device.getDeviceState() == DeviceState.ASYNC );
	}


	protected void beginAsync()
	{
/*		if( in == null && out == null )
		{
			in = new NMEAReceiveQueue( device, this );
			out = new NMEASendQueue( device, this );
*/			
			in.beginAsync();
			out.beginAsync();
//		}
	}
	
	protected void endAsync()
	{
/*		if( in != null && out != null )
		{*/
			in.endAsync();
			out.endAsync();
/*			
			in = null;
			out = null;
		}*/
	}
	
	
	public void addDeviceListener( DeviceListener listener )
	{
		device.addDeviceListener( listener );
	}
	
	public void removeDeviceListener( DeviceListener listener )
	{
		device.removeDeviceListener( listener );
	}
	
	public void addVerbListener( NMEAVerbListener listener )
	{
		verbs.addVerbListener( listener );
	}
	
	public void removeVerbListener( NMEAVerbListener listener )
	{
		verbs.removeVerbListener( listener );
	}
	
	public void deviceStateEvent( DeviceConnection obj, DeviceState state )
	{
		this.state = state;
		if( state == DeviceState.OPEN )
		{
			System.out.println( "Starting async on DeviceStateEvent." );
			beginAsync();
		}
		else if( state == DeviceState.ASYNC )
		{
			// should have already begun
		}
		else
		{
			System.out.println( "Ending async on DeviceStateEvent." );
			endAsync();
		}	
	}
	
	public void deviceDataEvent( DeviceConnection obj, DeviceListener.Direction dir, int len, String data )
	{

	}
	
	public void verbEvent( String verb, Object[] args )
	{

	}
	
	public void interpretSentence( NMEASentence sentence )
	{
		if( sentence.testChecksum() && verbs.containsKey( sentence.getVerb() ) )
		{
			verbs.get( sentence.getVerb() ).parse( sentence );
		}
		else
		{
			// checksum error, or was not contained in verb collection
		}
	}
	
	public void sendSentence( NMEASentence sentence )
	{
		out.sendSentence( sentence );
	}
	
	public void sendSentences( NMEASentence... sentences )
	{
		for( NMEASentence sentence : sentences )
		{
			out.sendSentence( sentence );
		}
	}
	
	public NMEAVerb getVerb( String verb )
	{
		return this.verbs.get( verb );
	}
	
	public long getSentProcessedCount()
	{
		return this.out.getProcessedCount();
	}
	
	public long getSentQueueTimeMax()
	{
		return this.out.getQueueTimeMax();
	}
	
	public float getSentQueueTimeAvg()
	{
		return this.out.getQueueTimeAvg();
	}
	
	public int getSentQueueLength()
	{
		return this.out.getQueueLength();
	}
	
	public long getSentOldest()
	{
		return this.out.getOldest();
	}
	
	public long getSentBytes()
	{
		return this.out.getBytes();
	}
	
	public long getRecdProcessedCount()
	{
		return this.in.getProcessedCount();
	}
	
	public long getRecdQueueTimeMax()
	{
		return this.in.getQueueTimeMax();
	}
	
	public float getRecdQueueTimeAvg()
	{
		return this.in.getQueueTimeAvg();
	}
	
	public int getRecdQueueLength()
	{
		return this.in.getQueueLength();
	}
	
	public long getRecdOldest()
	{
		return this.in.getOldest();
	}
	
	public long getRecdBytes()
	{
		return this.in.getBytes();
	}

}
