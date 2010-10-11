public class NMEASentenceQueueOfferFailedException extends Exception {

	private NMEASentence subject;

	public NMEASentenceQueueOfferFailedException( NMEASentence subject ) {
		this.subject = subject;
	}

	public NMEASentence getSubject() {
		return this.subject;
	}

}
