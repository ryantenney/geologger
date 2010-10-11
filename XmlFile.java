import java.io.*;
import javax.xml.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class XmlFile {

	protected Document doc;
	protected DocumentBuilder docBuilder;
	protected DocumentBuilderFactory docBuilderFactory;

	public XmlFile()
	{
		try {
			init();
		} catch( ParserConfigurationException pcex ) {}

		doc = docBuilder.newDocument();
	}

	public XmlFile( File file ) {
		try {
			init();

			doc = docBuilder.parse( file );
		} catch( ParserConfigurationException pcex ) {
		} catch( SAXException saxex ) {
		} catch( IOException ioex ) {}
	}

	public XmlFile( String data ) {
		try {
			init();

			doc = docBuilder.parse( data );
		} catch( ParserConfigurationException pcex ) {
		} catch( SAXException saxex ) {
		} catch( IOException ioex ) {}
	}

	private void init() throws ParserConfigurationException {
		docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilderFactory.setIgnoringElementContentWhitespace( true );

		docBuilder = docBuilderFactory.newDocumentBuilder();
	}

}
