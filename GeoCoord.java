/*	GeoCoord.java

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

import java.awt.geom.Point2D;

public class GeoCoord extends Point2D.Double
{

	private double a;

	public GeoCoord( double lat, double lon )
	{
		super.y = lat;
		super.x = lon;
		this.a = 0;
	}
	
	public GeoCoord( double lat, double lon, double alt )
	{
		super.y = lat;
		super.x = lon;
		this.a = alt;
	}

	public double getLatitude()
	{
		return super.y;
	}
	
	public void setLatitude( double value )
	{
		super.y = value;
	}
	
	public double getLongitude()
	{
		return super.x;
	}
	
	public void setLongitude( double value )
	{
		super.x = value;
	}
	
	public double getAltitude()
	{
		return this.a;
	}
	
	public void setAltitude( double value )
	{
		this.a = value;
	}

	public static double distanceSq( double x1, double y1, double x2, double y2 )
	{
		x1 -= x2;
		y1 -= y2;
		return (x1 * x1 + y1 * y1);
	}
	
	public static double distance( double x1, double y1, double x2, double y2 )
	{
		x1 -= x2;
		y1 -= y2;
		return Math.sqrt(x1 * x1 + y1 * y1);
	}
	
	public double distanceSq( double px, double py )
	{
		px -= getX();
		py -= getY();
		return (px * px + py * py);
	}

	public double distanceSq( Point2D pt )
	{
		double px = pt.getX() - this.getX();
		double py = pt.getY() - this.getY();
		return (px * px + py * py);
	}
	
	public double distance( double px, double py )
	{
		px -= getX();
		py -= getY();
		return Math.sqrt(px * px + py * py);
	}
	
	public double distance(Point2D pt)
	{
		double px = pt.getX() - this.getX();
		double py = pt.getY() - this.getY();
		return Math.sqrt(px * px + py * py);
	}
	
}