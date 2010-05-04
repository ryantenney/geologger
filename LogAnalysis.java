/*	LogAnalysis.java

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
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class LogAnalysis extends JFrame implements ActionListener
{
	private JButton loadFile, compare, convert, extract, prevButton, nextButton, jumpButton, compareToButton;
	private JLabel csvDisplay, binDisplay, compareDisplay;
	private JScrollPane textDisplayContainer;
	private JTextArea textDisplay;
	private File csvFile, binFile;

	private String[] csvHeaders, csvData;
	private byte[][] binData;
	private int[][] diffData;
	private int sentenceLen;
	
	private int viewIndex;
	
	public LogAnalysis()
	{
		super( "Log Analysis" );
//		this.setDefaultCloseOperation( JFrame. );
		this.getContentPane().setLayout( null );
	
		loadFile = new JButton( "Select Files" );
		loadFile.addActionListener( this );
		loadFile.setBounds( 10, 10, 120, 29 );
		this.getContentPane().add( loadFile );
		
		compare = new JButton( "Compare" );
		compare.addActionListener( this );
		compare.setBounds( 10, 45, 120, 29 );
		this.getContentPane().add( compare );

		convert = new JButton( "Convert" );
		convert.addActionListener( this );
		convert.setBounds( 135, 45, 120, 29 );
		this.getContentPane().add( convert );

		extract = new JButton( "Extract" );
		extract.addActionListener( this );
		extract.setBounds( 260, 45, 120, 29 );
		this.getContentPane().add( extract );
		
		csvDisplay = new JLabel();
		csvDisplay.setFont( new Font( "Courier New", Font.PLAIN, 12 ) );
		csvDisplay.setBounds( 10, 90, 780, 50 );
		this.getContentPane().add( csvDisplay );

		binDisplay = new JLabel();
		binDisplay.setFont( new Font( "Courier New", Font.PLAIN, 12 ) );
		binDisplay.setBounds( 10, 150, 780, 50 );
		this.getContentPane().add( binDisplay );

		compareDisplay = new JLabel();
		compareDisplay.setBounds( 10, 210, 780, 50 );
		compareDisplay.setFont( new Font( "Courier New", Font.PLAIN, 12 ) );
//		this.getContentPane().add( compareDisplay );
		
		textDisplay = new JTextArea();
		textDisplay.setFont( new Font( "Courier New", Font.PLAIN, 12 ) );
		textDisplay.setBounds( 0, 0, 780, 1000 );
		textDisplayContainer = new JScrollPane();
		textDisplayContainer.setBounds( 10, 210, 780, 220 );
		textDisplayContainer.add( textDisplay );
		this.getContentPane().add( textDisplayContainer );
		
		nextButton = new JButton( "Next" );
		nextButton.addActionListener( this );
		nextButton.setBounds( 140, 440, 120, 29 );
		this.getContentPane().add( nextButton );

		prevButton = new JButton( "Prev" );
		prevButton.addActionListener( this );
		prevButton.setBounds( 10, 440, 120, 29 );
		this.getContentPane().add( prevButton );

		jumpButton = new JButton( "Jump..." );
		jumpButton.addActionListener( this );
		jumpButton.setBounds( 270, 440, 120, 29 );
		this.getContentPane().add( jumpButton );
		
		compareToButton = new JButton( "Compare To..." );
		compareToButton.addActionListener( this );
		compareToButton.setBounds( 410, 440, 180, 29 );
		this.getContentPane().add( compareToButton );
		
		setSize( 800, 500 );
		setResizable( false );
		setVisible( true );
	}
	
	public void actionPerformed( ActionEvent e )
	{
		if( e.getSource() == loadFile )
		{
			FileDialog openDialog = new FileDialog( this, "Locate .bin File", FileDialog.LOAD );

			openDialog.setFilenameFilter( new FilenameFilter() {
				public boolean accept( File dir, String name )
				{
					return name.toLowerCase().trim().endsWith( ".bin" );
				}
			} );
			openDialog.show();
			binFile = new File( openDialog.getDirectory(), openDialog.getFile() );
			
			openDialog.setTitle( "Locate .csv File" );
			openDialog.setFilenameFilter( new FilenameFilter() {
				public boolean accept( File dir, String name )
				{
					return name.toLowerCase().trim().endsWith( ".csv" );
				}
			} );
			openDialog.show();
			csvFile = new File( openDialog.getDirectory(), openDialog.getFile() );
			
			parseData();
			
			openDialog = null;
		}
		else if( e.getSource() == compare )
		{
//			compareFiles();
			countOccurencesBin( 13 );
			System.out.println("next");
//			countOccurencesCsv( 1 );
//			System.out.println("next");
//			countOccurencesCsv( 8 );
			System.out.println("done");
		}
		else if( e.getSource() == convert )
		{
			BinFileUtilities.binFileConvert( binFile );
		}
		else if( e.getSource() == extract )
		{
			viewIndex = 0;
			redrawDisplay();
		}
		else if( e.getSource() == prevButton )
		{
			--viewIndex;
			if( viewIndex < 0 )
				viewIndex = 0;
			redrawDisplay();
		}
		else if( e.getSource() == nextButton )
		{
			++viewIndex;
			if( viewIndex >= csvData.length )
				viewIndex = csvData.length - 1;
			redrawDisplay();
		}
		else if( e.getSource() == jumpButton )
		{
			Object result = JOptionPane.showInputDialog( this, "Enter the record number to jump to:", Integer.toString( viewIndex + 1 ) );
			if( result instanceof String )
			{
				int val = Integer.parseInt( (String)result ) - 1;
				if( val >= 0 && val < csvData.length )
				{
					viewIndex = val;
					redrawDisplay();
				}
			}
		}
		else if( e.getSource() == compareToButton )
		{
			Object result = JOptionPane.showInputDialog( this, "Compare this record (# " + ( viewIndex + 1 ) + ") to record number:", Integer.toString( viewIndex + 2 ) );
			if( result instanceof String )
			{
				int val = Integer.parseInt( (String)result ) - 1;
				if( val >= 0 && val < csvData.length )
				{
					compareTo( viewIndex, val );
				}
			}
		}

	}
	
	public void redrawDisplay()
	{
		csvDisplay.setText( csvData[ viewIndex ] );
		binDisplay.setText( ByteOps.byteToHexString( binData[ viewIndex ] ) );
		csvDisplay.revalidate();
		binDisplay.revalidate();
		textDisplay.setText( "" );
		for( DataObject d : newExtractData( binData[ viewIndex ] ) )
		{
			textDisplay.append( Integer.toString( d.Offset ) );
			textDisplay.append( " - " );
			textDisplay.append( ByteOps.byteToHexString( d.Source ) );
			textDisplay.append( ": " );
			textDisplay.append( d.Value.toString() );
			textDisplay.append( "\r\n" );
		}
	}
	
/*	public void compareFilesDiff()
//	{
//		try
//		{
//			for( int i = 0; i < csvData.length; ++i )
//			{
//				
//				for( int k = 0; k < 10; ++k )
//				{
//					String[] line0, line1;
//					boolean[] differs = new boolean[ sentenceLen ];
//					
//					for( int b = 0; b < sentenceLen; ++b )
//					{
////						differs[ b ] = !ByteOps.compareByte( binData[ j ][ b ], binData[ i ][ b ] );
//					}
//					
////					line0 = csvData[ j ].split( "," );
////					line1 = csvData[ i ].split( "," );
//
//					for( int s = 0; s < csvHeaders.length; ++s )
//					{
//						if( !line0[ s ].equals( line1[ s ] ) )
//						{
//							++diffData[ s ][ 0 ];
//							
//							for( int b = 0; b < sentenceLen; ++b )
//							{
//								if( differs[ b ] )
//								{
//									++diffData[ s ][ b + 1 ];
//								}
//							}
//						}
//					}
//				}
//			}
//			
//			StringBuilder sbCsv = new StringBuilder( "<html><table border=0><tr>" );
//			StringBuilder sbBin = new StringBuilder( "<html><table border=0 cellpadding=0><tr>" );
//			StringBuilder sbCompare = new StringBuilder( "<html><table border=0 cellpadding=0>" );
//			
//			for( String header : csvHeaders )
//			{
//				sbCsv.append( "<td>" );
//				sbCsv.append( header );
//				sbCsv.append( "</td>" );
//			}
//			sbCsv.append( "</tr><tr>" );
//			for( String value : csvData[ 0 ].split( "," ) )
//			{
//				sbCsv.append( "<td>" );
//				sbCsv.append( value );
//				sbCsv.append( "</td>" );
//			}
//			sbCsv.append( "</tr></table>" );
//			
//			for( int b = 0; b < sentenceLen; ++b )
//			{
//				sbBin.append( "<td>" );
//				sbBin.append( ByteOps.byteToHexString( binData[ 0 ][ b ] ) );
//				sbBin.append( "</td>" );
//			}
//			sbBin.append( "</tr></table>" );
//
//			for( int s = 0; s < csvHeaders.length; ++s )
//			{
//				sbCompare.append( "<tr><td>" );
//				sbCompare.append( csvHeaders[ s ] );
//				sbCompare.append( "</td>" );
//				for( int b = 0; b < sentenceLen; ++b )
//				{
//					int shading = 255 - (int)( 255 * ( (double)diffData[ s ][ b + 1 ] / (double)diffData[ s ][ 0 ] ) );
////					int shading = 255 - (int)( 255 * ( (double)diffData[ s ][ b + 1 ] / (double)csvData.length ) );
//					String shadingHex = ByteOps.byteToHexString( shading );
//					sbCompare.append( "<td bgcolor=\"#" );
//					sbCompare.append( shadingHex + shadingHex + shadingHex );
//					if( shading < 128 )
//					{
//						sbCompare.append( "\" text=\"#FFFFFF" ); 
//					}
//					sbCompare.append( "\">" );
//					sbCompare.append( ByteOps.byteToHexString( binData[ s ][ b ] ) );
//					sbCompare.append( "</td>" );
//				}
//				sbCompare.append( "</tr>" );
//			}
//			sbCompare.append( "</table>" );
//			
//			csvDisplay.setText( sbCsv.toString() );
//			binDisplay.setText( sbBin.toString() );
//			compareDisplay.setText( sbCompare.toString() );
//			System.out.println( sbCompare.toString() );
//			
//			csvDisplay.revalidate();
//			binDisplay.revalidate();
//			compareDisplay.revalidate();
//		}
//		catch( Exception ex )
//		{
//			ex.printStackTrace();
//		}
//	} */
	
	public void compareTo( int index1, int index2 )
	{
		StringBuilder sbCsv = new StringBuilder( "<html><table border=0><tr>" );
		StringBuilder sbBin = new StringBuilder( "<html><table border=0 cellpadding=0><tr>" );
		
		for( String header : csvHeaders )
		{
			sbCsv.append( "<td>" );
			sbCsv.append( header );
			sbCsv.append( "</td>" );
		}
		sbCsv.append( "</tr><tr>" );
		
		String[] csv1 = csvData[ index1 ].split( "," );
		String[] csv2 = csvData[ index2 ].split( "," );
		
		for( int c = 0; c < csvHeaders.length; ++c )
		{
			sbCsv.append( "<td>" );
			sbCsv.append( csv1[ c ] );
			sbCsv.append( "<br>" );
			sbCsv.append( csv2[ c ] );
			sbCsv.append( "</td>" );
		}
		sbCsv.append( "</tr></table>" );
		
		for( int b = 0; b < sentenceLen; ++b )
		{
			sbBin.append( ByteOps.compareByte( binData[ index1 ][ b ], binData[ index2 ][ b ] ) ? "<td bgcolor=\"#FFFFFF\">" : "<td>" );
			sbBin.append( ByteOps.byteToHexString( binData[ index1 ][ b ] ) );
			sbBin.append( "<br>" );
			sbBin.append( ByteOps.byteToHexString( binData[ index2 ][ b ] ) );
			sbBin.append( "</td>" );
		}
		sbBin.append( "</tr></table>" );

		csvDisplay.setText( sbCsv.toString() );
		binDisplay.setText( sbBin.toString() );
		
		csvDisplay.revalidate();
		binDisplay.revalidate();
	}
	
	public void compareFiles()
	{
	// skip this day long process
	//return;
		try
		{
			for( int i = 0; i < csvData.length; ++i )
			{
				DataObject[] values = newExtractData( binData[ i ] );
				
				for( int k = 0; k < 10; ++k )
				{
					int j = (int)( Math.random() * csvData.length );
					String[] line0, line1;
					boolean[] differs = new boolean[ sentenceLen ];
					
					for( int b = 0; b < sentenceLen; ++b )
					{
						differs[ b ] = !ByteOps.compareByte( binData[ j ][ b ], binData[ i ][ b ] );
					}
					
					line0 = csvData[ j ].split( "," );
					line1 = csvData[ i ].split( "," );

					for( int s = 0; s < csvHeaders.length; ++s )
					{
						if( !line0[ s ].equals( line1[ s ] ) )
						{
							++diffData[ s ][ 0 ];
							
							for( int b = 0; b < sentenceLen; ++b )
							{
								if( differs[ b ] )
								{
									++diffData[ s ][ b + 1 ];
								}
							}
						}
					}
				}
			}
			
			StringBuilder sbCsv = new StringBuilder( "<html><table border=0><tr>" );
			StringBuilder sbBin = new StringBuilder( "<html><table border=0 cellpadding=0><tr>" );
			StringBuilder sbCompare = new StringBuilder( "<html><table border=0 cellpadding=0>" );
			
			for( String header : csvHeaders )
			{
				sbCsv.append( "<td>" );
				sbCsv.append( header );
				sbCsv.append( "</td>" );
			}
			sbCsv.append( "</tr><tr>" );
			for( String value : csvData[ 0 ].split( "," ) )
			{
				sbCsv.append( "<td>" );
				sbCsv.append( value );
				sbCsv.append( "</td>" );
			}
			sbCsv.append( "</tr></table>" );
			
			for( int b = 0; b < sentenceLen; ++b )
			{
				sbBin.append( "<td>" );
				sbBin.append( ByteOps.byteToHexString( binData[ 0 ][ b ] ) );
				sbBin.append( "</td>" );
			}
			sbBin.append( "</tr></table>" );

			for( int s = 0; s < csvHeaders.length; ++s )
			{
				sbCompare.append( "<tr><td>" );
				sbCompare.append( csvHeaders[ s ] );
				sbCompare.append( "</td>" );
				for( int b = 0; b < sentenceLen; ++b )
				{
					int shading = 255 - (int)( 255 * ( (double)diffData[ s ][ b + 1 ] / (double)diffData[ s ][ 0 ] ) );
//					int shading = 255 - (int)( 255 * ( (double)diffData[ s ][ b + 1 ] / (double)csvData.length ) );
					String shadingHex = ByteOps.byteToHexString( shading );
					sbCompare.append( "<td bgcolor=\"#" );
					sbCompare.append( shadingHex + shadingHex + shadingHex );
					if( shading < 128 )
					{
						sbCompare.append( "\" text=\"#FFFFFF" ); 
					}
					sbCompare.append( "\">" );
					sbCompare.append( ByteOps.byteToHexString( binData[ s ][ b ] ) );
					sbCompare.append( "</td>" );
				}
				sbCompare.append( "</tr>" );
			}
			sbCompare.append( "</table>" );
			
			csvDisplay.setText( sbCsv.toString() );
			binDisplay.setText( sbBin.toString() );
			compareDisplay.setText( sbCompare.toString() );
			System.out.println( sbCompare.toString() );
			
			csvDisplay.revalidate();
			binDisplay.revalidate();
			compareDisplay.revalidate();
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
	
	public void parseData()
	{
		try
		{
			BufferedReader csvIn = new BufferedReader( new FileReader( csvFile ) );
			ArrayList< String > lineBuffer = new ArrayList< String >();
			csvHeaders = csvIn.readLine().split( "," );
			while( csvIn.ready() )
			{
				lineBuffer.add( csvIn.readLine() );
			}
			csvData = lineBuffer.toArray( new String[ 0 ] );
			csvIn.close();
			csvIn = null;
			
			FileInputStream binIn = new FileInputStream( binFile );
			byte[] byteBuffer = new byte[ (int)binFile.length() ];
			binIn.read( byteBuffer );
			binIn.close();
			binIn = null;
			
			byteBuffer = BinFileUtilities.trimBin( byteBuffer );
			sentenceLen = (int)( byteBuffer.length / csvData.length );
			binData = new byte[ csvData.length ][ sentenceLen ];
			diffData = new int[ csvHeaders.length ][ sentenceLen + 1 ];
			
			for( int i = 0; i < csvData.length; ++i )
			{
				System.arraycopy( byteBuffer, i * sentenceLen, binData[ i ], 0, sentenceLen );
			}
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
	
	public int[] countOccurencesBin( int offset )
	{
		int[] count = new int[ 256 ];
		for( int i = 0; i < binData.length; ++i )
		{
			++count[ binData[ i ][ offset ] & 0xFF ];
		}
		for( int x = 0; x < count.length; ++x )
		{
			System.out.println( ByteOps.byteToHexString( x ) + ": " + count[ x ] );
		}
		return new int[ 0 ];
	}
	
	public byte[] countOccurencesCsv( int column )
	{
		HashMap< String, Integer > mapy = new HashMap< String, Integer >();
		for( int i = 0; i < csvData.length; ++i )
		{
			String[] lineEntries = csvData[ i ].split( "," );
			if( !mapy.containsKey( lineEntries[ column ] ) )
			{
				mapy.put( lineEntries[ column ], new Integer( 1 ) );
			}
			else
			{
				int count = mapy.get( lineEntries[ column ] ).intValue() + 1;
				mapy.remove( lineEntries[ column ] );
				mapy.put( lineEntries[ column ], new Integer( count ) );
			}
		}
		for( Map.Entry< String, Integer > e : mapy.entrySet() )
		{
			System.out.println( e.getKey() + " - " + e.getValue().toString() );
		}
		return new byte[ 0 ];
	}
	
	public static DataObject[] extractData( byte[] byteData )
	{
		ArrayList< DataObject > dataOut = new ArrayList< DataObject >();
		for( int x = 0; x < byteData.length; ++x )
		{
			try
			{
				// 8-bit
				DataObject b = new DataObject( new Byte( ByteOps.readByte( byteData, x ) ), x );
				DataObject ub = new DataObject( new Integer( (Byte)b.Value & 0xFF ), x );
				b.Source = new byte[ 1 ]; System.arraycopy( byteData, x, b.Source, 0, 1 );
				ub.Source = new byte[ 1 ]; System.arraycopy( byteData, x, ub.Source, 0, 1 );
				dataOut.add( b );
				dataOut.add( ub );
				
				// 16-bit
				DataObject s = new DataObject( new Short( ByteOps.readShort( byteData, x ) ), x );
				DataObject us = new DataObject( new Integer( (Short)s.Value & 0xFFFF ), x );
				s.Source = new byte[ 2 ]; System.arraycopy( byteData, x, s.Source, 0, 2 );
				us.Source = new byte[ 2 ]; System.arraycopy( byteData, x, us.Source, 0, 2 );
				dataOut.add( s );
				dataOut.add( us );
				
				// 32-bit
				DataObject i = new DataObject( new Integer( ByteOps.readInt( byteData, x ) ), x );
				DataObject f = new DataObject( new Float( Float.intBitsToFloat( (Integer)i.Value ) ), x );
				i.Source = new byte[ 4 ]; System.arraycopy( byteData, x, i.Source, 0, 4 );
				f.Source = new byte[ 4 ]; System.arraycopy( byteData, x, f.Source, 0, 4 );
				dataOut.add( i );
				dataOut.add( f );
				
				// 64-bit
				DataObject l = new DataObject( new Long( ByteOps.readLong( byteData, x ) ), x );
				DataObject d = new DataObject( new Double( Double.longBitsToDouble( (Long)l.Value ) ), x );
				l.Source = new byte[ 8 ]; System.arraycopy( byteData, x, l.Source, 0, 8 );
				d.Source = new byte[ 8 ]; System.arraycopy( byteData, x, d.Source, 0, 8 );
				dataOut.add( l );
				dataOut.add( d );
			}
			catch( ArrayIndexOutOfBoundsException aiex )
			{
				// this is a perfectly normal, and beautiful part of life
			}
		}

		return dataOut.toArray( new DataObject[ 0 ] );
	}
	
	public static DataObject[] newExtractData( byte[] byteData )
	{
		ArrayList< DataObject > dataOut = new ArrayList< DataObject >();
		DataInputStream dat = new DataInputStream( new ByteArrayInputStream( byteData ) );
		for( int x = 0; x < byteData.length; ++x )
		{
			try
			{
				dat.mark( 0 );
			
				// 8-bit
				DataObject b = new DataObject( new Byte( dat.readByte() ), x );
				DataObject ub = new DataObject( new Integer( (Byte)b.Value & 0xFF ), x );
				b.Source = new byte[ 1 ]; System.arraycopy( byteData, x, b.Source, 0, 1 );
				ub.Source = new byte[ 1 ]; System.arraycopy( byteData, x, ub.Source, 0, 1 );
				dataOut.add( b );
				dataOut.add( ub );
				dat.reset();
				
				// 16-bit
				DataObject s = new DataObject( new Short( Short.reverseBytes( dat.readShort() ) ), x );
				DataObject us = new DataObject( new Integer( (Short)s.Value & 0xFFFF ), x );
				s.Source = new byte[ 2 ]; System.arraycopy( byteData, x, s.Source, 0, 2 );
				us.Source = new byte[ 2 ]; System.arraycopy( byteData, x, us.Source, 0, 2 );
				dataOut.add( s );
				dataOut.add( us );
				dat.reset();
				
				// 32-bit
				DataObject i = new DataObject( new Integer( Integer.reverseBytes( dat.readInt() ) ), x );
				DataObject f = new DataObject( new Float( Float.intBitsToFloat( (Integer)i.Value ) ), x );
				i.Source = new byte[ 4 ]; System.arraycopy( byteData, x, i.Source, 0, 4 );
				f.Source = new byte[ 4 ]; System.arraycopy( byteData, x, f.Source, 0, 4 );
				dataOut.add( i );
				dataOut.add( f );
				dat.reset();
				
				// 64-bit
				DataObject l = new DataObject( new Long( Long.reverseBytes( dat.readLong() ) ), x );
				DataObject d = new DataObject( new Double( Double.longBitsToDouble( (Long)l.Value ) ), x );
				l.Source = new byte[ 8 ]; System.arraycopy( byteData, x, l.Source, 0, 8 );
				d.Source = new byte[ 8 ]; System.arraycopy( byteData, x, d.Source, 0, 8 );
				dataOut.add( l );
				dataOut.add( d );
				dat.reset();

				dat.skip( 1 );
			}
			catch( IOException ioex )
			{
			
			}
		}
		
		return dataOut.toArray( new DataObject[ 0 ] );
	}
}
