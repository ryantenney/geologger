/*	NMEAVerb.java

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

public abstract class NMEAVerb
{
	protected Integer count = 0;
	protected String verb = "";
	protected Boolean updating = false;
	protected long lastUpdated = -1;
	
	protected NMEAVerbCollection owner;
	
	protected abstract void abstractParser( NMEASentence sentence );
		
	protected NMEAVerb( String verbStr )
	{
		verb = verbStr;
	}

	public void parse( NMEASentence sentence )
	{
		if( this.equals( sentence ) )
		{
			abstractParser( sentence );
			lastUpdated = System.currentTimeMillis();
			++count;
		}
	}

	public String queryVerb()
	{
		return verb;
	}
	
	public long getLastUpdated()
	{
		return lastUpdated;
	}
	
	public int getSentenceCount()
	{
		synchronized( count )
		{
			return count;
		}
	}
	
	public void setOwner( NMEAVerbCollection ownerObject )
	{
		owner = ownerObject;
	}

	protected void raiseListener( Object... args )
	{
		if( owner != null )
		{
			owner.raiseListener( verb, args );
		}
	}
	
	public int hashCode()
	{
		return verb.hashCode();
	}
	
	public boolean equals( Object obj )
	{
		if( obj instanceof NMEAVerb )
		{
			NMEAVerb nmea = (NMEAVerb)obj;
			return verb.equalsIgnoreCase( nmea.queryVerb() );
		}
		else if( obj instanceof String )
		{
			String str = (String)obj;
			return verb.equalsIgnoreCase( str );
		}
		else if( obj instanceof NMEASentence )
		{
			NMEASentence sentence = (NMEASentence)obj;
			return verb.equalsIgnoreCase( sentence.getVerb() );
		}
		else
		{
			return false;
		}
	}
	
}
