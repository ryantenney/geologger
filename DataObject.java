//
//  DataObject.java
//  Geologger
//
//  Created by Ryan Tenney2ndary on 10/16/2007.
//  Copyright 2007 __MyCompanyName__. All rights reserved.
//

public class DataObject
{
	public Number Value;
	public int Offset;
	public byte[] Source;
	
	public DataObject( Number value, int offset )
	{
		Value = value;
		Offset = offset;
	}
}
