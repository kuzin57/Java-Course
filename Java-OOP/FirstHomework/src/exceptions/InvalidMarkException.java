package exceptions;

import java.io.IOException;

public class InvalidMarkException extends IOException {
	private static final long serialVersionUID = 1L;
	private static final String messageText = "Invalid mark";
		
	@Override
	public String getMessage() {
		return messageText;
	}
}
