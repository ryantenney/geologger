/*	LogDataType.java

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

public enum LogDataType
{
	BYTE		( 1, Short.class ),
	WORD		( 2, Integer.class ),
	LONG		( 4, Long.class ),
	FLOAT		( 4, Float.class ),
	DOUBLE		( 8, Double.class );

	private final int byteLength;
	private final Class dataClass;
	
	LogDataType( int byteLength, Class dataClass )
	{
		this.byteLength = byteLength;
		this.dataClass = dataClass;
	}
	
	public int getByteLength() { return this.byteLength; }
	public Class getDataClass() { return this.dataClass; }
	
}