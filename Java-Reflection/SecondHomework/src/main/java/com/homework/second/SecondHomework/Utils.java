package com.homework.second.SecondHomework;

import java.util.HashMap;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * Utils class, contains only one method cache()
 *
 */

class SomeClass {
	private static int runCalls = 0;
	
	public static int getRunCalls() {
		return runCalls;
	}
	
	@Cache
	public void run() {
		++runCalls;
	}
}

class Derived extends SomeClass {
	private String name;
	private static int methodCalls = 0;
	private static int nameGetterCalls = 0;
	
	public static int getMethodCalls() {
		return methodCalls;
	}
	
	public static int getNameGetterCalls() {
		return nameGetterCalls;
	}
	
	@Setter
	public void setName(String name) {
		this.name = name;
	}
	
	@Cache
	public String getName() {
		++nameGetterCalls;
		return name;
	}
	
	@Cache
	public void method() {
		++methodCalls;
	}
}

public class Utils 	{
	public static <T> T cache(T t) {
	    HashMap<String, Object> cachedResults = new HashMap<String, Object>();
	    T copy_t = t;
	    
	    var clazz = t.getClass();
	    MethodInterceptor handler = (obj, method, args, proxy) -> {
	    	if (method.getAnnotation(Cache.class) != null &&
	    		cachedResults.size() > 0 &&
	    		cachedResults.containsKey(method.getName())) {
	    		
		        return cachedResults.get(method.getName()); 
	    	}
	    	
	    	if (method.getAnnotation(Setter.class) != null) {
	    		cachedResults.clear();
	    	}
	    	
	    	var return_value = proxy.invoke(copy_t, args);
	        cachedResults.put(method.getName(), return_value);
	        return return_value; 
	    };	      
	    return (T) Enhancer.create(clazz, handler);
	  }
	
    public static void main(String[] args) {
    	Derived derived = Utils.cache(new Derived());
    	derived.setName("Lalala");
    	derived.getName();
    	derived.getName();
    	derived.getName();
    	String name = derived.getName();
    	System.out.println("Name: " + name + " Name getter calls: " + Derived.getNameGetterCalls());
    	derived.run();
    	derived.run();
    	derived.run();
    	derived.run();
    	System.out.println("Run calls: " + SomeClass.getRunCalls());
    	derived.method();
    	derived.method();
    	derived.method();
    	System.out.println("Method calls: " + Derived.getMethodCalls());
    }
}
