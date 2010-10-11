import java.io.*;

public class BinFileUtilities {

	public static void binFileConvert( File inFile ) {
		FileInputStream in;
		FileWriter out;

		try {
			in = new FileInputStream( inFile );
			out = new FileWriter( new File( inFile.toString() + ".trim" )  );

			byte[] data = new byte[ (int)inFile.length() ];

			in.read( data );

			out.write( binConvert( trimBin( data ) ) );
			out.flush();
			out.close();

		} catch (IOException ioex) {
			// eh...
		}
    }

	public static String binConvert( byte[] byteData ) {
		StringBuilder sb = new StringBuilder( byteData.length * 2 );

		for( int j = 0; j < byteData.length; ++j ) {
			String b = Integer.toHexString( byteData[ j ] & 0xFF ).toUpperCase();
			sb.append( b.length() == 1 ? "0" + b : b );
		}

		return sb.toString();
    }

	public static byte[] trimBin( byte[] in ) {
		int fileEnd, blockStart;

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		// A 16 byte set of data that crops up randomly throughout the file. Unable to determine the use
		// Example set of data here and the mask it should fit, so as to remove it from the data stream
		int[] miscelany = new int[] { 0xAA, 0xAA, 0xAA, 0xAA, 0xAA, 0xAA, 0xAA, 0x00, 0x00, 0x00, 0x00, 0x00, 0xBB, 0xBB, 0xBB, 0xBB };
		int[] miscMask =  new int[] { 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0xFF, 0xFF };

		for( fileEnd = 0x001FFFFF; fileEnd >= 0; --fileEnd ) {
			if( !ByteOps.compareByte( in[ fileEnd ], 0xFF ) ) {
				++fileEnd;
				break;
			}
		}

		fileEnd = (int)Math.ceil( (double)fileEnd / (double)0x00010000 ) * 0x00010000;

		for( blockStart = 0x00000000; blockStart < fileEnd; blockStart += 0x00010000 ) {
			int start = blockStart + 0x00000200;
			int end;

			for( end = blockStart + 0x0000FFFF; end >= start; --end ) {
				if( !ByteOps.compareByte( in[ end ], 0xFF ) ) {
					++end;
					break;
				}
			}

			for( int i = start; i < end; ++i ) {
				if( ByteOps.compareByte( in[ i ], miscelany[ 0 ], miscMask[ 0 ] ) && i + miscelany.length <= end ) {
					boolean matchSeq = true;

					for( int j = 1; j < miscelany.length; ++j ) {
						matchSeq &= ByteOps.compareByte( in[ i + j ], miscelany[ j ], miscMask[ j ] );
					}

					if( matchSeq ) {
						i += miscelany.length - 1;
						continue;
					}
				}

				out.write( in[ i ] & 0xFF );
			}
		}

		return out.toByteArray();
	}

}
