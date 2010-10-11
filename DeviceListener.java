public interface DeviceListener {

	public enum Direction {
		TRANSMIT, RECEIVE;
	}

	public void deviceDataEvent( DeviceConnection obj, Direction dir, int len, String data );
	public void deviceStateEvent( DeviceConnection obj, DeviceState state );

}
