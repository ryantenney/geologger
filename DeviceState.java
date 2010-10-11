public enum DeviceState {

	ASYNC	(  2 ),
	OPEN	(  1 ),
	CLOSED	(  0 ),
	FAILED	( -1 ),
	CLOSING	( -2 ),
	ATEOF	( -3 ),
	ERROR	( -4 );

	private int value;

	DeviceState( int value ) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	static DeviceState getState( int value ) {
		switch( value ) {
			case  2:	return ASYNC;
			case  1:	return OPEN;
			default:
			case  0:	return CLOSED;
			case -1:	return FAILED;
			case -2:	return CLOSING;
			case -3:	return ATEOF;
			case -4:	return ERROR;
		}
	}

}
