import java.util.*;
import java.util.concurrent.*;

public abstract class NMEASentenceQueue extends Thread {

	private ArrayBlockingQueue< NMEASentence > queue;

	private long bytes, processedSentences, maxTime, totalTime;

	public NMEASentenceQueue( int size ) {
		queue = new ArrayBlockingQueue< NMEASentence >( size );
	}

	protected NMEASentence take() throws NMEASentenceQueueTakeFailedException, InterruptedException {
		// variable to hold the amount of time sentence was in queue
		long time;

		NMEASentence result = null;

		try {
			// take new sentence, or wait until one is available
			result = queue.take();
		} catch( InterruptedException iex ) {
			throw iex;
		} catch( Exception ex ) {
			//System.out.println( "Take error" );
			throw new NMEASentenceQueueTakeFailedException();
		}

		synchronized( result ) {
			// mark the time the sentence is taken
			time = result.taken();
		}

		// keep stats on sentences and time in queue
		++processedSentences;
		if( result.getTimeOffered() != -1 && result.getTimeTaken() != -1 ) {
			this.totalTime += time;
			bytes += result.length();
			if( time > this.maxTime) {
				this.maxTime = time;
			}
		}

		return result;
	}

	protected boolean offer( NMEASentence obj ) {
		synchronized( obj ) {
			boolean result = this.queue.offer( obj );
			if( result ) {
				obj.offered();
			}
			return result;
		}
	}

	protected boolean offer( NMEASentence obj, long timeout, TimeUnit unit ) throws NMEASentenceQueueOfferFailedException, InterruptedException {
		synchronized( obj ) {
			boolean result = queue.offer( obj, timeout, unit );
			if( result ) {
				obj.offered();
			} else {
				//System.out.println( "offer failed: " + obj.getSentence().substring( 0, obj.length() > 32 ? 30 : obj.length() - 2 ) );
				throw new NMEASentenceQueueOfferFailedException( obj );
			}
			return result;
		}
	}

	public void beginAsync() {
		this.start();
	}

	public void endAsync() {
		this.stop();
	}

	public long getProcessedCount() {
		return this.processedSentences;
	}

	public long getQueueTimeMax() {
		long oldest = this.getOldest();
		if( oldest > this.maxTime ) {
			this.maxTime = oldest;
		}
		return this.maxTime;
	}

	public float getQueueTimeAvg() {
		return (float)this.totalTime / (float)this.processedSentences;
	}

	public int getQueueLength() {
		return this.queue.size();
	}

	public long getOldest() {
		long oldest = 0;
		synchronized( this.queue ) {
			NMEASentence top = this.queue.peek();

			if( top != null ) {
				oldest = System.currentTimeMillis() - top.getTimeOffered();
			}
		}
		return oldest;
	}

	public long getBytes() {
		return this.bytes;
	}

}
