/*	Geologger.java

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
import javax.swing.border.*;

public class Geologger extends JFrame implements ActionListener, DeviceListener, NMEAVerbListener
{
	// UI Elements
	private JButton connect, disconnect, download, erase, analyze;
	private JTextArea textoutput;
	private JComboBox portselector;
	private JProgressBar downloadProgress;
	private JScrollPane statListContainer, textOutputContainer;
	private JCheckBox enableLogging, dumpSerialData;
	private JList stats;
	private JLabel coordinates, satelliteTable;
	private JMenu windowMenu;
	private JMenuBar menus;
	
	// GPS objects
	//debug/*
	public BTQ1000 gps;
	//debug
//debug	private BTQ1000 gps;

	private NMEASatelliteMap satsInView;
	
	String[] menuWindowItems = new String[] { "Geologger", "PMTK Test", "Log Analysis", "GPS Clock" };
	
	public Geologger()
	{
		super( "Geologger" );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.getContentPane().setLayout( null );

		gps = new BTQ1000();
		gps.addDeviceListener( this );
		gps.addVerbListener( this );
	
		portselector = new JComboBox();
		portselector.addItem( "Bluetooth" );
		portselector.addItem( "USB" );
		portselector.addItem( "<html><hr width=123 align=left noshade>" );
		portselector.addItem( "Other..." );
//		portselector.addItem( "Edit Connections..." );
		portselector.setBounds( 10, 10, 175, 25 );
		this.getContentPane().add( portselector );
		
		stats = new JList();
				
		statListContainer = new JScrollPane( stats );
		statListContainer.setBounds( 10, 75, 530, 100 );
		this.getContentPane().add( statListContainer );
		
//		sentstats = new JLabel( "Sent Stats" );
//		sentstats.setBounds( 10, 74, 800, 29 );
//		sentstats.setFont( new Font( "Courier New", Font.PLAIN, 12 ) );
//		this.getContentPane().add( sentstats );
//		
//		recdstats = new JLabel( "Rec'd Stats" );
//		recdstats.setBounds( 10, 103, 800, 29 );
//		recdstats.setFont( new Font( "Courier New", Font.PLAIN, 12 ) );
//		this.getContentPane().add( recdstats );
//
//		coordinates = new JLabel( "Coordinates: " );
//		coordinates.setBounds( 10, 132, 800, 29 );
//		coordinates.setFont( new Font( "Courier New", Font.PLAIN, 12 ) );
//		this.getContentPane().add( coordinates );

		enableLogging = new JCheckBox();
		enableLogging.setBounds( 10, 185, 150, 25 );
		enableLogging.setText( "Log to File..." );
		enableLogging.addActionListener( this );
		this.getContentPane().add( enableLogging );

		dumpSerialData = new JCheckBox();
		dumpSerialData.setBounds( 140, 185, 150, 25 );
		dumpSerialData.setText( "Dump Serial Data" );
		dumpSerialData.addActionListener( this );
		this.getContentPane().add( dumpSerialData );
		
		downloadProgress = new JProgressBar( 0, 1000 );
		downloadProgress.setBounds( 10, 44, 300, 25 );
		downloadProgress.setStringPainted( true );
		this.getContentPane().add( downloadProgress );
		
		connect = new JButton( "Connect" );
		connect.addActionListener( this );
		connect.setBounds( 190, 6, 120, 29 );
		this.getContentPane().add( connect );
		
		disconnect = new JButton( "Disconnect" );
		disconnect.addActionListener( this );
		disconnect.setBounds( 315, 6, 120, 29 );
		this.getContentPane().add( disconnect );
		
		download = new JButton( "Download" );
		download.addActionListener( this );
		download.setBounds( 315, 40, 120, 29 );
		this.getContentPane().add( download );
		
		erase = new JButton( "Erase" );
		erase.addActionListener( this );
		erase.setBounds( 440, 40, 120, 29 );
		this.getContentPane().add( erase );
		
		analyze = new JButton( "Analyze" );
		analyze.addActionListener( this );
		analyze.setBounds( 440, 6, 120, 29 );
		this.getContentPane().add( analyze );
		
		textoutput = new JTextArea();
		textoutput.setEditable( false );
		textoutput.setFont( new Font( "Courier New", Font.PLAIN, 12 ) );
		
		textOutputContainer = new JScrollPane( textoutput );
		textOutputContainer.setBounds( 10, 220, 530, 200 );
		this.getContentPane().add( textOutputContainer );
		
		satelliteTable = new JLabel();
		satelliteTable.setFont( new Font( "Courier New", Font.PLAIN, 12 ) );
		satelliteTable.setBounds( 550, 220, 220, 245 );
		satelliteTable.setVerticalAlignment( SwingConstants.TOP );
		this.getContentPane().add( satelliteTable );

		satsInView = new NMEASatelliteMap( gps );
		satsInView.setMode( NMEASatellite.Mode.SINE );
		satsInView.setLabelsVisible( true );
		satsInView.setScaledSatellites( false );
		satsInView.setBounds( 590, 10, 200, 200 );
		gps.addVerbListener( satsInView );
		this.getContentPane().add( satsInView );
		
		windowMenu = new JMenu( "Windows" );		

		ActionListener openWindow = new ActionListener() {
			public void actionPerformed( ActionEvent event )
			{
				System.out.println( event.getActionCommand() );
			}
		};
		
		JMenuItem item;
		
		for( String menuItem : menuWindowItems )
		{
			windowMenu.add( item = new JMenuItem( menuItem ) );
			item.addActionListener( openWindow );
		}
		
		menus = new JMenuBar();
		menus.add( windowMenu );
		
		setJMenuBar( menus );
		
		//pack();
		
		setSize( 800, 500 );
		setResizable( false );
		setVisible( true );
	}
	
	public void deviceDataEvent( DeviceConnection obj, DeviceListener.Direction dir, int len, String data )
	{
		textoutput.append( ( dir == DeviceListener.Direction.RECEIVE ? ">> " : "<< " ) + data );
		
		textOutputContainer.getViewport().setViewPosition( new Point( 0, textoutput.getHeight() + 100 ) );
		
/*		sentstats.setText( String.format( "Sent stats: Proc'd: %s, MaxTime: %s, AvgTime: %s, Len: %s, Bytes: %s, Oldest: %s", gps.getSentProcessedCount(), gps.getSentQueueTimeMax(), gps.getSentQueueTimeAvg(), gps.getSentQueueLength(), gps.getSentBytes(), gps.getSentOldest() ) );
		recdstats.setText( String.format( "Recd stats: Proc'd: %s, MaxTime: %s, AvgTime: %s, Len: %s, Bytes: %s, Oldest: %s", gps.getRecdProcessedCount(), gps.getRecdQueueTimeMax(), gps.getRecdQueueTimeAvg(), gps.getRecdQueueLength(), gps.getRecdBytes(), gps.getRecdOldest() ) );

		sentstats.revalidate();
		recdstats.revalidate();				*/

/*		Thread[] tds = new Thread[ 32 ];
		Thread.enumerate( tds );
		for( Thread t : tds )
		{
			if( t != null )
			{
				System.out.println( t.getName() );				
			}
		}
		System.out.println("\\\\"); */
	}
	
	public void deviceStateEvent( DeviceConnection obj, DeviceState state )
	{
		textoutput.append( "%% " + state + "\r\n" );
	}
	
	public void verbEvent( String verb, Object[] args )
	{
		if( verb.equals( "PMTK" ) )
		{
			int val = (int)( ( gps.getDownloadProgress() * 1000 ) / 0x00200000 );
			downloadProgress.setIndeterminate( false );
			downloadProgress.setValue( val );
		}
		else if( verb.equals( "GPRMC" ) && args.length >= 1 )
		{
			GPRMC.DataPacket rmc = (GPRMC.DataPacket) args[ 0 ];
			coordinates.setText( "Coordinates: " + rmc.toString() );
			coordinates.revalidate();
		}
		else if( verb.equals( "GPGSV" ) && args.length >= 1 )
		{
			NMEASatellite[] sats = (NMEASatellite[]) args;
			StringBuilder sb = new StringBuilder();
			sb.append( "<html><table width=300 borderwidth=1 noshade cellspacing=0 borderwidth=1>" );
			sb.append( "<tr style=\"font-weight:bold;\">" );
			sb.append( "<td width=\"25%\">PRN</td>" );
			sb.append( "<td width=\"25%\">Elevation</td>" );
			sb.append( "<td width=\"25%\">Azimuth</td>" );
			sb.append( "<td width=\"25%\">SNR</td></tr>" );
			
/*
			try
			{
*/
				for( NMEASatellite thisSat : sats )
				{
					sb.append( "<tr><td>" );
					sb.append( thisSat.getPRN() );
					sb.append( "</td><td>" );
					sb.append( thisSat.getElevation() );
					sb.append( "</td><td>" );
					sb.append( thisSat.getAzimuth() );
					sb.append( "</td><td>" );
					sb.append( thisSat.getSNR() );
					sb.append( "</td></tr>" );
				}
/*
			}
			catch( Exception ex )
			{
				System.out.println("Error");
			}
			finally
			{
*/
				sb.append( "</table>" );
				satelliteTable.setText( sb.toString() );
				satelliteTable.revalidate();
//			}
		}
	}
	
	public void actionPerformed( ActionEvent e )
	{
		Object o = e.getSource();
		if( o == connect && !gps.isConnected() )
		{
			Object selected = portselector.getSelectedItem();
			if( selected instanceof String )
			{
				String portChoice = (String)selected;
				if( portChoice.equalsIgnoreCase( "bluetooth" ) )
				{
					gps.connect( BTQ1000.ConnectionMode.BLUETOOTH );
				}
				else if( portChoice.equalsIgnoreCase( "usb" ) )
				{
					gps.connect( BTQ1000.ConnectionMode.USB );
				}
				else if( portChoice.equalsIgnoreCase( "other..." ) )
				{
					Object result = JOptionPane.showInputDialog( this, "Enter port name, followed by baud rate (ie /dev/cu.iBT-GPS:38400):" );
					if( result instanceof String )
					{
						String strRes = (String) result;
						if( strRes.indexOf( ":" ) >= 0 )
						{
							String[] params = strRes.split( ":", 2 );
							gps.connect( params[ 0 ], Integer.parseInt( params[ 1 ] ) );
						}
						else
						{
							gps.connect( strRes );
						}
					}
				}
			}
		}
		else if( o == disconnect && gps.isConnected() )
		{
			gps.disconnect();
		}
		else if( o == download && gps.isConnected() )
		{
			downloadProgress.setIndeterminate( true );
			FileDialog saveDialog = new FileDialog( this, "Save Log As...", FileDialog.SAVE );
			saveDialog.show();
			downloadProgress.setValue( 0 );
			gps.beginDownloadLogData( new File( saveDialog.getDirectory(), saveDialog.getFile() ), BTQ1000.LogFormat.BIN );
		}
		else if( o == erase && gps.isConnected() )
		{
			downloadProgress.setIndeterminate( true );
			gps.eraseDevice();
		}
		else if( o == analyze )
		{
			new LogAnalysis();
		}
	}
	
}