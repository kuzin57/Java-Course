package services.logger;

public interface ILogger {
	public void Info(String message);
	public void Error(String message);
	public String InputMessage(String message);
}
