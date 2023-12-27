package app;

import exceptions.InvalidArgumentException;
import services.logger.ILogger;
import services.logger.LoggerFactory;
import commands.CommandFactory;
import commands.ICommand;
import entities.student.Student;

import java.io.Console;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StudentApp {	

	private enum AppCommand {
		CreateStudent,
		AddMarks,
		RemoveMark,
		PrintStudent;
		
		public static AppCommand fromInteger(int x) {
			switch (x) {
			case 1:
				return CreateStudent;
			case 2:
				return AddMarks;
			case 3:
				return RemoveMark;
			default:
				return PrintStudent;
			}
		}
	}
	
	private HashMap<AppCommand, ICommand> commandsHandlers;
	private ILogger logger;
	
	public StudentApp() {
		logger = new LoggerFactory().newConsoleLogger();
		CommandFactory factory = new CommandFactory();
		commandsHandlers = new HashMap<AppCommand, ICommand>();
		commandsHandlers.put(AppCommand.CreateStudent, factory.newCreateStudentCommand(logger));
		commandsHandlers.put(AppCommand.AddMarks, factory.newAddMarksCommand(logger));
		commandsHandlers.put(AppCommand.RemoveMark, factory.newRemoveMarkCommand(logger));
		commandsHandlers.put(AppCommand.PrintStudent, factory.newPrintStudentCommand(logger));
	}
	
	public static void main(String[] args) {			
		StudentApp app = new StudentApp();
		app.run();
	}
	
	public void run() {
		logger.Info("Добро пожаловать в приложение Студент!");
		Student<Integer> student = null;
		Console console = System.console();
		while (true) {
			logger.Info("Выберите действие: \n1 - создать студента с именем \n2 - добавить одну (или несколько) оценок ранее созданному студенту \n3 - удалить последнюю оценку с указанным значением \n4 - напечатать студента на экран");

			AppCommand cmd;
			try {
				int cmdNumber = Integer.parseInt(console.readLine(logger.InputMessage("Введите действие: ")));
				cmd = AppCommand.fromInteger(cmdNumber);
			} catch (NumberFormatException e) {
				logger.Error("Введите число!");
				continue;
			}
			
			List<String> args = null;
			if (cmd != AppCommand.PrintStudent) {
				args = Arrays.asList(console.readLine(logger.InputMessage("Введите аргументы: ")).split(" "));
			}
			
			try {
				student = commandsHandlers.get(cmd).run(student, args);
			} catch (InvalidArgumentException e) {
				logger.Error(e.getMessage());
			}
		}
	}
	
}

