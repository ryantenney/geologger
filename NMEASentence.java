/*	NMEASentence.java

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

public class NMEASentence
{
	
	private String cachedSentence;
	private String verb;
	private String[] args;
	
	private long timeOffered, timeTaken;
	
	public NMEASentence()
	{
		initVars();
	}
	
	public NMEASentence( String newSentence )
	{		
		initVars();
		
		cachedSentence = newSentence;
		
		parse();
		
		//purgeCached();
		cachedSentence = construct( true );
	}
	
	public NMEASentence( String verb, String[] args )
	{
		initVars();
		
		this.verb = verb;
		this.args = args;
		
		cachedSentence = construct( true );
	}
	
	public NMEASentence( String verb, String pmtkType, String[] args )
	{
		initVars();
		
		this.verb = verb + pmtkType;
		this.args = args;
	}

	private void initVars()
	{
		verb = "";
		cachedSentence = "";
		
		timeOffered = -1;
		timeTaken = -1;	
	}
	
	public void offered()
	{
		timeOffered = System.currentTimeMillis();
	}
	
	public long taken()
	{
		timeTaken = System.currentTimeMillis();
		return timeTaken - timeOffered;
	}

	public long getTimeOffered()
	{
		return timeOffered;
	}
	
	public long getTimeTaken()
	{
		return timeTaken;
	}
	
	public long getTimeQueued()
	{
		return timeTaken - timeOffered;
	}
	
	public String getSentence()
	{
		if( cachedSentence.length() == 0 )
		{
			cachedSentence = construct( true );
		}
		return cachedSentence;
	}
	
	public void setSentence( String value )
	{
		cachedSentence = value;
		parse();
	}
	
	public void setVerb( String value )
	{
		verb = value;
		purgeCached();
	}

	public String getVerb()
	{
		return verb.startsWith( "PMTK" ) ? "PMTK" : verb;
	}
	
	public static String getVerb( String sentence )
	{
		String verb = sentence.substring( sentence.indexOf( "$" ) + 1,  sentence.indexOf( "," ) );
		return verb.startsWith( "PMTK" ) ? "PMTK" : verb;
	}
	
	public String getPMTKType()
	{
		if( verb.startsWith( "PMTK" ) && verb.length() == 7 )
		{
			return verb.substring( 4, 7 );
		}
		else
		{
			return new String( "" );
		}
	}
	
	public static String getPMTKType( String sentence )
	{
		String verb = getVerb( sentence );
		if( verb.startsWith( "PMTK" ) && verb.length() == 7 )
		{
			return verb.substring( 4, 7 );
		}
		else
		{
			return new String( "" );
		}
	}
	
	public String[] getArgs()
	{
		return args;
	}
	
	public void setArgs( String[] value )
	{
		args = value;
		purgeCached();
	}
	
	public int length()
	{
		return getSentence().length();
	}
	
	public static String[] getArgs( String sentence )
	{
		return sentence.substring( sentence.indexOf( "," ) + 1,  sentence.indexOf( "*" ) ).split( "," );
	}
	
	public String computeChecksum()
	{
		return computeChecksum( construct( false ) );
	}
	
	public static String computeChecksum( String nmeaSentence )
	{
		byte[] nmeaBytes = nmeaSentence.getBytes();
		int dollar = nmeaSentence.indexOf( "$" );
		int asterisk = nmeaSentence.indexOf( "*" );
		int checksum = nmeaBytes[ dollar + 1 ];
		for( int i = dollar + 2; i < asterisk; i++ )
		{
			checksum ^= nmeaBytes[ i ];
		}
		String result = Integer.toHexString( checksum ).toUpperCase();
		return ( result.length() == 1 ? "0" + result : result );
	}
	
	public boolean testChecksum()
	{
		if( cachedSentence.length() > 0 )
		{
			return testChecksum( cachedSentence );
		}
		else
		{
			return true;
		}
	}
	
	public static boolean testChecksum( String nmeaSentence )
	{
		int asterisk = nmeaSentence.indexOf( "*" );
		if( asterisk > 0 && asterisk + 3 <= nmeaSentence.length() )
		{
			String given = nmeaSentence.substring( asterisk + 1, asterisk + 3 );
			String computed = computeChecksum( nmeaSentence );
			return computed.equals( given );
		}
		else
		{
			return false;
		}       
	}
	
	private void parse()
	{
		try
		{
			verb = cachedSentence.substring( cachedSentence.indexOf( "$" ) + 1, cachedSentence.indexOf( "," ) );
			args = cachedSentence.substring( cachedSentence.indexOf( "," ) + 1, cachedSentence.indexOf( "*" ) ).split( "," );
		}
		catch( StringIndexOutOfBoundsException sioobex )
		{
/*			int length = cachedSentence.length();
			if( length > 45 )
			{
				System.out.print( cachedSentence.substring( 0, 20) );
				System.out.print( " ... " );
				System.out.println( cachedSentence.substring( length - 20 ) );
			}
			else
			{
				System.out.println( cachedSentence );
			}
*/
		}
	}
	
	private void purgeCached()
	{
		cachedSentence = new String( "" );
	}
	
	private String construct( boolean checksum )
	{
		StringBuilder sb = new StringBuilder();
		sb.append( '$' );
		sb.append( verb );
		for( int i = 0; i < args.length; i++ )
		{
			sb.append( ',' );
			sb.append( args[ i ] );
		}
		sb.append( '*' );
		if( checksum )
		{
			sb.append( computeChecksum( sb.toString().toUpperCase() ) );
			sb.append( "\r\n" );
		}
		return sb.toString().toUpperCase();
	}
	
	public String toString()
	{
		return getSentence();
	}
	
	public boolean equals( Object o )
	{
		if( o instanceof NMEASentence )
		{
			NMEASentence sentence = (NMEASentence)o;
			return sentence.getSentence().equals( getSentence() );
		}
		else if( o instanceof NMEAVerb )
		{
			NMEAVerb verb = (NMEAVerb)o;
			return getVerb().equals( verb.queryVerb() );
		}
		else if( o instanceof String )
		{
			String str = (String)o;
			return getVerb().equals( str );
		}
		else
		{
			return false;
		}
	}
	
}
