package entities.student;

import java.util.ArrayList;
import java.util.HashSet;
import exceptions.InvalidMarkException;
import services.historyKeeper.HistoryKeeperFactory;
import services.historyKeeper.IHistoryKeeper;

public class Student<Mark> {
	private String name;
	private ArrayList<Mark> marksList;
	private HashSet<Mark> marksSet;
	private HashSet<Mark> validMarks;
	private IHistoryKeeper historyKeeper;
	
	{
		historyKeeper = new HistoryKeeperFactory().newSimpleHistoryKeeper();
		marksList = new ArrayList<Mark>();
		name = new String();
		validMarks = new HashSet<Mark>();
	}
	
	public Student() {}
	
	public Student(String name) {
		this.name = name;
	}
	
	public Student(String name, ArrayList<Mark> marks) {
		this.marksList = marks;
		this.name = name;
	}
	
	public Student(Student<Mark> other) {
		copy(other);
	}
	
	private void copy(Student<Mark> other) {
		name = other.name;
		marksList = other.marksList;
		validMarks = other.validMarks;
		historyKeeper = other.historyKeeper;
	}
	
	private boolean isValidMark(Mark mark) {
		return validMarks.size() == 0 || validMarks.contains(mark);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) throws CloneNotSupportedException {
		Student<Mark> copyStudent = new Student<Mark>(this);
		copyStudent.name = new String(name);
		historyKeeper.push(copyStudent);
		name = newName;
	}
	
	public void setValidMarks(ArrayList<Mark> marks) throws CloneNotSupportedException {
		Student<Mark> copyStudent = new Student<Mark>(this);
		copyStudent.validMarks = new HashSet<Mark>(validMarks);
		historyKeeper.push(copyStudent);
		
		validMarks = new HashSet<Mark>(marks);
	}
	
	public HashSet<Mark> getValidMarks() {
		return validMarks;
	}
	
	public void addMarkByIndex(Mark mark, int index) throws InvalidMarkException {	
		if (!isValidMark(mark)) {
			throw new InvalidMarkException();
		}
		
		Student<Mark> copyStudent = new Student<Mark>(this);
		copyStudent.marksList = new ArrayList<Mark>(marksList);
		historyKeeper.push(copyStudent);
		
		marksList.add(index, mark);
	}
	
	public int getMarksLength() {
		return marksList.size();
	}
	
	public void pushMarkBack(Mark mark) throws InvalidMarkException {
		if (!isValidMark(mark)) {
			throw new InvalidMarkException();
		}
		
		Student<Mark> copyStudent = new Student<Mark>(this);
		copyStudent.marksList = new ArrayList<Mark>(marksList);
		historyKeeper.push(copyStudent);

		marksList.add(mark);
	}
	
	public Mark removeMarkByIndex(int index) {
		Student<Mark> copyStudent = new Student<Mark>(this);
		copyStudent.marksList = new ArrayList<Mark>(marksList);
		historyKeeper.push(copyStudent);
		
		return marksList.remove(index);
	}
	
	public void addMark(Mark mark) {
		Student<Mark> copyStudent = new Student<Mark>(this);
		
		if (marksSet == null) {
			copyStudent.marksSet = null;
			marksSet = new HashSet<Mark>();
		} else {
			copyStudent.marksSet = new HashSet<Mark>(marksSet);
		}
		historyKeeper.push(copyStudent);
		
		marksSet.add(mark);
	}
	
	public Mark getMark(int index) {
		return marksList.get(index);
	}
	
	public HashSet<Mark> getMarksSet() {
		HashSet<Mark> copyMarksSet = new HashSet<Mark>();
		for (Mark mark : marksSet) {
			copyMarksSet.add(mark);
		}
		
		return copyMarksSet;
	}
	
	public ArrayList<Mark> getMarks() {
		ArrayList<Mark> copyMarksList = new ArrayList<Mark>();
		for (Mark mark : marksList) {
			copyMarksList.add(mark);
		}
		
		return copyMarksList;
	}
	
	public void cancelLastOperation() {
		copy((Student<Mark>) historyKeeper.pull());
	}
	
	@Override
	public String toString() {
		return new StringBuilder(name).append(": ").append(marksList.toString()).toString();
	}
}
