import java.util.*;

public class ByteOps {

	public static boolean compareByte( int b1, int b2 ) {
		return ( b1 & 0xFF ) == ( b2 & 0xFF );
	}

	public static boolean compareByte( int b1, int b2, int mask ) {
		return ( ( b1 & mask ) & 0xFF ) == ( ( b2 & mask ) & 0xFF );
	}

	public static String byteToHexString( int value ) {
		String b = Integer.toHexString( value & 0xFF ).toUpperCase();
		return b.length() == 1 ? "0" + b : b;
	}

	public static String byteToHexString( byte[] value ) {
		StringBuffer s = new StringBuffer( value.length * 2 );
		for( byte b : value ) {
			s.append( byteToHexString( b ) );
		}
		return s.toString();
	}

	/*
	 * Functions designed to read the reversed
	 */

	public static byte readByte( byte[] data, int offset ) throws ArrayIndexOutOfBoundsException {
		return data[ offset ];
	}

	public static int readUnsignedByte( byte[] data, int offset ) throws ArrayIndexOutOfBoundsException {
		return data[ offset ] & 0xFF;
	}

	public static short readShort( byte[] data, int offset ) throws ArrayIndexOutOfBoundsException {
		int retVal = data[ offset ] & 0xFF;
		retVal |= ( data[ ++offset ] & 0xFF ) << 8;
		return (short)retVal;
	}

	public static int readUnsignedShort( byte[] data, int offset ) throws ArrayIndexOutOfBoundsException {
		return readShort( data, offset ) & 0xFFFF;
	}

	public static int readInt( byte[] data, int offset ) throws ArrayIndexOutOfBoundsException {
		int shift = 0;
		int retVal = data[ offset ] & 0xFF;
		retVal |= ( data[ ++offset ] & 0xFF ) << ( ++shift * 8 );
		retVal |= ( data[ ++offset ] & 0xFF ) << ( ++shift * 8 );
		retVal |= ( data[ ++offset ] & 0xFF ) << ( ++shift * 8 );
		return retVal;
	}

	public static long readUnsignedInt( byte[] data, int offset ) throws ArrayIndexOutOfBoundsException {
		return readInt( data, offset ) & 0xFFFFFFFF;
	}

	public static float readFloat( byte[] data, int offset ) throws ArrayIndexOutOfBoundsException {
		return Float.intBitsToFloat( readInt( data, offset ) );
	}

	public static long readLong( byte[] data, int offset ) throws ArrayIndexOutOfBoundsException {
		int shift = 0;
		int retVal = data[ offset ] & 0xFF;
		retVal |= ( data[ ++offset ] & 0xFF ) << ( ++shift * 8 );
		retVal |= ( data[ ++offset ] & 0xFF ) << ( ++shift * 8 );
		retVal |= ( data[ ++offset ] & 0xFF ) << ( ++shift * 8 );
		retVal |= ( data[ ++offset ] & 0xFF ) << ( ++shift * 8 );
		retVal |= ( data[ ++offset ] & 0xFF ) << ( ++shift * 8 );
		retVal |= ( data[ ++offset ] & 0xFF ) << ( ++shift * 8 );
		retVal |= ( data[ ++offset ] & 0xFF ) << ( ++shift * 8 );
		return retVal;
	}

	public static double readDouble( byte[] data, int offset ) throws ArrayIndexOutOfBoundsException {
		return Double.longBitsToDouble( readLong( data, offset ) );
	}

}
