package services.historyKeeper;

public class HistoryKeeperFactory {
	public IHistoryKeeper newSimpleHistoryKeeper() {
		return new SimpleHistoryKeeper();
	}
}
