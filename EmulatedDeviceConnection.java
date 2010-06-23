/*	EmulatedDeviceConnection.java

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

import java.util.*;
import java.util.concurrent.*;

public class EmulatedDeviceConnection implements DeviceConnection
{

	private int size;
	private int baud;
	private DeviceState state;
	private ArrayBlockingQueue< String > inputQueue, outputQueue;

	protected Thread async;
	protected final Set< DeviceListener > callbacks;

	public EmulatedDeviceConnection( int size )
	{
		this.callbacks = Collections.synchronizedSet( new HashSet< DeviceListener >() );
		this.state = DeviceState.CLOSED;
		this.baud = 38400;
		this.size = size;
		this.inputQueue = new ArrayBlockingQueue< String >( size );
		this.outputQueue = new ArrayBlockingQueue< String >( size );
	}

	public void connect( String port )
	{
		connect();
	}
	
	public void connect( String port, int baudrate )
	{
		connect();
	}
	
	protected void connect()
	{
		if( state.compareTo( DeviceState.CLOSED ) <= 0 )
		{
			deviceStateEvent( DeviceState.OPEN );
		}
	}

	public void disconnect()
	{
		if( state.compareTo( DeviceState.OPEN ) >= 0 )
		{
			async = null;
			deviceStateEvent( DeviceState.CLOSED );
		}
	}

	public void beginReceiveAsync()
	{
		if( async == null && state == DeviceState.OPEN )
		{
			async = new Thread( new Runnable() {
				public void run() {
					//startGetsAsync();
				}			
			}, "EmulatedDeviceConnection::ReceiveAsync" );
			async.start();
			deviceStateEvent( DeviceState.ASYNC );
		}
	}
	
	public void endReceiveAsync()
	{
		if( async != null && state == DeviceState.ASYNC )
		{
			// wait for the thread to finish, mebbe?
			async = null;
			deviceStateEvent( DeviceState.OPEN );
		}
	}
	
	public void addDeviceListener( DeviceListener listener )
	{
		if( listener != null )
		{
			callbacks.add( listener );
		}
	}
	
	public void removeDeviceListener( DeviceListener listener )
	{
		callbacks.remove( listener );
	}
	
	protected void deviceDataEvent( final DeviceListener.Direction dir, final int len, final String data )
	{
		if( validateSentence( data ) )
		{
			final DeviceConnection finalThis = this;
						   
			// anonymous class
			new Thread( new Runnable() {
				public synchronized void run()
				{
					for( DeviceListener listener : callbacks )
					{
						if( listener != null )
						{
							listener.deviceDataEvent( finalThis, dir, len, data );
						}
						else
						{
							callbacks.remove( listener );
						}
					}
				}
			}, "EmulatedDeviceConnection::deviceDataEvent" ).start();
		}
	}
	
	protected void deviceStateEvent( final DeviceState state )
	{
		this.state = state;
		
		final DeviceConnection finalThis = this;
		
		// anonymous class
		new Thread( new Runnable() {
			public synchronized void run()
			{
				for( DeviceListener listener : callbacks )
				{
					if( listener != null )
					{	
						listener.deviceStateEvent( finalThis, state );
					}
					else
					{
						callbacks.remove( listener );
					}
				}
			}
		}, "EmulatedDeviceConnection::deviceStateEvent" ).start();
	}
		
	public void send( String data )
	{
		if( validateSentence( data ) )
		{
			//puts( data );
			deviceDataEvent( DeviceListener.Direction.TRANSMIT, data.length(), data );
		}
	}
	
	public DeviceState getDeviceState()
	{
		return this.state;
	}
	
	public int getBaudRate()
	{
		return this.baud;
	}
	
	public void setBaudRate( int value )
	{
		this.baud = value;
	}

	private final boolean validateSentence( String data )
	{
		int len = data.length();
		return len > 10
			&& data.charAt( 0 ) == 0x24			// $
			&& data.charAt( len - 5 ) == 0x2A	// *
			&& data.charAt( len - 2 ) == 0x0D	// CR
			&& data.charAt( len - 1 ) == 0x0A;	// LF
	}
	
}