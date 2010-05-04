/*	NMEASendQueue.java

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

public class NMEASendQueue extends NMEASentenceQueue
{
	private DeviceConnection device;
	private NMEA nmea;

	public NMEASendQueue( DeviceConnection dev, NMEA nmeaObj )
	{
		super( 512 );
		super.setName( "NMEASendQueue" );
		device = dev;
		nmea = nmeaObj;
//		this.start();
	}	

	public void sendSentence( NMEASentence data )
	{
		super.offer( data );
	}
	
	public void run()
	{
		try
		{
			while( nmea.isConnected() )
			{
				device.send( super.take().getSentence() );
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
