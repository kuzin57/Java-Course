package commands;

import java.util.List;

import entities.student.Student;
import exceptions.InvalidArgumentException;
import exceptions.InvalidMarkException;
import services.logger.ILogger;

class CreateStudentCommand implements ICommand {
	private ILogger logger;
	
	public CreateStudentCommand(ILogger logger) {
		this.logger = logger;
	}
	
	public Student<Integer> run(Student<Integer> student, List<String> args) throws InvalidArgumentException {
		if (args.size() == 0) {
			throw new InvalidArgumentException("Укажите имя студента");
		}
		
		student = new Student<Integer>(args.get(0));
		logger.Info("Студент " + args.get(0) + " создан");
		return student;
	}
}

class AddMarksCommand implements ICommand {
	private ILogger logger;
	
	public AddMarksCommand(ILogger logger) {
		this.logger = logger;
	}
	
	public Student<Integer> run(Student<Integer> student, List<String> args) throws InvalidArgumentException {
		if (student == null) {
			throw new InvalidArgumentException("Сначала надо создать студента");
		}
		
		for (String arg : args) {
			try {
				student.pushMarkBack(Integer.parseInt(arg));
			} catch (NumberFormatException e) {
				throw new InvalidArgumentException("Оценка должна быть целым числом");
			} catch (InvalidMarkException e) {
				throw new InvalidArgumentException("Оценка должна быть валидной");
			}
		}

		logger.Info("Студенту " +  student.getName() + " добавлены оценки " + args);
		return student;
	}
}

class RemoveMarkCommand implements ICommand {
	private ILogger logger;
	
	public RemoveMarkCommand(ILogger logger) {
		this.logger = logger;
	}
	
	public Student<Integer> run(Student<Integer> student, List<String> args) throws InvalidArgumentException {
		if (student == null) {
			throw new InvalidArgumentException("Сначала надо создать студента");
		}
		
		if (args.size() == 0) {
			throw new InvalidArgumentException("Укажите в аргументах оценку");
		}
		
		Integer markToRemove;
		try {
			markToRemove = Integer.parseInt(args.get(0));
		} catch (NumberFormatException e) {
			throw new InvalidArgumentException("Оценка должна быть целым числом");
		}

		for (int i = student.getMarksLength()-1; i >= 0; i--) {
			if (markToRemove.equals(student.getMark(i))) {
				logger.Info("Удалена оценка " + student.removeMarkByIndex(i));
				return student;
			}
		}

		logger.Error("Указанной оценки нет");
		return student;
	}
}

class PrintStudentCommand implements ICommand {
	private ILogger logger;
	
	public PrintStudentCommand(ILogger logger) {
		this.logger = logger;
	}
	
	public Student<Integer> run(Student<Integer> student, List<String> args) {
		logger.Info(student.toString());
		return student;
	}
}