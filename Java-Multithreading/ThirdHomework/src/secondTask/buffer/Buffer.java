package secondTask.buffer;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer<T> {
	private LinkedList<T> queue;
	private ReentrantLock locker;
	
	public Buffer() {
		queue = new LinkedList<T>();
		locker = new ReentrantLock();
	}
	
	public void put(T t) {
		locker.lock();
		queue.add(t);
		locker.unlock();
	}
	
	public T get() {
		locker.lock();
		T first = queue.poll();
		locker.unlock();
		return first;
	}
	
	public int size() {
		return queue.size();
	}
}
