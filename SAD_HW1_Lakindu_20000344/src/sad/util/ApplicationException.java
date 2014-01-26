package sad.util;

public class ApplicationException extends Exception {

	public ApplicationException(Exception e) {
		super(e);
	}

	public ApplicationException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}

}
