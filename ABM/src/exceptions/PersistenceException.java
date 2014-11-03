package exceptions;

public class PersistenceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PersistenceException(){
		super();
	}

	public PersistenceException(Exception exception){
		super(exception);
	}
	
	public PersistenceException(String message, Exception exception){
		super(message,exception);
		super.getCause();
	}

	public PersistenceException(String message) {
		super(message);
	}
}
