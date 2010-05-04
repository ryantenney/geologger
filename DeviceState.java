/*	DeviceState.java

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

public enum DeviceState
{
	ASYNC	(  2 ),
	OPEN	(  1 ),
	CLOSED	(  0 ),
	FAILED	( -1 ),
	CLOSING	( -2 ),
	ATEOF	( -3 ),
	ERROR	( -4 );
	
	private int value;
	
	DeviceState( int value ) { this.value = value; }
	
	public int getValue() { return value; }
	
	static DeviceState getState( int value )
	{
		switch( value )
		{
			case  2:	return ASYNC;
			case  1:	return OPEN;
			default:
			case  0:	return CLOSED;
			case -1:	return FAILED;
			case -2:	return CLOSING;
			case -3:	return ATEOF;
			case -4:	return ERROR;
		}
	}
}