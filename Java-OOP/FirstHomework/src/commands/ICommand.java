package commands;

import java.util.List;

import entities.student.Student;
import exceptions.InvalidArgumentException;

public interface ICommand {
	public Student<Integer> run(Student<Integer> student, List<String> args) throws InvalidArgumentException;
}