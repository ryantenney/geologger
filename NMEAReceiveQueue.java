/*	NMEAReceiveQueue.java

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

public class NMEAReceiveQueue extends NMEASentenceQueue implements DeviceListener
{
	protected DeviceConnection dev;
	protected NMEA nmea;
	
	public NMEAReceiveQueue( DeviceConnection device, NMEA nmeaObj )
	{
		super( 512 );
		super.setName( "NMEAReceiveQueue" );
		dev = device;
		nmea = nmeaObj;
		nmea.addDeviceListener( this );
//		this.start();
	}
	
	public void deviceDataEvent( DeviceConnection obj, DeviceListener.Direction dir, int len, String data )
	{
		try
		{
			if( dir == DeviceListener.Direction.RECEIVE )
			{
				NMEASentence sentence = new NMEASentence( data );
				super.offer( sentence, 1, TimeUnit.SECONDS );
			}
		}
		catch( NMEASentenceQueueOfferFailedException nsqofex )
		{
			// Offer failed handler
		}
		catch( InterruptedException iex )
		{
			// Interrupted handler
		}
	}
	
	public void deviceStateEvent( DeviceConnection obj, DeviceState state )
	{
		
	}
	
	public void run()
	{
		try
		{
			while( nmea.isConnected() )
			{
				nmea.interpretSentence( super.take() );
			}
		}
		catch( InterruptedException iex )
		{
			// interrupted
			System.out.println( "interrupted" );
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
	
}
