/*	NMEASatelliteMap.java

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

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class NMEASatelliteMap extends JPanel implements NMEAVerbListener
{

	private static final int mapRadius = 90;
	private static final int satRadius = 10;
	private static final int totalRadius = mapRadius + satRadius;
	
	private boolean labelsVisible;
	private boolean scaledSats;
	private NMEASatellite.Mode mode;
	private Font labelFont;
	
	private NMEA nmea;
	private NMEASatellite[] sats;
	
	public NMEASatelliteMap( NMEA obj )
	{
		super();
		
		nmea = obj;
		labelsVisible = true;
		scaledSats = false;
		labelFont = new Font( "Verdana", Font.BOLD, 10 );
		mode = NMEASatellite.Mode.LINEAR;

		sats = new NMEASatellite[ 0 ];
	}
	
	public NMEASatellite.Mode getMode()
	{
		return mode;
	}
	
	public void setMode( NMEASatellite.Mode value )
	{
		mode = value;
	}
	
	public boolean getLabelsVisible()
	{
		return labelsVisible;
	}
	
	public void setLabelsVisible( boolean value )
	{
		labelsVisible = value;
	}
	
	public boolean getScaledSatellites()
	{
		return scaledSats;
	}
	
	public void setScaledSatellites( boolean value )
	{
		scaledSats = value;
	}
	
	public void verbEvent( String verb, Object[] args )
	{
		if( verb == "GPGSV" )
		{
			if( args instanceof NMEASatellite[] )
			{
				sats = ( NMEASatellite[] )args;
				super.repaint();
			}
		}
	}
	
	private Rectangle placeSatellite( NMEASatellite thisSat )
	{
		Point p = centerSatellite( thisSat );
		int radius = scaledSats ? (int)( thisSat.getRadius() * satRadius ) : satRadius;
		return new Rectangle( p.x - radius, p.y - radius, 2 * radius, 2 * radius );
	}
	
	private Point centerSatellite( NMEASatellite thisSat )
	{
		return new Point( (int)( thisSat.getMapX( mode ) * mapRadius ) + totalRadius,
						  -(int)( ( thisSat.getMapY( mode ) * mapRadius ) - totalRadius ) );
	}
	
	private Point centerStringAt( String s, Point p, Graphics g )
	{
		FontMetrics fm = g.getFontMetrics();
		Rectangle2D.Float size = (Rectangle2D.Float)fm.getStringBounds( s, g );
		return new Point( (int)Math.round( p.x - size.width / 2.0 ), 
					  (int)Math.round( p.y + ( size.height - fm.getDescent() ) / 2.0 ) );
	}
	
	private void drawStringCenteredAt( String s, Point p, Graphics g )
	{
		FontMetrics fm = g.getFontMetrics();
		Rectangle2D.Float size = (Rectangle2D.Float)fm.getStringBounds( s, g );
		g.drawString( s, (int)Math.round( p.x - size.width / 2.0 ), 
					  (int)Math.round( p.y + ( size.height - fm.getDescent() ) / 2.0 ) );
	}
	
	public void paint( Graphics g )
	{
		// tell the superclass to paint itself first
		super.paint( g );
		
		// fill background of satellite map
		g.setColor( Color.WHITE );
		g.fillOval( satRadius, satRadius, mapRadius * 2, mapRadius * 2 );
		
		// draw outline and inner reference circles
		g.setColor( Color.BLACK );
		g.drawOval( satRadius, satRadius, mapRadius * 2, mapRadius * 2 );
		g.drawOval( satRadius + mapRadius / 2, satRadius + mapRadius / 2, mapRadius, mapRadius);
		
		// draw compass rose
		g.setFont( new Font( "Arial Black", Font.BOLD, 12 ) );
		drawStringCenteredAt( "N", new Point( satRadius + mapRadius, satRadius ), g );
		drawStringCenteredAt( "E", new Point( satRadius + mapRadius * 2, satRadius + mapRadius ), g );
		drawStringCenteredAt( "S", new Point( satRadius + mapRadius, satRadius + mapRadius * 2 ), g );
		drawStringCenteredAt( "W", new Point( satRadius, satRadius + mapRadius ), g );

		// set font to prepare to draw label
		g.setFont( labelFont );
		
		for( NMEASatellite thisSat : sats )
		{
			try
			{
				Rectangle coord = placeSatellite( thisSat );
				g.setColor( thisSat.isTracking() ? Color.RED : Color.GREEN );
				g.fillOval( coord.x, coord.y, coord.height, coord.width );
				g.setColor( Color.BLACK );
				g.drawOval( coord.x, coord.y, coord.height, coord.width );

				// prevent labels from printing if the sats scale
				// if( labelsVisible & !( labelsVisible & scaledSats ) )	// unnecessarily complex logic
				if( labelsVisible & !scaledSats )							// really simple logic
				{
					drawStringCenteredAt( thisSat.getPRN(), centerSatellite( thisSat ), g );
				}
			}
			catch( Exception ex )
			{
//				throw new NMEASatelliteMapException( ex ); 
				System.out.println( "Exception while painting satellites" );
				ex.printStackTrace();
			}
		}
	}
}