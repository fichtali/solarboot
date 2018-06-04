package emx.solar.pack.exception;

public class EmailExistsException extends Exception {

	private static final long serialVersionUID = -8973434671557748585L;

	public EmailExistsException(String name) {
		super(name);
	}
}
