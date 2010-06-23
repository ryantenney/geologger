/*	XmlFile.java
 
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

import java.io.*;
import javax.xml.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class XmlFile
{

	protected Document doc;
	protected DocumentBuilder docBuilder;
	protected DocumentBuilderFactory docBuilderFactory;
	
	public XmlFile()
	{
		try
		{
			init();
		}
		catch( ParserConfigurationException pcex )
		{
		}
		
		doc = docBuilder.newDocument();
	}

	public XmlFile( File file )
	{
		try
		{
			init();

			doc = docBuilder.parse( file );
		}
		catch( ParserConfigurationException pcex )
		{
		}
		catch( SAXException saxex )
		{
		}
		catch( IOException ioex )
		{
		}
	}
	
	public XmlFile( String data )
	{
		try
		{
			init();
		
			doc = docBuilder.parse( data );
		}
		catch( ParserConfigurationException pcex )
		{
		}
		catch( SAXException saxex )
		{
		}
		catch( IOException ioex )
		{
		}
	}
	
	private void init() throws ParserConfigurationException
	{
		docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilderFactory.setIgnoringElementContentWhitespace( true );
		
		docBuilder = docBuilderFactory.newDocumentBuilder();
	}

}