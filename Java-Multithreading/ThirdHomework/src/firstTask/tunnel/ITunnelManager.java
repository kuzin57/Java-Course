package firstTask.tunnel;

import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

import firstTask.ship.Ship;
import firstTask.ship.ShipType;

import java.util.concurrent.Executors;

public interface ITunnelManager {
	public boolean put(Ship ship);
	public int putMany(ArrayList<Ship> ships);
	public Ship take(ShipType type);
	public boolean empty();
}

class BlockingTunnelManager implements ITunnelManager {
	private LinkedList<Ship> tunnel; //guarded by tunnelLocker
	private ConcurrentHashMap<ShipType, LinkedList<Ship>> ready; // guarded by readyLockers
	
	private ReentrantLock tunnelLocker;
	private ConcurrentHashMap<ShipType, ReentrantLock> readyLockers;

	private ExecutorService queuePutter;
	private static final int capacity = 5;
	
	public BlockingTunnelManager(ShipType[] shipTypes) {
		tunnel = new LinkedList<Ship>();
		
		ready = new ConcurrentHashMap<ShipType, LinkedList<Ship>>();
		readyLockers = new ConcurrentHashMap<ShipType, ReentrantLock>();
				
		tunnelLocker = new ReentrantLock();
		queuePutter = Executors.newFixedThreadPool(1);
	}
	
	public Ship take(ShipType type) {
		if (!readyLockers.containsKey(type)) 
			return null;

		ReentrantLock locker = readyLockers.get(type);
		
		locker.lock();
		if (ready.size() == 0) {
			locker.unlock();
			return null;
		}
		
		Ship readyShip = null;
		try {
			LinkedList<Ship> queue = ready.get(type);
			if (queue == null) {
				locker.unlock();
				return null;
			}
			
			readyShip = queue.poll();
		} catch (NoSuchElementException e) {}
		locker.unlock();
		return readyShip;
	}
	
	private boolean putOne(Ship ship) {
		if (!ready.containsKey(ship.getType())) {
			ready.put(ship.getType(), new LinkedList<Ship>());
			readyLockers.put(ship.getType(), new ReentrantLock());
		}
		
		
		tunnelLocker.lock();
		if(tunnel.size() == capacity) {
			tunnelLocker.unlock();
			return false;
		}

		tunnel.add(ship);
		tunnelLocker.unlock();
		return true;
	}
	
	private void syncWithReady() {
		queuePutter.execute(() -> {
			tunnelLocker.lock();
			try {
				// ships go through tunnel
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.getLogger(this.getClass().getName()).log(Level.ERROR, e.getMessage());
			}
			
			while (tunnel.size() > 0) {
				Ship ship = tunnel.poll();
				
				ReentrantLock readyLocker = readyLockers.get(ship.getType());
				
				readyLocker.lock();
				ready.get(ship.getType()).add(ship);
				readyLocker.unlock();
			}
			tunnelLocker.unlock();
		});
	}
	
	public boolean put(Ship ship) {
		if (!putOne(ship))
			return false;
	
		syncWithReady();
		return true;
	}
	
	@Override
	public boolean empty() {
		boolean readyEmpty = true;
		for (LinkedList<Ship> queue : ready.values()) {
			readyEmpty &= queue.isEmpty();
		}
		return tunnel.size() == 0 && readyEmpty;
	}
	
	public int putMany(ArrayList<Ship> ships) {
		int i;
		for (i = 0; i < ships.size(); i++)
			if (!putOne(ships.get(i)))
				break;
		
		syncWithReady();
		return i;
	}
}