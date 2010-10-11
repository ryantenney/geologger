public interface DeviceConnection {

	public void connect( String port );
	public void connect( String port, int baudrate );
	public void disconnect();
	public void beginReceiveAsync();
	public void endReceiveAsync();
	public void addDeviceListener( DeviceListener listener );
	public void removeDeviceListener( DeviceListener listener );
	public void send( String data );
	public DeviceState getDeviceState();
	public int getBaudRate();
	public void setBaudRate( int value );

}
