package entities.student;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

import exceptions.InvalidMarkException;

public class StudentTest {

	@Test
	public void testStudent() {
		Student<Integer> st = new Student<Integer>();
		assertNotNull(st);
	}

	@Test
	public void testStudentString() {
		Student<Integer> st = new Student<Integer>("Ivan");
		assertEquals(st.getName(), "Ivan");
	}

	@Test
	public void testStudentStringArrayListOfMark() {
		Student<Integer> st = new Student<Integer>("Ivan", new ArrayList<Integer>());
		assertNotNull(st.getMarks());
		assertEquals(st.getName(), "Ivan");
	}

	@Test
	public void testStudentStudentOfMark() throws InvalidMarkException {
		Student<Integer> st = new Student<Integer>("Ivan");
		st.pushMarkBack(5);
		st.pushMarkBack(6);
		st.pushMarkBack(7);
		Student<Integer> other = new Student<Integer>(st);
		assertEquals(st.getName(), other.getName());
		assertEquals(st.getMarks(), other.getMarks());
		assertNotNull(other);
	}

	@Test
	public void testGetName() {
		Student<Integer> st = new Student<Integer>("Ivan");
		assertEquals(st.getName(), "Ivan");
	}

	@Test
	public void testSetName() throws CloneNotSupportedException {
		Student<Integer> st = new Student<Integer>();
		st.setName("Ivan");
		assertEquals(st.getName(), "Ivan");
	}

	@Test
	public void testSetValidMarks() throws CloneNotSupportedException {
		ArrayList<Integer> validMarks = new ArrayList<Integer>();
		validMarks.add(5);
		validMarks.add(6);
		
		Student<Integer> st = new Student<Integer>();
		st.setValidMarks(validMarks);
		
		var validMarksTest = st.getValidMarks();
		for (Integer mark : validMarks) {
			System.out.println("Mark:" + mark);
			assertTrue(validMarksTest.contains(mark));
		}
		assertNotNull(st.getValidMarks());
	}

	@Test
	public void testAddMarkByIndex() throws InvalidMarkException {
		Student<Integer> st = new Student<Integer>();
		st.addMarkByIndex(5, 0);
		st.addMarkByIndex(6, 1);
		
		var marks = st.getMarks();
		assertTrue(marks.size() == 2);
		assertTrue(marks.get(0).equals(5));
		assertTrue(marks.get(1).equals(6));
	}

	@Test
	public void testMarksLength() throws InvalidMarkException {
		Student<Integer> st = new Student<Integer>();
		st.addMarkByIndex(5, 0);
		st.addMarkByIndex(6, 1);
		
		assertTrue(st.getMarksLength() == 2);
	}

	@Test
	public void testPushMarkBack() throws InvalidMarkException {
		Student<Integer> st = new Student<Integer>();
		st.pushMarkBack(5);
		
		var marks = st.getMarks();
		assertTrue(marks.size() == 1);
		assertTrue(marks.get(marks.size()-1).equals(5));
	}

	@Test
	public void testRemoveMarkByIndex() throws InvalidMarkException {
		ArrayList<Integer> marks = new ArrayList<Integer>();
		marks.add(5);
		marks.add(6);
		marks.add(7);
		
		Student<Integer> st = new Student<Integer>("Ivan", marks);
		
		st.removeMarkByIndex(1);
		var studentMarks = st.getMarks();
		assertTrue(studentMarks.size() == 2);
		assertTrue(studentMarks.get(0).equals(5));
		assertTrue(studentMarks.get(1).equals(7));
	}

	@Test
	public void testAddMark() {
		Student<Integer> st = new Student<Integer>();
		st.addMark(5);
		st.addMark(6);
		
		HashSet<Integer> marks = st.getMarksSet();
		assertTrue(marks.size() == 2);
		assertTrue(marks.contains(5));
		assertTrue(marks.contains(6));
	}

	@Test
	public void testGetMark() {
		ArrayList<Integer> marks = new ArrayList<Integer>();
		marks.add(5);
		marks.add(6);
		marks.add(7);
		
		Student<Integer> st = new Student<Integer>("Ivan", marks);
		assertTrue(st.getMark(1).equals(6));
		assertTrue(st.getMark(0).equals(5));
		assertTrue(st.getMark(2).equals(7));
	}

	@Test
	public void testGetMarks() {
		ArrayList<Integer> marks = new ArrayList<Integer>();
		marks.add(5);
		marks.add(6);
		marks.add(7);
		
		Student<Integer> st = new Student<Integer>("Ivan", marks);
		assertEquals(st.getMarks(), marks);
	}

	@Test
	public void testCancelLastOperation() throws InvalidMarkException {
		Student<Integer> st = new Student<Integer>();
		st.addMarkByIndex(5, 0);
		st.addMarkByIndex(6, 1);
		
		st.cancelLastOperation();
		var marks = st.getMarks();
		assertTrue(marks.size() == 1);
		assertTrue(marks.get(0) == 5);
	}

	@Test
	public void testToString() throws InvalidMarkException {
		Student<Integer> st = new Student<Integer>("Ivan");
		st.addMarkByIndex(5, 0);
		st.addMarkByIndex(6, 1);
		
		String studentStringRepr = st.toString();
		assertEquals(studentStringRepr, "Ivan: [5, 6]");
	}

}
