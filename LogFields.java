/*	LogSentence.java

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

import java.util.EnumSet;

public enum LogFields
{
	UTC			( 0x000001, LogDataType.LONG ),
	VALID		( 0x000002, LogDataType.WORD ),
	LATITUDE	( 0x000004, LogDataType.DOUBLE ),
	LONGITUDE	( 0x000008, LogDataType.DOUBLE ),
	HEIGHT		( 0x000010, LogDataType.FLOAT ),
	SPEED		( 0x000020, LogDataType.FLOAT ),
	HEADING		( 0x000040, LogDataType.FLOAT ),
	DSTA		( 0x000080, LogDataType.WORD ),
	DAGE		( 0x000100, LogDataType.LONG ),
	PDOP		( 0x000200, LogDataType.WORD ),
	HDOP		( 0x000400, LogDataType.WORD ),
	VDOP		( 0x000800, LogDataType.WORD ),
	NSAT		( 0x001000, LogDataType.WORD ),
	SID			( 0x002000, LogDataType.LONG ),
	ELEVATION	( 0x004000, LogDataType.WORD ),
	AZIMUTH		( 0x008000, LogDataType.WORD ),
	SNR			( 0x010000, LogDataType.WORD ),
	RCR			( 0x020000, LogDataType.WORD ),
	MILISECOND	( 0x040000, LogDataType.WORD ),
	DISTANCE	( 0x080000, LogDataType.DOUBLE );

	private final int value;
	private final LogDataType type;
	
	LogFields( int value, LogDataType type )
	{
		this.value = value;
		this.type = type;
	}
	
	public int value() { return this.value; }
	public LogDataType type() { return this.type; }
	
	public static LogFields getLogField( int value )
	{
		LogFields result = null;
		for( LogFields field : LogFields.values() )
		{
			if( value == field.value() )
			{
				result = field;
			}
		}
		return result;
	}
	
	public static EnumSet< LogFields > fromBitwiseInt( int value )
	{
		EnumSet< LogFields > result = EnumSet.noneOf( LogFields.class );
		for( LogFields field : LogFields.values() )
		{
			if( ( value & field.value() ) == field.value() )
			{
				result.add( field );
			}
		}
		return result;
	}
	
	public static int toBitwiseInt( EnumSet< LogFields > valueSet )
	{
		int result = 0;
		for( LogFields field : valueSet )
		{
			result |= field.value();
		}
		return result;
	}
	
	public static int toBitwiseInt( LogFields... valueSet )
	{
		int result = 0;
		for( LogFields field : valueSet )
		{
			result |= field.value();
		}
		return result;
	}
}