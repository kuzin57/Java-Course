package services.logger;

public class LoggerFactory {
	public ILogger newConsoleLogger() {
		return new ConsoleLogger();
	}
}
