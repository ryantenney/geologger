public enum LogDataType {

	BYTE		( 1, Short.class   ),
	WORD		( 2, Integer.class ),
	LONG		( 4, Long.class    ),
	FLOAT		( 4, Float.class   ),
	DOUBLE		( 8, Double.class  );

	private final int byteLength;
	private final Class dataClass;

	LogDataType( int byteLength, Class dataClass ) {
		this.byteLength = byteLength;
		this.dataClass = dataClass;
	}

	public int getByteLength() {
		return this.byteLength;
	}

	public Class getDataClass() {
		return this.dataClass;
	}

}
