/*	GPGLL.java

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

public class GPGLL extends NMEAVerb
{
	public GPGLL()
	{
		super( "GPGLL" );
	}
	
	protected void abstractParser( NMEASentence sentence )
	{
	}

}