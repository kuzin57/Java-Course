package com.homework.second.SecondHomework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

interface ITestInterface {
	public void firstMethodInterface();
	
	public void secondMethodInterface(String s);
	
	public String thirdMethodInterface();
}

class VeryBase {
	private static int methodCalls = 0;
	
	public static int getMethodCalls() {
		return methodCalls;
	}
	
	@Setter
	public void flush() {
		methodCalls = 0;
	}
	
	@Setter
	public void falseFlush() {}

	@Cache
	public void run() {
		System.out.println("VeryBase");
	}
	
	@Cache
	public void method() {
		++methodCalls;
		System.out.println("VeryBase.method");
	}
}

class Base extends VeryBase {
	private static int runCalls = 0;
	
	public static int getRunCalls() {
		return runCalls;
	}
	
	@Override
	public void run() {
		++runCalls;
		System.out.println("Base");
	}
	
	@Override
	@Setter
	public void flush() {
		runCalls = 0;
	}
	
	@Override
	public void falseFlush() {}
}

class TestClass extends Base implements ITestInterface {
	private String firstField;
	private int secondField;
	
	private static int firstMethodCalls = 0;
	private static int secondMethodCalls = 0;
	private static int thirdMethodCalls = 0;
	
	public static int getFirstMethodCalls() {
		return firstMethodCalls;
	}
	
	public static int getSecondMethodCalls() {
		return secondMethodCalls;
	}
	
	public static int getThirdMethodCalls() {
		return thirdMethodCalls;
	}
	
	public static void flushCallsCounters() {
		firstMethodCalls = 0;
		secondMethodCalls = 0;
		thirdMethodCalls = 0;
		getterSecondFieldCallsCounter = 0;
	}
	
	@Cache
	@Override
	public void firstMethodInterface() {
		++firstMethodCalls;
		System.out.println("first method interface");
	}
	
	@Setter
	@Override
	public void secondMethodInterface(String s) {
		++secondMethodCalls;
		this.firstField = s;
	}
	
	@Cache
	@Override
	public String thirdMethodInterface() {
		++thirdMethodCalls;
		return firstField;
	}
	
	private static int getterSecondFieldCallsCounter = 0;
	
	public static int getSecondFieldGetterCallsCounter() {
		return getterSecondFieldCallsCounter;
	}
	
	@Setter
	public void setSecondField(int newValue) {
		secondField = newValue;
	}
	
	@Cache
	public int getSecondField() {
		++getterSecondFieldCallsCounter;
		return secondField;
	}
}

/**
 * Unit test for simple App.
 */
public class UtilsTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testInterfaceMethods() {
        ITestInterface instance = Utils.cache(new TestClass());
        instance.firstMethodInterface();
        instance.firstMethodInterface();
        assertEquals(TestClass.getFirstMethodCalls(), 1);
        TestClass.flushCallsCounters();
        instance.secondMethodInterface("test");
        instance.firstMethodInterface();
        instance.thirdMethodInterface();
        String res = instance.thirdMethodInterface();
        assertTrue("test".equals(res));
        assertEquals(TestClass.getFirstMethodCalls(), 1);
        assertEquals(TestClass.getThirdMethodCalls(), 1);
    }
    
    @Test
    public void testInnerMethods() {
    	TestClass instance = Utils.cache(new TestClass());
    	instance.setSecondField(5);
    	instance.getSecondField();
    	instance.getSecondField();
    	instance.getSecondField();
    	int res = instance.getSecondField();
    	assertEquals(res, 5);
    	assertEquals(TestClass.getSecondFieldGetterCallsCounter(), 1);
    }
    
    @Test
    public void testInheretance() {
    	TestClass instance = Utils.cache(new TestClass());
    	instance.run();
    	instance.run();
    	instance.run();
    	instance.run();
    	assertEquals(Base.getRunCalls(), 4);
    	instance.method();
    	instance.method();
    	instance.flush();
    	instance.method();
    	instance.method();
    	assertEquals(VeryBase.getMethodCalls(), 2);
    	instance.flush();
    	instance.run();
    	instance.run();
    	instance.run();
    	instance.falseFlush();
    	instance.run();
    	instance.run();
    	assertEquals(Base.getRunCalls(), 5);
    }
}
