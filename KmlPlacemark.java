public class KmlPlacemark {

	private String plName;
	private GeoRegion plRegion;

	public KmlPlacemark( String name, String coordStr ) {
		this.plName = name;

		this.plRegion = new GeoRegion();
		String[] coordSets = coordStr.split( " " );
		for( String coord : coordSets ) {
			String[] s3 = coord.split( ",", 3 );
			double[] d3 = new double[ 3 ];
			this.plRegion.add( new GeoCoord(
				Double.valueOf( s3[ 1 ] ),
				Double.valueOf( s3[ 0 ] ),
				Double.valueOf( s3[ 2 ] )
			) );
		}
	}

	public KmlPlacemark( String name, GeoRegion region ) {
		this.plName = name;
		this.plRegion = region;
	}

	public String getName() {
		return this.plName;
	}

	public void setName( String value ) {
		this.plName = value;
	}

	public GeoRegion getRegion() {
		return this.plRegion;
	}

	public void setRegion( GeoRegion value ) {
		this.plRegion = value;
	}

}
