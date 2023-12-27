package commands;

import services.logger.ILogger;

public class CommandFactory {
	public ICommand newCreateStudentCommand(ILogger logger) {
		return new CreateStudentCommand(logger);
	}
	
	public ICommand newAddMarksCommand(ILogger logger) {
		return new AddMarksCommand(logger);
	}
	
	public ICommand newRemoveMarkCommand(ILogger logger) {
		return new RemoveMarkCommand(logger);
	}
	
	public ICommand newPrintStudentCommand(ILogger logger) {
		return new PrintStudentCommand(logger);
	}
}
