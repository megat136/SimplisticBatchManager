package exceptions;

public class TaskExecutionException extends RuntimeException {


	private static final long serialVersionUID = -5577580703071580647L;

	public TaskExecutionException(){
		super();
	}

	public TaskExecutionException(Exception exception){
		super(exception);
	}
	
	public TaskExecutionException(String message, Exception exception){
		super(message,exception);
		super.getCause();
	}

	public TaskExecutionException(String message) {
		super(message);
	}

}
