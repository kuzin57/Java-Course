package exceptions;

import java.io.IOException;

public class NoMarksFoundException extends IOException {
	private static final long serialVersionUID = 1L;
	private static final String messageText = "No marks found";
		
	@Override
	public String getMessage() {
		return messageText;
	}
}