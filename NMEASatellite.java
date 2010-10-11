public class NMEASatellite {

	public static enum Mode {
		LINEAR,
		SINE,
		LOG,
		SQRT;
	}

	private String prn;
	private int elev, azim, snr;
	private Mode mode;

	public NMEASatellite() {
		this.prn = "";
		this.elev = 0;
		this.azim = 0;
		this.snr = 0;
		this.mode = Mode.LINEAR;
	}

	public String getPRN() {
		return this.prn;
	}

	public void setPRN( String value ) {
		this.prn = value;
	}

	public int getElevation() {
		return this.elev;
	}

	public void setElevation( int value ) {
		this.elev = value;
	}

	public int getAzimuth() {
		return this.azim;
	}

	public void setAzimuth( int value ) {
		this.azim = value;
	}

	public int getSNR() {
		return this.snr;
	}

	public void setSNR( int value ) {
		this.snr = value;
	}

	public Mode getMode() {
		return this.mode;
	}

	public void setMode( Mode mode ) {
		this.mode = mode;
	}

	public boolean isTracking() {
		return this.snr == 0;
	}

	public double getMapX() {
		return getMapX( this.mode );
	}

	public double getMapX( Mode mode ) {
		return getRadius( mode ) * Math.sin( Math.toRadians( this.azim ) );
	}

	public double getMapY() {
		return getMapY( this.mode );
	}

	public double getMapY( Mode mode ) {
		return getRadius( mode ) * Math.cos( Math.toRadians( this.azim ) );
	}

	public double getRadius() {
		return getRadius( this.mode );
	}

	public double getRadius( Mode mode ) {
		switch( mode ) {
			default:
			case LINEAR:
				return 1 - ( this.elev / 90.0 );
			case SINE:
				return Math.cos( Math.toRadians( this.elev ) );
			case LOG:
				return Math.log( 90 - this.elev ) / Math.log( 90.0 );
			case SQRT:
				return Math.sqrt( 90 - this.elev ) / Math.sqrt( 90.0 );
		}
	}

	public double getMapZ() {
		return Math.cos( Math.toRadians( this.elev ) );
	}

}
