/*	DeviceConnection.java

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

public interface DeviceConnection
{	
	
	public void connect( String port );
	public void connect( String port, int baudrate );
	public void disconnect();
	public void beginReceiveAsync();
	public void endReceiveAsync();
	public void addDeviceListener( DeviceListener listener );
	public void removeDeviceListener( DeviceListener listener );
	public void send( String data );
	public DeviceState getDeviceState();
	public int getBaudRate();
	public void setBaudRate( int value );

}