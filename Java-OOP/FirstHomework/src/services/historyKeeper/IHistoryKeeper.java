package services.historyKeeper;

public interface IHistoryKeeper {
	public void push(Object obj);
	public Object pull();
}
