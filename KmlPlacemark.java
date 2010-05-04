/*	KmlPlacemark.java
 
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

public class KmlPlacemark
{

	private String plName;
	private GeoRegion plRegion;

	public KmlPlacemark( String name, String coordStr )
	{
		this.plName = name;
		
		this.plRegion = new GeoRegion();
		String[] coordSets = coordStr.split( " " );
		for( String coord : coordSets )
		{
			String[] s3 = coord.split( ",", 3 );
			double[] d3 = new double[ 3 ];
			this.plRegion.add( new GeoCoord(
				Double.valueOf( s3[ 1 ] ),
				Double.valueOf( s3[ 0 ] ),
				Double.valueOf( s3[ 2 ] )
			) );
		}
	}

	public KmlPlacemark( String name, GeoRegion region )
	{
		this.plName = name;
		this.plRegion = region;
	}
	
	public String getName()
	{
		return this.plName;
	}
	
	public void setName( String value )
	{
		this.plName = value;
	}

	public GeoRegion getRegion()
	{
		return this.plRegion;
	}
	
	public void setRegion( GeoRegion value )
	{
		this.plRegion = value;
	}
	
}