package exceptions;

public class BatTaskException extends RuntimeException {

	private static final long serialVersionUID = 6148064754393840380L;
	/**
	 * This Exception class is thrown from BatTask.java for errors occured during scheduled jobs.
	 */
	public BatTaskException(){
		super();
	}

	public BatTaskException(Exception exception){
		super(exception);
	}
	
	public BatTaskException(String message, Exception exception){
		super(message,exception);
		super.getCause();
	}

	public BatTaskException(String message) {
		super(message);
	}

}
