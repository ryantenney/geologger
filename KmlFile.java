import java.io.*;
import java.util.*;
import javax.xml.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;

public class KmlFile extends XmlFile {

	public KmlFile( File file ) {
		super( file );
	}

	public KmlFile( String data ) {
		super( data );
	}

	public Map< String, KmlPlacemark > getPlacemarks() {
		Element e;
		String name, coords;
		KmlPlacemark place;
		Map< String, KmlPlacemark > places = new HashMap< String, KmlPlacemark >();
		NodeList nodes = doc.getElementsByTagName( "Placemark" );

		for( int i = 0; i < nodes.getLength(); i++ ) {
			e = (Element)nodes.item( i );
			name = e.getElementsByTagName( "name" ).item( 0 ).getTextContent();
			coords = e.getElementsByTagName( "coordinates" ).item( 0 ).getTextContent();;
			place = new KmlPlacemark( name, coords );
			places.put( name, place );
			//System.out.println( name + ": " + place.getRegion().getBoundingBox().toString() );
			java.awt.geom.Rectangle2D.Double rect = place.getRegion().getBoundingBox();
			String o =
				Double.toString( rect.getX() ) + "," +
				Double.toString( rect.getY() ) + ",0 " +
				Double.toString( rect.getX() + rect.getWidth() ) + "," +
				Double.toString( rect.getY() ) + ",0 " +
				Double.toString( rect.getX() + rect.getWidth() ) + "," +
				Double.toString( rect.getY() + rect.getHeight() ) + ",0 " +
				Double.toString( rect.getX() ) + "," +
				Double.toString( rect.getY() + rect.getHeight() ) + ",0 ";

			System.out.println( "<Placemark><name>" + name + "</name><styleUrl>#msn_ylw-pushpin</styleUrl><Polygon><tessellate>1</tessellate><outerBoundaryIs><LinearRing><coordinates>" + o + "</coordinates></LinearRing></outerBoundaryIs></Polygon></Placemark>" );
		}

		return places;
	}

}
