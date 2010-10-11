import java.util.Iterator;

public class LogFile implements Iterable {

	LogChunk[] chunks;

	public LogFile() {
		chunks = new LogChunk[ 200 ];
	}

	public Iterator< LogChunk > iterator() {
		//return new LogChunkIterator( chunks );
		return null;
	}

}
