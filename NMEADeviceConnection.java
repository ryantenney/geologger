public class NMEADeviceConnection extends SerialDeviceConnection {

	protected void deviceDataEvent( final DeviceListener.Direction dir, final int len, final String data ) {
		if( validateSentence( data ) ) {
			super.deviceDataEvent( dir, len, data );
		}
	}

	public void send( String data ) {
		if( validateSentence( data ) ) {
			super.send( data );
		}
	}

	private final boolean validateSentence( String data ) {
		int len = data.length();
		return len > 10
			&& data.charAt(       0 ) == 0x24	// $
			&& data.charAt( len - 5 ) == 0x2A	// *
			&& data.charAt( len - 2 ) == 0x0D	// CR
			&& data.charAt( len - 1 ) == 0x0A;	// LF
	}

}
