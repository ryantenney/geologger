import java.util.*;

public class PMTK extends NMEAVerb {

	public static class DataPacket {

		int address;
		byte[] bytedata;

		public DataPacket( int offset, byte[] data ) {
			address = offset;
			bytedata = data;
		}

		public int getOffset() {
			return address;
		}

		public void setOffset( int value ) {
			address = value;
		}

		public int getLength() {
			return bytedata.length;
		}

		public byte[] getData() {
			return bytedata;
		}

		public void setData( byte[] value ) {
			bytedata = value;
		}
	}

	private byte[] logData;
	private boolean[] blockStatus;
	private int bytesComplete;

	public PMTK() {
		super( "PMTK" );

		logData = new byte[ 0x00200000 ];
		blockStatus = new boolean[ 0x00200000 ];
		bytesComplete = 0;

		Arrays.fill( logData, 0x00000000, 0x00200000, (byte)0x00 );
		Arrays.fill( blockStatus, 0x00000000, 0x00200000, false );
	}

	protected void abstractParser( NMEASentence sentence ) {
		String pmtkType = sentence.getPMTKType();
		String args[] = sentence.getArgs();
		if( pmtkType.equals( "182" ) && args[ 0 ].equals( "8" ) )
		{
			int address = Integer.parseInt( args[ 1 ], 16 );
			int length = args[ 2 ].length() / 2;
/*			Arrays.fill( blockStatus, address, address + length, true );
			bytesComplete += length;
			raiseListener( new Integer( bytesComplete ) ); */
			raiseListener( new DataPacket( address, readHexToByteArray( args[ 2 ] ) ) );
		}
	}

	private byte[] readHexToByteArray( String hexData ) {
		byte[] output = new byte[ hexData.length() / 2 ];
		for( int i = 0; i < hexData.length(); i += 2 )
		{
			output[ i / 2 ] = (byte)( Integer.parseInt( hexData.substring( i, i + 2 ), 16 ) & 0xFF );
		}
		return output;
	}

	private void readHexToByteArray( String srcHex, byte[] destBin, int destOffset ) {
		for( int i = 0; i < srcHex.length(); i += 2 ) {
			destBin[ destOffset++ ] = (byte)( Integer.parseInt( srcHex.substring( i, i + 2 ), 16 ) & 0xFF );
		}
	}

	public boolean isComplete() {
		boolean retval = blockStatus[ 0 ];
		for( int i = 1; i < blockStatus.length; ++i ) {
			retval &= blockStatus[ i ];
			if( retval == false ) { break; }
		}
		return retval;
	}

	public float percentComplete() {
		float blocksComplete = 0.0f;
		for( int i = 0; i < blockStatus.length; ++i ) {
			if( blockStatus[ i ] ) {
				++blocksComplete;
			}
		}
		return blocksComplete / (float)blockStatus.length;
	}

}
