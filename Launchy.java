/*	Launchy.java

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

public class Launchy
{

	public static void main( String args[] )
	{

		KmlTest();

		// Static object to hold instances of Geologger
		// DEBUGGING ONLY
//		Geologger geo;
//		geo = new Geologger();

//		new LogAnalysis();
//		new PMTKTest( geo.gps );
//		new GPSClock( geo.gps );
		
//		int val = 0x000A127F;
//		int val = 0x000a003f;
//		EnumSet< LogFields > values = LogFields.fromBitwiseInt( val );
//
//		byte[] data = { (byte)0x2F, (byte)0xD7, (byte)0x71, (byte)0x47, (byte)0x01, (byte)0x00, (byte)0xE4, (byte)0x3D, (byte)0xE6, (byte)0x11, (byte)0x07, (byte)0x92, (byte)0x45, (byte)0x40, (byte)0x22, (byte)0xF7, (byte)0x5C, (byte)0xA1, (byte)0x19, (byte)0x63, (byte)0x53, (byte)0xC0, (byte)0x00, (byte)0x00, (byte)0x16, (byte)0x43 };
//		LogDataInputStream ldis = new LogDataInputStream( data );

//		for( LogFields field : values )
//		{
//			System.out.println( field.name() );
//			System.out.println( field.type().getByteLength() );
//		}
	}

	public static void KmlTest()
	{
		String p = "/Users/soyuz/dev/GPS Data/Regions/My Regions.kml";
		KmlFile r = new KmlFile( p );
		r.getPlacemarks();
	}
	
}