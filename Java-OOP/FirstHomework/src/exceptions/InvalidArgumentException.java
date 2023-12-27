package exceptions;

import java.io.IOException;

public class InvalidArgumentException extends IOException {
	private static final long serialVersionUID = 1L;
	private String messageText;
	
	public InvalidArgumentException(String message) {
		messageText = message;
	}
	
	@Override
	public String getMessage() {
		return messageText;
	}
}
