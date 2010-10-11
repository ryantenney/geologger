import java.util.*;

public class NMEAVerbCollection extends Hashtable< String, NMEAVerb > {

	protected final Set< NMEAVerbListener > callbacks;

	public NMEAVerbCollection() {
		super();
		callbacks = Collections.synchronizedSet( new HashSet< NMEAVerbListener >() );
	}

	public void put( NMEAVerb putObject) {
		putObject.setOwner( this );
		super.put( putObject.queryVerb(), putObject );
	}

	public NMEAVerb remove( String key ) {
		NMEAVerb removeObject = super.remove( key );
		removeObject.setOwner( null );
		return removeObject;
	}

	public void raiseListener( final String verb, final Object... args ) {
		new Thread( new Runnable() {
			public synchronized void run() {
				for( NMEAVerbListener thisListener : callbacks ) {
					try {
						thisListener.verbEvent( verb, args );
					} catch( Exception ex ) {
						// ignore exception unless it is
					}
				}
			}
		} ).start();
	}

	public void addVerbListener( NMEAVerbListener listener ) {
		callbacks.add( listener );
	}

	public void removeVerbListener( NMEAVerbListener listener ) {
		callbacks.remove( listener );
	}

}
