/*	NMEADeviceConnection.java

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

public class NMEADeviceConnection extends SerialDeviceConnection
{

	protected void deviceDataEvent( final DeviceListener.Direction dir, final int len, final String data )
	{
		if( validateSentence( data ) )
		{
			super.deviceDataEvent( dir, len, data );
		}
	}
	
	public void send( String data )
	{
		if( validateSentence( data ) )
		{
			super.send( data );
		}
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