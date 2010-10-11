//import java.lang.reflect.*;
//
//public class LogDataInputStream
//{
//
//	int pos, len, mark;
//	byte[] data;
//
//	public LogDataInputStream( byte[] data )
//	{
//		this.data = data;
//		this.pos = 0;
//		this.mark = 0;
//		this.len = data.length;
//	}
//
//	public Type read( LogDataType type )
//	{
//		//byte[] b
//		int b = read( bytetype.getByteLength() );
//
//		switch( type )
//		{
//			case BYTE:
//				return new Byte( b & 0xFF );
//			case WORD:
//				return new Short( b & 0xFFFF );
//			case LONG:
//				return new Integer( b );
//			case FLOAT:
//				return new Float( Float.intBitsToFloat( b ) );
//			case DOUBLE:
//				return new Double( Double.longBitsToDouble( b ) );
//		}
//
//		return null;
//	}
//
//	public int read( int readlen )
//	{
//		int out = 0;
//		for( int i = readlen; i > 0; i-- )
//		{
//			out = ( out << 8 ) | ( this.data[ pos++ ] & 0xFF );
//		}
//	}
//
////	public byte[] read( int readlen )
////	{
////		byte[] out = new byte[ readlen ];
////		for( int i = readlen; i > 0; i-- )
////		{
////			out[ i ] = this.data[ pos++ ];
////		}
////	}
//
//	public void mark()
//	{
//		this.mark = this.pos;
//	}
//
//	public void reset()
//	{
//		this.pos = this.mark;
//	}
//
//}
