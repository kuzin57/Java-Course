package services.historyKeeper;

import java.util.Stack;

class SimpleHistoryKeeper implements IHistoryKeeper {
	private Stack<Object> savedStates;
	
	SimpleHistoryKeeper() {
		savedStates = new Stack<Object>();
	}
	
	public void push(Object obj) {
		savedStates.add(obj);
	}
	
	public Object pull() {
		return savedStates.pop();
	}
}